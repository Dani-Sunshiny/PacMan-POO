/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */
public abstract class Fantasma extends Entidad {
    
   protected int velocidad;
   protected MoviDireccion movidireccion;
   protected int xInicial;
   protected int yInicial;
   protected String colorfantasma;

    public Fantasma(int velocidad, MoviDireccion movidireccion, int x, int y,String colorfantasma) {
        super(x, y);
        this.velocidad = velocidad;
        this.movidireccion = movidireccion;
        this.xInicial = x;
        this.yInicial = y;
        this.colorfantasma= colorfantasma;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public String getColorfantasma() {
        return colorfantasma;
    }

    public void setColorfantasma(String colorfantasma) {
        this.colorfantasma = colorfantasma;
    }

    public MoviDireccion getMovidireccion() {
        return movidireccion;
    }

    public void setMovidireccion(MoviDireccion movidireccion) {
        this.movidireccion = movidireccion;
    }

    public int getxInicial() {
        return xInicial;
    }

    public int getyInicial() {
        return yInicial;
    }
    
    public void setPosicionInicial(
        int xInicial,
        int yInicial) {

    this.xInicial = xInicial;
    this.yInicial = yInicial;
}
    
    public boolean moverA(int nuevoX, int nuevoY, Laberinto laberinto) {

        if (laberinto.esMovimientoValido(nuevoX, nuevoY)) {
            this.x = nuevoX;
            this.y = nuevoY;
            return true;
        }

        return false;
    }
    public void reiniciarPosicion() {

        this.x = xInicial;
        this.y = yInicial;
    }

    public abstract void perseguirPacMan(Laberinto laberinto,PacMancito pacman);
   
}
