# PacMan-POO
# 🕹️ Proyecto Pac-Man en JavaFX

![Pac-Man Banner](PACMAN.png)

Este proyecto es una recreación del clásico juego **Pac-Man**, desarrollada en **Java** utilizando el framework gráfico **JavaFX**. El objetivo principal es aplicar conceptos de programación orientada a objetos (POO), arquitectura Modelo-Vista-Controlador (MVC) y manejo de bucles de juego (game loops).

---

## 🚀 Características Técnicas

* **Arquitectura MVC:** Separación clara entre la lógica del juego (`Controlador`), la visualización (`Vista`) y el modelo de datos.
* **Game Loop Robusto:** Implementación de un `AnimationTimer` optimizado.
* **Sincronización:** Uso de un factor de conversión (`* 1_000_000`) para alinear la lógica (milisegundos) con el reloj del sistema (nanosegundos).
* **Renderizado por Capas:** Dibujado eficiente en `Canvas` utilizando un sistema de capas para objetos y personajes.

---

## 📁 Estructura del Proyecto

```text
src/
├── Controlador/       # Lógica del juego, colisiones y gestión de estados
├── Vista/             # Renderizado en Canvas, gestión de imágenes y UI
├── Modelo/            # Entidades (Pac-Man, Fantasmas, Pellets, Laberinto)
└── PAC_MAN.java       # Punto de entrada principal (Main)
