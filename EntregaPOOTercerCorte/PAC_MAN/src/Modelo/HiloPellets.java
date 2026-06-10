/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author danie
 */

public class HiloPellets extends Thread {

    private Juego juego;
    private boolean activo = true;

    public HiloPellets(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void run() {

        while (activo) {

            try {

                Thread.sleep(10000); // cada 10 segundos

                if (juego.getEstado() == EstadoJuego.JUGANDO) {
                    juego.generarPelletEspecial();
                }

            } catch (InterruptedException e) {
                activo = false;
            }
        }
    }

    public void detener() {
        activo = false;
        interrupt();
    }
}