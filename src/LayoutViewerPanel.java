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
                } else if(parentWindow.getCurrentCard() == 2) {
                    parentWindow.getEditorMenu().setSelectedShelf(selectedShelf);
                } else if(parentWindow.getCurrentCard() == 4) {
                    parentWindow.getItemViewMenu().saveShelfItems();
                    if(selectedShelf != null) {
                        openItemViewer(selectedShelf);
                    } else {
                        parentWindow.setCard(0);
                    }
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
        for(Shelf shelf : shelfArr) {
            g.drawRect(shelf.getXPos(), shelf.getYPos(), shelf.getWidth(), shelf.getLength());
            if(shelf.getIsHighlighted()) {
                g.setColor(Color.ORANGE);
                g.fillRect(shelf.getXPos(), shelf.getYPos(), shelf.getWidth(), shelf.getLength());
                g.setColor(Color.BLACK);
            }
            FontMetrics fontMetrics = g.getFontMetrics();
            int textXPos = shelf.getXPos() + shelf.getWidth()/2 - fontMetrics.stringWidth(shelf.getName()) / 2;
            int textYPos = shelf.getYPos() + shelf.getLength()/2 - fontMetrics.getHeight() / 2;
            g.drawString(shelf.getName(), textXPos, textYPos);
        }
        revalidate();
        repaint();
    }

    private Shelf checkShelfClicked(int xClicked, int yClicked) {
        for(Shelf shelf : shelfArr) {
            if(shelf.isClicked(xClicked, yClicked)) {
                return shelf;
            }
        }
        return null;
    }

    private void openItemViewer(Shelf selectedShelf) {
        parentWindow.getItemViewMenu().setSelectedShelf(selectedShelf);
        parentWindow.setCard(4);

    }

    public void clearHighlightedShelves() {
        for(Shelf shelf : parentWindow.getStorageLayout().getShelfList()) {
            shelf.setHighlighted(false);
        }
        revalidate();
        repaint();
    }

    //Accessors
}
