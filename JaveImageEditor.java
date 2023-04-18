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
