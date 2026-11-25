import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.swing.*;

public class visuales {

    //brillo y contraste
    public static BufferedImage rescaleop(BufferedImage origen, float contraste, float brillo) {
        RescaleOp operador = new RescaleOp(contraste, brillo, null);
        return operador.filter(origen, null);
    }

    //rotacion y escala
    public static BufferedImage transformacion(BufferedImage origen, double grados, double escala, int tipoInterpolacion) {
        double centroX = origen.getWidth() / 2.0;
        double centroY = origen.getHeight() / 2.0;

        AffineTransform matriz = new AffineTransform();
        matriz.rotate(Math.toRadians(grados), centroX, centroY);
        matriz.scale(escala, escala);

        AffineTransformOp operador = new AffineTransformOp(matriz, tipoInterpolacion);
        return operador.filter(origen, null);
    }

    //filtro con matriz Kernel
    public static BufferedImage convolve(BufferedImage origen, float[] matrizKernel, int anchoKernel, int altoKernel) {
        Kernel kernel = new Kernel(anchoKernel, altoKernel, matrizKernel);
        ConvolveOp operador = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return operador.filter(origen, null);
    }

    public static void mostrargaleria(BufferedImage[] imagenes, String[] titulos) {
        JFrame ventana = new JFrame("Procesamiento de Imagenes");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1280, 720);
        ventana.setLocationRelativeTo(null);

        JPanel panelCuadricula = new JPanel(new GridLayout(2, 3, 15, 15));
        panelCuadricula.setBackground(new Color(30, 30, 30));
        panelCuadricula.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (int i = 0; i < imagenes.length; i++) {
            int anchoOriginal = imagenes[i].getWidth();
            int altoOriginal = imagenes[i].getHeight();

            double factor = Math.min(380.0 / anchoOriginal, 280.0 / altoOriginal);
            int nuevoAncho = (int) (anchoOriginal * factor);
            int nuevoAlto = (int) (altoOriginal * factor);

            Image miniatura = imagenes[i].getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);

            JLabel etiqueta = new JLabel(titulos[i], new ImageIcon(miniatura), SwingConstants.CENTER);
            etiqueta.setVerticalTextPosition(SwingConstants.BOTTOM);
            etiqueta.setHorizontalTextPosition(SwingConstants.CENTER);
            etiqueta.setForeground(Color.WHITE);
            etiqueta.setFont(new Font("SansSerif", Font.BOLD, 14));
            panelCuadricula.add(etiqueta);
        }

        ventana.add(panelCuadricula);
        ventana.setVisible(true);
    }
}