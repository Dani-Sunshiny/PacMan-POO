/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.ControladorJuego;
import javafx.scene.image.*;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 *
 * @author danie
 */

public class VistaPacMan extends Application{

    private ControladorJuego controlador; // Necesitamos

    private Label lblPuntaje;
    private Label lblVidas;
    private Label lblNivel;
    private Label lblEstado;
    private Image pacAbiDere;
    private Image pacAbiIzq;
    private Image pacAbiArriba;
    private Image pacAbiAbajo;

    private Image pacCerrDere;
    private Image pacCerrIzq;
    private Image pacCerrArriba;
    private Image pacCerrAbajo;

    private Image fantasma;

    private Image pellet;

    private Image fruta;
    private Canvas canvas;

    private final int TAM_CELDA = 30; //Tamaño en píxeles de cada celda del laberinto (ej. 30x30)
    private boolean bocaAbierta = true;// Estado para la animación visual: alterna entre verdadero/falso para mover la boca de Pac-Man

    private long ultimaEjecucion = 0; //Guarda el momento exacto (en milisegundos) de la última vez que se ejecutó el juego
    private final long VELOCIDAD_JUEGO = 250; // Define la velocidad de refresco de la lógica en milisegundos (ms).
     //(250ms = 0.25 segundos). 
   
    public VistaPacMan(ControladorJuego controlador) {
        this.controlador = controlador;
    }
    
    @Override
    public void start(Stage stage) {
        // Cargamos sprites
        cargarSprites();
        // 2. CREA EL CANVAS AQUÍ, ANTES DE USARLO
        canvas = new Canvas(600, 600);

        // Indica que el Canvas se enfoque para capturar eventos (como el teclado).
        // Sin esto, JavaFX ignoraría las teclas que presionas, ya que el sistema no sabría
        // que el Canvas es el receptor principal de los comandos del usuario.
        canvas.setFocusTraversable(true);
        // 2. Definimos el diseño base (BorderPane) ANTES de usarlo en la escena
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:black;");
        
        // 3. Ahora creamos la escena usando el root que ya existe
        Scene escena = new Scene(root, 575, 600);
        escena.setOnKeyPressed(e -> controlador.procesarTeclado(e));
        

        // Menu principal
        

        MenuBar barraMenu = new MenuBar();

        Menu menuJuego = new Menu("Juego");

        MenuItem iniciar = new MenuItem("Iniciar");
        MenuItem pausar = new MenuItem("Pausar");
        MenuItem reiniciar = new MenuItem("Reiniciar");
        MenuItem salir = new MenuItem("Salir");

        menuJuego.getItems().addAll(iniciar, pausar, reiniciar, new SeparatorMenuItem(),salir);

        barraMenu.getMenus().add(menuJuego);

        
        // Informacion del panel
        

        lblPuntaje = new Label("Puntaje: 0");
        lblVidas = new Label("Vidas: 3");
        lblNivel = new Label("Nivel: -");
        lblEstado = new Label("Estado: MENU");
        
        //Estilo de la letra
        String estiloHUD = "-fx-font-size:18;" + "-fx-font-weight:bold;" + "-fx-text-fill:yellow;";

        lblPuntaje.setStyle(estiloHUD);
        lblVidas.setStyle(estiloHUD);
        lblNivel.setStyle(estiloHUD);
        lblEstado.setStyle(estiloHUD);

        HBox panelInfo = new HBox(20);

        panelInfo.getChildren().addAll(lblPuntaje, lblVidas, lblNivel, lblEstado
        );

        //Centre los paneles
        panelInfo.setAlignment(Pos.CENTER);

      //-------------------------------------
        // Mensajes del juego
        

        Label mensajeInferior = new Label("PAC-MAN JAVAFX creado por tus estudiantes favoritos");

       
        VBox superior = new VBox();
        superior.getChildren().addAll(barraMenu, panelInfo);
        root.setTop(superior);
        root.setCenter(canvas);
        root.setBottom(mensajeInferior);

       
        // Menus
       

        iniciar.setOnAction(e -> {
            controlador.iniciarJuego();
            actualizarLabels();
        });

        pausar.setOnAction(e -> {
            controlador.pausarJuego();
            actualizarLabels();
        });

        reiniciar.setOnAction(e -> {
            controlador.reiniciarJuego(); // Llamamos al método del controlador
            actualizarLabels();
        });

        salir.setOnAction(e -> {

            stage.close();
        });

     
        // Loop
        

        AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long now) {
        
        // Ejecutamos la lógica de juego (Controlador)
        // Comparamos el tiempo transcurrido (now - ultimaEjecucion) contra la velocidad.
        // La multiplicación * 1_000_000 convierte nuestra VELOCIDAD_JUEGO (que está en milisegundos)
        // a nanosegundos, que es la unidad en la que trabaja el parámetro 'now' del sistema.
        if (now - ultimaEjecucion > VELOCIDAD_JUEGO * 1_000_000) {
            // Ejecuta la lógica del juego (movimiento, colisiones, etc.)
            controlador.ejecutarCiclo();

            // Actualizamos la marca de tiempo al valor actual (now).
            // Esto reinicia el contador interno, asegurando que el próximo ciclo 
            // espere a que pase el tiempo definido en VELOCIDAD_JUEGO nuevamente.
            ultimaEjecucion = now;

            // Animación de boca (solo cambia cuando se mueve el modelo)
            bocaAbierta = !bocaAbierta; 
        }
       
        
        // 3. Dibujamos y actualizamos
           dibujar();

           actualizarLabels();
        }
        };
        
        // Iniciamos el temporizador para que el AnimationTimer comience a ejecutar 
        // el método 'handle' de forma continua. sí no, el bucle permanecería detenido.
        timer.start();
        
 //-------------------------------
       // Ventana del proyecto
       
       

        stage.setTitle("Pac-Man MVC");
        stage.setScene(escena);
        stage.show();
        
        // Esto ayuda a asegurar que el foco se mantenga en la escena y asi no se deconecten los eventos, etc
        root.requestFocus();

    }

    
    // Actualizar estado de los labels
    

    public void actualizarLabels() {
        if (controlador == null || controlador.getJuego() == null) return;
 
            // Usamos los puentes del controlador.
            lblPuntaje.setText(controlador.getPuntajeTexto());
            lblVidas.setText(controlador.getVidasTexto());
            lblNivel.setText(controlador.getNivelTexto());
            lblEstado.setText(controlador.getEstadoTexto());
    }

    //Dibujado En General

        public void dibujar() {
                // Obtenemos el contexto gráfico
                GraphicsContext g = canvas.getGraphicsContext2D();
                
                // Limpiamos cada fotograma, para que no se dibuje encima del anterior, 
                // dejando un rastro o "estela" de las posiciones pasadas de Pac-Man.
                g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // ¡Crucial!

                // 1. Limpiamos la pantalla
                g.setFill(Color.BLACK);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // 2. Llamamos a todos los métodos de dibujo, pasando el juego y el contexto
                dibujarLaberinto(g);
                dibujarPellets(g);
                dibujarPowerUps(g);
                dibujarPacman(g);
                dibujarFantasmas(g);
                dibujarMensajes(g);
            }
    //Carga de los Sprites
    public void cargarSprites() {

    pacAbiDere =new Image(getClass().getResourceAsStream("/ImagenesYGifs/PacAbiDere.png"));

    pacAbiIzq = new Image(getClass().getResourceAsStream( "/ImagenesYGifs/PacAbiIzq.png"));

    pacAbiArriba = new Image(getClass().getResourceAsStream("/ImagenesYGifs/PacAbiArriba.png"));

    pacAbiAbajo = new Image(getClass().getResourceAsStream("/ImagenesYGifs/PacAbiAbajo.png"));

    pacCerrDere = new Image(getClass().getResourceAsStream("/ImagenesYGifs/PaxCerrDere.png"));

    pacCerrIzq = new Image(getClass().getResourceAsStream("/ImagenesYGifs/PaxCerrIzq.png"));

    pacCerrArriba =  new Image(getClass().getResourceAsStream( "/ImagenesYGifs/PaxCerrArriba.png"));

    pacCerrAbajo =new Image(getClass().getResourceAsStream( "/ImagenesYGifs/PaxCerrAbajo.png"));

    fantasma =new Image(getClass().getResourceAsStream("/ImagenesYGifs/Fantasma.png"));

    pellet = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Pellet.png"));

    fruta = new Image(getClass().getResourceAsStream("/ImagenesYGifs/fresa.jpg"));
}
    

    //Construccion Laberinto

    private void dibujarLaberinto( GraphicsContext g){ 
// 1. Pedimos el mapa al controlador
    char[][] mapa = controlador.getMapa();
    
    // 2. Validación de seguridad
    if (mapa == null) return;

    // 3. Dibujado
    for (int fila = 0; fila < mapa.length; fila++) {
        for (int columna = 0; columna < mapa[fila].length; columna++) {
            if (mapa[fila][columna] == '#') {
                g.setFill(Color.DARKBLUE);
                g.fillRect(columna * TAM_CELDA,  fila * TAM_CELDA, TAM_CELDA, TAM_CELDA );
            }
        }
    }
}


    // PACMAN
    //-------------------------------------------------

 private void dibujarPacman(GraphicsContext g ) {

    if (!controlador.tienePacman()) return;

    int x = controlador.getPacmanX();
    int y = controlador.getPacmanY();
    int dir = controlador.getPacmanDireccion(); // Recibimos un simple int

    Image sprite = null;
    
    // Usamos el código numérico para elegir el sprite, relacionado
    switch(dir) {
        case 0 -> sprite = bocaAbierta ? pacAbiArriba : pacCerrArriba; // NORTE
        case 1 -> sprite = bocaAbierta ? pacAbiAbajo : pacCerrAbajo;   // SUR
        case 2 -> sprite = bocaAbierta ? pacAbiIzq : pacCerrIzq;       // OESTE
        case 3 -> sprite = bocaAbierta ? pacAbiDere : pacCerrDere;     // ESTE
    }

    if (sprite != null) {
        g.drawImage(sprite, x * TAM_CELDA, y * TAM_CELDA, TAM_CELDA, TAM_CELDA);
    }
    if (controlador.getJuego().getPacman().isInvencible()) {
        // RECORDAR PONER PACMAN DE UN COLOR DIFERENTE CUANDO TOQUE UN POWER UP
        //g.setGlobalAlpha(0.5); // Hace que Pac-Man se vea "fantasmagórico"
    } else {
        g.setGlobalAlpha(1.0); // Opacidad normal
    }
 }


    //-------------------------------------------------
    // Dibuja fantasmas
    //-------------------------------------------------

   private void dibujarFantasmas( GraphicsContext g ) {
// La vista no conoce la clase 'Fantasma', solo recibe una lista de coordenadas [x, y].
        for (int[] pos : controlador.getPosicionesFantasmas()) {
            // Dibujamos la imagen del fantasma escalándola al tamaño de nuestra celda (TAM_CELDA).
                g.drawImage(fantasma, pos[0] * TAM_CELDA, pos[1] * TAM_CELDA, TAM_CELDA, TAM_CELDA);
            }
}

    //-------------------------------------------------
    // PELLETS
    //-------------------------------------------------

    private void dibujarPellets(GraphicsContext g) {
    // La vista no sabe qué es un 'Pellet', solo sabe dibujar imágenes en coordenadas
        for (int[] pos : controlador.getPosicionesPellets()) { // ajuste de tamaño
            // Aquí sumas +8 a las coordenadas para centrar el pellet dentro de la celda de 30x30.
            // Al usar 14x14, garantizas que sea más pequeño que la celda, dándole el aspecto de "punto".
            g.drawImage(pellet, pos[0] * TAM_CELDA + 8, pos[1] * TAM_CELDA + 8,14,14);
        }
    }

    //-------------------------------------------------
    // POWERUPS
    //-------------------------------------------------

    private void dibujarPowerUps(GraphicsContext g) {
        // La vista ahora solo recibe una lista de coordenadas [x, y]
        for (int[] pos : controlador.getPosicionesPowerUps()) {
            g.drawImage(fruta,pos[0] * TAM_CELDA,pos[1] * TAM_CELDA,TAM_CELDA, TAM_CELDA);
        }
    }

    //-------------------------------------------------
    // MENSAJES
    //-------------------------------------------------

    private void dibujarMensajes(GraphicsContext g) {
       g.setFill(Color.WHITE);
       g.setFont(Font.font(30));

       // Obtenemos el nombre del estado como un String
       String estado = controlador.getEstadoJuegoTexto();

       // Dibujamos según el texto recibido
       switch (estado) {
           case "PAUSADO" -> g.fillText("PAUSADO", 220, 200);
           case "VICTORIA" -> g.fillText("VICTORIA", 220, 200);
           case "GAME_OVER" -> g.fillText("GAME OVER", 220, 200);
       }
    }
}