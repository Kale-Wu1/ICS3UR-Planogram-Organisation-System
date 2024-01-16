import javax.swing.*;
import java.awt.*;

public class LayoutViewerWindow extends JFrame {
    //Parent Panel
    private JPanel parentPanel;

    //Define Main Divisions
    private JPanel parentViewPanel;
    private JPanel parentToolMenuPanel;

    //Menu Panels
    private JPanel searchMenu;
    private JPanel notesMenu;
    private JPanel editorMenu;


    //Current Layout Object
    Layout currentLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LayoutViewerWindow();
            }
        });
    }

    public LayoutViewerWindow() {

        //currentLayout = currentLayout_;
        final int FRAME_WIDTH = 1000;
        final int FRAME_LENGTH = 500;

        //Set values for window
        setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        setTitle("[Layout Name] - Planogram v.1"); //TODO: Add Layout Name to Title
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);

        //Add Base Panels
        parentPanel = new JPanel(null);
        GridBagConstraints gbc;

        parentViewPanel = new JPanel(null);
        parentViewPanel.setBounds(0, 0, FRAME_WIDTH/10*7, FRAME_LENGTH);
        parentPanel.add(parentViewPanel);

        //Add Menu Panels
        searchMenu = new LayoutSearchToolsPanel(this);
        notesMenu = new LayoutNotesViewerPanel(this);
        editorMenu = new LayoutEditorToolsPanel(this, null);

        parentToolMenuPanel = new JPanel(new CardLayout());
        parentToolMenuPanel.add(searchMenu, "searchMenu");
        parentToolMenuPanel.add(notesMenu, "notesMenu");
        parentToolMenuPanel.add(editorMenu, "editorMenu");

        parentToolMenuPanel.setBounds((int) (FRAME_WIDTH/10*6.9), 0, (int) (FRAME_WIDTH/10*2.9), FRAME_LENGTH-20);
        parentPanel.add(parentToolMenuPanel);

        add(parentPanel);


        setVisible(true);

    }

    public JPanel getParentToolMenuPanel() {
        return parentToolMenuPanel;
    }

    public JPanel getParentViewPanel() {
        return parentViewPanel;
    }

    public void setCard(int card) {
        switch(card) {
            case 0:
                searchMenu.setVisible(true);
                notesMenu.setVisible(false);
                editorMenu.setVisible(false);
                break;
            case 1:
                searchMenu.setVisible(false);
                notesMenu.setVisible(true);
                editorMenu.setVisible(false);
                break;
            case 2:
                searchMenu.setVisible(false);
                notesMenu.setVisible(false);
                editorMenu.setVisible(true);
                break;
        }
    }
}