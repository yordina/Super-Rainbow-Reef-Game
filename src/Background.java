

import java.awt.*;
import java.awt.image.ImageObserver;

public class Background extends GameObject {
    int width, height;
    private Image img;

    public Background(Image img, int width, int height) {
        super(img, new Point(0, 0));
        this.img = super.getImg();
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }
    public void draw(Graphics2D g2, ImageObserver obs) {
        g2.drawImage(img, 0, 0, width, height, obs);
    }
}
