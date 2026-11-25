import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class archivos {

    public static BufferedImage cargarimagen(String ruta) {
        try {
            BufferedImage original = ImageIO.read(new File(ruta));
            if (original == null) {
                System.out.println("No se pudo leer la imagen: " + ruta);
                return null;
            }

            //convertir a RGB
            BufferedImage imagenRGB = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g = imagenRGB.createGraphics();
            g.drawImage(original, 0, 0, null);
            g.dispose();

            return imagenRGB;
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
            return null;
        }
    }

    public static void guardarimagen(BufferedImage imagen, String rutaSalida, String formato) {
        try {
            File archivoSalida = new File(rutaSalida);
            if (archivoSalida.getParentFile() != null) {
                archivoSalida.getParentFile().mkdirs();
            }

            if (ImageIO.write(imagen, formato, archivoSalida)) {
                System.out.println("Guardada en: " + rutaSalida);
            } else {
                System.out.println("Formato no soportado: " + formato);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}