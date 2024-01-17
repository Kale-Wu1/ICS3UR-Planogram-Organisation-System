import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutEditorToolsPanel extends JPanel implements GBCLayoutOrganiser{
    LayoutViewerWindow parentWindow;
    Shelf selectedShelf;
    JPanel editorTools;

    public LayoutEditorToolsPanel(LayoutViewerWindow parentWindow_, Shelf selectedShelf_) {
        parentWindow = parentWindow_;
        selectedShelf = selectedShelf_;


        //Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Shelf Editor");
        headerPanel.add(mainMenuHeader);

        //Create CardLayout for selecting shelves
        editorTools = new JPanel(new CardLayout());
        editorTools.add(noShelfSelectedMenu(), "noShelfSelectedMenu");







        //Shelf Name
        //Pos and Size
        //Rotation Buttons
        //Utility Buttons
        //Return to Layout


    }

    private JPanel noShelfSelectedMenu() {
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

        return noShelfSelectedMenu;
    }

    private JPanel shelfSelectedMenu() {
        JPanel shelfSelectedMenu = new JPanel(new GridBagLayout());

        JPanel shelfNamePanel = new JPanel(new GridBagLayout());
        JLabel shelfNameLabel = new JLabel("Name:");
        JTextField shelfNameTextField = new JTextField();
        shelfNamePanel.add(shelfNameLabel, createGbc(0, 0));
        shelfNamePanel.add(shelfNameTextField, createGbc(1, 0));

        JPanel positionPanel = new JPanel(new GridBagLayout());
        JLabel positionLabel = new JLabel("Position:");
        JLabel xPosLabel = new JLabel("X:");
        JTextField xPosTextField = new JTextField();
        JLabel yPosLabel = new JLabel("Y:");
        JTextField yPosTextField = new JTextField();
        positionPanel.add(positionLabel, createGbc(0, 0));
        positionPanel.add(xPosLabel, createGbc(1, 0));
        positionPanel.add(xPosTextField, createGbc(2, 0));
        positionPanel.add(yPosLabel, createGbc(3, 0));
        positionPanel.add(yPosTextField, createGbc(4, 0));

        JPanel sizePanel = new JPanel(new GridBagLayout());
        JLabel sizeLabel = new JLabel("Size:");
        JLabel widthLabel = new JLabel("Length:");
        JTextField widthTextField = new JTextField();
        JLabel lengthLabel = new JLabel("Length:");
        JTextField lengthTextField = new JTextField();
        sizePanel.add(sizeLabel, createGbc(0, 0));
        sizePanel.add(widthLabel, createGbc(1, 0));
        sizePanel.add(widthTextField, createGbc(2, 0));
        sizePanel.add(lengthLabel, createGbc(3, 0));
        sizePanel.add(lengthTextField, createGbc(4, 0));

        JPanel rotationPanel = new JPanel(new GridBagLayout());
        JLabel rotationLabel = new JLabel("Rotate:");
        JButton rotationButton = new JButton("\uD83D\uDD04");
        rotationPanel.add(rotationLabel, createGbc(0, 0));
        rotationPanel.add(rotationButton, createGbc(1, 0));

        //TODO: Finish Adding these and draw shelves
        JPanel utilButtonsPanel = new JPanel(new GridBagLayout());
        JButton rotationButton = new JButton("\uD83D\uDD04");
        JButton rotationButton = new JButton("\uD83D\uDD04");
        JPanel returnPanel = new JPanel(new GridBagLayout());
    }
    private void createShelf() {

    }

}
