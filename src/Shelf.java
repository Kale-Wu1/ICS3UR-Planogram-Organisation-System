import java.awt.*;

public class Shelf {
    //Instance Variables
    private String name;
    private int xPos;
    private int yPos;
    private int length;
    private int width;
    private String[] itemArr;
    private int rotationAngle;

    public Shelf(String name_, int xPos_, int yPos_, int length_, int width_, int rotationAngle_, String[] itemArr_) {
        name = name_;
        xPos = xPos_;
        yPos = yPos_;
        length = length_;
        width = width_;
        itemArr = itemArr_;
        rotationAngle = rotationAngle_;

    }

    public Shelf() {
        this("Shelf", 0, 0, 10, 10, 0, new String[0]);
    }

    //Accessor Methods
    public String getName() {
        return name;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public String[] getItemArr() {
        return itemArr;
    }


    //Mutator Methods
    public void setName(String name_) {
        name = name_;
    }

    public void setXPos(int xPos_) {
        xPos = xPos_;
    }

    public void setYPos(int yPos_) {
        yPos = yPos_;
    }

    public void setLength(int length_) {
        length = length_;
    }

    public void setWidth(int width_) {
        width = width_;
    }

    public void setRotationAngle(int rotationAngle_) {
        rotationAngle = rotationAngle_;
    }

    public void setItemArr(String[] itemArr_) {
        itemArr = itemArr_;
    }

    //Instance Methods
    public boolean isClicked(int xClicked, int yClicked) {
        return (xClicked > xPos && xClicked < xPos+width) && (yClicked > yPos && yClicked < yPos+length);
    }
}
