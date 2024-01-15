import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutEditorToolsPanel extends JPanel implements GBCLayoutOrganiser{
    LayoutViewerWindow parentWindow;
    Shelf selectedShelf;

    public LayoutEditorToolsPanel(LayoutViewerWindow parentWindow_, Shelf selectedShelf_) {
        parentWindow = parentWindow_;
        selectedShelf = selectedShelf_;


        //Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Shelf Editor");
        headerPanel.add(mainMenuHeader);

        //Create CardLayout for selecting shelves
        JPanel editorTools = new JPanel(new CardLayout());

        //Shelf Not Selected Menu
        JPanel noShelfSelectedMenu = new JPanel(new GridBagLayout());
        JLabel instruction = new JLabel("Please select a shelf or click [Create New Shelf] to create a new one!");
        JButton createNewShelfButton = new JButton("Create New Shelf");
        createNewShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Create New Shelf
            }
        });

        JButton returnButton = new JButton("Return to Search Menu");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });

        noShelfSelectedMenu.add(instruction, createGbc(0, 0));
        noShelfSelectedMenu.add(createNewShelfButton, createGbc(0, 1));
        noShelfSelectedMenu.add(returnButton, createGbc(0, 2));

        editorTools.add(noShelfSelectedMenu, "noShelfSelectedMenu");


        JPanel shelfSelectedMenu = new JPanel(new GridBagLayout());


        //Shelf Name
        //Pos and Size
        //Rotation Buttons
        //Utility Buttons
        //Return to Layout


    }

}
