import java.awt.*;

/**
 * The interface Gbc layout organiser.
 */
public interface GBCLayoutOrganiser {
    //Code from Hovercraft Full Of Eels from https://stackoverflow.com/questions/9851688/how-to-align-left-or-right-inside-gridbaglayout-cell
    /**
     * Create grid bag constraints.
     * @param x the x grid value
     * @param y the y grid value
     * @return the grid bag constraints
     */
    default GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }
}
