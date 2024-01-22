import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The panel containing item searching and central menu redirecting functionality. Used in LayoutViewerWindow.
 */
public class LayoutSearchToolsPanel extends JPanel implements GBCLayoutOrganiser{
    /**
     * The Parent window.
     */
    LayoutViewerWindow parentWindow;

    /**
     * The Search text field.
     */
    JTextField searchTextField;

    /**
     * The Search results info list.
     */
    DefaultListModel<String> searchResultsInfoList;

    /**
     * The Search results buffer list.
     */
    JList<String> searchResultsBufferList;

    /**
     * The Selected shelf.
     */
    Shelf selectedShelf;

    /**
     * Instantiates a new Layout search tools panel.
     * @param parentWindow_ the parent window
     */
    public LayoutSearchToolsPanel(LayoutViewerWindow parentWindow_) {
        parentWindow = parentWindow_;
        setLayout(new GridBagLayout());

        //Add Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Layout Search Tools");

        headerPanel.add(mainMenuHeader);


        //Add Layout Directory Search Bar
        JPanel searchBarPanel = new JPanel(new GridBagLayout());
        searchTextField = new JTextField(30);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchForItem(searchTextField.getText());
            }
        });
        searchBarPanel.add(searchTextField, createGbc(1, 0));
        searchBarPanel.add(searchButton, createGbc(2, 0));

        //Add Available Layouts Results
        JPanel searchResultsPanel = new JPanel(new GridBagLayout()); //Create New Panel
        JLabel availLayoutMessage = new JLabel("Search Results: "); //Add Subheading
        searchResultsPanel.add(availLayoutMessage, createGbc(1, 0));

        searchResultsInfoList = new DefaultListModel<>(); //Create DefaultListModel for search results
        searchResultsBufferList = new JList<>(searchResultsInfoList); //Create JList to contain searchResults
        JScrollPane searchResultsScroll = new JScrollPane(searchResultsBufferList); //Create JScrollPane to contain searchResultsBufferList
        searchResultsBufferList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    //TODO: Highlight correct shelf when an item is selected
                    if(searchResultsBufferList.getSelectedIndex() != -1) {
                        openItemViewerForIndex(searchResultsBufferList.getSelectedIndex());
                    }
                }
            }
        });

        //Create and add JScrollPane
        searchResultsScroll.setMinimumSize(new Dimension(300, 200));
        searchResultsScroll.setPreferredSize(new Dimension(300, 200));
        searchResultsScroll.setMaximumSize(new Dimension(300, 200));
        searchResultsScroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        searchResultsScroll.createVerticalScrollBar();
        searchResultsPanel.add(searchResultsScroll, createGbc(1, 1));

        //Add Utility Buttons
        JPanel utilityButtonsPanel = new JPanel(new GridBagLayout());

        JButton notesButton = new JButton("Notes");
        notesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(1);
            }
        });

        JButton editButton = new JButton("Edit Layout");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.setCard(2);
            }
        });

        utilityButtonsPanel.add(notesButton, createGbc(1, 0));
        utilityButtonsPanel.add(editButton, createGbc(2, 0));

        //Add Exit Button
        JPanel returnButtonPanel = new JPanel(new GridBagLayout());
        JButton exitButton = new JButton("Return to Main Menu");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAll();
                new MainMenuWindow();
                parentWindow.dispose();
            }
        });
        returnButtonPanel.add(exitButton, createGbc(1, 0));

        //Add Components to mainMenuLayout
        add(headerPanel, createGbc(0, 0));
        add(searchBarPanel, createGbc(0, 1));
        add(searchResultsPanel, createGbc(0, 2));
        add(utilityButtonsPanel, createGbc(0, 3));
        add(returnButtonPanel, createGbc(0, 4));

    }

    /**
     * Save current layout status to file.
     */
    private void saveAll() {
        parentWindow.getStorageLayout().saveToFile();
    }

    /**
     * Search for item in all shelves in layout and display appropriate results
     * @param item the item to search for
     */
    private void searchForItem(String item) {
        //Clear highlights and search results
        searchResultsInfoList.clear();
        parentWindow.getParentViewPanel().clearHighlightedShelves();

        //Find item
        for(Shelf shelf : parentWindow.getStorageLayout().getShelfList()) {
            int searchIndex = binarySearch(shelf.getItemArr(), item);
            if(searchIndex != -1) {
                shelf.setHighlighted(true);
                searchResultsInfoList.addElement("Item " + (searchIndex + 1) + " of " + shelf.getName());
            }
        }

        if(searchResultsInfoList.isEmpty()) {
            searchResultsInfoList.addElement("Item " + item + " not found.");
        }
        searchResultsBufferList.revalidate();
        searchResultsBufferList.repaint();

    }

    /**
     * Binary search itemArr for item.
     * @param itemArr String[] the item array
     * @param item the item to search for
     * @return the index of the searched item
     */
    public static int binarySearch(String[] itemArr, String item) {
        int lowIndex = 0;
        int highIndex = itemArr.length - 1;

        while (lowIndex <= highIndex) {
            int searchIndex = lowIndex + (highIndex - lowIndex) / 2;
            int itemDiff = item.toLowerCase().replaceAll(" ", "").compareTo(itemArr[searchIndex].toLowerCase().replaceAll(" ", ""));

            if (itemDiff == 0) {
                return searchIndex;
            } else if (itemDiff < 0) {
                highIndex = searchIndex - 1;
            } else {
                lowIndex = searchIndex + 1;
            }
        }

        return -1;
    }

    /**
     * Opens LayoutItemViewerToolsPanel for the selectedSearchResult index.
     * @param selectedSearchResult the index of the selected search result
     */
    private void openItemViewerForIndex(int selectedSearchResult) {
        for(Shelf shelf : parentWindow.getStorageLayout().getShelfList()) {
            if(searchResultsInfoList.get(selectedSearchResult).contains(shelf.getName())) {
                parentWindow.getItemViewMenu().setSelectedShelf(shelf);
                parentWindow.setCard(4);
            }
        }
    }

    /**
     * Clear searchTextField and search results.
     */
    public void refresh() {
        searchTextField.setText("");
        searchResultsInfoList.clear();
    }



}
