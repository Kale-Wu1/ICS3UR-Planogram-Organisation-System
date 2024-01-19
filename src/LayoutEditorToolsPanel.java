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
    //Menu Cards
    JPanel noShelfSelectedMenu;
    JPanel shelfSelectedMenu;


    //Information Fields
    JTextField shelfNameTextField;
    JTextField xPosTextField;
    JTextField yPosTextField;
    JTextField widthTextField;
    JTextField lengthTextField;

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
        editorTools = new JPanel(new CardLayout());
        noShelfSelectedMenu = noShelfSelectedMenu();
        shelfSelectedMenu = shelfSelectedMenu();
        editorTools.add(noShelfSelectedMenu, "noShelfSelectedMenu");
        editorTools.add(shelfSelectedMenu, "shelfSelectedMenu");

        add(headerPanel, createGbc(0, 0));
        add(editorTools, createGbc(0, 1));

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
                setCard(1);
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

    private void setCard(int card) {
        switch(card) {
            case 0:
                noShelfSelectedMenu.setVisible(true);
                shelfSelectedMenu.setVisible(false);
                break;
            case 1:
                noShelfSelectedMenu.setVisible(false);
                shelfSelectedMenu.setVisible(true);
                break;
        }
    }

    private JPanel shelfSelectedMenu() {
        JPanel shelfSelectedMenu = new JPanel(new GridBagLayout());

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
                if(currentShelfRotation == 0) {
                    currentShelfRotation = 1;
                } else {
                    currentShelfRotation = 0;
                }

                if(selectedShelf != null) {
                    if(selectedShelf.getRotationAngle() == 0) {
                    selectedShelf.setRotationAngle(1);
                    } else {
                    selectedShelf.setRotationAngle(0);
                    }
                }
            }
        });
        rotationPanel.add(rotationLabel, createGbc(0, 0));
        rotationPanel.add(rotationButton, createGbc(1, 0));

        //Util Buttons
        JPanel utilButtonsPanel = new JPanel(new GridBagLayout());
        JButton saveCurrentShelfButton = new JButton("Save Current Shelf");
        saveCurrentShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Save Current Shelf
                if(selectedShelf == null) {
                    createNewShelf();
                } else {
                    /*
                    Get current shelf
                    Find line in file
                    Rewrite file with line changed
                     */
                }
            }
        });

        JButton newShelfButton = new JButton("Create New Shelf");
        newShelfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Add Shelf Creations
                selectedShelf = null;
            }
        });
        utilButtonsPanel.add(saveCurrentShelfButton, createGbc(1, 0));
        utilButtonsPanel.add(newShelfButton, createGbc(2, 0));


        JPanel returnPanel = new JPanel(new GridBagLayout());
        JButton returnButton = new JButton("Return to Search");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });
        returnPanel.add(returnButton);

        shelfSelectedMenu.add(shelfNamePanel, createGbc(0, 0));
        shelfSelectedMenu.add(positionPanel, createGbc(0, 1));
        shelfSelectedMenu.add(sizePanel, createGbc(0, 2));
        shelfSelectedMenu.add(rotationPanel, createGbc(0, 3));
        shelfSelectedMenu.add(utilButtonsPanel, createGbc(0, 4));
        shelfSelectedMenu.add(returnPanel, createGbc(0, 5));

        return shelfSelectedMenu;
    }

    private void addLineToLayout(String line) {
        String[] currentLayoutContent = parentWindow.getStorageLayout().getLayoutInfo();
        String[] layoutContent = new String[currentLayoutContent.length+1];
        for(int i = 0; i < currentLayoutContent.length; i++) {
            layoutContent[i] = currentLayoutContent[i];
        }
        layoutContent[layoutContent.length-1] = line;

        try {
            FileWriter fw = new FileWriter(parentWindow.getStorageLayout().getDirectory());
            PrintWriter pw = new PrintWriter(fw);

            for(String element : layoutContent) {
                pw.println();
            }

            pw.close();
        } catch (IOException e) {
            System.err.println("There was a problem writing to file.");
        }
    }

    private void createNewShelf() {
        boolean dataIsValid = true;
        String[] shelfInfo = new String[7];
        shelfInfo[0] = shelfNameTextField.getText();
        shelfInfo[1] = xPosTextField.getText();
        shelfInfo[2] = yPosTextField.getText();
        shelfInfo[3] = widthTextField.getText();
        shelfInfo[4] = lengthTextField.getText();
        shelfInfo[5] = Integer.toString(currentShelfRotation);
        //shelfInfo[6] = notesTextArea.getText().replaceAll("\n","\\\\n");


    }


    public void setSelectedShelf(Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
    }
}
