

import java.awt.*;
import java.util.ArrayList;

public class Wall extends GameObject {
    private ArrayList<BoundingRectangle> boundList;
    public Wall(int x, int y, Image img) {
        super(img, new Point(x * 20, y * 20));
        boundList = new ArrayList<>();
    }
    public void createBoundingList() {
        int brThickness = 2;
        Rectangle left = new Rectangle(super.getX(), super.getY(), brThickness, super.getHeight());
        boundList.add(new BoundingRectangle(left, "left"));
        Rectangle top = new Rectangle(super.getX() + 2, super.getY(), super.getWidth() - 4, brThickness);
        boundList.add(new BoundingRectangle(top, "top"));
        Rectangle right = new Rectangle(super.getX() + super.getWidth() - 2, super.getY(), brThickness, super.getHeight());
        boundList.add(new BoundingRectangle(right, "right"));
        Rectangle bottom = new Rectangle(super.getX() + 2, super.getY() + super.getHeight() - 2, super.getWidth() - 4, brThickness);
        boundList.add(new BoundingRectangle(bottom, "bottom"));
    }
//
}
