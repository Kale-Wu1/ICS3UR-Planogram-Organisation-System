import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class LayoutItemViewerToolsPanel extends JPanel implements GBCLayoutOrganiser{
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

        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanelPopulate(itemPanel, selectedShelf.getItemArr());

        JScrollPane itemPanelScroll = new JScrollPane(itemPanel); //Create JScrollPane to contain availLayouts
        itemPanelScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        itemListPanel.add(itemPanelScroll, createGbc(0, 0));

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
        itemPanel.removeAll(); //remove all items from panel
        insertionSort(items);
        for (String item : items) {
            JTextField newTextField = new JTextField(item);
            newTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, newTextField.getPreferredSize().height));
            itemPanel.add(newTextField);
        }
        JTextField newTextField = new JTextField();
        newTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, newTextField.getPreferredSize().height));
        itemPanel.add(newTextField);
    }

    private void refreshItemList() {
        String[] items = readCurrentItems(itemPanel); //read items
        insertionSort(items); //sort items

        itemPanelPopulate(itemPanel, items); //populate with new info
        itemPanel.revalidate();
        itemPanel.repaint();

    }

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
        itemPanelPopulate(itemPanel, selectedShelf.getItemArr());
    }

    public void saveShelfItems() {
        selectedShelf.setItemArr(readCurrentItems(itemPanel));
    }


}
