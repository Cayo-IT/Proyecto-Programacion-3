import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        //imagen a manejar
        String rutaEntrada = "pacman.jpg";
        //carga la imagen en memoria usando el modulo archivos
        BufferedImage imgBase = archivos.cargarimagen(rutaEntrada);

        //si la imagen no se pudo cargar, detiene el programa para evitar NullPointerException
        if (imgBase == null) {
            return;
        }
        //variables auxiliares para medir el tiempo de ejecucion
        long inicio, fin;

        //RescaleOp (Aumento de contraste y brillo)
        inicio = System.nanoTime();
        //multiplica cada canal por 1.3 (30% más contraste) y suma 25 unidades de brillo
        BufferedImage imgRescale = visuales.rescaleop(imgBase, 1.3f, 25.0f);
        fin = System.nanoTime();
        System.out.printf("1. RescaleOp ejecutado en: %.3f ms%n", (fin - inicio) / 1_000_000.0);
        archivos.guardarimagen(imgRescale, "salida/1_rescale_brillo.jpg", "jpg");

        //AffineTransformOp (Rotacion con interpolacion Bilineal)
        inicio = System.nanoTime();
        //Rota, mantiene escala 1.0 (100%) y usa interpolacion bilineal para evitar bordes dentados
        BufferedImage imgAfin = visuales.transformacion(
                imgBase,
                47.0,
                1.0,
                AffineTransformOp.TYPE_BILINEAR
        );

        fin = System.nanoTime();
        System.out.printf("2. AffineTransformOp ejecutado en: %.3f ms%n", (fin - inicio) / 1_000_000.0);
        archivos.guardarimagen(imgAfin, "salida/2_afin_rotacion.jpg", "jpg");

        //ConvolveOp (Filtro de Desenfoque)
        //matriz de 3x3 donde cada vecino aporta 1/9 del valor total (la suma de todos es 1.0 para mantener el brillo)
        float noveno = 1.0f / 25.0f;
        float[] kernelBlur = {
                noveno, noveno, noveno,
                noveno, noveno, noveno,
                noveno, noveno, noveno
        };

        inicio = System.nanoTime();
        BufferedImage imgBlur = visuales.convolve(imgBase, kernelBlur, 3, 3);
        fin = System.nanoTime();
        System.out.printf("3A. ConvolveOp (Blur) ejecutado en: %.3f ms%n", (fin - inicio) / 1_000_000.0);
        archivos.guardarimagen(imgBlur, "salida/3a_convolve_blur.jpg", "jpg");


        //ConvolveOp (Detección de Bordes)
        //matriz donde el centro tiene peso 8 y los vecinos -1 (suma total = 0; zonas uniformes se vuelven negras)
        float[] kernelBordes = {
                -1.0f, -1.0f, -1.0f,
                -1.0f,  8.0f, -1.0f,
                -1.0f, -1.0f, -1.0f
        };

        inicio = System.nanoTime();
        BufferedImage imgBordes = visuales.convolve(imgBase, kernelBordes, 3, 3);
        fin = System.nanoTime();
        System.out.printf("3B. ConvolveOp (Bordes) ejecutado en: %.3f ms%n", (fin - inicio) / 1_000_000.0);
        archivos.guardarimagen(imgBordes, "salida/3b_convolve_bordes.jpg", "jpg");

        //prepara los arreglos con la imagen original y los 4 resultados
        BufferedImage[] galeria = { imgBase, imgRescale, imgAfin, imgBlur, imgBordes };
        String[] nombres = {
                "Original (RGB)",
                "1. RescaleOp (Brillo + Contraste)",
                "2. AffineTransformOp (Rotacion 47°)",
                "3A. ConvolveOp (Box Blur 3x3)",
                "3B. ConvolveOp (Bordes Laplaciano)"
        };

        //abrir ventana con las imagenes
        visuales.mostrargaleria(galeria, nombres);
    }
}