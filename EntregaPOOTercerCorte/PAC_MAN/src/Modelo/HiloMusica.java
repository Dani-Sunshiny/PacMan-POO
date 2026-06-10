/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class HiloMusica{

    private MediaPlayer reproductor;

public HiloMusica() {

    Platform.runLater(() -> {

        try {

            String ruta = getClass()
                    .getResource("/ImagenesYGifs/PacRemix.mp3")
                    .toExternalForm();

            Media media = new Media(ruta);

            reproductor = new MediaPlayer(media);

            reproductor.setCycleCount(MediaPlayer.INDEFINITE);

            reproductor.play();

        } catch(Exception e) {
            e.printStackTrace();
            reproductor.stop();
        }
    });
}

    public void start() {

        Platform.runLater(() -> {
            if(reproductor != null) {
                reproductor.play();
            }
        });
    }

public void pausar() {

    System.out.println("PAUSAR");
    System.out.println(reproductor);

    Platform.runLater(() -> {
        if(reproductor != null) {
            reproductor.pause();
        }
    });
}
public void reanudar() {

    System.out.println("REANUDAR");
    System.out.println(reproductor);

    Platform.runLater(() -> {
        if(reproductor != null) {
            reproductor.play();
        }
    });
}

    public void detener() {

        Platform.runLater(() -> {
            if(reproductor != null) {
                reproductor.stop();
            }
        });
    }


}