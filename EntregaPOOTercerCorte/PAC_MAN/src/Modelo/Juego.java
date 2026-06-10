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
import java.util.Random;
import java.util.Collections;

public class Juego {

    private PacMancito pacman;
    private List<Fantasma> fantasmas;// Lista de fantasmas presentes en el juego
    private List<Pellet> pellets; // Lista de puntos disponibles
    private List<PowerUp> powerUps; // Lista de potenciadores disponibles
    private Nivel nivelActual; // Nivel que está jugando actualmente
    private EstadoJuego estado; // Estado actual del juego
    private int puntaje; // Puntaje acumulado
    private HiloMusica hiloMusica;// Thread de la musica
    private HiloPotenciadores hiloPowerUp;// Thread del powerup
    private HiloPellets hiloPellets;
    private HiloFrutas hiloFrutas;
    private HiloPacMancito hiloPacman;
    public Juego() {
    // COMPOSICIÓN: Juego es responsable de instanciar sus partes
        this.fantasmas =Collections.synchronizedList(new ArrayList<>());

        this.pellets = Collections.synchronizedList(new ArrayList<>());

        this.powerUps =Collections.synchronizedList(new ArrayList<>());
        this.puntaje = 0;
        this.estado = EstadoJuego.MENU_PRINCIPAL;
        
    }

    public void iniciarJuego(int numeroNivel) {
        
        fantasmas.clear();
        pellets.clear();
        powerUps.clear();
        // Cargamos el nivel usando la fábrica
        if (numeroNivel == 1) {
            this.nivelActual = FabricaNiveles.crearNivel1();
        } else {
            this.nivelActual = FabricaNiveles.crearNivel2();
        }
        
       // Le damos una dirección inicial en una posición fija (o definida por el nivel)
        this.pacman = new PacMancito(MoviDireccion.ESTE, nivelActual.getPacmanStartX(),  nivelActual.getPacmanStartY());

        // Poblar el nivel con elementos en posiciones dinámicas
        generarElementosDelNivel();
        this.estado = EstadoJuego.JUGANDO;
        hiloMusica = new HiloMusica();
        hiloMusica.start();
        hiloPellets = new HiloPellets(this);
        hiloPellets.start();
        hiloFrutas = new HiloFrutas(this);
        hiloFrutas.start();
        hiloPacman = new HiloPacMancito(this);
        hiloPacman.start();
        
        
    }
// Dentro de la clase Juego.java
    public void generarElementosDelNivel() {
        Laberinto laberinto = nivelActual.getLaberinto(); // Asumiendo que Nivel tiene un Laberinto
        char[][] mapa = laberinto.getMapa();

        for (int y = 0; y < laberinto.getFilas(); y++) {
            for (int x = 0; x < laberinto.getColumnas(); x++) {
                char celda = mapa[y][x];

                switch (celda) {
                    case '.':
                        pellets.add(new Pellet(x, y));
                        break;
                         // Aquí decidimos qué forma tiene el power up que crea
                    case 'S':
                        powerUps.add(new PowerUp(x, y, TipoDePotenciador.FRESA));
                        break;
                    case 'A':
                        powerUps.add(new PowerUp(x, y, TipoDePotenciador.MANZANA));
                        break;
                    case 'O':
                        powerUps.add(new PowerUp(x, y, TipoDePotenciador.NARANJA));
                        break;
                    case 'W':
                        powerUps.add(new PowerUp(x, y, TipoDePotenciador.SANDIA));
                        break;
                    // Aquí decidimos qué fantasma crear
                    case 'F':                       
                        fantasmas.add(new FantasmaPerseguidor(1, MoviDireccion.NORTE, x, y, "ROJO"));
                        break;
                    case 'G':
                        fantasmas.add(new FantasmaPerseguidor(1, MoviDireccion.NORTE, x, y, "VERDE"));
                        break;
                    case 'B':
                        fantasmas.add(new FantasmaAleatorio(1, MoviDireccion.NORTE, x, y, "AZUL"));
                        break;
                    case 'R':
                        fantasmas.add(new FantasmaAleatorio(1, MoviDireccion.NORTE, x, y, "NARANJA"));
                        break;
                }
            }
        }
    } 

    

    public void pausarJuego() {
        hiloMusica.pausar();
        estado = EstadoJuego.PAUSADO;
    }

    public void reanudarJuego() {
        hiloMusica.reanudar();
        if (this.estado == EstadoJuego.PAUSADO) {
            this.estado = EstadoJuego.JUGANDO;
        }
    }
    

 // revisa si Pac-Man consumió algún pellet. Sí lo hizo incrementa el puntaje.

    public synchronized void verificarPellets() {
        if (this.estado != EstadoJuego.JUGANDO) return;
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
    public synchronized void actualizarPacman() {

        if(estado != EstadoJuego.JUGANDO)
            return;

        pacman.moverse(
        nivelActual.getLaberinto());

        verificarPellets();

        verificarPowerUps();

        verificarColisionFantasma();

        verificarVictoria();
    }
 //Verifica si Pac-Man consumió un potenciador
    public synchronized boolean verificarPowerUps() {
        if (this.estado != EstadoJuego.JUGANDO) return false;
        // Iteramos de atrás hacia adelante para procesar la lista con seguridad
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);

            // Si Pac-Man toca un PowerUp
            if (powerUp.getX() == pacman.getX() && powerUp.getY() == pacman.getY()) {
                puntaje += 40;
                powerUps.remove(i);
                consumirPowerUp();
                // Eliminamos el elemento de forma segura sin afectar los que faltan por evaluar
                 
                return true; // Indicamos que SÍ se comió un potenciador
        }
    }
            return false;
    }
    public synchronized void consumirPowerUp() {
        pacman.setInvencible(true);

        if(hiloPowerUp != null && hiloPowerUp.isAlive()) {
            hiloPowerUp.interrupt();
        }

        hiloPowerUp = new HiloPotenciadores(pacman);
        hiloPowerUp.start();
    }
    
    public synchronized void generarPowerUpAleatorio() {

    if(estado != EstadoJuego.JUGANDO) {
        return;
    }

    Random random = new Random();

    Laberinto lab = nivelActual.getLaberinto();
    char[][] mapa = lab.getMapa();

    int x;
    int y;

    do {

        x = random.nextInt(lab.getColumnas());
        y = random.nextInt(lab.getFilas());

    } while(mapa[y][x] == '#');

    TipoDePotenciador tipo =
            TipoDePotenciador.values()
            [random.nextInt(TipoDePotenciador.values().length)];

    powerUps.add(new PowerUp(x, y, tipo));

    System.out.println("Nueva fruta creada en: "
            + x + "," + y);
    }
    public synchronized void verificarColisionFantasma() {
        if (this.estado != EstadoJuego.JUGANDO) return;
        for (Fantasma fantasma : fantasmas) {
        if (fantasma.getX() == pacman.getX() && fantasma.getY() == pacman.getY()) {
            
            // --- NUEVA LÓGICA DE INVENCIBILIDAD ---
            if (pacman.isInvencible()) {
                // Si es invencible, el fantasma vuelve a su base y pacman gana 100 puntos
                puntaje+=100;
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
                    detenerHilos(); 
                }
            }
        }
    }
}


    public void verificarVictoria() {
         if (this.estado != EstadoJuego.JUGANDO) return;
        // Si la lista de pellets está vacía, el jugador ha limpiado el nivel
        if (pellets.isEmpty()&&powerUps.isEmpty()) {
            if (nivelActual.getNumero() == 1) {
                // Si estaba en el nivel 1, cargamos el mensaje de nicel
                 this.estado = EstadoJuego.NIVEL_COMPLETADO;
                 detenerHilos(); 
            } else {
               // Si ya no hay niveles, declaramos la victoria final
               estado = EstadoJuego.VICTORIA;
               detenerHilos(); 
            }
        }
    }
    public void agregarFantasma(Fantasma fantasma) {
        fantasmas.add(fantasma);
    }

    public void agregarPellet(Pellet pellet) {
        pellets.add(pellet);
    }
    private boolean existePellet(int x, int y) {

    for (Pellet p : pellets) {

        if (p.getX() == x && p.getY() == y) {
            return true;
        }
    }

    return false;
    }
    private boolean existePowerUp(int x, int y) {

    for (PowerUp p : powerUps) {

        if (p.getX() == x && p.getY() == y) {
            return true;
        }
    }

    return false;
    }
    public synchronized void generarPelletEspecial() {

    Random random = new Random();

    Laberinto lab = nivelActual.getLaberinto();
    char[][] mapa = lab.getMapa();

    int x;
    int y;

    do {

        x = random.nextInt(lab.getColumnas());
        y = random.nextInt(lab.getFilas());

    } while (
            mapa[y][x] == '#'
            || existePellet(x, y)
            || existePowerUp(x, y)
    );

    pellets.add(new Pellet(x, y));

    System.out.println("Pellet especial generado en (" + x + "," + y + ")");
    }
    public void agregarPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }
    public void detenerHilos() {

    if(hiloFrutas != null) {
        hiloFrutas.detener();
    }

    if(hiloMusica != null) {
        hiloMusica.detener();
    }
    if(hiloPellets != null) {
        hiloPellets.detener();
    }
    if(hiloPacman != null) {
    hiloPacman.detener();
    }
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

    public void setEstado(EstadoJuego estado) {
        this.estado = estado;
    }    

    public int getPuntaje() {
        return puntaje;
    }
}