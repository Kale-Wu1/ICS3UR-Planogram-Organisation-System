import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class LayoutEditorToolsPanel extends JPanel implements GBCLayoutOrganiser, FileUtils {
    LayoutViewerWindow parentWindow;
    Shelf selectedShelf;
    JPanel editorTools;



    //Information Fields
    JTextField shelfNameTextField;
    JTextField xPosTextField;
    JTextField yPosTextField;
    JTextField widthTextField;
    JTextField lengthTextField;
    
    //Error Message Label
    JLabel errorMessageLabel;

    //Current Shelf Rotation
    private int currentShelfRotation;


    public LayoutEditorToolsPanel(LayoutViewerWindow parentWindow_, Shelf selectedShelf_) {
        parentWindow = parentWindow_;
        selectedShelf = selectedShelf_;

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

        //Position textfields
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

        //Size Textfields
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

        //Error Message label
        JPanel errorMessagePanel = new JPanel(new GridBagLayout());
        errorMessageLabel = new JLabel();
        errorMessagePanel.add(errorMessageLabel, createGbc(0, 0));

        //Util Buttons
        JPanel utilButtonsPanel = new JPanel(new GridBagLayout());
        JButton saveCurrentShelfButton = new JButton("Save");
        saveCurrentShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveButtonAction();
            }
        });

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


        JButton newShelfButton = new JButton("Clear Text");
        newShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Set selectedShelf to null
                selectedShelf = null;
                displayShelfInfo(selectedShelf);
            }
        });
        utilButtonsPanel.add(saveCurrentShelfButton, createGbc(1, 0));
        utilButtonsPanel.add(deleteShelfButton, createGbc(2, 0));
        utilButtonsPanel.add(newShelfButton, createGbc(3, 0));


        JPanel returnPanel = new JPanel(new GridBagLayout());
        JButton returnButton = new JButton("Return to Search");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });
        returnPanel.add(returnButton);

        editorTools.add(shelfNamePanel, createGbc(0, 0));
        editorTools.add(positionPanel, createGbc(0, 1));
        editorTools.add(sizePanel, createGbc(0, 2));
        editorTools.add(rotationPanel, createGbc(0, 3));
        editorTools.add(errorMessagePanel, createGbc(0, 4));
        editorTools.add(utilButtonsPanel, createGbc(0, 5));
        editorTools.add(returnPanel, createGbc(0, 6));

        add(headerPanel, createGbc(0, 0));
        add(editorTools, createGbc(0, 1));
    }

    private void updateShelfInfo(Shelf selectedShelf_, boolean isNewShelf) {
        boolean infoIsValid = true;
        //Errorcheck all inputs
        if(shelfNameTextField.getText().isEmpty()) {
            errorMessageLabel.setText("Please enter a valid shelf name!");
            infoIsValid = false;
        } else if(shelfNameTextField.getText().contains("|")) { //TODO: Errorcheck for | character (Does not work here)
            errorMessageLabel.setText("| Character is Invalid");
            infoIsValid = false;
        } else {
            try {

                int xPos = Integer.parseInt(xPosTextField.getText());
                int yPos = Integer.parseInt(yPosTextField.getText());
                int width = Integer.parseInt(widthTextField.getText());
                int length = Integer.parseInt(lengthTextField.getText());

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

        if(infoIsValid) {
            selectedShelf.setName(shelfNameTextField.getText().replaceAll(" ", ""));
            selectedShelf.setXPos(Integer.parseInt(xPosTextField.getText()));
            selectedShelf.setYPos(Integer.parseInt(yPosTextField.getText()));
            selectedShelf.setWidth(Integer.parseInt(widthTextField.getText()));
            selectedShelf.setLength(Integer.parseInt(lengthTextField.getText()));
            selectedShelf.setRotationAngle(currentShelfRotation);
        }
    }

    private void createNewShelf() {
        //TODO: Communicate with layout object to create shelf.
        selectedShelf = new Shelf();
        updateShelfInfo(selectedShelf, true);
        if(!selectedShelf.getName().isEmpty()) {
            parentWindow.getStorageLayout().addShelf(selectedShelf);
            parentWindow.getParentViewPanel().repaint();
        }

    }

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

    private void removeShelf(Shelf shelfToRemove) {
        parentWindow.getStorageLayout().removeShelf(shelfToRemove);
    }


    public void setSelectedShelf(Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
        displayShelfInfo(selectedShelf);
        //setCard(1);
    }

    private Shelf findShelfFromName(String shelfName) {
        for(Shelf shelf : parentWindow.getStorageLayout().getShelfList()) {
            if(shelf.getName().equals(shelfName)) {
                return shelf;
            }
        }
        return null;
    }

    private void saveButtonAction() {
        selectedShelf = findShelfFromName(shelfNameTextField.getText());


        if(selectedShelf == null) { //If name of shelf is not found to exist
            createNewShelf();
        } else { //if current shelf already exists
            updateShelfInfo(selectedShelf, false);
        }

        //Update view panel
        parentWindow.getParentViewPanel().repaint();
    }

    private void rotateShelf() {
        String temp = widthTextField.getText();
        widthTextField.setText(lengthTextField.getText());
        lengthTextField.setText(temp);

        if(currentShelfRotation == 0) {
            currentShelfRotation = 1;
        } else {
            currentShelfRotation = 0;
        }

        if(selectedShelf != null) {
            updateShelfInfo(selectedShelf, false);
        }
    }
}
