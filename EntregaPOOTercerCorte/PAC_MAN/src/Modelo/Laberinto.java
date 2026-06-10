/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */

// Es el mapa del juego, maneja las paredes y valida si un movimiento es permitido.
 
public class Laberinto {
    private char[][] mapa;
// Matriz que almacena la estructura del mapa
    public Laberinto(char[][] mapa) {
        this.mapa = mapa;
    }

    public char[][] getMapa() {
        return this.mapa;
    }

 //Verifica si una posición puede ser ocupada por una entidad,por posisicones (x,y) 
 //y nos dice sí la casilla es valida para el movimeinto

    public boolean esMovimientoValido(int x, int y) {

        if (x < 0 || y < 0) {
            return false;
        }

        if (y >= mapa.length) {
            return false;
        }

        if (x >= mapa[0].length) {
            return false;
        }

        return mapa[y][x] != '#';
    }

    public int getFilas() {
        return mapa.length;
    }

    public int getColumnas() {
        return mapa[0].length;
    }
    
}
