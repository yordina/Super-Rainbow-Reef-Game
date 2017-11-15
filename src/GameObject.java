

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

public abstract class GameObject implements Observer{
    private Image img;
    private Point initialLocation;
    private Point currentLocation;
    private int width, height;
    private boolean isShown;
    private Rectangle objectBox;


    public GameObject() {}
    public GameObject(Image img, Point initialLocation) {
        this.img = img;
        this.initialLocation = initialLocation;
        this.currentLocation = this.initialLocation;
        this.width = this.img.getWidth(null);
        this.height = this.img.getHeight(null);
        objectBox = new Rectangle(this.initialLocation.x, this.initialLocation.y, this.width, this.height);
        isShown = true;
    }


    public Image getImg() { return img; }
    public void setImg(Image img) {
        this.img = img;
        this.width = this.img.getWidth(null);
        this.height = this.img.getHeight(null);
    }
    public Point getInitialLocation() { return initialLocation; }
    public Point getCurrentLocation() { return currentLocation; }
    public void setNewLocation(Point location) { this.currentLocation = location; };
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isShown() { return isShown; }
    public void hidde() { this.isShown = false; }
    public void show() { this.isShown = true; }
    public Rectangle getObjectBox() { return objectBox; }
    public int getX() { return objectBox.x; }
    public int getY() { return objectBox.y; }
    public void setX(int x) { this.objectBox.x = x; }
    public void setY(int y) { this.objectBox.y = y; }

    // check collision
    public boolean collision(GameObject otherObject) {
        return objectBox.intersects(otherObject.getObjectBox());
    }

    public void draw(Graphics2D g2, ImageObserver obs) {
        if (isShown)
            g2.drawImage(img, objectBox.x, objectBox.y, obs);
    }

    public void update(Observable obj, Object arg) {

    }
    public void update() {

    }
    public void updateLeft(int left) { }
    public void updateRight(int right) { }
    public void updateUp(int up) { }
    public void updateDown(int down) { }
    public void setFire() { }
    public void setUnFire() { }
    public ListIterator<BoundingRectangle> getBoundingList() { return null; }
}
