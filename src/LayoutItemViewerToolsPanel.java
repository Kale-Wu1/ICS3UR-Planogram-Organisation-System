import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class LayoutItemViewerToolsPanel extends JPanel implements GBCLayoutOrganiser, FileUtils {
    Shelf selectedShelf;
    LayoutViewerWindow parentWindow;
    JPanel itemPanel;

    public LayoutItemViewerToolsPanel(LayoutViewerWindow parentWindow_, Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
        parentWindow = parentWindow_;

        //Hide this window if created with null selected shelf
        if(selectedShelf == null) {
            selectedShelf = new Shelf();
        }

        setLayout(new GridBagLayout());

        //Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        JLabel header = new JLabel(selectedShelf.getName() + " Items");
        headerPanel.add(header, createGbc(0, 0));

        //Item List
        JPanel itemListPanel = new JPanel(new GridBagLayout());

        itemPanel = new JPanel(new GridBagLayout());
        itemPanelPopulate(itemPanel, selectedShelf.getItemArr());
        JScrollPane itemJListScroll = new JScrollPane(itemPanel); //Create JScrollPane to contain availLayouts

        itemListPanel.add(itemJListScroll, createGbc(0, 0));

        //Refresh Button
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

    private void itemPanelPopulate(JPanel itemPanel, String[] items) {
        insertionSort(items);
        for(int i = 0; i < items.length; i++) {
            JTextField newTextField = new JTextField(items[i]);
            newTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, newTextField.getPreferredSize().height));
            itemPanel.add(newTextField, createGbc(1, i));
        }
        JTextField newTextField = new JTextField();
        newTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, newTextField.getPreferredSize().height));
        itemPanel.add(newTextField, createGbc(1, items.length));
    }

    private void refreshItemList() {
        String[] items = readCurrentItems(itemPanel); //read items
        insertionSort(items); //sort items

        itemPanel.removeAll(); //remove all items from panel
        itemPanelPopulate(itemPanel, items); //populate with new info
        repaint();

    }

    private String[] readCurrentItems(JPanel itemPanel) {
        Component[] textFields = itemPanel.getComponents();
        ArrayList<String> itemList = new ArrayList<>();
        JTextField textField;

        for(Component component : textFields) {
            if(component instanceof JTextField) {
                textField = (JTextField) component;

                if(!textField.getText().isEmpty()) {
                    itemList.add(textField.getText());
                }
            }
        }

        String[] itemArr = new String[itemList.size()];
        itemArr = itemList.toArray(itemArr);
        insertionSort(itemArr);
        return itemArr;
    }


    private static void insertionSort(String[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            String key = arr[i];
            int j;

            for (j = i - 1; j >= 0 && arr[j].compareTo(key) > 0; --j) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = key;
        }
    }

    public void setSelectedShelf(Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
    }

    public void saveShelfItems() {
        selectedShelf.setItemArr(readCurrentItems(itemPanel));
    }


}
