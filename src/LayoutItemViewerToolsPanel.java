import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Arrays;

public class LayoutItemViewerToolsPanel extends JPanel implements GBCLayoutOrganiser, FileUtils {
    DefaultListModel<JTextField> itemList;
    Shelf selectedShelf;
    LayoutViewerWindow parentWindow;

    public LayoutItemViewerToolsPanel(LayoutViewerWindow parentWindow_, Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
        parentWindow = parentWindow_;

        //Hide this window if created with null selected shelf
        if(selectedShelf == null) {
            parentWindow.setCard(0);
        }

        //Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        JLabel header = new JLabel(selectedShelf.getName() + " Items");
        headerPanel.add(header, createGbc(0, 0));

        //Item List
        JPanel itemListPanel = new JPanel(new GridBagLayout());
        itemList = new DefaultListModel<>(); //Create DefaultListModel for layout titles
        JList<JTextField> itemJList = new JList<>(itemList); //Create JList to contain layoutTitlesList
        itemJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Refresh itemList if list is clicked off.
                if(!e.getValueIsAdjusting()) {
                    if(itemJList.getSelectedIndex() == -1) {
                        refreshItemList(itemList);
                    }
                }
            }
        });
        JScrollPane itemJListScroll = new JScrollPane(itemJList); //Create JScrollPane to contain availLayouts
        itemListPanel.add(itemJListScroll, createGbc(0, 0));


    }

    private void refreshItemList(DefaultListModel<JTextField> OrigItemList) {
        //Remove all empty containers
        for(int i = 0; i < OrigItemList.size(); i++) {
            if(OrigItemList.get(i).getText().isEmpty()) {
                OrigItemList.remove(i);
            }
        }

        //Sort list
        insertionSort(OrigItemList);

        //Copy DefaultListModel to String[] and replace selectedShelf's itemArr
        String[] itemArr = new String[itemList.getSize()];
        for(int i = 0; i < itemList.getSize(); i++) {
            itemArr[i] = itemList.get(i).getText();
        }
        selectedShelf.setItemArr(itemArr);

        //Add Empty Text Area
        OrigItemList.addElement(new JTextField());
    }



    private void insertionSort(DefaultListModel<JTextField> OrigItemList) {
        for (int i = 1; i < OrigItemList.getSize(); i++) {
            for(int j = i; j > 0; j--) {
                if(OrigItemList.get(j).getText().compareToIgnoreCase(OrigItemList.get(j-1).getText()) < 0) { //If item at j is < item at j-1
                    JTextField temp = OrigItemList.get(j);
                    OrigItemList.setElementAt(OrigItemList.get(j-1), j);
                    OrigItemList.setElementAt(temp, j-1);

                } else { //If item at j >= j-1;
                    break;
                }
            }
        }
    }

    public void setSelectedShelf(Shelf selectedShelf_) {
        selectedShelf = selectedShelf_;
    }


}
