import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * The panel containing shelf editing functionality. Used in LayoutViewerWindow.
 */
public class LayoutEditorToolsPanel extends JPanel implements GBCLayoutOrganiser, FileUtils {
    /**
     * This panel's parent window.
     */
    LayoutViewerWindow parentWindow;

    /**
     * The selected shelf.
     */
    Shelf selectedShelf;

    /**
     * The Editor tools panel.
     */
    JPanel editorTools;

    /**
     * The Shelf name text field.
     */
    JTextField shelfNameTextField;

    /**
     * The X pos text field.
     */
    JTextField xPosTextField;

    /**
     * The Y pos text field.
     */
    JTextField yPosTextField;

    /**
     * The Width text field.
     */
    JTextField widthTextField;

    /**
     * The Length text field.
     */
    JTextField lengthTextField;

    /**
     * The Error message label.
     */
    JLabel errorMessageLabel;

    /**
     * Current Shelf Rotation.
     */
    private int currentShelfRotation;


    /**
     * Instantiates a new layout editor tools panel.
     *
     * @param parentWindow_  the parent window
     * @param selectedShelf_ the selected shelf
     */
    public LayoutEditorToolsPanel(LayoutViewerWindow parentWindow_, Shelf selectedShelf_) {
        //Instantiate parentWindow and selected shelf
        parentWindow = parentWindow_;
        selectedShelf = selectedShelf_;

        //Set panel layout
        setLayout(new GridBagLayout());


        //Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Shelf Editor");
        headerPanel.add(mainMenuHeader);

        //Create CardLayout for selecting shelves
        editorTools = new JPanel(new GridBagLayout());

        //Name Textfield
        JPanel shelfNamePanel = new JPanel(new GridBagLayout());
        JLabel shelfNameLabel = new JLabel("Name:");
        shelfNameTextField = selectedShelf == null ? new JTextField() : new JTextField(selectedShelf.getName());
        shelfNamePanel.add(shelfNameLabel, createGbc(0, 0));
        shelfNamePanel.add(shelfNameTextField, createGbc(1, 0));

        //Position text fields
        JPanel positionPanel = new JPanel(new GridBagLayout());
        JLabel positionLabel = new JLabel("Position:");
        JLabel xPosLabel = new JLabel("X:");
        xPosTextField = selectedShelf == null ? new JTextField() : new JTextField(selectedShelf.getXPos());
        JLabel yPosLabel = new JLabel("Y:");
        yPosTextField = selectedShelf == null ? new JTextField() : new JTextField(selectedShelf.getYPos());
        positionPanel.add(positionLabel, createGbc(0, 0));
        positionPanel.add(xPosLabel, createGbc(1, 0));
        positionPanel.add(xPosTextField, createGbc(2, 0));
        positionPanel.add(yPosLabel, createGbc(3, 0));
        positionPanel.add(yPosTextField, createGbc(4, 0));

        //Size text fields
        JPanel sizePanel = new JPanel(new GridBagLayout());
        JLabel sizeLabel = new JLabel("Size:");
        JLabel widthLabel = new JLabel("Width:");
        widthTextField = selectedShelf == null ? new JTextField() : new JTextField(selectedShelf.getWidth());
        JLabel lengthLabel = new JLabel("Length:");
        lengthTextField = selectedShelf == null ? new JTextField() : new JTextField(selectedShelf.getLength());
        sizePanel.add(sizeLabel, createGbc(0, 0));
        sizePanel.add(widthLabel, createGbc(1, 0));
        sizePanel.add(widthTextField, createGbc(2, 0));
        sizePanel.add(lengthLabel, createGbc(3, 0));
        sizePanel.add(lengthTextField, createGbc(4, 0));

        //Rotation Button
        JPanel rotationPanel = new JPanel(new GridBagLayout());
        JLabel rotationLabel = new JLabel("Rotate:");
        JButton rotationButton = new JButton("\uD83D\uDD04");
        rotationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateShelf();

            }
        });
        rotationPanel.add(rotationLabel, createGbc(0, 0));
        rotationPanel.add(rotationButton, createGbc(1, 0));

        //Error message label
        JPanel errorMessagePanel = new JPanel(new GridBagLayout());
        errorMessageLabel = new JLabel();
        errorMessagePanel.add(errorMessageLabel, createGbc(0, 0));

        //Util buttons panel
        JPanel utilButtonsPanel = new JPanel(new GridBagLayout());

        //Save shelf button
        JButton saveCurrentShelfButton = new JButton("Save");
        saveCurrentShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonAction();
            }
        });

        //Delete shelf button
        JButton deleteShelfButton = new JButton("Delete");
        deleteShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedShelf != null) {
                    removeShelf(selectedShelf);
                    selectedShelf = null;
                }
            }
        });

        //Clear text button
        JButton clearTextButton = new JButton("Clear Text");
        clearTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Set selectedShelf to null
                selectedShelf = null;
                displayShelfInfo(selectedShelf);
            }
        });
        utilButtonsPanel.add(saveCurrentShelfButton, createGbc(1, 0));
        utilButtonsPanel.add(deleteShelfButton, createGbc(2, 0));
        utilButtonsPanel.add(clearTextButton, createGbc(3, 0));

        //Return to search menu
        JPanel returnPanel = new JPanel(new GridBagLayout());
        JButton returnButton = new JButton("Return to Search Menu");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });
        returnPanel.add(returnButton);

        //Add all elements to the editorTools panel
        editorTools.add(shelfNamePanel, createGbc(0, 0));
        editorTools.add(positionPanel, createGbc(0, 1));
        editorTools.add(sizePanel, createGbc(0, 2));
        editorTools.add(rotationPanel, createGbc(0, 3));
        editorTools.add(errorMessagePanel, createGbc(0, 4));
        editorTools.add(utilButtonsPanel, createGbc(0, 5));
        editorTools.add(returnPanel, createGbc(0, 6));

        //Add header and editorTools to main LayoutEditorToolsPanel
        add(headerPanel, createGbc(0, 0));
        add(editorTools, createGbc(0, 1));
    }

    /**
     * Error check panel information and update to shelf info if valid.
     * @param selectedShelf_ the selected shelf to update
     * @param isNewShelf whether the selected shelf being updated is new
     */
    private void updateShelfInfo(Shelf selectedShelf_, boolean isNewShelf) {
        //Error check all inputs in panel
        boolean infoIsValid = true;

        if(shelfNameTextField.getText().isEmpty()) { //Error check for empty name text field
            errorMessageLabel.setText("Please enter a valid shelf name!");
            infoIsValid = false;
        } else if(shelfNameTextField.getText().contains("|")) { //Error check for | character (conflict with file)
            errorMessageLabel.setText("| Character is Invalid");
            infoIsValid = false;
        } else {
            //Error check numerical values for position
            try {
                //Check for valid number
                int xPos = Integer.parseInt(xPosTextField.getText());
                int yPos = Integer.parseInt(yPosTextField.getText());
                int width = Integer.parseInt(widthTextField.getText());
                int length = Integer.parseInt(lengthTextField.getText());

                //Check for valid position on screen
                if(xPos < 0 || xPos > parentWindow.getStorageLayout().getroomWidth()) {
                    errorMessageLabel.setText("Please enter a valid x position (0 - " + parentWindow.getStorageLayout().getroomWidth() + ").");
                    infoIsValid = false;
                } else if(yPos < 0 || yPos > parentWindow.getStorageLayout().getroomLength()) {
                    errorMessageLabel.setText("Please enter a valid y position (0 - " + parentWindow.getStorageLayout().getroomLength() + ").");
                    infoIsValid = false;
                } else if(width < 1 || width > parentWindow.getStorageLayout().getroomWidth()-xPos) {
                    errorMessageLabel.setText("Please enter a valid width (1 - " + (parentWindow.getStorageLayout().getroomWidth()-xPos) + ").");
                    infoIsValid = false;
                } else if(length < 1 || length > parentWindow.getStorageLayout().getroomLength()-yPos) {
                    errorMessageLabel.setText("Please enter a valid length (1 - " + (parentWindow.getStorageLayout().getroomLength()-yPos) + ").");
                    infoIsValid = false;
                } else {
                    Shelf tempShelf = new Shelf("", xPos, yPos, length, width, currentShelfRotation, new String[0]);
                    if(isNewShelf) {
                        //Compare this shelf's position to all existing shelves
                        for (Shelf shelf : parentWindow.getStorageLayout().getShelfList()) {
                            if (isIntersecting(shelf, tempShelf)) {
                                errorMessageLabel.setText("Shelves cannot intersect!");
                                infoIsValid = false;
                                break;
                            }
                        }
                    } else {
                        //Compare this shelf's position to all existing shelves except itself
                        for (Shelf shelf : parentWindow.getStorageLayout().getShelfList()) {
                            if (isIntersecting(shelf, tempShelf) && shelf != selectedShelf) {
                                errorMessageLabel.setText("Shelves cannot intersect!");
                                infoIsValid = false;
                                break;
                            }
                        }
                    }
                }

            } catch (NumberFormatException e) {
                errorMessageLabel.setText("Please enter valid positive integer values for shelf position and size.");
                infoIsValid = false;
            }
        }

        //Update selectedShelf information if information is valid
        if(infoIsValid) {
            selectedShelf.setName(shelfNameTextField.getText().replaceAll(" ", ""));
            selectedShelf.setXPos(Integer.parseInt(xPosTextField.getText()));
            selectedShelf.setYPos(Integer.parseInt(yPosTextField.getText()));
            selectedShelf.setWidth(Integer.parseInt(widthTextField.getText()));
            selectedShelf.setLength(Integer.parseInt(lengthTextField.getText()));
            selectedShelf.setRotationAngle(currentShelfRotation);
        }
    }

    /**
     * Create new shelf object and add to layout
     */
    private void createNewShelf() {
        //Create new shelf and update with entered information
        selectedShelf = new Shelf();
        updateShelfInfo(selectedShelf, true);

        //Add new shelf to layout if information is valid
        if(!selectedShelf.getName().isEmpty()) {
            parentWindow.getStorageLayout().addShelf(selectedShelf);
            parentWindow.getParentViewPanel().repaint();
        }

    }

    /**
     * Check if two shelves are intersection
     * @param shelf1 the first shelf
     * @param shelf2 the second shelf
     * @return if shelf1 is intersecting shelf2
     */
    private static boolean isIntersecting(Shelf shelf1, Shelf shelf2) {
        boolean xOverlaps = false;
        boolean yOverlaps = false;

        if((shelf1.getXPos() >= shelf2.getXPos() && shelf1.getXPos() <= shelf2.getXPos()+shelf2.getWidth()) || (shelf2.getXPos() >= shelf1.getXPos() && shelf2.getXPos() <= shelf1.getXPos()+shelf1.getWidth())) {
            xOverlaps = true;
        }
        if((shelf1.getYPos() >= shelf2.getYPos() && shelf1.getYPos() <= shelf2.getYPos()+shelf2.getLength()) || (shelf2.getYPos() >= shelf1.getYPos() && shelf2.getYPos() <= shelf1.getYPos()+shelf1.getLength())) {
            yOverlaps = true;
        }

        return xOverlaps && yOverlaps;
    }

    /**
     * Display selectedShelf information to the EditorToolsPanel
     * @param selectedShelf the shelf to retrieve information
     */
    private void displayShelfInfo(Shelf selectedShelf) {
        if(selectedShelf == null) {
            //Empty all text boxes
            shelfNameTextField.setText("");
            xPosTextField.setText("");
            yPosTextField.setText("");
            widthTextField.setText("");
            lengthTextField.setText("");
        } else {
            //Set all text boxes to selectedShelf info
            shelfNameTextField.setText(selectedShelf.getName());
            xPosTextField.setText(Integer.toString(selectedShelf.getXPos()));
            yPosTextField.setText(Integer.toString(selectedShelf.getYPos()));
            widthTextField.setText(Integer.toString(selectedShelf.getWidth()));
            lengthTextField.setText(Integer.toString(selectedShelf.getLength()));
        }
    }

    /**
     * Remove shelf from layout.
     * @param shelfToRemove the shelf to remove from the layout
     */
    private void removeShelf(Shelf shelfToRemove) {
        parentWindow.getStorageLayout().removeShelf(shelfToRemove);
    }


    /**
     * Sets selected shelf.
     * @param selectedShelf_ the shelf to select
     */
    public void setSelectedShelf(Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
        displayShelfInfo(selectedShelf);
        //setCard(1);
    }

    /**
     * Returns shelf from currently opened layout. Returns null if shelf with appropriate name is not found.
     * @param shelfName the name of shelf to retrieve
     * @return the shelf from the layout with the corresponding name
     */
    private Shelf findShelfFromName(String shelfName) {
        for(Shelf shelf : parentWindow.getStorageLayout().getShelfList()) {
            if(shelf.getName().equals(shelfName)) {
                return shelf;
            }
        }
        return null;
    }

    /**
     * Creates new shelf if one with currently entered name does not exist. Updates existing shelf if shelf already exists.
     */
    private void saveButtonAction() {
        selectedShelf = findShelfFromName(shelfNameTextField.getText());

        if(selectedShelf == null) { //If name of shelf is not found to exist, create new shelf
            createNewShelf();
        } else { //If current shelf already exists, update selectedShelf with new information
            updateShelfInfo(selectedShelf, false);
        }

        //Update view panel
        parentWindow.getParentViewPanel().repaint();
    }

    /**
     * Rotates selected shelf and updates visuals.
     */
    private void rotateShelf() {
        //Swap current shelf's length and width
        String temp = widthTextField.getText();
        widthTextField.setText(lengthTextField.getText());
        lengthTextField.setText(temp);

        //Switch current shelf rotation value
        if(currentShelfRotation == 0) {
            currentShelfRotation = 1;
        } else {
            currentShelfRotation = 0;
        }

        //Update selected shelf values
        if(selectedShelf != null) {
            updateShelfInfo(selectedShelf, false);
        }
    }
}
