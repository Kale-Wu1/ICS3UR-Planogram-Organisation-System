import java.awt.*;

public interface GBCLayoutOrganiser {
    //Code from Hovercraft Full Of Eels from https://stackoverflow.com/questions/9851688/how-to-align-left-or-right-inside-gridbaglayout-cell

    default GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
        gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

        gbc.insets = (x == 0) ? new Insets(5, 0, 5, 5) : new Insets(5, 5, 5, 0);
        gbc.weightx = (x == 0) ? 0.1 : 1.0;
        gbc.weighty = 1.0;
        return gbc;
    }
}
