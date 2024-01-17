public class Shelf {
    //Instance Variables
    private String name;
    private int xPos;
    private int yPos;
    private int length;
    private int width;
    private String[] itemArr;
    private int rotationAngle;

    public Shelf(String name_, int xPos_, int yPos_, int length_, int width_, String[] itemArr_) {
        name = name_;
        xPos = xPos_;
        yPos = yPos_;
        length = length_;
        width = width_;
        itemArr = itemArr_;
        rotationAngle = 0;
    }

    //Accessor Methods
    public String getName() {
        return name;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public String[] getItemArr() {
        return itemArr;
    }

    //Instance Methods

}
