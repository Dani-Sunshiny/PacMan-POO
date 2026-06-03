/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;



/**
 *
 * @author User
 */
public class PacMancito extends Entidad implements Posicion {
    
    private int vidas;
    private MoviDireccion movidireccion; //aqui hacemos una asociacion con el Enum
    private boolean invencible;
    private int xInicial;
    private int yInicial;

    public PacMancito(MoviDireccion movidireccion, int x, int y) {
        super(x, y);
        this.vidas = 3;
        this.movidireccion = movidireccion;
        this.invencible = false;
        this.xInicial = x;
        this.yInicial = y;
    }
    
    @Override
    public void moverse(Laberinto laberinto){
        int nuevoX = x; //ponemos estas variables para asignar un nuevo valor en la posicion y que cambie durante el juego
        int nuevoY = y;
        
        switch(movidireccion){//imaginamos el mapa donde se va a mover el PCMAN como un plano cartesiano de 4 cuadrantes
            
           
                
            case NORTE:
                nuevoY--;
                break;
                
            case SUR:
                nuevoY++;
                break;
                
            case OESTE:
                nuevoX--;
                break;
                
            case ESTE:
                nuevoX++;
                break;
                       
        }
        /**boolean esValido = laberinto.esMovimientoValido(nuevoX, nuevoY);
    System.out.println("Intentando mover a: " + nuevoX + "," + nuevoY + " | ¿Es válido? " + esValido);

    if (esValido) {
        x = nuevoX;
        y = nuevoY;
    } else {
        // ¡Aquí está el secreto!
        System.out.println("El Pac-Man está chocando con una pared en el Laberinto.");
    }***/
        if (laberinto.esMovimientoValido(nuevoX, nuevoY)) {
            x = nuevoX;
            y = nuevoY;
        }
    }
     public void perderVida() { // si PACMAN toca un fantasma sin ser invisible, no hay manera de ayudar
        if (!invencible) {
            vidas--;
        }
    }
    
    public int getVidas() {
        return vidas;
    }

    public MoviDireccion getMovidireccion() {
        return movidireccion;
    }

    public void setMovidireccion(MoviDireccion movidireccion) {
        this.movidireccion = movidireccion;
    }

    public boolean isInvencible() {
        return invencible;
    }

    public void setInvencible(boolean invencible) {
        this.invencible = invencible;
    }
    public int getxInicial() {
        return xInicial;
    }

    public int getyInicial() {
        return yInicial;
    }
    public void reiniciarPosicion() {
        x = xInicial;
        y = yInicial;
    }
    public void setPosicionInicial(int xInicial, int yInicial) {
        this.xInicial = xInicial;
        this.yInicial = yInicial;
    }
    
}
