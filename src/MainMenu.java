import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;

public class MainMenu extends JFrame{
    //Parent Panel
    JPanel parentPanel;

    //CardLayout Panels
    JPanel mainMenu;
    JPanel instructions;
    JPanel layoutCreator;

    //Organisation Panels
    JPanel headerPanel;
    JPanel availableLayoutsPanel;
    JPanel searchDirectoryPanel;
    JPanel utilityButtonsPanel;
    JPanel exitButtonPanel;

    //Variables
    File[] fileArr = new File[0];
    String searchDirectory = null;

    DefaultListModel<String> testList;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu();
            }
        });
    }

    public MainMenu() {
        //Initialise Window
        final int FRAME_WIDTH = 600;
        final int FRAME_LENGTH = 750;
        final int INDENT_GAP = 50;

        //Set values for window
        setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        setTitle("Planogram v.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        //Initialise GridBagConstraints Variable
        GridBagConstraints gbc;

        //Initialise parentPanel
        parentPanel = new JPanel(new CardLayout());

        //Define mainMenu JPanel
        mainMenu = new JPanel(new GridBagLayout());

        //Add Header
        //TODO: Center text in title
        headerPanel = new JPanel();
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Welcome to the Planogram System");

        headerPanel.add(mainMenuHeader);

        //Add Available Layouts Results
        availableLayoutsPanel = new JPanel(new GridBagLayout()); //Create New Panel
        JLabel availLayoutMessage = new JLabel("Current Layouts: "); //Add Subheading
        availableLayoutsPanel.add(availLayoutMessage, createGbc(0, 0));

        //Available Layout Search Results List w/ Scroll
        //JList<String> availLayouts = getUpdatedSearchResults();

        /*Testing List*/
        DefaultListModel<String> testList = new DefaultListModel<>();
        for(int i = 0; i < 20; i++) {
            testList.addElement(Integer.toString(i));
        }

        JList<String> availLayouts = new JList<>(testList);
        JScrollPane availLayoutOptionsScroll = new JScrollPane(availLayouts);
        availLayouts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    //TODO: Open Layout When one is selected
                    System.out.println(availLayouts.getSelectedIndex());
                }
            }
        });

        //Create and add JScrollPane
        availLayoutOptionsScroll.setMaximumSize(new Dimension(400, 300));
        availLayoutOptionsScroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        availLayoutOptionsScroll.createVerticalScrollBar();
        availableLayoutsPanel.add(availLayoutOptionsScroll, createGbc(0, 1));


        //Add Layout Directory Search Bar
        searchDirectoryPanel = new JPanel(new GridBagLayout());
        JLabel searchLabel = new JLabel("Search Directory:");

        JTextField searchTextField = new JTextField(30);
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDirectory = searchTextField.getText();
            }
        });

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Add Search Functionality

            }
        });

        searchDirectoryPanel.add(searchLabel, createGbc(0, 0));
        searchDirectoryPanel.add(searchTextField, createGbc(1, 0));
        searchDirectoryPanel.add(searchButton, createGbc(2, 0));

        //Add Utility Buttons
        utilityButtonsPanel = new JPanel(new GridBagLayout());

        JButton instructionsButton = new JButton("How to Use");
        instructionsButton.setPreferredSize(new Dimension(100, 50));
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Add Instruction Functionality

            }
        });

        JButton newLayoutButton = new JButton("New Layout");
        newLayoutButton.setPreferredSize(new Dimension(100, 50));

        newLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Add New Layout Functionality

            }
        });

        gbc = createGbc(0, 0);
        gbc.weightx = 1;
        gbc.weighty = 1;
        utilityButtonsPanel.add(instructionsButton, gbc);

        gbc = createGbc(1, 0);
        utilityButtonsPanel.add(newLayoutButton, gbc);



        //Add Components to mainMenuLayout
        mainMenu.add(headerPanel, createGbc(0, 0));
        mainMenu.add(availableLayoutsPanel, createGbc(0, 1));
        mainMenu.add(searchDirectoryPanel, createGbc(0, 3));
        mainMenu.add(utilityButtonsPanel, createGbc(0, 4));

        //Add Components to parentPanel
        parentPanel.add(mainMenu, "mainMenu");
        //parentPanel.add(instructions, "instructions");
        //parentPanel.add(layoutCreator, "layoutCreator");

        //Add Parent Panel to Window
        add(parentPanel);

        setVisible(true);
    }

    //Method takes no parameters and returns updated JList with updated FileArr
    private JList<String> getUpdatedSearchResults() {
        DefaultListModel<String> newList = new DefaultListModel<>();
        for(String title:getTitles(fileArr)) {
            newList.addElement(title);
        }
        return new JList<>(newList);
    }

    //Method searchFile searches a directory for valid layout files and returns all files that match using linear search
    private File[] searchFiles(String directory) {
        return null;
    }

    //Code from Hovercraft Full Of Eels from https://stackoverflow.com/questions/9851688/how-to-align-left-or-right-inside-gridbaglayout-cell
    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.insets = (x == 0) ? new Insets(5, 0, 5, 5) : new Insets(5, 5, 5, 0);
        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }

    //Method takes directory and currentLayoutsArr and returns a File[] with updated values
    private File[] updateAvailResults(String directory, File[] currentLayoutsArr) {
        File[] searchResults = searchFiles(directory);
        File[] newArr = new File[searchResults.length + currentLayoutsArr.length];

        for(int i = 0; i < currentLayoutsArr.length; i++) {
            newArr[i] = currentLayoutsArr[i];
        }

        for(int i = currentLayoutsArr.length; i < newArr.length; i++) {
            newArr[i] = searchResults[i-currentLayoutsArr.length];
        }

        return newArr;
    }

    //Method takes File[] fileArr and returns a String[] with titles only
    private String[] getTitles(File[] fileArr) {
        //TODO: Get first line from File object

        return null;
    }


}