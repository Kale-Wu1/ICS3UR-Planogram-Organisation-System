import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayoutSearchToolsPanel extends JPanel implements GBCLayoutOrganiser{
    LayoutViewerWindow parentWindow;

    public LayoutSearchToolsPanel(LayoutViewerWindow parentWindow_) {
        parentWindow = parentWindow_;
        setLayout(new GridBagLayout());
        //JPanel parentPanel = parentWindow_.getParentToolMenuPanel();

        //Add Header
        //TODO: Center text in title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        JLabel mainMenuHeader = new JLabel("Layout Search Tools");

        headerPanel.add(mainMenuHeader);


        //Add Layout Directory Search Bar
        JPanel searchBarPanel = new JPanel(new GridBagLayout());

        JTextField searchTextField = new JTextField(30);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Search and update DefaultListModel
            }
        });

        searchBarPanel.add(searchTextField, createGbc(1, 0));
        searchBarPanel.add(searchButton, createGbc(2, 0));



        //Add Available Layouts Results
        JPanel searchResultsPanel = new JPanel(new GridBagLayout()); //Create New Panel
        JLabel availLayoutMessage = new JLabel("Current Layouts: "); //Add Subheading
        searchResultsPanel.add(availLayoutMessage, createGbc(1, 0));

        DefaultListModel<String> searchResultsInfoList = new DefaultListModel<>(); //Create DefaultListModel for layout titles
        JList<String> searchResultsBufferList = new JList<>(searchResultsInfoList); //Create JList to contain layoutTitlesList
        JScrollPane searchResultsScroll = new JScrollPane(searchResultsBufferList); //Create JScrollPane to contain availLayouts
        searchResultsBufferList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    //TODO: Highlight correct shelf when an item is selected

                }
            }
        });

        //Create and add JScrollPane
        searchResultsScroll.setMaximumSize(new Dimension(300, 400));
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
                //TODO: Go to Editor
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
                System.exit(0);
            }
        });
        returnButtonPanel.add(exitButton, createGbc(1, 0));

        //Add Components to mainMenuLayout
        add(headerPanel, createGbc(0, 0));
        add(searchBarPanel, createGbc(0, 1));
        add(searchResultsPanel, createGbc(0, 2));
        add(utilityButtonsPanel, createGbc(0, 3));
        add(returnButtonPanel, createGbc(0, 4));
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA I AM VERY UPSET RIGHT NOW!!!!!!!AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

    }

}
