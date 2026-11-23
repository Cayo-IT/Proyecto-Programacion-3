import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

//librerias para interfaz grafica
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class visuales {
    //RescaleOp: Operaciones puntuales (Brillo y Contraste)

    public static BufferedImage rescaleop(BufferedImage origen, float contraste, float brillo) {
        //contraste (scaleFactor): 1.0f mantiene igual, >1.0f aumenta contraste, <1.0f reduce
        //brillo (offset): 0.0f mantiene igual, >0 aclara, <0 oscurece
        //null: sin sugerencias de renderizado extra (RenderingHints)
        RescaleOp operador = new RescaleOp(contraste, brillo, null);

        //aplica el filtro sobre la imagen 'origen'. Al pasar 'null' como segundo parámetro,
        //java crea y devuelve automáticamente un nuevo BufferedImage con el resultado
        return operador.filter(origen, null);
    }

    //AffineTransformOp: Transformaciones geometricas (Rotación y Escala)
    //mapea coordenadas (x, y) a (x', y') usando matrices de 3x3
    public static BufferedImage transformacion(BufferedImage origen, double grados, double escala, int tipoInterpolacion) {
        //calcula el punto central de la imagen para que rote sobre su propio eje y no desde la esquina (0,0)
        double centroX = origen.getWidth() / 2.0;
        double centroY = origen.getHeight() / 2.0;

        //instancia la matriz de transformacion afin (inicia como matriz identidad)
        AffineTransform matriz = new AffineTransform();
        //aplica la rotacion convirtiendo los grados a radianes, anclada al centro de la imagen
        matriz.rotate(Math.toRadians(grados), centroX, centroY);
        //aplica el escalado (zoom in / zoom out) en ambos ejes (X e Y)
        matriz.scale(escala, escala);

        //AffineTransformOp.TYPE_NEAREST_NEIGHBOR (rápido, pixelado)
        //AffineTransformOp.TYPE_BILINEAR (intermedio, suavizado)
        //AffineTransformOp.TYPE_BICUBIC (lento, alta calidad)
        AffineTransformOp operador = new AffineTransformOp(matriz, tipoInterpolacion);

        //Ejecuta la transformación espacial y retorna la nueva imagen resultante
        return operador.filter(origen, null);
    }

    //ConvolveOp: Operaciones de vecindario (Filtros espaciales mediante Kernel)
    public static BufferedImage convolve(BufferedImage origen, float[] matrizKernel, int anchoKernel, int altoKernel) {
        Kernel kernel = new Kernel(anchoKernel, altoKernel, matrizKernel);
        //kernel: la matriz de pesos a deslizar sobre la imagen
        //ConvolveOp.EDGE_NO_OP: indica que los pixeles de las orillas (donde el kernel se sale) se copian sin cambios
        //null: sin RenderingHints adicionales
        ConvolveOp operador = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        //Desliza el kernel por toda la matriz de píxeles y devuelve la imagen filtrada
        return operador.filter(origen, null);
    }

    //visor swing: Muestra un arreglo de imágenes lado a lado con sus títulos
    public static void mostrargaleria(BufferedImage[] imagenes, String[] titulos) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Procesamiento de Imagenes");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cierra el programa al cerrar la ventana
            ventana.setSize(1280, 720); //Tamaño inicial cómodo para proyector
            ventana.setLocationRelativeTo(null); //Centra la ventana en el monitor

            //panel en cuadrícula de 2 filas y 3 columnas con 15px de separación
            JPanel panelCuadricula = new JPanel(new GridLayout(2, 3, 15, 15));
            panelCuadricula.setBackground(new Color(30, 30, 30)); // Fondo oscuro para que resalten los filtros
            panelCuadricula.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            //recorrer cada imagen para escalarla a miniatura de vista previa y ponerle etiqueta
            for (int i = 0; i < imagenes.length; i++) {
                //Calcula una escala proporcional para que la imagen quepa sin deformarse
                int anchoOriginal = imagenes[i].getWidth();
                int altoOriginal = imagenes[i].getHeight();
                double factor = Math.min(380.0 / anchoOriginal, 280.0 / altoOriginal);
                int nuevoAncho = (int) (anchoOriginal * factor);
                int nuevoAlto = (int) (altoOriginal * factor);

                //copia escalada únicamente para visualizarla en el JLabel
                Image miniatura = imagenes[i].getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);

                //JLabel que contiene tanto la imagen (ImageIcon) como el texto debajo
                JLabel etiqueta = new JLabel(titulos[i], new ImageIcon(miniatura), SwingConstants.CENTER);
                etiqueta.setVerticalTextPosition(SwingConstants.BOTTOM);
                etiqueta.setHorizontalTextPosition(SwingConstants.CENTER);
                etiqueta.setForeground(Color.WHITE);
                etiqueta.setFont(new Font("SansSerif", Font.BOLD, 14));
                panelCuadricula.add(etiqueta);
            }

            //cuadricula a la ventana y que sea visible
            ventana.add(panelCuadricula);
            ventana.setVisible(true);
        });
    }
}