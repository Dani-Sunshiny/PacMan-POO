/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
public class HiloFrutas extends Thread {

    private Juego juego;
    private boolean activo = true;

    public HiloFrutas(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void run() {

        while(activo) {

            try {

                Thread.sleep(30000);

                if(activo) {
                    juego.generarPowerUpAleatorio();
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