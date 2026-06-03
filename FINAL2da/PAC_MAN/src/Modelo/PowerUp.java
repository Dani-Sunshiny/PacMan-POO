/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */

//Es el potenciador especial dentro del juego, cuando es consumido por Pac-Man activa un efecto temporal.
 
public class PowerUp extends Entidad {
 // Nos dice el tipo de potenciador que contiene este objeto
    private TipoDePotenciador tipo;

    public PowerUp(int x, int y, TipoDePotenciador tipo) {
        super(x, y);
        this.tipo = tipo;
    }

    public TipoDePotenciador getTipo() {
        return tipo;
    }

    public void setTipo(TipoDePotenciador tipo) {
        this.tipo = tipo;
    }
}
