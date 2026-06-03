/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.VistaPacMan;
import javafx.scene.input.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Controlador: Coordina la lógica del juego y comunica la Vista con el Modelo.
 */
public class ControladorJuego {
    private Juego juego;
    private VistaPacMan vista;

    public ControladorJuego() {
        this.juego = new Juego(); // El controlador crea su propio modelo
        inicializarJuego();
    }

    //  el controlador "adopta" a la vista
    public void setVista(VistaPacMan vista) {
        this.vista = vista;
    }
    

    // Inicializa el estado inicial del modelo
    private void inicializarJuego() {
        juego.iniciarJuego();
        //Funcionan con x y y, en cambio el laberinto se construye y entiente por y y x porque javafx funciona asi
        juego.setPacman(new PacMancito(MoviDireccion.ESTE, 1,1 ));
        
        juego.getFantasmas().add(new FantasmaAleatorio(1, MoviDireccion.OESTE, 9, 5));
        juego.getFantasmas().add(new FantasmaPerseguidor(1, MoviDireccion.ESTE, 17, 9));
        
        //Pone los puntos en el mapa donde haya puntos
        char[][] mapa = juego.getNivelActual().getLaberinto().getMapa();
            for (int y = 0; y <  mapa.length; y++) {
               for (int x = 0; x < mapa[y].length; x++) {
                   if (mapa[y][x] == '.') {
                       juego.getPellets().add(new Pellet(x, y));
                   }
               }
           }
       
        
        juego.getPowerUps().add(new PowerUp(6, 8, TipoDePotenciador.INVENCIBILIDAD));
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

    public List<int[]> getPosicionesPowerUps() {
        return juego.getPowerUps().stream().map(pu -> new int[]{pu.getX(), pu.getY()}).collect(Collectors.toList());
    }
    
    public String getEstadoJuegoTexto() {
    // Retorna el nombre del estado directamente
    return juego.getEstado().name(); 
}

    /**
     * Ciclo principal de ejecución (llamado por el AnimationTimer de la Vista).
     * Aquí ocurre toda la lógica de movimiento y colisiones.
     */
    public void ejecutarCiclo() {
        // Validación de seguridad extra
        if (juego == null || juego.getEstado() != EstadoJuego.JUGANDO || juego.getNivelActual() == null) {
            return;
        }

        // 1. Mover PacMan
        if (juego.getPacman() != null) {
            juego.getPacman().moverse(juego.getNivelActual().getLaberinto());
        }

        // 2. Mover Fantasmas
        for (Fantasma f : juego.getFantasmas()) {
            f.perseguirPacMan(juego.getNivelActual().getLaberinto(), juego.getPacman());
        }
        // Verificamos si se consumió un potenciador
        if (juego.verificarPowerUps()) {
        iniciarTemporizadorInvencibilidad(5); // Duración de 5 segundos
         }

        // 3. Verificar reglas de juego
        juego.verificarPellets();
        juego.verificarPowerUps();
        juego.verificarColisionFantasma();
        juego.verificarVictoria();
    }

/**
     * Procesa las entradas del teclado desde la Vista.
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
            default:
                break;
        }
    }

    // --- Métodos de Acción para los Menús ---

    public void iniciarJuego() {
        juego.iniciarJuego();
    }

    public void pausarJuego() {
        juego.pausarJuego();
    }

    public void reanudarJuego() {
        juego.reanudarJuego();
    }
    public void reiniciarJuego() {
    this.juego = new Juego(); // Creamos una nueva instancia limpia
    // Aquí podrías volver a llamar a inicializarJuego() si necesitas poner los fantasmas de nuevo
    inicializarJuego(); 
}

    // --- Getter necesario para que la Vista acceda al Modelo ---
// El dibujo sigue necesitando el objeto juego, pero ahora el controlador es el intermediario
    public Juego getJuego() {
        return this.juego;
    }
    
    /**Inicia un temporizador para controlar cuánto tiempo dura la invencibilidad.
    * Al terminar el tiempo, Pac-Man vuelve a ser vulnerable.*/
    private void iniciarTemporizadorInvencibilidad(int segundos) {
       // 1. Creamos el temporizador con la duración deseada (en segundos).
       // Usamos PauseTransition de JavaFX porque no congela el juego mientras espera.
       PauseTransition pausa = new PauseTransition(Duration.seconds(segundos));

       // 2. Definimos qué pasará al terminar el tiempo.
       // Cuando el tiempo llega a cero, el evento "OnFinished" ejecuta este código:
       pausa.setOnFinished(e -> {
           // Le pedimos al modelo (juego/pacman) que desactive la invencibilidad.
           juego.getPacman().setInvencible(false);
       });

       // 3. ¡Arrancamos el reloj! El juego sigue corriendo mientras el temporizador cuenta.
       pausa.play();
   }
}

