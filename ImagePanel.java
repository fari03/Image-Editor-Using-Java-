import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ImagePanel extends JPanel {
    private BufferedImage image;
    private Stack<BufferedImage> undoStack;
    private Stack<BufferedImage> redoStack;

    public ImagePanel() {
        // Constructor for creating the image panel
        setBackground(Color.WHITE);
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Handle mouse press event
                // Add your implementation here
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Handle mouse release event
                // Add your implementation here
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Handle mouse drag event
                // Add your implementation here
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    
}
