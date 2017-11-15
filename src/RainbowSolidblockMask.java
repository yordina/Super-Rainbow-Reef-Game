

import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;


public class RainbowSolidblockMask extends GameObject {
    private int width, height;
    private ArrayList<BoundingRectangle> boundList;
    private Rectangle box;
    public RainbowSolidblockMask(int x, int y, int height) {
        super((height == 180) ? ReefWorld.getImage("Resources/Block_solid_mask1.png") : ReefWorld.getImage("Resources/Block_solid_mask2.png"), new Point(x, y));
        this.height = height;
        width = super.getWidth();
        boundList = new ArrayList<>();
        createBoundingList();
        box = new Rectangle(x, y, width, height);
        System.out.println("Mask: " + box);
    }
    public void createBoundingList() {
        int brThickness = 2;
        Rectangle left = new Rectangle(super.getX(), super.getY(), brThickness, height);
        boundList.add(new BoundingRectangle(left, "left"));
        Rectangle top = new Rectangle(super.getX(), super.getY(), super.getWidth(), brThickness);
        boundList.add(new BoundingRectangle(top, "top"));
        Rectangle right = new Rectangle(super.getX() + super.getWidth(), super.getY(), brThickness, height);
        boundList.add(new BoundingRectangle(right, "right"));
        Rectangle bottom = new Rectangle(super.getX(), super.getY() + height, super.getWidth(), brThickness);
        boundList.add(new BoundingRectangle(bottom, "bottom"));
    }
    public ListIterator<BoundingRectangle> getBoundingList() {
        return boundList.listIterator();
    }
    public Rectangle getObjectBox() {
        return box;
    }
}
