import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileIOUtils {

    public static BufferedImage loadImage(String imagePath) {
        try {
            File file = new File(imagePath);
            return ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imagePath);
            e.printStackTrace();
            return null;
        }
    }

    public static void saveImage(BufferedImage image, String imagePath, String imageFormat) {
        try {
            File file = new File(imagePath);
            ImageIO.write(image, imageFormat, file);
        } catch (IOException e) {
            System.err.println("Failed to save image: " + imagePath);
            e.printStackTrace();
        }
    }

   
}
