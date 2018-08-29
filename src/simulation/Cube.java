package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import math.Vector2D;

public class Cube {
	Vector2D position;
	private double rotation = 0;
	
	private static final int BOX_WIDTH = (int) (15 * Main.PIXELS_PER_INCH);
	
	public Cube() {
		position = new Vector2D(Main.FRAME_WIDTH / 2 + Main.r.nextDouble() * 200 - 100, Main.FRAME_HEIGHT - 350);
		rotation = Main.r.nextDouble() - 0.5;
	}
	

	protected void paintComponent(Graphics g) {
		int x = (int) position.x,
			y = (int) position.y;
		Graphics2D gg = (Graphics2D) g;
		AffineTransform oldXForm = gg.getTransform();
		gg.rotate(rotation, x, y);
		gg.setColor(Color.YELLOW);
	    gg.fillRect(x - BOX_WIDTH / 2, y - BOX_WIDTH / 2, BOX_WIDTH, BOX_WIDTH);
	    gg.setTransform(oldXForm);
	}
	
	double getX() {
		return position.x;
	}
	
	double getY() {
		return position.y;
	}
	
	double getRotation() {
		return rotation;
	}
}
