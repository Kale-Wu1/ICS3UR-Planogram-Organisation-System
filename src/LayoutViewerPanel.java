import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class LayoutViewerPanel extends JPanel {
    LayoutViewerWindow parentWindow;
    ArrayList<Shelf> shelfArr;
    Shelf selectedShelf;

    public LayoutViewerPanel(LayoutViewerWindow parentWindow_, ArrayList<Shelf> shelfArr_) {
        parentWindow = parentWindow_;
        shelfArr = shelfArr_;
        selectedShelf = null;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShelf = checkShelfClicked(e.getX(), e.getY());

                if(parentWindow.getCurrentCard() == 0 && selectedShelf != null) {
                    openItemViewer(selectedShelf);
                    System.out.println("Selected shelf: " + selectedShelf.getName());
                    System.out.println("With items" + Arrays.toString(selectedShelf.getItemArr()));
                } else if(parentWindow.getCurrentCard() == 2) {
                    parentWindow.getEditorMenu().setSelectedShelf(selectedShelf);
                } else if(parentWindow.getCurrentCard() == 4) {
                    if(selectedShelf != null) {
                        openItemViewer(selectedShelf);
                        System.out.println("Opened Array:" + Arrays.toString(selectedShelf.getItemArr()));
                    } else {
                        parentWindow.setCard(0);
                    }
                    parentWindow.getItemViewMenu().saveShelfItems();
                } else {
                    parentWindow.setCard(0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawShelves(g);
    }

    //Draw all shelves in shelf array
    private void drawShelves(Graphics g) {
        shelfArr = parentWindow.getStorageLayout().getShelfList();
        for(Shelf element : shelfArr) {
            g.drawRect(element.getXPos(), element.getYPos(), element.getWidth(), element.getLength());
            FontMetrics fontMetrics = g.getFontMetrics();
            int textXPos = element.getXPos() + element.getWidth()/2 - fontMetrics.stringWidth(element.getName()) / 2;
            int textYPos = element.getYPos() + element.getLength()/2 - fontMetrics.getHeight() / 2;
            g.drawString(element.getName(), textXPos, textYPos);
        }
        revalidate();
        repaint();
    }

    private Shelf checkShelfClicked(int xClicked, int yClicked) {
        for(Shelf element : shelfArr) {
            if(element.isClicked(xClicked, yClicked)) {
                return element;
            }
        }
        return null;
    }

    private void openItemViewer(Shelf selectedShelf) {
        parentWindow.getItemViewMenu().setSelectedShelf(selectedShelf);
        parentWindow.setCard(4);

    }

    //Accessors
}
