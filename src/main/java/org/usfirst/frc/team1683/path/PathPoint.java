package org.usfirst.frc.team1683.path;

/**
 * A point on a path
 * Contains useful data such as the angle and distance
 * @author Pran
 *
 */
public class PathPoint {
	private double x, y, angle;
	private boolean isRelative;
	/**
	 * Creates a point on a path
	 * @param x The x coordinate, in inches
	 * @param y The y coordinate, in inches
	 * Note: isRelative defaults to true
	 */
	public PathPoint(double x, double y) {
		this(x, y, true);
	}
	/**
	 * Creates a point on a path
	 * @param x The x coordinate, in inches
	 * @param y The y coordinate, in inches
	 * @param isRelative True if the point should be considered relative to the previous point (or the origin if it is the first point) in the path,
	 * 						false if it should be considered absolutely on the plane.
	 */
	public PathPoint(double x, double y, boolean isRelative) {
		this.x = x;
		this.y = y;
		this.isRelative = isRelative;
		angle = Math.toDegrees(Math.atan2(y, x));
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public static void convertAbsoluteToRelative(PathPoint[] path) {
		for (int i = path.length - 1; i > 0; --i) {
			if (!path[i].isRelative()) {
				path[i].setRelativeTo(path[i - 1]);
			}
		}
	}
	/**
	 * 
	 * @return The angle this point is on, in degrees, where 0 degrees is horizontal and moving counterclockwise
	 * 
	 */
	public double getAngle() {
		return angle;
	}
	public double getDistance() {
		return Math.hypot(x, y);
	}
	public boolean isRelative() {
		return isRelative;
	}
	public String toString() {
		return x + ", " + y + ", " + getAngle();
	}
	private void setRelativeTo(PathPoint other) {
		x -= other.x;
		y -= other.y;
		isRelative = true;
		angle = Math.toDegrees(Math.atan2(y, x));
	}
	
	/**
	 * Creates a path point that has coordinates (0, 0) (aka the robot doesn't move)
	 * but has a specified angle
	 * This is useful when you want the robot to turn at the end of the path
	 * but not move any
	 * @param angle The angle to turn in degrees.
	 * Note: 0 does NOT mean no turn, 0 means align with the horizontal x-axis
	 * @return A path point at the origin with the specified angle
	 */
	public static PathPoint fromAngle(double angle) {
		PathPoint p = new PathPoint(0, 0);
		angle %= 360;
		if (angle > 180)
			angle -= 360;
		p.angle = angle;
		return p;
	}
	
	/**
	 * Creates a path point with coordinates (0, 0) but pointed towards the point (x, y)
	 * @param x
	 * @param y
	 * @return A path point pointed at the origin pointed at (x, y)
	 * @see PathPoint#fromAngle(double angle)
	 */
	public static PathPoint inDirectionOf(double x, double y) {
		PathPoint p = new PathPoint(x, y);
		p.x = 0;
		p.y = 0;
		return p;
	}
}
