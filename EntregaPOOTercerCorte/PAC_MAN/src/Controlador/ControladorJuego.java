/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.VistaPacMan;
import javafx.scene.input.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Controlador: Coordina la lógica del juego y comunica la Vista con el Modelo.
 */
public class ControladorJuego {
    private Juego juego;
    private VistaPacMan vista;

    public ControladorJuego() {
        this.juego = new Juego(); // El controlador crea su propio modelo
        
    }

    //  el controlador "adopta" a la vista
    public void setVista(VistaPacMan vista) {
        this.vista = vista;
    }
    public void iniciarJuegoDesdeMenu(int nivel) {
    // 1. Validación: Solo permitimos iniciar si estamos en el menú
    if (juego.getEstado() == EstadoJuego.MENU_PRINCIPAL) {
        
        // 2. Pedimos al modelo que reinicie todo (limpieza de listas, nuevos fantasmas, etc.)
        juego.iniciarJuego(nivel); 
        
        // 3. Opcional: Si necesitas que la vista haga algo extra (como ocultar el menú), lo haces aquí
        System.out.println("Cambiando a modo JUGANDO...");
    }
}
    
// --- PUENTES PARA EL HUD (Labels) ---
    public String getPuntajeTexto() { return "Puntaje: " + juego.getPuntaje(); }
    public String getVidasTexto() { 
        return (juego.getPacman() != null) ? "Vidas: " + juego.getPacman().getVidas() : "Vidas: 0"; 
    }
    public String getNivelTexto() { 
        return (juego.getNivelActual() != null) ? "Nivel: " + juego.getNivelActual().getNumero() : "Nivel: -"; 
    }
    public String getEstadoTexto() { return "Estado: " + juego.getEstado().toString(); }

    // --- PUENTES PARA EL DIBUJO ---
    
    // PACMAN: Solo devolvemos lo necesario para dibujar
    public boolean tienePacman() { return juego.getPacman() != null; }
    public int getPacmanX() { return juego.getPacman().getX(); }
    public int getPacmanY() { return juego.getPacman().getY(); }
    public int getPacmanDireccion() {
    return switch (juego.getPacman().getMovidireccion()) {
        case NORTE -> 0;
        case SUR   -> 1;
        case OESTE -> 2;
        case ESTE  -> 3;
        
    };
   }
    // MAPA: Devolvemos el char[][] 
    public char[][] getMapa() { 
        return (juego.getNivelActual() != null) ? juego.getNivelActual().getLaberinto().getMapa() : null; 
    }

    // LISTAS: En lugar de devolver la lista de objetos (Fantasma, Pellet), 
    // devolvemos una lista de coordenadas (int[]) para que la Vista no importe clases del modelo
    public List<int[]> getPosicionesFantasmas() {
        return juego.getFantasmas().stream().map(f -> new int[]{f.getX(), f.getY()}).collect(Collectors.toList());
    }

    public List<int[]> getPosicionesPellets() {
        return juego.getPellets().stream().map(p -> new int[]{p.getX(), p.getY()}).collect(Collectors.toList());
    }
    
    public String getEstadoJuegoTexto() {
    // Retorna el nombre del estado directamente
    return juego.getEstado().name(); 
    }
    
    //Maneja los datos de los parametros de los power ups y fantasmas para transmitirlos del modelo a la vista
    public List<String[]> getDatosPowerUps() {
        List<String[]> datos= new ArrayList<>();

        for (PowerUp p : juego.getPowerUps()) {

            datos.add(new String[]{
                String.valueOf(p.getX()),
                String.valueOf(p.getY()),
                p.getTipo().name()
            });
        }

        return datos;
    }
    public List<String[]> getDatosFantasmas() {

        List<String[]> datos = new ArrayList<>();

        for(Fantasma f : juego.getFantasmas()) {

            datos.add(
                new String[]{
                    String.valueOf(f.getX()),
                    String.valueOf(f.getY()),
                    f.getColorfantasma(),
                    f.getMovidireccion().name()
                }
            );
        }

        return datos;
    }
    
    public boolean esPacmanInvencible() {
        return juego.getPacman().isInvencible();
    }

    /**
     * Ciclo principal de ejecución (llamado por el AnimationTimer de la Vista).
     * Aquí ocurre toda la lógica de movimiento y colisiones.
     */
    public void ejecutarCiclo() {
        // 1. Validación de seguridad extra
        if (juego == null || juego.getEstado() != EstadoJuego.JUGANDO || juego.getNivelActual() == null) {
            return;
        }


        // 2. Mover Fantasmas
        for (Fantasma f : juego.getFantasmas()) {
            f.perseguirPacMan(juego.getNivelActual().getLaberinto(), juego.getPacman());
        }
        

        // 3. Verificar reglas de juego
        juego.verificarPellets();
        juego.verificarPowerUps();
        juego.verificarColisionFantasma();
        juego.verificarVictoria();
    }

/**
     * Procesa las entradas del teclado desde la Vista para el juego.
     * Soporta tanto Flechas como WASD.
     * @param event
     */
    public void procesarTeclado(KeyEvent event) {
        if (juego.getPacman() == null) return;

        switch (event.getCode()) {
            // Caso WASD
            case W:
            case UP:
                juego.getPacman().setMovidireccion(MoviDireccion.NORTE);
                break;
            case S:
            case DOWN:
                juego.getPacman().setMovidireccion(MoviDireccion.SUR);
                break;
            case A:
            case LEFT:
                juego.getPacman().setMovidireccion(MoviDireccion.OESTE);
                break;
            case D:
            case RIGHT:
                juego.getPacman().setMovidireccion(MoviDireccion.ESTE);
                break;
            case SPACE:// PAUSA = ESPACIO
                pausarJuego1();
                break;            
            case CAPS:// REANUDAR = CAPS LOCK (BLOQ MAYUS)
                vista.ejecutarConRetardo(() ->reanudarJuego());
                break;
            case BACK_SPACE:// REINICIAR = BORRAR (BACKSPACE)
                vista.ejecutarConRetardo(() ->reiniciarJuego());
                break;
            case CONTROL:// VOLVER MENU = CONTROL (CTRL)
                vista.ejecutarConRetardo(() ->volverMenuJuego());
                break;
            case ESCAPE:// SALIR = ESC
                vista.ejecutarConRetardo(() ->System.exit(0));
                break;
            default:
                break;
        }
    }
    //aqui usamos eto para procesar el avance de nivel ENTER
    public void procesarTeclaAvance() {
    if (juego.getEstado() == EstadoJuego.NIVEL_COMPLETADO) {
        int siguienteNivel = juego.getNivelActual().getNumero() + 1;
        // Aquí iniciamos el siguiente nivel
        juego.iniciarJuego(siguienteNivel);
    }
}
    //AQUI METODOS PARA EL ESTADO Y PASAR DE IMAGENES
   public String getEstadoActualParaDibujar() {
        return juego.getEstado().toString(); // Devuelve "MENU", "PAUSADO", etc.
}
    public boolean estamosEnMenuPrincipal() {
    // Aquí es donde el Controlador "traduce" el enum del modelo a algo que la vista entiende
        return juego.getEstado() == EstadoJuego.MENU_PRINCIPAL;
}
    public void estamosPausa() {
    // Aquí es donde el Controlador "traduce" el enum del modelo a algo que la vista entiende
        this.juego.pausarJuego();
        this.juego.setEstado(EstadoJuego.MENU);
}
    public void estamosJugandol() {
    // Aquí es donde el Controlador "traduce" el enum del modelo a algo que la vista entiende
        this.juego.setEstado(EstadoJuego.JUGANDO);
}
    public void estamosAyuda() {
    // Aquí es donde el Controlador "traduce" el enum del modelo a algo que la vista entiende
        this.juego.setEstado(EstadoJuego.AYUDA);
}
    public void estamoCreditos() {
    // Aquí es donde el Controlador "traduce" el enum del modelo a algo que la vista entiende
        this.juego.setEstado(EstadoJuego.CREDITOS);
}
    //PARA REANUDAR Y REINIAR USE LOS METODOS DE ACCION DE MENUS
    public void irAtras() {
    // Si estás en AYUDA o CREDITOS, vuelves al MENU
    if (juego.getEstado() == EstadoJuego.AYUDA || juego.getEstado() == EstadoJuego.CREDITOS || juego.getEstado() == EstadoJuego.MENU) {
        juego.setEstado(EstadoJuego.MENU_PRINCIPAL);
    }
}
    
   

    // --- Métodos de Acción para los Menús ---

public void pausarJuego1() {
         // Verificamos internamente si estamos jugando
     if (juego.getEstado() == EstadoJuego.JUGANDO)  {
         juego.pausarJuego();         // Detiene la lógica (movimiento de fantasmas, etc.)
         this.juego.setEstado(EstadoJuego.MENU); // Cambia el estado para que el dibujo cambie
         System.out.println(" Juego pausado y regresando al menu.");
     } else {
         System.out.println("Controlador: No se puede volver al menu porque no estamos jugando.");
     }
    }

    public void reanudarJuego() {
         // Verificamos internamente si estamos pausados
     if (juego.getEstado() == EstadoJuego.MENU)  {
         juego.reanudarJuego();
         this.juego.setEstado(EstadoJuego.JUGANDO); // Cambia el estado para que el dibujo cambie
         System.out.println(" Juego pausado y regresando al menu.");
     } else {
         System.out.println("Controlador: No se puede volver al menu porque no estamos jugando.");
     }
    }
    public void reiniciarJuego() {
         // Verificamos internamente si estamos pausados
     if (juego.getEstado() == EstadoJuego.MENU|| juego.getEstado() == EstadoJuego.JUGANDO   ||juego.getEstado() == EstadoJuego.GAME_OVER)  {
         this.juego = new Juego(); // Creamos una nueva instancia limpia
         // Aquí podrías volver a llamar a inicializarJuego() si necesitas poner los fantasmas de nuevo
        juego.iniciarJuego(1);
         System.out.println(" Juego pausado y regresando al menu.");
     } else {
         System.out.println("Controlador: No se puede volver al juego porque no estamos jugando.");
     }
    }
  // --- Getter necesario para que la Vista acceda al Modelo ---
// El dibujo sigue necesitando el objeto juego, pero ahora el controlador es el intermediario
    public Juego getJuego() {
        return this.juego;
    }
    public void volverMenuJuego() {
        // Verificamos internamente si estamos jugando
     if (juego.getEstado() == EstadoJuego.JUGANDO || juego.getEstado() == EstadoJuego.PAUSADO)  {
         juego.pausarJuego();         // Detiene la lógica (movimiento de fantasmas, etc.)
         this.juego.setEstado(EstadoJuego.MENU_PRINCIPAL); // Cambia el estado para que el dibujo cambie
         System.out.println("Controlador: Juego pausado y regresando al menu.");
     } else {
         System.out.println("Controlador: No se puede volver al menu porque no estamos jugando.");
     }
    }    

    
}

