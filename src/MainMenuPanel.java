import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenuPanel extends JPanel implements GBCLayoutOrganiser{
    //Parent Window
    private final Main parentWindow;

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


    public MainMenuPanel(Main parentWindow_) {
        parentWindow = parentWindow_;
        setLayout(new GridBagLayout());

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
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Add Instruction Functionality
                parentWindow.setCard(1);
            }
        });

        JButton newLayoutButton = new JButton("New Layout");
        newLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Add New Layout Functionality

            }
        });

        utilityButtonsPanel.add(instructionsButton, createGbc(1, 0));
        utilityButtonsPanel.add(newLayoutButton, createGbc(2, 0));

        //Add Exit Button
        exitButtonPanel = new JPanel(new GridBagLayout());

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitButtonPanel.add(exitButton, createGbc(0, 0));

        //Add Components to mainMenuLayout
        add(headerPanel, createGbc(0, 0));
        add(availableLayoutsPanel, createGbc(0, 1));
        add(searchDirectoryPanel, createGbc(0, 3));
        add(utilityButtonsPanel, createGbc(0, 4));
        add(exitButtonPanel, createGbc(0, 5));
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