import java.awt.*;

public class BoundingRectangle {
    private Rectangle rectangle;
    private String position;

    public BoundingRectangle(Rectangle rectangle, String position) {
        this.rectangle = rectangle;
        this.position = position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public String getPosition() {
        return position;
    }

    public boolean intersects(Rectangle r) {
        return rectangle.intersects(r);
    }
}
