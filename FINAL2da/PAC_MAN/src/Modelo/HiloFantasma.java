/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */

/** Hilo encargado de controlar el movimiento independiente
 * de un fantasma dentro del juego. */
public class HiloFantasma implements Runnable {

    private Fantasma fantasma;
    private PacMancito pacman;
    private Laberinto laberinto;
    private boolean activo; // Indica si el hilo continúa ejecutándose

    public HiloFantasma(Fantasma fantasma, PacMancito pacman, Laberinto laberinto) {
        this.fantasma = fantasma;
        this.pacman = pacman;
        this.laberinto = laberinto;
        activo = true;
    }

    //Detiene la ejecución del hilo, si es falso
    public void detener() {
        activo = false;
    }

 //Ejecuta el movimiento del fantasma mientras el hilo permanezca activo.
    @Override
    public void run() {

        while (activo) {
            fantasma.perseguirPacMan(laberinto,pacman);

            try {
                Thread.sleep(fantasma.getVelocidad());

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        }
    }
}
