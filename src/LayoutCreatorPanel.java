import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LayoutCreatorPanel extends JPanel implements GBCLayoutOrganiser,FileUtils{
    //Parent Window
    private final MainMenuWindow parentWindow;
    private String selectedUnit;

    //Information Fields
    private final JTextField layoutNameTextField;
    private final JTextField authorNameTextField;
    private final JComboBox<String> unitOptionsComboBox;
    private final JTextField roomWidthTextField;
    private final JTextField roomLengthTextField;
    private final JTextField newLayoutDirectoryTextField;
    private final JTextArea notesTextArea;
    private final String[] unitOptions;
    private final JLabel errorMessage;

    public LayoutCreatorPanel(MainMenuWindow parentWindow_) {
        parentWindow = parentWindow_;
        setLayout(new GridBagLayout());

        //Add Header
        JPanel headerPanel = new JPanel(new GridBagLayout());;
        JLabel mainMenuHeader = new JLabel("Create New Layout");

        headerPanel.add(mainMenuHeader);

        //Basic Information Textfields
        JPanel basicInfoForms = new JPanel(new GridBagLayout());

        JLabel layoutNameText = new JLabel("Layout Title: ");
        layoutNameTextField = new JTextField(30);

        JLabel authorNameText = new JLabel("Author: ");
        authorNameTextField = new JTextField(30);

        JLabel unitsText = new JLabel("Units: ");
        unitOptions = new String[] {"in.", "ft.", "yds.", "cm", "m"};
        unitOptionsComboBox = new JComboBox<>(unitOptions);
        unitOptionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUnit = unitOptions[unitOptionsComboBox.getSelectedIndex()];
            }
        });

        basicInfoForms.add(layoutNameText, createGbc(0, 0));
        basicInfoForms.add(layoutNameTextField, createGbc(1, 0));
        basicInfoForms.add(authorNameText, createGbc(0, 1));
        basicInfoForms.add(authorNameTextField, createGbc(1, 1));
        basicInfoForms.add(unitsText, createGbc(0, 2));
        basicInfoForms.add(unitOptionsComboBox, createGbc(1, 2));

        //Room Dimensions Forms
        JPanel roomDimensionForms = new JPanel(new GridBagLayout());

        JLabel roomWidthText = new JLabel("Room Width (X): ");
        roomWidthTextField = new JTextField(4);
        JLabel unitLabel1 = new JLabel(selectedUnit);

        JLabel roomLengthText = new JLabel("Room Length (Y): ");
        roomLengthTextField = new JTextField(4);
        JLabel unitLabel2 = new JLabel(selectedUnit);

        roomDimensionForms.add(roomWidthText, createGbc(0, 0));
        roomDimensionForms.add(roomWidthTextField, createGbc(1, 0));
        roomDimensionForms.add(unitLabel1, createGbc(2, 0));

        roomDimensionForms.add(roomLengthText, createGbc(0, 1));
        roomDimensionForms.add(roomLengthTextField, createGbc(1, 1));
        roomDimensionForms.add(unitLabel2, createGbc(2, 1));



        //Directory Text Field
        JPanel newLayoutDirectoryPanel = new JPanel(new GridBagLayout());
        JLabel newLayoutDirectoryText = new JLabel("Storage Directory: ");

        newLayoutDirectoryTextField = new JTextField(30);

        newLayoutDirectoryPanel.add(newLayoutDirectoryText, createGbc(0, 0));
        newLayoutDirectoryPanel.add(newLayoutDirectoryTextField, createGbc(1, 0));

        //Notes Text Field
        JPanel notesPanel = new JPanel(new GridBagLayout());
        JLabel notesText = new JLabel("Notes: ");

        notesTextArea = new JTextArea(3, 30);

        notesPanel.add(notesText, createGbc(0, 0));
        notesPanel.add(notesTextArea, createGbc(1, 0));

        //Utility Buttons
        JPanel utilityButtonsPanel = new JPanel(new GridBagLayout());

        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(0);
            }
        });

        JButton createNewLayoutButton = new JButton("Confirm and Create");
        createNewLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewLayout();
            }
        });

        utilityButtonsPanel.add(returnButton, createGbc(1, 0));
        utilityButtonsPanel.add(createNewLayoutButton, createGbc(2, 0));

        //Input Error Notes
        JPanel errorMessagePanel = new JPanel(new GridBagLayout());
        errorMessage = new JLabel();
        errorMessagePanel.add(errorMessage);

        //Add all panels to LayoutCreatorPanel
        add(headerPanel, createGbc(0, 0));
        add(basicInfoForms, createGbc(0, 1));
        add(roomDimensionForms, createGbc(0, 2));
        add(newLayoutDirectoryPanel, createGbc(0, 3));
        add(notesPanel, createGbc(0, 4));
        add(errorMessagePanel, createGbc(0, 5));
        add(utilityButtonsPanel, createGbc(0, 6));
    }

    private void createNewLayout() {
        boolean dataIsValid = true;
        String[] layoutInfo = new String[7];
        layoutInfo[0] = layoutNameTextField.getText();
        layoutInfo[1] = authorNameTextField.getText();
        layoutInfo[2] = unitOptions[unitOptionsComboBox.getSelectedIndex()];
        layoutInfo[3] = roomWidthTextField.getText();
        layoutInfo[4] = roomLengthTextField.getText();
        layoutInfo[5] = newLayoutDirectoryTextField.getText();
        layoutInfo[6] = notesTextArea.getText().replaceAll("\n","\\\\n");

        //Errorchecking
        for(String element:layoutInfo) {
            if(element.isEmpty()) {
                dataIsValid = false;
                errorMessage.setText("One or more of the text-fields is empty!");
                break;
            }
        }

        //Errorcheck for positive ints as room dimensions
        try {
            int roomWidth = Integer.parseInt(roomWidthTextField.getText());
            if(roomWidth <= 0) {
                dataIsValid = false;
                errorMessage.setText("The room width specified must be positive!");
            }
        } catch (NumberFormatException e) {
            dataIsValid = false;
            errorMessage.setText("The room width specified is invalid!");
        }

        try {
            int roomLength = Integer.parseInt(roomLengthTextField.getText());
            if(roomLength <= 0) {
                dataIsValid = false;
                errorMessage.setText("The room length specified must be positive!");
            }
        } catch (NumberFormatException e) {
            dataIsValid = false;
            errorMessage.setText("The room length specified is invalid!");
        }

        String userPath = layoutInfo[5];
        //Check for valid path
        if(!isValidPath(userPath)) {
            dataIsValid = false;
            errorMessage.setText("Directory Invalid!");
        } else {
            //Removes / from end of path
            for(int i = 0; i < 2; i++) {
                if(userPath.charAt(userPath.length()-1) == '\\') {
                    userPath = userPath.substring(0, userPath.length()-2);
                }
            }

            userPath = userPath + "\\\\" + layoutNameTextField.getText() + "Layout.txt";



        }

        if(dataIsValid) {
            try{
                //Write new file
                File newLayout = new File(userPath);

                if(!newLayout.createNewFile()) { //IMPORTANT: NEW FILE PATHS MUST BE TXT AND SPECIFY EXACT FILE THAT DOES NOT YET EXIST
                    errorMessage.setText("The file path/layout name specified already exists!");
                } else {
                    //Write information to file
                    FileWriter fw = new FileWriter(newLayout.getAbsoluteFile());
                    PrintWriter pw = new PrintWriter(fw);

                    for(String element : layoutInfo) {
                        pw.println(element);
                    }

                    pw.close();

                    new LayoutViewerWindow(new Layout(newLayout.getAbsoluteFile().toString()));
                    parentWindow.dispose();
                }
            } catch (IOException e) {
                errorMessage.setText("Problem creating file. Please ensure that the directory provided is correct.");
            }
        }
    }

    private boolean isValidLayout(String[] layoutInfo) {
        //Layout info as appears in form.
        return false;
    }
}