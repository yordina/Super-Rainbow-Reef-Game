

import java.awt.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class Blocks extends GameObject {
    private int strength;
    private int giveScore;
    private String type;
    private ArrayList<BoundingRectangle> boundList;
    public Blocks(String type, int x, int y, int strengthngth) {
        super(ReefWorld.getImage(type), new Point(x * 20, y * 20));
        this.strength = strength;
        this.type = type;
        boundList = new ArrayList<>();
        if (!this.equals("Resources/Block_solid.png"))
            createBoundingList();
        setScore();
    }
    public void reduceStrangth() {
        if (!type.equals("Resources/Block_solid.png")) {
            this.strength -= 100;
            if (strength == 0) {
                super.hidde();
                ReefWorld.getRoom().updatePassCounter(-1);
            }
        }
    }
    public int getGiveScore() { return giveScore; }
    public String getType() { return type; }
    public void createBoundingList() {
        int brThickness = 2;
        Rectangle left = new Rectangle(super.getX(), super.getY() + 2, brThickness, super.getHeight() - 4);
        boundList.add(new BoundingRectangle(left, "left"));
        Rectangle top = new Rectangle(super.getX() , super.getY(), super.getWidth(), brThickness);
        boundList.add(new BoundingRectangle(top, "top"));
        Rectangle right = new Rectangle(super.getX() + super.getWidth() - brThickness, super.getY() + 2, brThickness, super.getHeight() - 4);
        boundList.add(new BoundingRectangle(right, "right"));
        Rectangle bottom = new Rectangle(super.getX(), super.getY() + super.getHeight() - brThickness, super.getWidth(), brThickness);
        boundList.add(new BoundingRectangle(bottom, "bottom"));
    }
    public ListIterator<BoundingRectangle> getBoundingList() {
        return boundList.listIterator();
    }
    public void setScore() {
        if (type.equals("Resources/Block_double.png"))
            giveScore = 100;
        else if (type.equals("Resources/Block_life.png"))
            giveScore = 400;
        else if (type.equals("Resources/Block_solid.png"))
            giveScore = 0;
        else if (type.equals("Resources/Block_split.png"))
            giveScore = 300;
        else
            giveScore = 100;
    }
}
