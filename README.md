# Procesamiento de Imágenes en Java

**Proyecto — Programación III**

## Integrantes del Equipo

- **César Osvaldo Díaz Martínez** — *225217170*
- **Luis Ángel López Cruz** — *225216692*
- **Carlos Omar Sánchez Torrescano** — *223220435*

## Introducción

Este proyecto muestra cómo manipular y aplicar filtros digitales a imágenes utilizando únicamente las herramientas nativas de Java (`java.awt.image` y `javax.swing`), sin depender de librerías externas.

El sistema trabaja con objetos `BufferedImage` para representar la imagen como una matriz de píxeles y utiliza la interfaz `BufferedImageOp` para transformar una imagen de origen en una nueva imagen procesada. Su arquitectura modular permite ajustar fácilmente los parámetros o combinar distintos filtros modificando unas cuantas líneas en el archivo principal.

## Estructura del Proyecto

El código está dividido en tres clases principales dentro de la carpeta `src/`:

- **`archivos.java`**: Se encarga de leer cualquier imagen desde el disco duro, estandarizarla a formato RGB (`TYPE_INT_RGB`) para evitar problemas de compatibilidad con los filtros y guardar los resultados.
- **`visuales.java`**: Contiene los métodos reutilizables que aplican las transformaciones matemáticas a los píxeles y la interfaz gráfica en Swing para comparar los resultados.
- **`Main.java`**: Funciona como panel de control donde se define la imagen de entrada, se configuran los parámetros deseados para cada filtro y se mandan a guardar y mostrar las imágenes.

## ¿Qué hace el programa?

El programa carga una imagen base, convierte su modelo de color a RGB estándar y ejecuta un flujo configurable de operaciones que puedes moldear a tu gusto desde `Main.java`:

1. **Ajuste de Brillo y Contraste (`RescaleOp`):**
    - Modifica cada píxel de manera individual mediante la ecuación lineal $P_{nuevo} = (P_{original} \cdot \alpha) + \beta$.
    - Permite personalizar libremente el factor de contraste ($\alpha$) y el desplazamiento de brillo ($\beta$) para aclarar, oscurecer o resaltar los tonos de la imagen.

2. **Transformaciones Geométricas (`AffineTransformOp`):**
    - Permite rotar la imagen a cualquier ángulo (en grados) y modificar su escala (zoom) sobre su punto central.
    - Permite elegir el tipo de interpolación (`TYPE_NEAREST_NEIGHBOR`, `TYPE_BILINEAR` o `TYPE_BICUBIC`) para controlar el equilibrio entre velocidad y suavizado de bordes.

3. **Filtros de Convolución (`ConvolveOp`):**
    - Desliza una matriz de pesos (`Kernel`) personalizable sobre cada píxel y sus vecinos para calcular un nuevo valor:
        - **Desenfoque:** Promedia el color de cada píxel con sus vecinos; puedes ajustar el tamaño de la matriz para controlar qué tan borrosa sale la imagen.
        - **Detección de Bordes:** Utiliza matrices de contraste (como el filtro con peso central positivo y vecinos negativos) que apagan las zonas de color sólido y resaltan únicamente los contornos. Puedes modificar los valores del arreglo para probar otros efectos como enfoque (*sharpen*) o relieve (*emboss*).

4. **Interfaz Visual Comparativa y Exportación:**
    - Abre una ventana (`JFrame`) en cuadrícula donde puedes mostrar tantas variaciones como agregues al arreglo de galería.
    - Exporta automáticamente cada imagen resultante a la ruta y formato (`jpg`, `png`, etc.) que especifiques en `archivos.guardarimagen(...)`.

## Material Visual

La presentación utilizada durante la exposición está disponible para consulta en línea:

- [**Ver Presentación del Proyecto (Google Slides)**](https://docs.google.com/presentation/d/1IXlm8YQ7z_bMOs_pcW26j8Ck7RE2lPVxH-gQQyBHfvc/edit?usp=sharing)

## Cómo descargarlo y ejecutarlo

### Requisitos previos

- **Java Development Kit (JDK):** Versión 17 o superior.
- **Entorno de desarrollo:** IntelliJ IDEA, Visual Studio Code, Eclipse, NetBeans o terminal del sistema.

### Descarga del código

Clona el repositorio desde una terminal:

```bash
git clone [https://github.com/Cayo-IT/Proyecto-Programacion-3.git](https://github.com/Cayo-IT/Proyecto-Programacion-3.git)