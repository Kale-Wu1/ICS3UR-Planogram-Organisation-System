import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutCreatorPanel extends JPanel implements GBCLayoutOrganiser{
    //Parent Window
    private final Main parentWindow;
    private String selectedUnit;

    public LayoutCreatorPanel(Main parentWindow_) {
        parentWindow = parentWindow_;
        setLayout(new GridBagLayout());

        //Add Header
        //TODO: Center text in title
        JPanel headerPanel = new JPanel(new GridBagLayout());;
        JLabel mainMenuHeader = new JLabel("Create New Layout");

        headerPanel.add(mainMenuHeader);

        //Basic Information Textfields
        JPanel basicInfoForms = new JPanel(new GridBagLayout());

        JLabel layoutNameText = new JLabel("Layout Title: ");
        JTextField layoutNameTextField = new JTextField(30);

        JLabel authorNameText = new JLabel("Author: ");
        JTextField authorNameTextField = new JTextField(30);

        JLabel unitsText = new JLabel("Units: ");
        String[] unitOptions = new String[] {"in.", "ft.", "yds.", "cm", "m"};
        JComboBox<String> unitOptionsComboBox = new JComboBox<>(unitOptions);
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
        JTextField roomWidthTextField = new JTextField(4);
        JLabel unitLabel1 = new JLabel(selectedUnit);

        JLabel roomLengthText = new JLabel("Room Width (Y): ");
        JTextField roomLengthTextField = new JTextField(4);
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

        JTextField newLayoutDirectoryTextField = new JTextField(30);

        newLayoutDirectoryPanel.add(newLayoutDirectoryText, createGbc(0, 0));
        newLayoutDirectoryPanel.add(newLayoutDirectoryTextField, createGbc(1, 0));

        //Notes Text Field
        JPanel notesPanel = new JPanel(new GridBagLayout());
        JLabel notesText = new JLabel("Storage Directory: ");

        JTextArea notesTextArea = new JTextArea(3, 30);

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
                //TODO: Add New Layout Creation Functionality
            }
        });

        utilityButtonsPanel.add(returnButton, createGbc(1, 0));
        utilityButtonsPanel.add(createNewLayoutButton, createGbc(2, 0));

        //Add all panels to LayoutCreatorPanel
        add(headerPanel, createGbc(0, 0));
        add(basicInfoForms, createGbc(0, 1));
        add(roomDimensionForms, createGbc(0, 2));
        add(newLayoutDirectoryPanel, createGbc(0, 3));
        add(notesPanel, createGbc(0, 4));
        add(utilityButtonsPanel, createGbc(0, 5));
    }

}