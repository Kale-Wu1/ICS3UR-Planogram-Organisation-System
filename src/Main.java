import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JFrame{
    //Parent Panel
    JPanel parentPanel;

    //Organisation Panels
    JPanel headerPanel;
    JPanel availableLayoutsPanel;
    JPanel searchDirectoryPanel;
    JPanel utilityButtonsPanel;
    JPanel exitButtonPanel;

    //Variables
    File[] fileArr = new File[0];
    String searchDirectory = null;

    DefaultListModel<String> testList;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        //Initialise Window
        final int FRAME_WIDTH = 600;
        final int FRAME_LENGTH = 750;
        final int INDENT_GAP = 50;

        //Set values for window
        setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        setTitle("Planogram v.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Initialise parentPanel
        parentPanel = new JPanel(new CardLayout());

        //Add Components to parentPanel
        parentPanel.add(new MainMenuPanel(), "mainMenu");
        //parentPanel.add(instructions, "instructions");
        //parentPanel.add(layoutCreator, "layoutCreator");

        //Add Parent Panel to Window
        add(parentPanel);

        setVisible(true);
    }

}