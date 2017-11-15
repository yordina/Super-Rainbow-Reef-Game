

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;


public class Katch extends GameObject {
    private int speed;
    private int width, height, x, y;
    private final ReefWorld rainbowRoom;
    private Point initialLocation;
    private Point currentLocation;
    private boolean fire;
    private Rectangle katchBox;
    private Rectangle[] collisionBoxes;
    private int lastKeyFrame;
    private KeyControl keyControl;
    private boolean isFirstFire;
    private ArrayList<BoundingRectangle> boundsList;

    private int numberOfLives, score;
    private int left = 0, right = 0, up = 0, down = 0;

    public Katch(Image img, Point initialLocation, int speed, int[] controls) {
        super(img, initialLocation);
        rainbowRoom = ReefWorld.getRoom();
        this.speed = speed;
        width = 80;
        height = 30;
        this.initialLocation = initialLocation;
        currentLocation = initialLocation;

        x = initialLocation.x;
        y = initialLocation.y;
        katchBox = new Rectangle(x, y, width, height);
        collisionBoxes = new Rectangle[5];
        lastKeyFrame = rainbowRoom.getFrameNumber();
        keyControl = new KeyControl(this, controls, rainbowRoom);
        boundsList = new ArrayList<>();
        isFirstFire = true;
        numberOfLives = 3;
        createBoundsList();
    }

    public void updateLeft(int left) { this.left = left; }
    public void updateRight(int right) { this.right = right; }
    public void updateUp(int up) { this.up = up; }
    public void updateDown(int down) { this.down = down; }
    public void setFire() { this.fire = true; }
    public void setUnFire() { this.fire = false; }
    public int getX() { return katchBox.x; }
    public int getY() { return katchBox.y; }
    public Rectangle getBox() { return this.katchBox; }
    public int getNumLives() { return numberOfLives; }
    public Point getCurrentLocation() { return currentLocation; }
    public void updateScore(int score) { this.score += score; }
    public int getScore() { return score; }
    public Rectangle getObjectBox() { return katchBox; }
    public void addLive() { numberOfLives++; }
    public void reduceLive() { numberOfLives--; }
    public void setIsFirstFire() { isFirstFire = true; }

    public void update(Observable obj, Object arg) {
        ListIterator<RainbowPop> objIt = rainbowRoom.getPopsIt();
        RainbowPop pop = null;
        if (objIt.hasNext()) {
           pop = objIt.next();
        }
        if (left == 1 && katchBox.x - speed > 22) {
            katchBox.x -= speed;
            if (isFirstFire && pop != null)
                pop.moveLeft(speed);
            updateBoundsList();
        }
        if (right == 1 && katchBox.x + speed < 778 - 80) {
            katchBox.x += speed;
            if (isFirstFire && pop != null)
                pop.moveRight(speed);
            updateBoundsList();
        }
        currentLocation.x = katchBox.x;
        currentLocation.y = katchBox.y;
        if (isFirstFire && fire) {
            pop.startMove();
            isFirstFire = false;
        }
//
    }
    public void createBoundsList() {
        Rectangle left1 = new Rectangle(katchBox.x, katchBox.y, katchBox.width / 5, 5);
        boundsList.add(new BoundingRectangle(left1, "left1"));
        Rectangle left2 = new Rectangle(katchBox.x + katchBox.width / 5, katchBox.y, katchBox.width / 5, 5);
        boundsList.add(new BoundingRectangle(left2, "left2"));
        Rectangle middle = new Rectangle(katchBox.x + katchBox.width / 5 * 2, katchBox.y, katchBox.width / 5, 5);
        boundsList.add(new BoundingRectangle(middle, "middle"));
        Rectangle right1 = new Rectangle(katchBox.x + katchBox.width / 5 * 3, katchBox.y, katchBox.width / 5, 5);
        boundsList.add(new BoundingRectangle(right1, "right1"));
        Rectangle right2 = new Rectangle(katchBox.x + katchBox.width / 5 * 4, katchBox.y, katchBox.width / 5, 5);
        boundsList.add(new BoundingRectangle(right2, "right2"));
    }
    public void updateBoundsList() {
        for (int i = 0; i < 5; i++) {
            boundsList.get(i).getRectangle().x = katchBox.x + i * katchBox.width / 5;
        }
    }
    public ListIterator<BoundingRectangle> getBoundingList() {
        return boundsList.listIterator();
    }
    public void draw(Graphics2D g2, ImageObserver obs) {
        if (super.isShown()) {
            if (rainbowRoom.getFrameNumber() < lastKeyFrame + 15)
                g2.drawImage(super.getImg(),
                            katchBox.x, katchBox.y,
                            katchBox.x + width, katchBox.y + height,
                            0, 0,
                            width, height,
                            obs);
            else if (rainbowRoom.getFrameNumber() >= lastKeyFrame + 15) {
                        g2.drawImage(super.getImg(),
                        katchBox.x, katchBox.y,
                        katchBox.x + width, katchBox.y + height,
                        width, 0,
                        width * 2, height,
                        obs);
                if (rainbowRoom.getFrameNumber() == lastKeyFrame + 30)
                    lastKeyFrame = rainbowRoom.getFrameNumber();
            }
        }
    }
}
