/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */
public class FantasmaPerseguidor extends Fantasma {

    public FantasmaPerseguidor(int velocidad, MoviDireccion movidireccion, int x, int y, String colorfantasma) {
        super(velocidad, movidireccion, x, y,colorfantasma);
    }
    
    @Override
    public void perseguirPacMan(Laberinto laberinto, PacMancito pacman) {

        int distanciaX = pacman.getX() - x;// calcula la distancia en x respecto a PACMAN

        int distanciaY = pacman.getY() - y;// calcula la distancia en y respecto a PACMAN
        
        /**
 * 
 * Mira si el movimiento principal es horizontal o vertica, compara las distancias absolutas,
 * a ver cual es mayor
 */

        boolean moverHorizontal = Math.abs(distanciaX)>= Math.abs(distanciaY);

        if (moverHorizontal) {
            
            // esto es parra las animaciones de las imagenes de los personajes
             if (distanciaX > 0) {
                movidireccion = MoviDireccion.ESTE;
            } else if (distanciaX < 0) {
                movidireccion = MoviDireccion.OESTE;
            }
    

            // Intenta avanzar en horizontal
            if (!moverA(x + Integer.signum(distanciaX), y, laberinto)) {
            // Si choca, intenta avanzar en vertical
            moverA(x, y + Integer.signum(distanciaY), laberinto);
            }
        } else {
            
            // esto es parra las animaciones de las imagenes de los personajes
            if (distanciaY > 0) {
                movidireccion = MoviDireccion.SUR;
            } else if (distanciaY < 0) {
                movidireccion = MoviDireccion.NORTE;
            }

            // Intenta avanzar en vertical
            if (!moverA(x, y + Integer.signum(distanciaY), laberinto)) {
            // Si choca, intenta avanzar en horizontal
            moverA(x + Integer.signum(distanciaX), y, laberinto);
            }
        }
    }

    
}
