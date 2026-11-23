import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class archivos {

    //leer la imagen
    public static BufferedImage cargarimagen(String ruta) {
        try {
            File archivo = new File(ruta);
            BufferedImage imagenOriginal = ImageIO.read(archivo);

            //verificar si la imagen es valida
            if (imagenOriginal == null) {
                System.err.println("Error: El archivo no es una imagen valida: " + ruta);
                return null;
            }
            //lienzo nuevo en blanco con las mismas dimensiones y formato estándar RGB (sin canal alfa)
            //evita errores cuando imagenes o en escala de grises entran a ConvolveOp o RescaleOp
            BufferedImage imagenEstandar = new BufferedImage(
                    imagenOriginal.getWidth(),
                    imagenOriginal.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2d = imagenEstandar.createGraphics();
            //Copia la imagen original dentro del lienzo estándar
            g2d.drawImage(imagenOriginal, 0, 0, null);
            //Libera los recursos de memoria del pincel grafico
            g2d.dispose();
            return imagenEstandar;
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
    }

    public static void guardarimagen(BufferedImage imagen, String rutaSalida, String formato) {
        try {
            File archivoSalida = new File(rutaSalida);
            if (archivoSalida.getParentFile() != null) {
                archivoSalida.getParentFile().mkdirs();
            }

            //se pide guardar en JPG y la imagen tiene transparencia (ARGB), la pasamos a RGB
            BufferedImage imagenFinal = imagen;
            if (formato.equalsIgnoreCase("jpg") || formato.equalsIgnoreCase("jpeg")) {
                if (imagen.getColorModel().hasAlpha()) {
                    imagenFinal = new BufferedImage(imagen.getWidth(), imagen.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = imagenFinal.createGraphics();
                    g.drawImage(imagen, 0, 0, null);
                    g.dispose();
                }
            }
            boolean exito = ImageIO.write(imagenFinal, formato, archivoSalida);
            if (exito) {
                System.out.println("Imagen guardada exitosamente en: " + rutaSalida);
            } else {
                System.err.println("Error: Formato de imagen no soportado (" + formato + ")");
            }
        } catch (IOException e) {
            System.err.println("Error al guardar la imagen: " + e.getMessage());
        }
    }
}