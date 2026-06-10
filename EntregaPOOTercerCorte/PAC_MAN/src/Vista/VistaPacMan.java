/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.ControladorJuego;
import javafx.util.Duration;
import javafx.scene.image.*;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 *
 * @author danie
 */

public class VistaPacMan extends Application{

    private ControladorJuego controlador; // Necesitamos

    private VBox superior; //OPCIONES
    
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

    private Image fantasmaVerde;
    private Image fantasmaVerdeIzq;
    private Image fantasmaVerdeDere;

    private Image fantasmaAzul;
    private Image fantasmaAzulIzq;
    private Image fantasmaAzulDere;

    private Image fantasmaRojo;
    private Image fantasmaRojoIzq;
    private Image fantasmaRojoDere;

    private Image fantasmaNaranja;
    private Image fantasmaNaranjaIzq;
    private Image fantasmaNaranjaDere;
   
    private Image fresa;
    private Image naranja;
    private Image sandia;
    private Image manzana;
    private Image pellet;
    
    private Image ayuda1;
    private Image ayuda2;
    
    private Image creditos1;
    private Image creditos2;
    private int contadorAnimacion = 0; // Este contador nos ayuda a medir el tiempo
    
    private Image derrota;
    private Image derrota2;
    private Image menuPrincipal;
    private Image pausa1;
    private Image pausa2;
    private Image vic1;
    private Image vic1_1;
    private Image vic2;
    private Image vic2_2;
    
    //boton atras
    private final double VOLVER_X = 450; 
    private final double VOLVER_Y = 530;
    private final double VOLVER_W = 130; // Ajusta el ancho según tu imagen
    private final double VOLVER_H = 50;  // Ajusta el alto según tu imagen
    
    private BorderPane root;
    private Canvas canvas;

    private final int TAM_CELDA = 30; //Tamaño en píxeles de cada celda del laberinto (ej. 30x30)
    private boolean bocaAbierta = true;// Estado para la animación visual: alterna entre verdadero/falso para mover la boca de Pac-Man

    private long ultimaEjecucion = 0; //Guarda el momento exacto (en milisegundos) de la última vez que se ejecutó el juego
    private final long VELOCIDAD_JUEGO = 220; // Define la velocidad de refresco de la lógica en milisegundos (ms).
     //(250ms = 0.25 segundos). 
   
    public VistaPacMan(ControladorJuego controlador) {
        this.controlador = controlador;
    }
    
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
        root = new BorderPane();
        root.setStyle("-fx-background-color:black;");
        
        // 3. Ahora creamos la escena usando el root que ya existe
        Scene escena = new Scene(root, 600, 600);
        // Aqui el estilo del menu para vincular el CSS:
        escena.getStylesheets().add(getClass().getResource("/ImagenesYGifs/estilos.css").toExternalForm());
        //Aqui lee las teclas
        escena.setOnKeyPressed(e -> controlador.procesarTeclado(e));
        root.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) {
        controlador.procesarTeclaAvance(); }
        });
        //Aqui lee el Mouse
        canvas.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            String estado = controlador.getEstadoActualParaDibujar();

            // Ajusta estas coordenadas según donde esté tu botón "JUGAR" en la imagen
            // Suponiendo que el botón JUGAR esté aproximadamente en x: 200 a 400, y: 300 a 350
            if (estado.equals("MENU_PRINCIPAL")) {
                if (mouseX >= 200 && mouseX <= 400 && mouseY >= 300 && mouseY <= 350) {
                    aplicarTransicion(() -> { controlador.iniciarJuegoDesdeMenu(1);});
                          
                }// AYUDA
                else if (mouseX >= 200 && mouseX <= 400 && mouseY >= 400 && mouseY <= 450) {
                    aplicarTransicion(() -> {controlador.estamosAyuda();}); 
                } // CREDITOS
                else if (mouseX >= 10 && mouseX <= 150 && mouseY >= 475 && mouseY <= 515) {
                    aplicarTransicion(() ->{controlador.estamoCreditos();});
                
                }// SALIR
                else if (mouseX >= 10 && mouseX <= 150 && mouseY >= 540 && mouseY <= 585) {
                    aplicarTransicion(() ->{System.exit(0);});
                }
            }
            if (estado.equals("MENU")) {
            //REANUDAR
                if (mouseX >= 190 && mouseX <= 410 && mouseY >=160  && mouseY <= 215) {
                     controlador.reanudarJuego();
                          
                }// REINICIAR
                else if (mouseX >= 190 && mouseX <= 410 && mouseY >= 275 && mouseY <= 330) {
                    aplicarTransicion(() -> {controlador.reiniciarJuego();}); 
                
                }
            }
               if (estado.equals("AYUDA") || estado.equals("CREDITOS") || estado.equals("MENU")) {
                if (mouseX >= VOLVER_X && mouseX <= VOLVER_X + VOLVER_W && 
                    mouseY >= VOLVER_Y && mouseY <= VOLVER_Y + VOLVER_H) {
                    aplicarTransicion(() -> controlador.irAtras());
                }
                }
                 
        });
        //MOSTRAR MANITO
        canvas.setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();
            String estado = controlador.getEstadoActualParaDibujar();

            // Solo cambiamos si estamos en el menú principal ESTE ES PARA BBOTN VOVER
            boolean sobreBotonVolver = (estado.equals("AYUDA") || estado.equals("CREDITOS") || estado.equals("MENU")) && 
                               ((x >= VOLVER_X && x <= VOLVER_X + VOLVER_W && 
                                y >= VOLVER_Y && y <= VOLVER_Y + VOLVER_H));
            boolean sobreBotonMenuPrin = (estado.equals("MENU_PRINCIPAL")) &&
                // Ajusta estos números a las coordenadas de tus botones
                ((x >= 200 && x <= 400 && y >= 300 && y <= 350) || // JUGAR
                    (x >= 200 && x <= 400 && y >= 400 && y <= 450) || // AYUDA
                    (x >= 10 && x <= 150 && y >= 475 && y <= 515) || // CREDITOS
                    (x >= 10 && x <= 150 && y >= 540 && y <= 585)); 
            boolean sobreBotonMenu = (estado.equals("MENU")) &&
                // Ajusta estos números a las coordenadas de tus botones
                ((x >= 190 && x <= 410 && y >= 160 && y <= 215) || // REANUDAR
                    (x >= 190 && x <= 410 && y >= 275  && y <= 330)); // REINICIAR
                    
                    
             // Aplicar el cursor según corresponda
                 if (sobreBotonVolver || sobreBotonMenuPrin || sobreBotonMenu) {
                     canvas.setCursor(Cursor.HAND); // Se muestra la manito
                 } else {
                     canvas.setCursor(Cursor.DEFAULT); // Se muestra la flecha normal
                 }
        });
        //-------------------- TRANSIONES ANIMACIONES
        

        // Menu principal jugando
        

        
        MenuBar barraMenu = new MenuBar();
        barraMenu.getStyleClass().add("mi-menu-estilo");
        Menu menuJuego = new Menu(".....OPCIONES.....");
        MenuItem pausar = new MenuItem("Pausar(Espacio)");
        MenuItem salir = new MenuItem("Salir (Esc)");
        

        

        menuJuego.getItems().addAll( pausar, new SeparatorMenuItem(),salir);

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

       //MENU CENTRO OPCIONES
        superior = new VBox();
        barraMenu.setMaxWidth(Region.USE_PREF_SIZE);
        superior.setAlignment(Pos.CENTER); // Esto centra todos los elementos dentro del VBox (barraMenu y panelInfo)
        superior.getChildren().addAll(barraMenu, panelInfo);
        root.setTop(superior);
        root.setCenter(canvas);
        root.setBottom(mensajeInferior);

       
        // Menus de botnes
       
        pausar.setOnAction(e -> {
            controlador.estamosPausa();
            actualizarLabels();
        });

         salir.setOnAction(e -> {
             controlador.volverMenuJuego();
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
        stage.setResizable(false);// para que no puedan agradar
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
    //Definicion del centrado del juego
    private int centradoX() {
        char[][] mapa = controlador.getMapa();

    if(mapa == null) return 0;

    return (int)((canvas.getWidth()
            - mapa[0].length * TAM_CELDA) / 2);
    }
    //Dibujado En General

        public void dibujar() {
                // Obtenemos el contexto gráfico
                GraphicsContext g = canvas.getGraphicsContext2D();
                
                
                // Obtenemos el estado actual del juego
                String estado = controlador.getEstadoActualParaDibujar();
                boolean esJuego = estado.equals("JUGANDO");

                // Control de visibilidad del HUD (MenuBar y panel superior)
                superior.setVisible(esJuego);
                superior.setManaged(esJuego);

                // Limpieza de fondo obligatoria (evita estelas blancas)
                g.setFill(Color.BLACK);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Selección de pantalla según el estado
                switch (estado) {
                    case "MENU_PRINCIPAL":
                        g.drawImage(menuPrincipal, 0, 0, 600, 600);
                        break;
                        
                    case "MENU":
                        // Incrementamos el contador en cada dibujo
                    contadorAnimacion++;

                     // Si es menor a 30, mostramos la imagen 1, si no, la imagen 2
                     if (contadorAnimacion < 120) {
                         g.drawImage(pausa1, 0, 0, 600, 600);
                    } else {
                         g.drawImage(pausa2, 0, 0, 600, 600);
                     }

                     // Reiniciamos el ciclo para que la animación sea infinita
                    if (contadorAnimacion >= 240) {
                         contadorAnimacion = 0;
                    }
                        break;

                    case "AYUDA":
                        
                        // Incrementamos el contador en cada dibujo
                    contadorAnimacion++;

                     // Si es menor a 30, mostramos la imagen 1, si no, la imagen 2
                     if (contadorAnimacion < 60) {
                         g.drawImage(ayuda1, 0, 0, 600, 600); 
                    } else {
                         g.drawImage(ayuda2, 0, 0, 600, 600); 
                     }

                     // Reiniciamos el ciclo para que la animación sea infinita
                    if (contadorAnimacion >= 120) {
                         contadorAnimacion = 0;
                    }
                        break;

                    case "CREDITOS":
                     // Incrementamos el contador en cada dibujo
                    contadorAnimacion++;

                     // Si es menor a 30, mostramos la imagen 1, si no, la imagen 2
                     if (contadorAnimacion < 30) {
                         g.drawImage(creditos1, 0, 0, 600, 600);
                    } else {
                         g.drawImage(creditos2, 0, 0, 600, 600);
                     }

                     // Reiniciamos el ciclo para que la animación sea infinita
                    if (contadorAnimacion >= 60) {
                         contadorAnimacion = 0;
                    }
                        break;

                    case "JUGANDO":
                    // Limpiamos cada fotograma, para que no se dibuje encima del anterior, 
                    // dejando un rastro o "estela" de las posiciones pasadas de Pac-Man.
                    g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // ¡Crucial!

                    // 2. Llamamos a todos los métodos de dibujo, pasando el juego y el contexto
                    dibujarLaberinto(g);
                    dibujarPellets(g);
                    dibujarPowerUps(g);
                    dibujarPacman(g);
                    dibujarFantasmas(g);
                    dibujarMensajes(g);
                    break;
                    
                    case "NIVEL_COMPLETADO":
                        // Incrementamos el contador en cada dibujo
                    contadorAnimacion++;

                     // Si es menor a 30, mostramos la imagen 1, si no, la imagen 2
                     if (contadorAnimacion < 30) {
                         g.drawImage(vic1, 0, 0, 600, 600);
                    } else {
                         g.drawImage(vic1_1, 0, 0, 600, 600);
                     }

                     // Reiniciamos el ciclo para que la animación sea infinita
                    if (contadorAnimacion >= 60) {
                         contadorAnimacion = 0;
                    }
                        break;
                         
                    case "GAME_OVER":
                         // Incrementamos el contador en cada dibujo
                    contadorAnimacion++;

                     // Si es menor a 30, mostramos la imagen 1, si no, la imagen 2
                     if (contadorAnimacion < 30) {
                         g.drawImage(derrota, 0, 0, 600, 600);
                    } else {
                         g.drawImage(derrota2, 0, 0, 600, 600);
                     }

                     // Reiniciamos el ciclo para que la animación sea infinita
                    if (contadorAnimacion >= 60) {
                         contadorAnimacion = 0;
                    }
                        break;
                        
                         
                    case "VICTORIA":
                        // Incrementamos el contador en cada dibujo
                    contadorAnimacion++;

                     // Si es menor a 30, mostramos la imagen 1, si no, la imagen 2
                     if (contadorAnimacion < 30) {
                         g.drawImage(vic2, 0, 0, 600, 600);
                    } else {
                         g.drawImage(vic2_2, 0, 0, 600, 600);
                     }

                     // Reiniciamos el ciclo para que la animación sea infinita
                    if (contadorAnimacion >= 60) {
                         contadorAnimacion = 0;
                    }
                        break;
            }
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

    fantasmaVerde =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaVerdeQuieto.png"));
    fantasmaVerdeIzq =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaVerdeIzq.png"));
    fantasmaVerdeDere =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaVerdeDere.png"));
    
    fantasmaAzul=new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaAZulQuieto.png"));
    fantasmaAzulIzq =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaAzulIzq.png"));
    fantasmaAzulDere =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaAzulDere.png"));
    
    fantasmaRojo =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaRojoQuieto.png"));
    fantasmaRojoIzq =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaRojoIzq.png"));
    fantasmaRojoDere =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaRojoDere.png"));
    
    fantasmaNaranja=new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaNaranjaQuieto.png"));
    fantasmaNaranjaIzq =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaNaranjaIzq.png"));
    fantasmaNaranjaDere =new Image(getClass().getResourceAsStream("/ImagenesYGifs/FantasmaNaranjaDere.png"));
    

    pellet = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Pellet.png"));

    fresa = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Fresa.png"));
    naranja = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Naranja.png"));
    sandia = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Sandia.png"));
    manzana = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Manzana.png"));
    
    ayuda1 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Ayuda.jpg"));
    ayuda2 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Ayuda2.jpeg"));
    creditos1 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/CREDITOS1.jpeg"));
    creditos2 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/CREDITOS2.jpeg"));
    derrota = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Derrota.png"));
    derrota2 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Derrota2.jpeg"));
    menuPrincipal = new Image(getClass().getResourceAsStream("/ImagenesYGifs/MenuPrincipal600x600.png"));
    pausa1 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/PAUSA1.jpeg"));
    pausa2 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/PAUSA2.jpeg"));
    vic1 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Victoria1.png"));
    vic1_1 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Victoria1_1.jpeg"));
    vic2 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Victoria2.png"));
    vic2_2 = new Image(getClass().getResourceAsStream("/ImagenesYGifs/Victoria2_2.jpeg"));
}
    

    //Construccion Laberinto

    private void dibujarLaberinto( GraphicsContext g){ 
// 1. Pedimos el mapa al controlador
    char[][] mapa = controlador.getMapa();
    
    // 2. Validación de seguridad
    if (mapa == null) return;
    int CentradoX=centradoX();
    // 3. Dibujado
    for (int fila = 0; fila < mapa.length; fila++) {
        for (int columna = 0; columna < mapa[fila].length; columna++) {
            if (mapa[fila][columna] == '#') {
                g.setFill(Color.DARKBLUE);
                g.fillRect(CentradoX + columna * TAM_CELDA,fila * TAM_CELDA,TAM_CELDA,TAM_CELDA);
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
    int CentradoX=centradoX();
    Image sprite = null;
    
    // Usamos el código numérico para elegir el sprite, relacionado
    switch(dir) {
        case 0 -> sprite = bocaAbierta ? pacAbiArriba : pacCerrArriba; // NORTE
        case 1 -> sprite = bocaAbierta ? pacAbiAbajo : pacCerrAbajo;   // SUR
        case 2 -> sprite = bocaAbierta ? pacAbiIzq : pacCerrIzq;       // OESTE
        case 3 -> sprite = bocaAbierta ? pacAbiDere : pacCerrDere;     // ESTE
    }

    if (sprite != null) {
        g.drawImage(sprite, CentradoX+x * TAM_CELDA, y * TAM_CELDA, TAM_CELDA, TAM_CELDA);
    }
   if (controlador.esPacmanInvencible())  {
        // RECORDAR PONER PACMAN DE UN COLOR DIFERENTE CUANDO TOQUE UN POWER UP
        //g.setGlobalAlpha(0.5); // Hace que Pac-Man se vea "fantasmagórico"
    } else {
        g.setGlobalAlpha(1.0); // Opacidad normal
    }
 }


    //-------------------------------------------------
    // Dibuja fantasmas
    //-------------------------------------------------
    private void dibujarFantasmas(GraphicsContext g) {

    for(String[] datos : controlador.getDatosFantasmas()) {
        //toma los datos tipo string que se tenian en el controlador respecto a los fantasmas
        int x = Integer.parseInt(datos[0]);
        int y = Integer.parseInt(datos[1]);
        int CentradoX=centradoX();
        //parseint utilizado para convertir los numeros tipo string en tipo int
        String tipo = datos[2];
        String direccion = datos[3];
        //la variable tipo es para tomar el color del fantasma y la direccion a la que se dirige el mismo para agarrar su respectivo sprite con su respectivo color
        Image sprite = null;

        if(tipo.equals("VERDE")) {

            switch(direccion) {

                case "OESTE":
                    sprite = fantasmaVerdeIzq;
                    break;

                case "ESTE":
                    sprite = fantasmaVerdeDere;
                    break;

                default:
                    sprite = fantasmaVerde;
            }
        }

        else if(tipo.equals("ROJO")) {

            switch(direccion) {

                case "OESTE":
                    sprite = fantasmaRojoIzq;
                    break;

                case "ESTE":
                    sprite = fantasmaRojoDere;
                    break;

                default:
                    sprite = fantasmaRojo;
            }
        }
        else if(tipo.equals("AZUL")) {

            switch(direccion) {

                case "OESTE":
                    sprite = fantasmaAzulIzq;
                    break;

                case "ESTE":
                    sprite = fantasmaAzulDere;
                    break;

                default:
                    sprite = fantasmaAzul;
            }
        }
        else if(tipo.equals("NARANJA")) {

            switch(direccion) {

                case "OESTE":
                    sprite = fantasmaNaranjaIzq;
                    break;

                case "ESTE":
                    sprite = fantasmaNaranjaDere;
                    break;

                default:
                    sprite = fantasmaNaranja;
            }
        }

        g.drawImage(
            sprite,
            CentradoX+x * TAM_CELDA,
            y * TAM_CELDA,
            TAM_CELDA,
            TAM_CELDA
        );
    }
    }

    //-------------------------------------------------
    // PELLETS
    //-------------------------------------------------

    private void dibujarPellets(GraphicsContext g) {
    // La vista no sabe qué es un 'Pellet', solo sabe dibujar imágenes en coordenadas
    int CentradoX=centradoX();
        for (int[] pos : controlador.getPosicionesPellets()) { // ajuste de tamaño
            // Aquí sumas +8 a las coordenadas para centrar el pellet dentro de la celda de 30x30.
            // Al usar 14x14, garantizas que sea más pequeño que la celda, dándole el aspecto de "punto".
            g.drawImage(pellet,CentradoX+pos[0] * TAM_CELDA + 8, pos[1] * TAM_CELDA + 8,14,14);
        }
    }

    //-------------------------------------------------
    // POWERUPS
    //-------------------------------------------------


    private void dibujarPowerUps(GraphicsContext g) {

    for(String[] datos : controlador.getDatosPowerUps()) {
        //toma los datos tipo string que se tenian en el controlador respecto a los power ups
        int x = Integer.parseInt(datos[0]);
        int y = Integer.parseInt(datos[1]);
        int CentradoX=centradoX();
        String tipo = datos[2];
        Image sprite = null;

        //la variable tipo es para determinar cual fruta es el power up
        switch(tipo) {

            case "FRESA":
                sprite = fresa;
                break;

            case "NARANJA":
                sprite = naranja;
                break;

            case "SANDIA":
                sprite = sandia;
                break;

            case "MANZANA":
                sprite = manzana;
                break;
        }

        g.drawImage(
            sprite,
            CentradoX+x*TAM_CELDA,
            y*TAM_CELDA,
            TAM_CELDA,
            TAM_CELDA
        );
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
           case "NIVEL_COMPLETADO" -> {g.fillText("¡NIVEL COMPLETADO!", 150, 200);
            g.setFont(Font.font(20));
            g.fillText("Presiona ENTER para continuar", 150, 240);}
    }
}
    
    private void aplicarTransicion(Runnable alFinalizar) {
                // 'root' es tu contenedor principal (BorderPane)
                FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
                ft.setFromValue(1.0); // Opaco
                ft.setToValue(0.0);   // Transparente

                ft.setOnFinished(event -> {
                    alFinalizar.run(); // Aquí se ejecuta: controlador.iniciarJuegoDesdeMenu(1)

                    // Efecto de aparición (Fade In)
                    FadeTransition ftIn = new FadeTransition(Duration.millis(1000), root);
                    ftIn.setFromValue(0.0);
                    ftIn.setToValue(1.0);
                    ftIn.play();
                });

                ft.play();
            }


    //Metodo para que cuando toquemos los controles de teclado se ejecute un poquito mas lento
    public void ejecutarConRetardo(Runnable accion) {
        PauseTransition pausa = new PauseTransition(Duration.millis(300));
        pausa.setOnFinished(e -> accion.run());
        pausa.play();
}
}