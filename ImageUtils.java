import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static BufferedImage flipHorizontal(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();
        return flippedImage;
    }

    public static BufferedImage rotateClockwise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        Graphics2D g = rotatedImage.createGraphics();
        g.rotate(Math.toRadians(90), rotatedImage.getWidth() / 2.0, rotatedImage.getHeight() / 2.0);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return rotatedImage;
    }

   
}
