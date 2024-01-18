import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LayoutViewerPanel extends JPanel {
    LayoutViewerWindow parentWindow;
    Shelf[] shelfArr;
    Shelf selectedShelf;

    public LayoutViewerPanel(LayoutViewerWindow parentWindow_, Shelf[] shelfArr_) {
        parentWindow = parentWindow_;
        shelfArr = shelfArr_;
        selectedShelf = null;

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedShelf = checkShelfClicked(e.getX(), e.getY());
                if(parentWindow.getCurrentCard() == 2) {
                    parentWindow.getEditorMenu().setSelectedShelf(selectedShelf);
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

    }
    private void drawShelves(Graphics g) {
        for(Shelf element : shelfArr) {
            g.drawRect(element.getXPos(), element.getYPos(), element.getWidth(), element.getLength());
            FontMetrics fontMetrics = g.getFontMetrics();
            int textXPos = element.getXPos() + element.getWidth()/2 - fontMetrics.stringWidth(element.getName()) / 2;
            int textYPos = element.getYPos() + element.getLength()/2 - fontMetrics.getHeight() / 2;
            g.drawString(element.getName(), textXPos, textYPos);
        }
    }

    private Shelf checkShelfClicked(int xClicked, int yClicked) {
        for(Shelf element : shelfArr) {
            if(element.isClicked(xClicked, yClicked)) {
                return element;
            }
        }
        return null;
    }
}
