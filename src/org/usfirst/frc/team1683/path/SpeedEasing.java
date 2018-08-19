package org.usfirst.frc.team1683.path;

public interface SpeedEasing {
	/**
	 * Takes in the current distance and total distance and returns what fraction of the total
	 * speed to go
	 * @param currentDistance The current distance the mover has traveled, should be nonnegative
	 * @param totalDistance The total distance the mover needs to move, should be nonnegative
	 * @return What fraction of the maximum speed to go at in order for there to not be any
	 * jerky starts/stops. This is a value between 0 and 1.
	 */
	public double getSpeed(double currentDistance, double totalDistance);
}
