import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The panel containing instructions for the program. Used in MainMenuWindow.
 */
public class InstructionsPanel extends JPanel implements GBCLayoutOrganiser{
    private final MainMenuWindow parentWindow;


    /**
     * Instantiates a new Instructions panel.
     *
     * @param parentWindow_ the parent window
     */
    public InstructionsPanel(MainMenuWindow parentWindow_) {
        parentWindow = parentWindow_;
        //Set layout manager for this panel
        setLayout(new GridBagLayout());

        //Add Instructions
        final String INSTRUCTIONS_TEXT = "<html>The planogram storage system is a workplace organisation system developed by Kale Wu for ICS3UR." +
                "<br><br>The program works by visually representing a workspace via \"shelves\" in a \"layout\". Each shelf contains items which are stored." +
                "<br><br>To begin, create a new layout and fill out the appropriate information. Then create new shelves by clicking the appropriate buttons and filling out the information." +
                "<br><br>Items may be added, removed, or changed from a shelf by clicking on the shelf which in the search menu, and accessing them from the item list on the right hand side of the screen directly. " +
                "<br><br>Shelf characteristics may be adjusted by accessing the \"Edit Shelf\" menu, and clicking on the shelf that requires changes." +
                "<br><br>After changes to a shelf are performed, please remember to save your changes with the appropriate button. Item changes are saved automatically when the layout is exited." +
                "<br><br>When finished with a layout, return to the main menu to save your changes.";

        JPanel instructionsPanel = new JPanel(new GridBagLayout());
        JLabel instructionsHeader = new JLabel("Instructions");
        JLabel instructionsLabel = new JLabel(INSTRUCTIONS_TEXT);
        instructionsLabel.setFont(new Font("Courier", Font.PLAIN, 12));

        instructionsPanel.add(instructionsHeader, createGbc(0, 0));
        instructionsPanel.add(instructionsLabel, createGbc(0, 1));

        //Add Return Button
        JPanel returnButtonPanel = new JPanel(new GridBagLayout());
        JButton returnButton = new JButton("Return to Main Menu");

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });
        returnButtonPanel.add(returnButton, createGbc(0, 0));

        add(instructionsPanel, createGbc(0, 0));
        add(returnButtonPanel, createGbc(0, 1));
    }
}