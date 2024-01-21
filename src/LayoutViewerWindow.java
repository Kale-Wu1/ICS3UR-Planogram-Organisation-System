import javax.swing.*;
import java.awt.*;

public class LayoutViewerWindow extends JFrame {

    //Define Main Divisions
    private final LayoutViewerPanel parentViewPanel;
    private final JPanel parentToolMenuPanel;

    //Menu Panels
    private final LayoutSearchToolsPanel searchMenu;
    private final LayoutNotesViewerPanel notesMenu;
    private final LayoutEditorToolsPanel editorMenu;
    private final LayoutItemViewerToolsPanel itemViewMenu;


    //Current Layout Object
    Layout layout;

    //Current Menu
    private int currentCard;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LayoutViewerWindow(new Layout("C:\\Users\\kalew\\Downloads\\testLayouts\\MyLayoutLayout.txt"));
            }
        });
    }

    public LayoutViewerWindow(Layout layout_) {
        layout = layout_;
        currentCard = 0;

        //currentLayout = currentLayout_;
        final int FRAME_WIDTH = layout.getroomWidth();
        final int FRAME_LENGTH = layout.getroomLength();

        //Set values for window
        setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        setTitle(layout.getName() + " - Planogram v.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);

        //Add Base Panels
        //Parent Panel
        JPanel parentPanel = new JPanel(null);
        GridBagConstraints gbc;

        parentViewPanel = new LayoutViewerPanel(this, layout.getShelfList());
        parentViewPanel.setBounds(0, 0, FRAME_WIDTH/10*7, FRAME_LENGTH);
        parentPanel.add(parentViewPanel);

        //Add Menu Panels
        searchMenu = new LayoutSearchToolsPanel(this);
        notesMenu = new LayoutNotesViewerPanel(this);
        editorMenu = new LayoutEditorToolsPanel(this, null);
        itemViewMenu = new LayoutItemViewerToolsPanel(this, null);

        parentToolMenuPanel = new JPanel(new CardLayout());
        parentToolMenuPanel.add(searchMenu, "searchMenu");
        parentToolMenuPanel.add(notesMenu, "notesMenu");
        parentToolMenuPanel.add(editorMenu, "editorMenu");
        parentToolMenuPanel.add(itemViewMenu, "itemViewMenu");

        parentToolMenuPanel.setBounds((int) (FRAME_WIDTH/10*6.9), 0, (int) (FRAME_WIDTH/10*2.9), FRAME_LENGTH-20);
        parentPanel.add(parentToolMenuPanel);

        add(parentPanel);

        setVisible(true);

    }

    public JPanel getParentToolMenuPanel() {
        return parentToolMenuPanel;
    }

    public LayoutViewerPanel getParentViewPanel() {
        return parentViewPanel;
    }

    public Layout getStorageLayout() {
        return layout;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public LayoutEditorToolsPanel getEditorMenu() {
        return editorMenu;
    }

    public LayoutItemViewerToolsPanel getItemViewMenu() {
        return itemViewMenu;
    }

    public void setCard(int card) {
        currentCard = card;
        switch(card) {
            case 0:
                searchMenu.refresh();
                searchMenu.setVisible(true);
                notesMenu.setVisible(false);
                editorMenu.setVisible(false);
                itemViewMenu.setVisible(false);
                break;
            case 1:
                searchMenu.setVisible(false);
                notesMenu.setVisible(true);
                editorMenu.setVisible(false);
                itemViewMenu.setVisible(false);
                break;
            case 2:
                searchMenu.setVisible(false);
                notesMenu.setVisible(false);
                editorMenu.setVisible(true);
                itemViewMenu.setVisible(false);
                break;
            case 4:
                searchMenu.setVisible(false);
                notesMenu.setVisible(false);
                editorMenu.setVisible(false);
                itemViewMenu.setVisible(true);
                break;
        }
    }
}