/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */


//Maneja el estado de la partida, los niveles, puntaje, colisiones y reglas del juego.
import java.util.ArrayList;
import java.util.List;

public class Juego {

    private PacMancito pacman;
    private List<Fantasma> fantasmas;// Lista de fantasmas presentes en el juego
    private List<Pellet> pellets; // Lista de puntos disponibles
    private List<PowerUp> powerUps; // Lista de potenciadores disponibles
    private Nivel nivelActual; // Nivel que está jugando actualmente
    private EstadoJuego estado; // Estado actual del juego
    private int puntaje; // Puntaje acumulado

    public Juego() {

        fantasmas = new ArrayList<>();
        pellets = new ArrayList<>();
        powerUps = new ArrayList<>();
        puntaje = 0;
        estado = EstadoJuego.MENU;
    }

    public void iniciarJuego() {
        estado = EstadoJuego.JUGANDO;
        nivelActual = FabricaNiveles.crearNivel1();
        // Le das una dirección inicial (ej. ESTE) y una posición X, Y (ej. 1, 1)
        this.pacman = new PacMancito(MoviDireccion.ESTE, 1, 1);
    }

    public void pausarJuego() {
        estado = EstadoJuego.PAUSADO;
    }

    public void reanudarJuego() {
        estado = EstadoJuego.JUGANDO;
    }

 // revisa si Pac-Man consumió algún pellet. Sí lo hizo incrementa el puntaje.
    public void verificarPellets() {
        // Recorremos la lista desde el último índice hasta el 0
        for (int i = pellets.size() - 1; i >= 0; i--) {
            Pellet pellet = pellets.get(i);

            // Verificamos colisión
            if (pellet.getX() == pacman.getX() && pellet.getY() == pacman.getY()) {
                puntaje += 10;
                // Eliminamos por índice: al ser hacia atrás, 
                // los índices de los elementos anteriores (i-1) no cambian
                pellets.remove(i); 
            }
        }
    }
    
 //Verifica si Pac-Man consumió un potenciador
    public boolean verificarPowerUps() {
        // Iteramos de atrás hacia adelante para procesar la lista con seguridad
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);

            // Si Pac-Man toca un PowerUp
            if (powerUp.getX() == pacman.getX() && powerUp.getY() == pacman.getY()) {
                pacman.setInvencible(true);
                // Eliminamos el elemento de forma segura sin afectar los que faltan por evaluar
                powerUps.remove(i); 
                 return true; // Indicamos que SÍ se comió un potenciador
        }
    }
            return false;
    }

    public void verificarColisionFantasma() {
        
        for (Fantasma fantasma : fantasmas) {
        if (fantasma.getX() == pacman.getX() && fantasma.getY() == pacman.getY()) {
            
            // --- NUEVA LÓGICA DE INVENCIBILIDAD ---
            if (pacman.isInvencible()) {
                // Si es invencible, el fantasma vuelve a su base
                fantasma.reiniciarPosicion();
                System.out.println("¡Pac-Man se comió al fantasma!");
            } else {
                // --- LÓGICA ORIGINAL ---
                pacman.perderVida();
                pacman.reiniciarPosicion();

                for (Fantasma f : fantasmas) {
                    f.reiniciarPosicion();
                }

                if (pacman.getVidas() <= 0) {
                    estado = EstadoJuego.GAME_OVER;
                }
            }
        }
    }
}
        

    public void verificarVictoria() {
        // Si la lista de pellets está vacía, el jugador ha limpiado el nivel
        if (pellets.isEmpty()) {
            if (nivelActual.getNumero() == 1) {
                // Si estaba en el nivel 1, cargamos el nivel 2
                nivelActual = FabricaNiveles.crearNivel2();
            } else {
               // Si ya no hay niveles, declaramos la victoria final
               estado = EstadoJuego.VICTORIA;
            }
        }
    }
    public void agregarFantasma(Fantasma fantasma) {
        fantasmas.add(fantasma);
    }

    public void agregarPellet(Pellet pellet) {
        pellets.add(pellet);
    }

    public void agregarPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public PacMancito getPacman() {
        return pacman;
    }

    public void setPacman(PacMancito pacman) {
        this.pacman = pacman;
    }

    public List<Fantasma> getFantasmas() {
        return fantasmas;
    }

    public List<Pellet> getPellets() {
        return pellets;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public Nivel getNivelActual() {
        return nivelActual;
    }

    public EstadoJuego getEstado() {
        return estado;
    }

    public int getPuntaje() {
        return puntaje;
    }
}