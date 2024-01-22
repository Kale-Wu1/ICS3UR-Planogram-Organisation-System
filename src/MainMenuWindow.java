import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The Main Menu Window from which layouts can be opened or created and instructions can be read.
 */
public class MainMenuWindow extends JFrame{
    /**
     * The Parent panel.
     */
    JPanel parentPanel;

    /**
     * The Main menu panel.
     */
    JPanel mainMenuPanel;

    /**
     * The Instructions panel.
     */
    JPanel instructionsPanel;

    /**
     * The Layout creator panel.
     */
    JPanel layoutCreatorPanel;

    /**
     * The File arr.
     */
    File[] fileArr = new File[0];

    /**
     * The Search directory.
     */
    String searchDirectory = null;

    /**
     * Instantiates a new Main menu window.
     */
    public MainMenuWindow() {
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

    /**
     * Sets card for menu. Used to switch between menus on this window.
     * @param card the card index for the desired menu
     */
    public void setCard(int card) {
        switch(card) {
            case 0: //Display main menu
                mainMenuPanel.setVisible(true);
                instructionsPanel.setVisible(false);
                layoutCreatorPanel.setVisible(false);
                break;
            case 1: //Display instructions
                mainMenuPanel.setVisible(false);
                instructionsPanel.setVisible(true);
                layoutCreatorPanel.setVisible(false);
                break;
            case 2: //Display layout creator menu
                mainMenuPanel.setVisible(false);
                instructionsPanel.setVisible(false);
                layoutCreatorPanel.setVisible(true);
                break;
        }
    }
}