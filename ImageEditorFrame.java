import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageEditorFrame extends JFrame {
    private ImagePanel imagePanel;
    private JButton flipHorizontalButton;
    private JButton rotateButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton resetButton;
    private JButton exitButton;

    public ImageEditorFrame() {
        // Constructor for creating the GUI frame
        setTitle("Image Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);

        flipHorizontalButton = new JButton("Flip Horizontal");
        rotateButton = new JButton("Rotate");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        resetButton = new JButton("Reset");
        exitButton = new JButton("Exit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(flipHorizontalButton);
        buttonPanel.add(rotateButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Set up event listeners
        flipHorizontalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagePanel.flipImageHorizontally();
            }
        });

        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter rotation angle (in degrees):");
                try {
                    double degrees = Double.parseDouble(input);
                    imagePanel.rotateImage(degrees);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
                }
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagePanel.undo();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagePanel.redo();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagePanel.reset();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    
}
