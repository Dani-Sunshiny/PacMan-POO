/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */
// Crea un nivel del juego, cada nivel contiene un laberinto diferente.
public class Nivel {
    // Número de id del nivel y se asocia al Laberinto
    private int numero;
    private Laberinto laberinto;
    // Agregamos puntos de inicio para composición
    private int pacmanStartX, pacmanStartY;

    public Nivel(int numero, Laberinto laberinto, int px, int py) {
        this.numero = numero;
        this.laberinto = laberinto;
        this.pacmanStartX = px;
        this.pacmanStartY = py;
    }

    public int getNumero() {
        return numero;
    }

    public Laberinto getLaberinto() {
        return laberinto;
    }

    public int getPacmanStartX() {
        return pacmanStartX;
    }

    public int getPacmanStartY() {
        return pacmanStartY;
    }
    
}
