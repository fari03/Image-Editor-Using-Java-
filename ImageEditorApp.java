import javax.swing.SwingUtilities;

public class ImageEditorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageEditorFrame frame = new ImageEditorFrame();
            frame.setVisible(true);
        });
    }
}
