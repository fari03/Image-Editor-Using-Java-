import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JavaImageEditor extends JFrame {

    private JLabel imageLabel;
    private BufferedImage image;

    public JavaImageEditor() {
        setTitle("Java Image Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openImage();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem resizeMenuItem = new JMenuItem("Resize");
        JMenuItem rotateMenuItem = new JMenuItem("Rotate");
        JMenuItem cropMenuItem = new JMenuItem("Crop");
        JMenuItem grayscaleMenuItem = new JMenuItem("Grayscale");
        JMenuItem sepiaMenuItem = new JMenuItem("Sepia");

        resizeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showResizeDialog();
            }
        });

        rotateMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRotateDialog();
            }
        });

        cropMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCropDialog();
            }
        });

        grayscaleMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyGrayscale();
            }
        });

        sepiaMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applySepia();
            }
        });

        editMenu.add(resizeMenuItem);
        editMenu.add(rotateMenuItem);
        editMenu.add(cropMenuItem);
        editMenu.add(grayscaleMenuItem);
        editMenu.add(sepiaMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        imageLabel = new JLabel();
        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(selectedFile);
                imageLabel.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to open image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveImage() {
        if (image != null) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    String formatName = selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.') + 1);
                    ImageIO.write(image, formatName, selectedFile);
                } catch (IOException ex)
                                        JOptionPane.showMessageDialog(this, "Failed to save image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showResizeDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter new width and height separated by comma (e.g. 800,600):", "Resize Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] dimensions = input.split(",");
                if (dimensions.length == 2) {
                    try {
                        int newWidth = Integer.parseInt(dimensions[0].trim());
                        int newHeight = Integer.parseInt(dimensions[1].trim());
                        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
                        Graphics2D g = resizedImage.createGraphics();
                        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g.drawImage(image, 0, 0, newWidth, newHeight, null);
                        g.dispose();
                        image = resizedImage;
                        imageLabel.setIcon(new ImageIcon(image));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid integer values for width and height.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter width and height separated by comma.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showRotateDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter rotation angle in degrees (e.g. 90 for clockwise rotation):", "Rotate Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                try {
                    int angle = Integer.parseInt(input.trim());
                    double radian = Math.toRadians(angle);
                    double sin = Math.abs(Math.sin(radian));
                    double cos = Math.abs(Math.cos(radian));
                    int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
                    int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);
                    BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
                    Graphics2D g = rotatedImage.createGraphics();
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.rotate(radian, newWidth / 2, newHeight / 2);
                    g.drawImage(image, 0, 0, null);
                    g.dispose();
                    image = rotatedImage;
                    imageLabel.setIcon(new ImageIcon(image));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer value for rotation angle.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showCropDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter x, y, width, and height separated by comma (e.g. 100,100,300,200):", "Crop Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] coordinates = input.split(",");
                if (coordinates.length == 4) {
                    try {
                        int x = Integer.parseInt(coordinates[0].trim());
                        int y = Integer.parseInt(coordinates[1].trim());
                        int width = Integer.parseInt(coordinates[2].trim());
                        int height = Integer.parseInt(coordinates[3].trim());
                        if (x >=                     JOptionPane.showMessageDialog(this, "Failed to save image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showResizeDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter new width and height separated by comma (e.g. 800,600):", "Resize Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] dimensions = input.split(",");
                if (dimensions.length == 2) {
                    try {
                        int newWidth = Integer.parseInt(dimensions[0].trim());
                        int newHeight = Integer.parseInt(dimensions[1].trim());
                        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
                        Graphics2D g = resizedImage.createGraphics();
                        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g.drawImage(image, 0, 0, newWidth, newHeight, null);
                        g.dispose();
                        image = resizedImage;
                        imageLabel.setIcon(new ImageIcon(image));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid integer values for width and height.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter width and height separated by comma.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showRotateDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter rotation angle in degrees (e.g. 90 for clockwise rotation):", "Rotate Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                try {
                    int angle = Integer.parseInt(input.trim());
                    double radian = Math.toRadians(angle);
                    double sin = Math.abs(Math.sin(radian));
                    double cos = Math.abs(Math.cos(radian));
                    int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
                    int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);
                    BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
                    Graphics2D g = rotatedImage.createGraphics();
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.rotate(radian, newWidth / 2, newHeight / 2);
                    g.drawImage(image, 0, 0, null);
                    g.dispose();
                    image = rotatedImage;
                    imageLabel.setIcon(new ImageIcon(image));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer value for rotation angle.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showCropDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter x, y, width, and height separated by comma (e.g. 100,100,300,200):", "Crop Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] coordinates = input.split(",");
                if (coordinates.length == 4) {
                    try {
                        int x = Integer.parseInt(coordinates[0].trim());
                        int y = Integer.parseInt(coordinates[1].trim());
                        int width = Integer.parseInt(coordinates[2].trim());
                        int height = Integer.parseInt(coordinates[3].trim());
                        if (x >=                     JOptionPane.showMessageDialog(this, "Failed to save image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showResizeDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter new width and height separated by comma (e.g. 800,600):", "Resize Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] dimensions = input.split(",");
                if (dimensions.length == 2) {
                    try {
                        int newWidth = Integer.parseInt(dimensions[0].trim());
                        int newHeight = Integer.parseInt(dimensions[1].trim());
                        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
                        Graphics2D g = resizedImage.createGraphics();
                        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g.drawImage(image, 0, 0, newWidth, newHeight, null);
                        g.dispose();
                        image = resizedImage;
                        imageLabel.setIcon(new ImageIcon(image));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid integer values for width and height.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter width and height separated by comma.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showRotateDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter rotation angle in degrees (e.g. 90 for clockwise rotation):", "Rotate Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                try {
                    int angle = Integer.parseInt(input.trim());
                    double radian = Math.toRadians(angle);
                    double sin = Math.abs(Math.sin(radian));
                    double cos = Math.abs(Math.cos(radian));
                    int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
                    int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);
                    BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
                    Graphics2D g = rotatedImage.createGraphics();
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.rotate(radian, newWidth / 2, newHeight / 2);
                    g.drawImage(image, 0, 0, null);
                    g.dispose();
                    image = rotatedImage;
                    imageLabel.setIcon(new ImageIcon(image));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer value for rotation angle.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showCropDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter x, y, width, and height separated by comma (e.g. 100,100,300,200):", "Crop Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] coordinates = input.split(",");
                if (coordinates.length == 4) {
                    try {
                        int x = Integer.parseInt(coordinates[0].trim());
                        int y = Integer.parseInt(coordinates[1].trim());
                        int width = Integer.parseInt(coordinates[2].trim());
                        int height = Integer.parseInt(coordinates[3].trim());
                        if (x >=                     JOptionPane.showMessageDialog(this, "Failed to save image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showResizeDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter new width and height separated by comma (e.g. 800,600):", "Resize Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] dimensions = input.split(",");
                if (dimensions.length == 2) {
                    try {
                        int newWidth = Integer.parseInt(dimensions[0].trim());
                        int newHeight = Integer.parseInt(dimensions[1].trim());
                        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
                        Graphics2D g = resizedImage.createGraphics();
                        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        g.drawImage(image, 0, 0, newWidth, newHeight, null);
                        g.dispose();
                        image = resizedImage;
                        imageLabel.setIcon(new ImageIcon(image));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid integer values for width and height.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter width and height separated by comma.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showRotateDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter rotation angle in degrees (e.g. 90 for clockwise rotation):", "Rotate Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                try {
                    int angle = Integer.parseInt(input.trim());
                    double radian = Math.toRadians(angle);
                    double sin = Math.abs(Math.sin(radian));
                    double cos = Math.abs(Math.cos(radian));
                    int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
                    int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);
                    BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
                    Graphics2D g = rotatedImage.createGraphics();
                    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g.rotate(radian, newWidth / 2, newHeight / 2);
                    g.drawImage(image, 0, 0, null);
                    g.dispose();
                    image = rotatedImage;
                    imageLabel.setIcon(new ImageIcon(image));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer value for rotation angle.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showCropDialog() {
        if (image != null) {
            String input = JOptionPane.showInputDialog(this, "Enter x, y, width, and height separated by comma (e.g. 100,100,300,200):", "Crop Image", JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.isEmpty()) {
                String[] coordinates = input.split(",");
                if (coordinates.length == 4) {
                    try {
                        int x = Integer.parseInt(coordinates[0].trim());
                        int y = Integer.parseInt(coordinates[1].trim());
                        int width = Integer.parseInt(coordinates[2].trim());
                        int height = Integer.parseInt(coordinates[3].trim());
                        if (x >= 



