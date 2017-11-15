

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ListIterator;


public class RainbowPop extends GameObject {
    private int direction;
    private int speed;
    private ReefWorld rainbowRoom;
    private int power;
    private int width, height;
    private int imgDirection;
    private int lastKeyFrame;
    private Rectangle popBox;
    private boolean isStop;
    private boolean firstFire;
    private boolean isDuplicate;
    String pop = "Resources/Pop_strip45.png";

    public RainbowPop(int x, int y, int direction, int speed, ReefWorld rainbowRoom, boolean isDuplicate) {
        super(ReefWorld.getImage("Resources/Pop_strip45.png"), new Point(x, y));
        this.direction = direction;
        this.speed = speed;
        this.rainbowRoom = rainbowRoom;
        width = 35;
        height = 35;
        imgDirection = 0;
        lastKeyFrame = 0;
        popBox = new Rectangle(x, y, 35, 35);
        isStop = true;
        firstFire = true;
    }

    public void update() {
        int movementX, movementY;
        Rectangle thisNextBox;
        ListIterator<GameObject> objectsInWorld;
        GameObject otherObj;
        int newDirection;

        if (!isStop) {
            movementX = popBox.x + (int) (speed * Math.cos(Math.toRadians((double) direction)));
            movementY = popBox.y - (int) (speed * Math.sin(Math.toRadians((double) direction)));
            thisNextBox = new Rectangle(movementX, movementY, this.width, this.height);
            objectsInWorld = rainbowRoom.getGameObjects();
            if (collision(thisNextBox, rainbowRoom.getKatch().getObjectBox())) {
                ReefWorld.sound.play("Resources/Sound_katch.wav");
                popBox.x = movementX;
                popBox.y = movementY;
                direction = getKatchBounceDirection(rainbowRoom.getKatch().getBoundingList());
                movementX = popBox.x + (int) (speed * Math.cos(Math.toRadians((double) direction)));
                movementY = popBox.y - (int) (speed * Math.sin(Math.toRadians((double) direction)));

            }
            while (objectsInWorld.hasNext()) {
                otherObj = objectsInWorld.next();
                if (!(otherObj instanceof Wall) && otherObj.isShown() && collision(thisNextBox, otherObj.getObjectBox())) {

                    if (otherObj instanceof RainbowWallMask
                            || otherObj instanceof RainbowSolidblockMask
                            || otherObj instanceof Blocks
                            || otherObj instanceof RainbowLegs) {
                        if (otherObj instanceof Blocks)
                            newDirection = getBounceDirection(otherObj.getBoundingList(), thisNextBox, true);
                        else
                            newDirection = getBounceDirection(otherObj.getBoundingList(), thisNextBox, false);
                        this.direction += newDirection;
                        if (direction < 0)
                            direction += 360;
                        else if (direction > 360)
                            direction -= 360;
                        movementX = popBox.x + (int) (speed * Math.cos(Math.toRadians((double) direction)));
                        movementY = popBox.y - (int) (speed * Math.sin(Math.toRadians((double) direction)));
                        if (otherObj instanceof Blocks) {
                            ReefWorld.sound.play("Resources/Sound_block.wav");
                            ((Blocks) otherObj).reduceStrangth();
                            rainbowRoom.getKatch().updateScore(((Blocks) otherObj).getGiveScore());
                            if (((Blocks) otherObj).getType().equals("Block_split")) {
                                ReefWorld.sound.play("Resources/Treasure.wav");
                                RainbowPop duplicate = new RainbowPop(popBox.x, popBox.y, direction + 90, speed, rainbowRoom, true);
                                rainbowRoom.addPops(duplicate);
                                duplicate.startMove();
                            }
                            else if (((Blocks) otherObj).getType().equals("Block_life")) {
                                ReefWorld.sound.play("Resources/Treasure.wav");
                                rainbowRoom.getKatch().addLive();
                            }
                        }
                        if (otherObj instanceof RainbowLegs) {
                            ReefWorld.sound.play("Resources/Sound_bigleg.wav");
                            ((RainbowLegs) otherObj).reduceStangth();
                            rainbowRoom.getKatch().updateScore(((RainbowLegs) otherObj).getGiveScore());
                        }
                        if (otherObj instanceof RainbowWallMask)
                            ReefWorld.sound.play("Resources/Sound_wall.wav");
                        break;

                    }
                }
            }

            popBox.x = movementX;
            popBox.y = movementY;
        }
        if (popBox.y > 600) {
            ReefWorld.sound.play("Resources/Sound_lost.wav");
            super.hidde();
            rainbowRoom.getPops().remove(this);
        }
        if (rainbowRoom.getPops().isEmpty())
            rainbowRoom.getKatch().reduceLive();
    }
    public void startMove() {
        isStop = false;
        direction = 70;
    }
    public void moveLeft(int speed) {
        popBox.x -= speed;
    }
    public void moveRight(int speed) {
        popBox.x += speed;
    }
    public ListIterator<BoundingRectangle> getBoundingList() {
        return null;
    }
    public boolean collision(Rectangle thisNextBox, Rectangle otherBox) {
        return thisNextBox.intersects(otherBox);
    }
//

    public int getBounceDirection(ListIterator<BoundingRectangle> boundsList, Rectangle thisNextBox, boolean isBlocks) {
        int result = 0;
        BoundingRectangle bound = null;
        boolean collision = false;
        while (boundsList.hasNext()) {
            bound = boundsList.next();
            if (thisNextBox.intersects(bound.getRectangle())) {
                if (isBlocks)
                collision = true;
                break;
            }
        }

            if (bound.getPosition().equals("top")) {
                if (direction > 270 && direction < 360)
                    result = (360 - direction) * 2;
                else if (direction > 180 && direction < 270)
                    result = (180 - direction) * 2;
                else if (direction == 360 || direction == 0 || direction == 270 || direction == 180 || direction == 90)
                    result = 180;
            } else if (bound.getPosition().equals("bottom")) {
                if (isBlocks)
                if (direction > 0 && direction < 90)
                    result = (0 - direction) * 2;
                else if (direction > 90 && direction < 180)
                    result = (180 - direction) * 2;
                else if (direction == 360 || direction == 0 || direction == 270 || direction == 180 || direction == 90)
                    result = 180;
            } else if (bound.getPosition().equals("left")) {
                if (direction > 0 && direction < 90)
                    result = (90 - direction) * 2;
                else if (direction > 270 && direction < 360) {
                    result = (270 - direction) * 2;
                }
                else if (direction == 360 || direction == 0 || direction == 270 || direction == 180 || direction == 90)
                    result = 180;
            } else {
                if (direction > 90 && direction < 180)
                    result = (90 - direction) * 2;
                else if (direction > 180 && direction < 270)
                    result = (270 - direction) * 2;
                else if (direction == 360 || direction == 0 || direction == 270 || direction == 180 || direction == 90)
                    result = 180;
            }

        return result;
    }

    public int getKatchBounceDirection(ListIterator<BoundingRectangle> boundsList) {
        int result;
        BoundingRectangle bound = null;
        while (boundsList.hasNext()) {
            bound = boundsList.next();
            if (popBox.intersects(bound.getRectangle()))
                break;
        }
        if (bound.getPosition().equals("left1"))
            result = 130;
        else if (bound.getPosition().equals("left2"))
            result = 110;
        else if (bound.getPosition().equals("middle"))
            result = 90;
        else if (bound.getPosition().equals("right1"))
            result = 70;
        else
            result = 50;

        return result;
    }

    public void draw(Graphics2D g2, ImageObserver obs) {
        if (super.isShown()) {
            g2.drawImage(super.getImg(),
                            popBox.x, popBox.y,
                            popBox.x + width, popBox.y + height,
                            imgDirection / 8 * width, 0,
                            imgDirection / 8 * width + width, height,
                            obs);
            imgDirection += 4;
            if (imgDirection == 360)
                imgDirection = 0;
        }
    }
}
