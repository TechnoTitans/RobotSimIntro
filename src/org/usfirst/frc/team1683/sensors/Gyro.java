package org.usfirst.frc.team1683.sensors;

import simulation.Main;

public class Gyro {
	private double angle = 0;
	private static final double VARIATION = 0.0006;
	public void reset() {
		angle = 0;
	}

	public double getAngle() {
		return Math.toDegrees(angle);
	}

	public void changeAngle(double newAngle, double oldAngle) {
		double deltaAngle = newAngle - oldAngle;
		deltaAngle %= 2*Math.PI;
		if (deltaAngle > Math.PI) {
			deltaAngle -= 2*Math.PI;
		} else if (deltaAngle < -Math.PI) {
			deltaAngle += 2*Math.PI;
		}
		angle += deltaAngle + (Main.r.nextDouble() - 0.5) * VARIATION;;
	}
}
