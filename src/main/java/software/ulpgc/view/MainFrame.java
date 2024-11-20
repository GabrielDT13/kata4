package software.ulpgc.view;

import software.ulpgc.control.io.SQLiteTitleReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {

    private final JFreeBarCharHistogram histogramDisplay;
    private JLabel label;
    private final File dbFile;
    private String randomTitle;

    public MainFrame(File dbFile) throws HeadlessException {
        setTitle("Histogram Display");
        setLocationRelativeTo(null);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(BorderLayout.NORTH, toolbar());
        histogramDisplay = new JFreeBarCharHistogram();
        add(histogramDisplay);
        this.dbFile = dbFile;
    }

    public void put(Histogram histogram) {
        histogramDisplay.display(histogram);
    }

    public JPanel toolbar(){
        JPanel jPanel = new JPanel();
        label = new JLabel("");
        jPanel.add(button());
        jPanel.add(label);
        return jPanel;
    }

    private Component button() {
        JButton button = new JButton("Random Title");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomTitle(dbFile);
            }
        });

        return button;
    }

    public void generateRandomTitle(File dbFile){
        try(SQLiteTitleReader reader = new SQLiteTitleReader(dbFile)){
            randomTitle = reader.readRandomTitle();
        } catch (Exception e) {
            System.out.println("Error al leer aleatoriamente" + e.getMessage());
        }

        label.setText(randomTitle);
    }
}
