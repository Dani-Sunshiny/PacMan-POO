# PacMan-POO
# 🕹️ Proyecto Pac-Man en JavaFX
<div align="center">
<img src="PACMAN.png"  alt="Pac-Man Banner">

Este proyecto es una recreación del clásico juego **Pac-Man**, desarrollada en **Java** utilizando el framework gráfico **JavaFX**. El objetivo principal es aplicar conceptos de programación orientada a objetos (POO), arquitectura Modelo-Vista-Controlador (MVC) y manejo de bucles de juego (game loops).
</div>

---
## 👥 Equipo de Desarrollo

| Nombre Completo | Código Estudiantil |
| :--- | :---: |
| Ana Sofía Villalobos Pérez | 20242020063 |
| Daniel Antonio Olivares Hinojosa | 20252020016 |
---

## 🚀 Características Técnicas

* **Arquitectura MVC:** Separación clara entre la lógica del juego (`Controlador`), la visualización (`Vista`) y el modelo de datos.
* **Game Loop Robusto:** Implementación de un `AnimationTimer` optimizado.
* **Sincronización:** Uso de un factor de conversión (`* 1_000_000`) para alinear la lógica (milisegundos) con el reloj del sistema (nanosegundos).
* **El Bucle de Juego (Game Loop)**
La sincronización es fundamental. Utilizamos `AnimationTimer` comparando el tiempo del sistema en nanosegundos:
```java
if (now - ultimaEjecucion > VELOCIDAD_JUEGO * 1_000_000) {
    controlador.ejecutarCiclo();
    ultimaEjecucion = now;
    }
```

* **Renderizado por Capas:** Dibujado eficiente en `Canvas` utilizando un sistema de capas para objetos y personajes.

---
## 🏗️ Arquitectura
El proyecto sigue el patrón **MVC**, lo que permite escalar el juego sin que el código se vuelva un caos:


<img src="MVC.jpg" >
<div align="center">

* **Modelo:** Gestiona el estado lógico, coordenadas de la matriz y reglas del juego.
* **Vista:** Maneja exclusivamente el `Canvas` de JavaFX. Convierte datos en píxeles.
* **Controlador:** Orquesta la comunicación entre el modelo y la vista, y gestiona el `AnimationTimer`.
</div>

---
## 🎮 Controles del Juego

El juego ha sido diseñado para ser flexible, permitiendo dos esquemas de control simultáneos para mayor comodidad del usuario:

| Acción | Teclas (Direccionales) | Teclas (WASD) |
| :--- | :---: | :---: |
| **Mover Arriba** | `↑` | `W` |
| **Mover Abajo** | `↓` | `S` |
| **Mover Izquierda** | `←` | `A` |
| **Mover Derecha** | `→` | `D` |

*Nota: Para asegurar una respuesta inmediata, el `Canvas` solicita el foco al iniciar la aplicación mediante `canvas.requestFocus()`. Si el juego no responde, simplemente haz clic dentro de la ventana de juego.*

---

## 📁 Estructura del Proyecto

```text
src/
├── Controlador/       # Lógica del juego, colisiones y gestión de estados
├── Vista/             # Renderizado en Canvas, gestión de imágenes y UI
├── Modelo/            # Entidades (Pac-Man, Fantasmas, Pellets, Laberinto)
└── PAC_MAN.java       # Punto de entrada principal (Main)
```
# Esto es lo que va adentro
```text
src/
│
├── Modelo/
│   ├── Juego.java
│   ├── PacMancito.java
│   ├── Fantasma.java
│   ├── FantasmaAleatorio.java
│   ├── FantasmaPerseguidor.java
│   ├── Pellet.java
│   ├── PowerUp.java
│   ├── TipoDePotenciador.java
│   ├── Nivel.java
│   ├── FabricaNiveles.java
│   ├── Laberinto.java
│   ├── EstadoJuego.java
│   ├── MoviDireccion.java
│   └── Entidad.java
│
├── Controlador/
│   └── ControladorJuego.java
│
├── Vista/
│   └── VistaPacMan.java
│
├── ImagenesYGifs/
│   ├── PacAbiDere.png
│   ├── PacAbiIzq.png
│   ├── PacAbiArriba.png
│   ├── PacAbiAbajo.png
│   ├── PaxCerrDere.png
│   ├── PaxCerrIzq.png
│   ├── PaxCerrArriba.png
│   ├── PaxCerrAbajo.png
│   ├── Fantasma.png
│   ├── Pellet.png
│   └── fresa.jpg
│
└── PAC_MAN.java
```


---

## 📊 Estado del Desarrollo

Este es el progreso actual de las funcionalidades implementadas en el motor de juego:

| Característica | Estado | Notas Técnicas |
| :--- | :---: | :--- |
| **Motor de Renderizado** | ✅ Completado | Uso de `GraphicsContext` y `AnimationTimer` |
| **Bucle de Juego (Tick)** | ✅ Completado | Sincronización mediante `1_000_000` (ns) |
| **Sistema de Movimiento** | ✅ Completado | Basado en rejilla (`TAM_CELDA`) |
| **Gestión de Pellets** | ✅ Completado | Renderizado con offset de centrado (`+8px`) |
| **Colisiones** | 🚧 En Proceso | Detección de paredes y entidades |
| **Inteligencia Artificial** | 🚧 En Proceso | Algoritmos de persecución (Ghost AI) |
| **Menú de Inicio** | ❌ Pendiente | Interfaz de selección de niveles |

*Leyenda: ✅ Completado | 🚧 En Proceso | ❌ Pendiente*

---

## 🛠️ Resolución de Problemas Comunes

Si encuentras dificultades al ejecutar el juego, revisa esta guía rápida:

### 1. El teclado no responde
Si al presionar las teclas el juego no se mueve, es probable que el `Canvas` haya perdido el **foco**.
* **Solución:** Hemos implementado `canvas.requestFocus()` al iniciar. Si aún falla, asegúrate de que tu ventana principal esté activa al hacer clic sobre ella.

### 2. Error en la compilación de JavaFX
Si obtienes errores de tipo `JavaFX runtime components are missing`:
* **Causa:** JavaFX ya no está incluido en el JDK estándar desde la versión 11.
* **Solución:** Asegúrate de tener los módulos de JavaFX configurados en los argumentos de la VM de tu IDE (`--module-path /ruta/a/javafx/lib --add-modules javafx.controls,javafx.fxml`).

---
#  Diagramas del Sistema

Para el diseño y documentación del proyecto se elaboraron diversos diagramas UML que permiten comprender la estructura, comportamiento e interacción de los componentes del videojuego Pac-Man desarrollado bajo la arquitectura MVC.

---

## 📋 Diagrama de Clases

Representa la estructura estática del sistema mostrando las clases principales, sus atributos, métodos y relaciones.

### Objetivos

- Visualizar la organización general del proyecto.
- Identificar relaciones entre clases.
- Comprender la distribución de responsabilidades dentro del patrón MVC.
- Mostrar la estructura completa del sistema.

<div align="center">

### Figura 1. Diagrama de Clases
<img src="Diagramas UML - PAC MAN/Diagrama de Clases-PACMAN.png" >
</div>

---

## 🔄 Diagrama de Secuencia

Representa el flujo de comunicación entre los objetos del sistema durante la ejecución de una funcionalidad específica.

### Objetivos

- Mostrar la interacción entre Vista, Controlador y Modelo.
- Representar el ciclo principal de ejecución del juego.
- Visualizar el intercambio de mensajes entre los componentes.

<div align="center">

### Figura 2. Diagrama de Secuencia
<img src="Diagramas UML - PAC MAN/Diagrama de Secuencia PACMAN.png" >

</div>

---

## 👤 Diagrama de Casos de Uso

Representa las funcionalidades que el sistema ofrece al jugador y la manera en que este interactúa con ellas.

### Objetivos

- Identificar las acciones disponibles para el usuario.
- Mostrar los requerimientos funcionales principales.
- Definir la interacción entre el jugador y el sistema.

<div align="center">

### Figura 3. Diagrama de Casos de Uso
<img src="Diagramas UML - PAC MAN/Diagrama de Casos de Uso PACMAN.png" >

</div>


