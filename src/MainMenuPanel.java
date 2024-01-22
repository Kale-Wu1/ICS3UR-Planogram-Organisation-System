import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The panel containing the main menu, including file searching and opening functionality and central redirecting. Used in MainMenuWindow.
 */
public class MainMenuPanel extends JPanel implements GBCLayoutOrganiser {
    //Parent Window
    private final MainMenuWindow parentWindow;

    /**
     * The Header panel.
     */
    JPanel headerPanel;

    /**
     * The Available layouts panel.
     */
    JPanel availableLayoutsPanel;

    /**
     * The Search directory panel.
     */
    JPanel searchDirectoryPanel;

    /**
     * The Utility buttons panel.
     */
    JPanel utilityButtonsPanel;

    /**
     * The Exit button panel.
     */
    JPanel exitButtonPanel;

    /**
     * The array of valid layout files.
     */
    File[] fileArr = new File[0];

    /**
     * The Search directory.
     */
    String searchDirectory = null;

    /**
     * The Layout titles list.
     */
    DefaultListModel<String> layoutTitlesList;

    /**
     * The Search results label.
     */
    JLabel searchResultsLabel;


    /**
     * Instantiates a new MainMenuPanel.
     *
     * @param parentWindow_ the parent window
     */
    public MainMenuPanel(MainMenuWindow parentWindow_) {
        //Instantiate parentWindow
        parentWindow = parentWindow_;

        //Set layout
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

        //Available layouts list
        layoutTitlesList = new DefaultListModel<>(); //Create DefaultListModel for layout titles
        JList<String> availLayouts = new JList<>(layoutTitlesList); //Create JList to contain layoutTitlesList
        JScrollPane availLayoutOptionsScroll = new JScrollPane(availLayouts); //Create JScrollPane to contain availLayouts
        availLayouts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    //TODO: Open Layout When one is selected
                    /*
                    Open LayoutViewer with a file directory as a parameter
                     */
                    new LayoutViewerWindow(new Layout(fileArr[availLayouts.getSelectedIndex()].getAbsolutePath()));
                    parentWindow_.dispose();
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

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Add Search Functionality
                fileArr = updateAvailResults(searchTextField.getText(), fileArr, layoutTitlesList);
            }
        });

        //Search results message label - used in case no new results are found.
        searchResultsLabel = new JLabel();

        //Add all elements to searchDirectoryPanel
        searchDirectoryPanel.add(searchLabel, createGbc(0, 0));
        searchDirectoryPanel.add(searchTextField, createGbc(1, 0));
        searchDirectoryPanel.add(searchButton, createGbc(2, 0));
        searchDirectoryPanel.add(searchResultsLabel, createGbc(0, 1));

        //Add Utility Buttons
        utilityButtonsPanel = new JPanel(new GridBagLayout());

        JButton instructionsButton = new JButton("How to Use");
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(1);
            }
        });

        JButton newLayoutButton = new JButton("New Layout");
        newLayoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(2);
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


    /**
     * Searches a directory for valid layout files and returns all files that match using linear search.
     * @param directory filepath to search for files
     * @return a File[] with valid files.
     */
    private File[] searchFiles(String directory) {
        //File searching code edited from Mike Samuel - https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
        File dir = new File(directory);
        File[] fileList = dir.listFiles();
        ArrayList<File> tempFileList = new ArrayList<>();
        if (fileList != null) {
            for (File element : fileList) {
                if (element.getAbsoluteFile().toString().endsWith("Layout.txt")) {
                    tempFileList.add(element.getAbsoluteFile());
                }
            }

            File[] searchResults = new File[tempFileList.size()];
            searchResults = tempFileList.toArray(searchResults);
            return searchResults;
        } else {
            searchResultsLabel.setText("No valid results were found.");
        }
        return null;
    }


    /**
     * Returns File[] with updated available results
     * @param directory the filepath to search for more valid files
     * @param currentLayoutsArr current File[] of available layouts
     * @param syncedTitleList the list of layout titles synced to the currentLayoutsArr
     * @return File[] with added results. Returns currentLayoutsArr with no changes if no new valid layout files are found
     */
    private File[] updateAvailResults(String directory, File[] currentLayoutsArr, DefaultListModel<String> syncedTitleList) {
        //Search for new files
        File[] searchResults = searchFiles(directory);
        File[] newArr;

        //Determine appropriate length of newArr
        if (searchResults == null) {
            newArr = new File[currentLayoutsArr.length];
        } else {
            //Determine length of newArr based on valid new results
            int newArrLength = currentLayoutsArr.length;

            //Add 1 to newArrLength if a new, unique file is found
            for (File searchResult : searchResults) {
                boolean isUnique = true;
                for (File currentLayoutFile : currentLayoutsArr) {
                    if (currentLayoutFile.equals(searchResult)) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    newArrLength++;
                }
            }

            newArr = new File[newArrLength];
        }

        //Add currentLayoutsArr items to newArr
        System.arraycopy(currentLayoutsArr, 0, newArr, 0, currentLayoutsArr.length);

        //Add searchResults to newArr;
        for (int i = currentLayoutsArr.length; i < newArr.length; i++) {
            //Add new file to newArr if it is valid and unique
            boolean fileExists = false;
            for (File element : currentLayoutsArr) {
                if (element.equals(searchResults[i - currentLayoutsArr.length])) {
                    fileExists = true;
                    break;
                }
            }
            if (!fileExists) {
                newArr[i] = searchResults[i - currentLayoutsArr.length];
            }
        }

        //Ensure syncedTitleList is synchronised with newArr
        syncedTitleList.clear();
        for (String title : getTitles(newArr)) {
            syncedTitleList.addElement(title);
        }

        return newArr;
    }

    /**
     * Retrieve titles from File[].
     * @param fileArr File[] from which to retrieve titles
     * @return String[] with titles only
     */
    private String[] getTitles(File[] fileArr) {
        //TODO: Get first line from File object
        String[] titles = new String[fileArr.length];
        for (int i = 0; i < titles.length; i++) {
            try {
                FileReader fr = new FileReader(fileArr[i]);
                BufferedReader br = new BufferedReader(fr);
                titles[i] = br.readLine();
            } catch (IOException e) {
                System.err.println("Error reading file.");
            }
        }
        return titles;
    }
}