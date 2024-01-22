/**
 * The shelf class. Stores information and translates between GUI and file information. Interacts geometrically with GUI.
 */
public class Shelf {
    //Instance Variables
    private String name;
    private int xPos;
    private int yPos;
    private int length;
    private int width;
    private String[] itemArr;
    private int rotationAngle;
    private boolean highlighted;

    /**
     * Instantiates a new Shelf.
     *
     * @param name_          the name
     * @param xPos_          the x pos
     * @param yPos_          the y pos
     * @param length_        the length
     * @param width_         the width
     * @param rotationAngle_ the rotation angle
     * @param itemArr_       the item arr
     */
    public Shelf(String name_, int xPos_, int yPos_, int length_, int width_, int rotationAngle_, String[] itemArr_) {
        name = name_;
        xPos = xPos_;
        yPos = yPos_;
        length = length_;
        width = width_;
        itemArr = itemArr_;
        rotationAngle = rotationAngle_;
    }

    /**
     * Instantiates a default new Shelf.
     */
    public Shelf() {
        this("", 0, 0, 0, 0, 0, new String[0]);
    }

    //Accessor methods
    /**
     * Gets name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets x pos.
     * @return the x pos
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Gets y pos.
     * @return the y pos
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Gets length.
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets width.
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets rotation angle.
     * @return the rotation angle
     */
    public int getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Get item array as String[].
     * @return the String[]
     */
    public String[] getItemArr() {
        return itemArr;
    }

    /**
     * Gets if this shelf is highlighted.
     * @return the is highlighted
     */
    public boolean getIsHighlighted() {
        return highlighted;
    }


    //Mutator Methods
    /**
     * Sets name.
     * @param name_ the name
     */
    public void setName(String name_) {
        name = name_;
    }

    /**
     * Sets x pos.
     * @param xPos_ the x pos
     */
    public void setXPos(int xPos_) {
        xPos = xPos_;
    }

    /**
     * Sets y pos.
     * @param yPos_ the y pos
     */
    public void setYPos(int yPos_) {
        yPos = yPos_;
    }

    /**
     * Sets length.
     * @param length_ the length
     */
    public void setLength(int length_) {
        length = length_;
    }

    /**
     * Sets width.
     * @param width_ the width
     */
    public void setWidth(int width_) {
        width = width_;
    }

    /**
     * Sets rotation angle.
     * @param rotationAngle_ the rotation angle
     */
    public void setRotationAngle(int rotationAngle_) {
        rotationAngle = rotationAngle_;
    }

    /**
     * Sets item array.
     * @param itemArr_ the item array
     */
    public void setItemArr(String[] itemArr_) {
        itemArr = itemArr_;
    }

    /**
     * Sets if this shelf is highlighted.
     * @param highlighted highlighted
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    //Instance Methods
    /**
     * Returns if the coordinates given intersects this shelf
     * @param xClicked the x clicked coordinate
     * @param yClicked the y clicked coordinate
     * @return whether this shelf is clicked
     */
    public boolean isClicked(int xClicked, int yClicked) {
        return (xClicked > xPos && xClicked < xPos+width) && (yClicked > yPos && yClicked < yPos+length);
    }

}
