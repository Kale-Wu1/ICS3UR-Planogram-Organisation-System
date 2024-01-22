import javax.swing.*;
import java.awt.*;

/**
 * The window in which layouts are viewed and edited.
 */
public class LayoutViewerWindow extends JFrame {

    //Define MainMenuWindow Divisions
    private final LayoutViewerPanel parentViewPanel;
    private final JPanel parentToolMenuPanel;

    //Menu Panels
    private final LayoutSearchToolsPanel searchMenu;
    private final LayoutNotesViewerPanel notesMenu;
    private final LayoutEditorToolsPanel editorMenu;
    private final LayoutItemViewerToolsPanel itemViewMenu;


    /**
     * The layout of this window.
     */
    Layout layout;

    //Current Menu Card
    private int currentCard;

    /**
     * Instantiates a new Layout viewer window.
     * @param layout_ the layout
     */
    public LayoutViewerWindow(Layout layout_) {
        //Instantiate layout and currentCard
        layout = layout_;
        currentCard = 0;

        //Set frame dimensions to
        final int FRAME_WIDTH = layout.getroomWidth() + (int) (((float)3/7)*layout.getroomWidth());
        final int FRAME_LENGTH = layout.getroomLength();

        //Set values for window
        setBounds(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        setTitle(layout.getName() + " - Planogram v.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //Add Base Panels
        //Parent Panel
        JPanel parentPanel = new JPanel(null);
        GridBagConstraints gbc;

        parentViewPanel = new LayoutViewerPanel(this, layout.getShelfList());
        parentViewPanel.setBounds(0, 0, layout.getroomWidth(), FRAME_LENGTH);
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

        //Set menu to viewer panel rations
        parentToolMenuPanel.setBounds((int) (FRAME_WIDTH/10*7.1), 0, (int) (FRAME_WIDTH/10*2.9), FRAME_LENGTH-20);
        parentPanel.add(parentToolMenuPanel);

        //Add parent panel
        add(parentPanel);

        setVisible(true);

    }


    /**
     * Gets parent view panel.
     * @return the parent view panel
     */
    public LayoutViewerPanel getParentViewPanel() {
        return parentViewPanel;
    }

    /**
     * Gets storage layout.
     * @return the storage layout
     */
    public Layout getStorageLayout() {
        return layout;
    }

    /**
     * Gets current card.
     * @return the current card
     */
    public int getCurrentCard() {
        return currentCard;
    }

    /**
     * Gets editor menu.
     * @return the editor menu
     */
    public LayoutEditorToolsPanel getEditorMenu() {
        return editorMenu;
    }

    /**
     * Gets item view menu.
     * @return the item view menu
     */
    public LayoutItemViewerToolsPanel getItemViewMenu() {
        return itemViewMenu;
    }

    /**
     * Sets card for menu. Used to switch between menus.
     * @param card the card index to set the menu.
     */
    public void setCard(int card) {
        currentCard = card;
        switch(card) {
            case 0: //Switch to search menu
                searchMenu.refresh();
                searchMenu.setVisible(true);
                notesMenu.setVisible(false);
                editorMenu.setVisible(false);
                itemViewMenu.setVisible(false);
                break;
            case 1: //Switch to notes menu
                searchMenu.setVisible(false);
                notesMenu.setVisible(true);
                editorMenu.setVisible(false);
                itemViewMenu.setVisible(false);
                break;
            case 2: //Switch to shelf editor menu
                searchMenu.setVisible(false);
                notesMenu.setVisible(false);
                editorMenu.setVisible(true);
                itemViewMenu.setVisible(false);
                break;
            case 4: //Switch to item viewer menu
                searchMenu.setVisible(false);
                notesMenu.setVisible(false);
                editorMenu.setVisible(false);
                itemViewMenu.setVisible(true);
                break;
        }
    }
}