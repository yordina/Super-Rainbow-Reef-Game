

import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class RainbowWallMask extends GameObject {
    private int width, height;
    private ArrayList<BoundingRectangle> boundList;
    private Rectangle box;
    public RainbowWallMask(int x, int y, boolean isVertical) {
        super(isVertical ? ReefWorld.getImage("Resources/WallMask_v.png") : ReefWorld.getImage("Resources/WallMask_v.png"), new Point(x, y));
        boundList = new ArrayList<>();
        width = isVertical ? 20 : 800;
        height = isVertical ? 600 : 20;
        createBoundingList();
        box = new Rectangle(x, y, width, height);
    }
    public void createBoundingList() {
        int brThickness = 2;
        Rectangle left = new Rectangle(super.getX(), super.getY(), brThickness, height);
        boundList.add(new BoundingRectangle(left, "left"));
        Rectangle top = new Rectangle(super.getX() + 2, super.getY(), super.getWidth() - 4, brThickness);
        boundList.add(new BoundingRectangle(top, "top"));
        Rectangle right = new Rectangle(super.getX() + super.getWidth() - 2, super.getY(), brThickness, height);
        boundList.add(new BoundingRectangle(right, "right"));
        Rectangle bottom = new Rectangle(super.getX() + 2, super.getY() + height - 2, super.getWidth() - 4, brThickness);
        boundList.add(new BoundingRectangle(bottom, "bottom"));
    }
    public ListIterator<BoundingRectangle> getBoundingList() {
        return boundList.listIterator();
    }
    public Rectangle getObjectBox() { return box; }
}

