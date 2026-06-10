/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pac_man;

/**
 *
 * @author User
 */
import Controlador.ControladorJuego;
import Vista.VistaPacMan;
import javafx.application.Application;
import javafx.stage.Stage;

public class PAC_MAN extends Application {

   public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 1. Creamos al jefe
        ControladorJuego controlador = new ControladorJuego();
        
        // 2. Creamos la vista y le inyectamos al jefe
        VistaPacMan vista = new VistaPacMan(controlador);
        
        // 3. El jefe toma el mando de la vista
        controlador.setVista(vista);
        
        // 4. Arrancamos la interfaz
        vista.start(primaryStage);
    }
}
