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

            drawShelfText(g, shelf.getName(), shelf);
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

    private void drawShelfText(Graphics g, String text, Shelf shelf) {
        // Set font
        g.setFont(g.getFont().deriveFont(12f));

        // Get FontMetrics to calculate text size
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getAscent();

        // Calculate position to center the text within the rectangle
        int stringXPos = shelf.getXPos() + (shelf.getWidth() - textWidth) / 2;
        int stringYPos = shelf.getYPos() + (shelf.getLength() + textHeight) / 2;

        // Truncate the text if it exceeds the width of the rectangle
        if (textWidth > shelf.getWidth()) {
            // Truncate text and append ellipsis to indicate truncation
            int ellipsisWidth = g.getFontMetrics().stringWidth("...");
            int remainingWidth = shelf.getWidth() - ellipsisWidth;

            for(int i = text.length(); i > 0; i--) {
                if(g.getFontMetrics().stringWidth(text.substring(0, i)) <= remainingWidth) {
                    text = text.substring(0, i) + "...";
                    break;
                }
            }

            stringXPos = shelf.getXPos();

        }



        // Draw text
        g.setColor(Color.BLACK);
        g.drawString(text, stringXPos, stringYPos);
    }

}
