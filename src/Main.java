import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        BufferedImage imgBase = archivos.cargarimagen("pacman.jpg");
        if (imgBase == null) {
            return;
        }

        //brillo y contraste
        BufferedImage imgRescale = visuales.rescaleop(imgBase, 1.3f, 25.0f);
        archivos.guardarimagen(imgRescale, "salida/1_rescale_brillo.jpg", "jpg");

        //rotacion con interpolacion bilineal
        BufferedImage imgAfin = visuales.transformacion(imgBase, 47.0, 1.0, AffineTransformOp.TYPE_BILINEAR);
        archivos.guardarimagen(imgAfin, "salida/2_afin_rotacion.jpg", "jpg");

        //desenfoque
        int tam = 15;
        float valor = 1.0f / (tam * tam);
        float[] kernelBlur = new float[tam * tam];
        Arrays.fill(kernelBlur, valor);

        BufferedImage imgBlur = visuales.convolve(imgBase, kernelBlur, tam, tam);
        archivos.guardarimagen(imgBlur, "salida/3_desenfoque.jpg", "jpg");

        //deteccion de bordes
        float[] kernelBordes = {
                -1.0f, -1.0f, -1.0f,
                -1.0f,  8.0f, -1.0f,
                -1.0f, -1.0f, -1.0f
        };
        BufferedImage imgBordes = visuales.convolve(imgBase, kernelBordes, 3, 3);
        archivos.guardarimagen(imgBordes, "salida/4_bordes.jpg", "jpg");

        //mostrar resultados en una ventana
        BufferedImage[] galeria = { imgBase, imgRescale, imgAfin, imgBlur, imgBordes };
        String[] nombres = {
                "Original",
                "1. Brillo y Contraste",
                "2. Rotacion (47°)",
                "3. Desenfoque",
                "4. Deteccion de Bordes"
        };

        visuales.mostrargaleria(galeria, nombres);
    }
}