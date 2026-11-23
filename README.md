# Procesamiento de Imágenes en Java 

**Proyecto — Programación III**

## Integrantes del Equipo

* **César Osvaldo Díaz Martínez** — *225217170*

* **Luis Ángel López Cruz** — *225216692*

* **Carlos Omar Sánchez Torrescano** — *223220435*

## Introducción

Este proyecto se trata de las capacidades para la manipulación y el filtrado digital de imágenes en memoria sin depender de librerías externas.

El sistema utiliza objetos `BufferedImage` como representación matricial de píxeles e implementa la interfaz `BufferedImageOp` para aplicar transformaciones matemáticas de origen a destino (`filter(src, dst)`).

## ¿Qué hace el proyecto?

El programa carga una imagen base, estandariza su modelo de color a `TYPE_INT_RGB` para garantizar compatibilidad de canales y ejecuta un *pipeline* de cuatro operaciones fundamentales:

1. **Ajuste de Brillo y Contraste (`RescaleOp`):**

    * **Operación puntual** mediante la ecuación lineal $P_{nuevo} = (P_{original} \cdot \alpha) + \beta$.

    * Permite estirar o comprimir el rango dinámico de los canales RGB y desplazar la luminosidad.

2. **Transformaciones Geométricas (`AffineTransformOp`):**

    * **Mapeo espacial** de coordenadas $(x, y) \rightarrow (x', y')$ mediante matrices de $3 \times 3$ encapsuladas en `AffineTransform`.

    * Ejecuta rotaciones sobre el centro de la imagen utilizando interpolación bilineal (`TYPE_BILINEAR`) para suavizar bordes y evitar el efecto escalera.

3. **Filtros de Convolución (`ConvolveOp`):**

    * **Operación de vecindario** que desliza una matriz de pesos (`Kernel`) sobre cada píxel y sus vecinos:

        * **Box Blur (**$3 \times 3$**):** Promedia la intensidad de los píxeles vecinos (suma de pesos $= 1.0$) para suavizar la imagen.

        * **Detección de Bordes Laplaciano (**$3 \times 3$**):** Resalta cambios bruscos de intensidad y anula zonas planas (suma de pesos $= 0.0$).

4. **Interfaz Visual Comparativa y Exportación:**

    * Muestra la imagen original junto con los resultados procesados en una galería comparativa (`JFrame`).

    * Exporta automáticamente cada imagen resultante a la carpeta `salida/`.

## Material Visual 

El material de apoyo utilizado durante la exposición está disponible para su consulta y descarga directa en este repositorio:

* [**Descargar Presentación del Proyecto (PDF/PPTX)**](./nohayaunxd.pdf) *

## Cómo descargarlo y ejecutarlo

### Requisitos previos

* **Java Development Kit (JDK):** Versión 26 o una estable.

* **Entorno de desarrollo:** Visual Studio Code, IntelliJ IDEA, Eclipse, NetBeans o terminal del sistema.

### 1. Descarga del código

Clona el repositorio desde una terminal:

```
git clone https://github.com/Cayo-IT/Proyecto-Programacion-3.git

```

*(También puedes hacer clic en el botón verde **Code** en la parte superior de esta página y seleccionar **Download ZIP**).*

### 2. Preparación de la imagen de prueba

Asegúrate de que la imagen de prueba (en formato `.jpg` o `.png`) se encuentre en la **carpeta raíz** del proyecto (al mismo nivel que la carpeta `src`) y que su nombre coincida con la variable `rutaEntrada` definida en `Main.java`.

### 3. Ejecución según tu entorno

* **Visual Studio Code (VS Code):**

    1. Instala el paquete de extensiones **Extension Pack for Java** de Microsoft.

    2. Abre la carpeta del proyecto (`File > Open Folder`).

    3. Abre el archivo `src/Main.java` y haz clic en el botón **Run** que aparece encima del método `main` (o presiona `F5`).

* **IntelliJ IDEA / Eclipse / NetBeans:**

    1. Abre o importa la carpeta del proyecto desde el menú del IDE.

    2. Ubica el archivo `src/Main.java`, haz clic derecho sobre él y selecciona **Run 'Main.main()'**.

* **Desde la terminal (sin IDE):**
  Sitúate en la carpeta raíz del proyecto y ejecuta los siguientes comandos:

  ```
  javac -d out src/*.java
  java -cp out Main
  
  ```