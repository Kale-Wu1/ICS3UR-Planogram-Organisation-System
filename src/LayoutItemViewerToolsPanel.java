import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * The panel containing shelf item viewing/editing. Used in LayoutViewerWindow.
 */
public class LayoutItemViewerToolsPanel extends JPanel implements GBCLayoutOrganiser{
    /**
     * The Selected shelf.
     */
    Shelf selectedShelf;

    /**
     * The Parent window.
     */
    LayoutViewerWindow parentWindow;

    /**
     * The Item panel.
     */
    JPanel itemPanel;

    /**
     * Instantiates a new Layout item viewer tools panel.
     *
     * @param parentWindow_  the parent window
     * @param selectedShelf_ the selected shelf
     */
    public LayoutItemViewerToolsPanel(LayoutViewerWindow parentWindow_, Shelf selectedShelf_) {
        //Instantiate parentWindow and selected shelf
        selectedShelf = selectedShelf_;
        parentWindow = parentWindow_;

        //Hide this window if created with null selected shelf
        if(selectedShelf == null) {
            selectedShelf = new Shelf();
        }

        //Set panel layout
        setLayout(new GridBagLayout());

        //Define header panel
        JPanel headerPanel = new JPanel(new GridBagLayout());
        JLabel header = new JLabel(selectedShelf.getName() + " Items");
        headerPanel.add(header, createGbc(0, 0));

        //Define item list panel
        JPanel itemListPanel = new JPanel(new GridBagLayout());

        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanelPopulate(itemPanel, selectedShelf.getItemArr());

        JScrollPane itemPanelScroll = new JScrollPane(itemPanel); //Create JScrollPane to contain availLayouts
        itemPanelScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        itemListPanel.add(itemPanelScroll, createGbc(0, 0));

        //Define refresh button panel
        JPanel refreshPanel = new JPanel(new GridBagLayout());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshItemList();
            }
        });
        refreshPanel.add(refreshButton, createGbc(1, 0));

        //Add panels
        add(headerPanel, createGbc(0, 0));
        add(itemListPanel, createGbc(0, 1));
        add(refreshPanel, createGbc(0, 2));
    }

    /**
     * Populates itemPanel with information
     * @param itemPanel item panel to populate
     * @param items String[] of items to populate the itemPanel with
     */
    private void itemPanelPopulate(JPanel itemPanel, String[] items) {
        //Remove all items from itemPanel
        itemPanel.removeAll();
        insertionSort(items);

        //Add items text field
        for (String item : items) {
            JTextField newTextField = new JTextField(item);
            newTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, newTextField.getPreferredSize().height));
            itemPanel.add(newTextField);
        }

        //Add blank text field
        JTextField newTextField = new JTextField();
        newTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, newTextField.getPreferredSize().height));
        itemPanel.add(newTextField);
    }

    /**
     * Refresh item list
     */
    private void refreshItemList() {
        //Repopulate itemPanel with new information
        itemPanelPopulate(itemPanel, readCurrentItems(itemPanel));
        itemPanel.revalidate();
        itemPanel.repaint();
    }

    /**
     * Read current items in itemPanel.
     * @param itemPanel the panel to read items from
     * @return a String[] of items in current itemPanel
     */
    private String[] readCurrentItems(JPanel itemPanel) {
        Component[] textFields = itemPanel.getComponents();
        ArrayList<String> itemList = new ArrayList<>();
        JTextField textField;

        for(Component component : textFields) {
            if(component instanceof JTextField) {
                textField = (JTextField) component;

                if(!textField.getText().trim().isEmpty() && !itemList.contains(textField.getText().trim())) {
                    itemList.add(textField.getText().replaceAll(",", ""));
                }
            }
        }

        String[] itemArr = new String[itemList.size()];
        itemArr = itemList.toArray(itemArr);
        insertionSort(itemArr);
        return itemArr;
    }

    /**
     * Sorts itemArr alphabetically using insertionSort.
     * @param itemArr the array of items to sort
     */
    private static void insertionSort(String[] itemArr) {
        int n = itemArr.length;
        for (int i = 1; i < n; ++i) {
            String key = itemArr[i];
            int j;

            for (j = i - 1; j >= 0 && itemArr[j].compareTo(key) > 0; --j) {
                itemArr[j + 1] = itemArr[j];
            }
            itemArr[j + 1] = key;
        }
    }

    /**
     * Sets selected shelf.
     * @param selectedShelf_ the selected shelf
     */
    public void setSelectedShelf(Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
        itemPanelPopulate(itemPanel, selectedShelf.getItemArr());
    }

    /**
     * Save shelf items to selectedShelf.
     */
    public void saveShelfItems() {
        selectedShelf.setItemArr(readCurrentItems(itemPanel));
    }


}
