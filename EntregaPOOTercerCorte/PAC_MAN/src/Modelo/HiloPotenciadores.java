/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author danie
 */
public class HiloPotenciadores extends Thread{

    private PacMancito pacman;

    public HiloPotenciadores(PacMancito pacman) {
        this.pacman=pacman;
    }

    @Override
    public void run() {

        try {

            Thread.sleep(5000);

            pacman.setInvencible(false);

        } catch (InterruptedException e) {

            return;
    }
    }
    
}