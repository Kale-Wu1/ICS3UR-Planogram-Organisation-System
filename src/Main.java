import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JFrame{
    //Parent Panel
    JPanel parentPanel;

    //Organisation Panels
    JPanel mainMenuPanel;
    JPanel instructionsPanel;
    JPanel layoutCreatorPanel;

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

        //Initialise Component Panels
        mainMenuPanel = new MainMenuPanel(this);
        instructionsPanel = new InstructionsPanel(this);
        layoutCreatorPanel = new LayoutCreatorPanel(this);


        //Add Components to parentPanel
        parentPanel.add(mainMenuPanel, "mainMenu");
        parentPanel.add(instructionsPanel, "instructions");
        parentPanel.add(layoutCreatorPanel, "layoutCreator");

        //Add Parent Panel to Window
        add(parentPanel);

        setVisible(true);
    }

    public void setCard(int card) {
        switch(card) {
            case 0:
                mainMenuPanel.setVisible(true);
                instructionsPanel.setVisible(false);
                layoutCreatorPanel.setVisible(false);
                break;
            case 1:
                mainMenuPanel.setVisible(false);
                instructionsPanel.setVisible(true);
                layoutCreatorPanel.setVisible(false);
                break;
            case 2:
                mainMenuPanel.setVisible(false);
                instructionsPanel.setVisible(false);
                layoutCreatorPanel.setVisible(true);
                break;
        }
    }
}