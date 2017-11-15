

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.ListIterator;

public class RainbowLegs extends GameObject {
    private int strength;
    private int giveScore;
    private int width, height;
    private Rectangle legBox;
    private int lastKeyFrame;
    private ReefWorld rainbowRoom;
    private int frameRate;
    private ArrayList<BoundingRectangle> boundList;
    public RainbowLegs(String path, int x, int y) {
        super(ReefWorld.getImage(path), new Point(x * 20, y * 20));
        strength = path.equals("Resources/Bigleg_strip24.png") ? 200 : 100;
        giveScore = path.equals("Resources/Bigleg_strip24.png") ? 200 : 100;
        width = path.equals("Resources/Bigleg_strip24.png") ? 80 : 40;
        height = path.equals("Resources/Bigleg_strip24.png") ? 80 : 40;
        legBox = new Rectangle(x * 20, y * 20, width, height);
        rainbowRoom = ReefWorld.getRoom();
        lastKeyFrame = rainbowRoom.getFrameNumber();
        frameRate = path.equals("Resources/Bigleg_strip24.png") ? 18 : 12;
        boundList = new ArrayList<>();
        createBoundingList();
    }
    public Rectangle getObjectBox() { return legBox; }
    public int getStrangth() { return strength; }
    public int getGiveScore() { return giveScore; }
    public void reduceStangth() {
        strength -= 50;
        if (strength == 0) {
            super.hidde();
            ReefWorld.getRoom().updatePassCounter(-1);
        }
    }
    public void draw(Graphics2D g2, ImageObserver obs) {
        if (super.isShown()) {
            if (rainbowRoom.getFrameNumber() < lastKeyFrame + frameRate) {
                g2.drawImage(super.getImg(),
                            legBox.x, legBox.y,
                            legBox.x + width, legBox.y + height,
                            0, 0,
                            width, height,
                            obs);
            } else if (rainbowRoom.getFrameNumber() >= lastKeyFrame + frameRate) {
                g2.drawImage(super.getImg(),
                            legBox.x, legBox.y,
                            legBox.x + width, legBox.y + height,
                            width, 0,
                            width * 2, height,
                            obs);
                if (rainbowRoom.getFrameNumber() == lastKeyFrame + frameRate * 2)
                    lastKeyFrame = rainbowRoom.getFrameNumber();
            }
        }
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
    public ListIterator<BoundingRectangle> getBoundingList() {
        return boundList.listIterator();
    }
}
