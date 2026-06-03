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

    public Nivel(int numero, Laberinto laberinto) {
        this.numero = numero;
        this.laberinto = laberinto;
    }

    public int getNumero() {
        return numero;
    }

    public Laberinto getLaberinto() {
        return laberinto;
    }
}
