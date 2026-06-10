/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author User
 */

// Hereda de la clase base Fantasma
public class FantasmaAleatorio extends Fantasma {

    // Generador de números aleatorios para decidir la dirección
    private Random random;

    public FantasmaAleatorio(int velocidad, MoviDireccion movidireccion, int x, int y, String colorfantasma) {
        super(velocidad, movidireccion, x, y,colorfantasma);
        this.random = new Random();   // Instancia el objeto Random
    }

    //Método principal de movimiento. Se ejecuta en cada ciclo del juego.
    @Override
    public void perseguirPacMan(Laberinto laberinto, PacMancito pacman) {

        // Lista dinámica para almacenar solo los caminos que no tienen pared
        List<MoviDireccion> direccion = new ArrayList<>();

        // 1. Verificamos qué direcciones vecinas son válidas
        //El punto (0,0) está en la esquina superior izquierda de la pantalla o del mapa.
        if (laberinto.esMovimientoValido(x, y - 1))
            direccion.add(MoviDireccion.NORTE);

        if (laberinto.esMovimientoValido(x, y + 1))
            direccion.add(MoviDireccion.SUR);

        if (laberinto.esMovimientoValido(x - 1, y))
            direccion.add(MoviDireccion.OESTE);

        if (laberinto.esMovimientoValido(x + 1, y))
            direccion.add(MoviDireccion.ESTE);

        // 2. Si el fantasma está completamente encerrado, no hace nada/ lo hacemos por logica
        if (direccion.isEmpty())
            return;

        // 3. Elige una direcion al azar entre las posibles válidas encontradas(es entre el tamaño del arreglo    
        MoviDireccion elegida = direccion.get(random.nextInt(direccion.size()));
        this.movidireccion= elegida;

        // 4. Aplica el movimiento según la dirección seleccionada
        switch (elegida) {
            case NORTE:
                moverA(x, y - 1, laberinto);
                break;

            case SUR:
                moverA(x, y + 1, laberinto);
                break;

            case OESTE:
                moverA(x - 1, y, laberinto);
                break;

            case ESTE:
                moverA(x + 1, y, laberinto);
                break;
        }
    }
}

    

