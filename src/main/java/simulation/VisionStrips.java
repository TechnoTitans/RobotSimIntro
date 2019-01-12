package simulation;

import math.Vector2D;

import java.awt.*;

public class VisionStrips {
    Vector2D position;

    private static final int BOX_WIDTH = (int) (15 * Main.PIXELS_PER_INCH);

    public VisionStrips() {
        position = new Vector2D(Main.FRAME_WIDTH / 2,  Main.topY);
    }


    protected void paintComponent(Graphics g) {
        int x = (int) position.x,
                y = (int) position.y;
        Graphics2D gg = (Graphics2D) g;
        gg.setColor(Color.YELLOW);
        gg.fillRect(x - BOX_WIDTH / 2, y - BOX_WIDTH / 2, BOX_WIDTH, BOX_WIDTH);
    }

    double getX() {
        return position.x;
    }

    double getY() {
        return position.y;
    }
}
