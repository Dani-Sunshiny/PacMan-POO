/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class HiloPacMancito extends Thread {

    private Juego juego;
    private boolean activo = true;

    public HiloPacMancito(Juego juego) {
        this.juego = juego;
    }

@Override
public void run() {

    System.out.println("Hilo Pacman iniciado");

    while(activo) {

        try {
            
            Thread.sleep(220);

            if(juego.getEstado() == EstadoJuego.JUGANDO) {

                juego.actualizarPacman();
            }

        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            break;
        }
    }
}

    public void detener() {

        activo = false;
        interrupt();
    }
}