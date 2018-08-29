package org.usfirst.frc.team1683.sensors;

import math.Vector2D;

public class Vision {
	private double cubeRotation,
					cubeX,
					cubeY;
	
	private double angleToCube = 0;
	private Vector2D diffVecToCube;
	
	private Vector2D perpVec;
	
	public Vision(double cubeRotation, double cubeX, double cubeY) {
		this.cubeX = cubeX;
		this.cubeY = cubeY;
		this.cubeRotation = cubeRotation;
	}
	
	public double getAngle() {
		if (!isVisible()) return -1;
		return Math.toDegrees(angleToCube);
	}
	
	public double getDistance() {
		if (!isVisible()) return -1;
		return diffVecToCube.magnitude();
	}
	
	public double getSkew() {
		if (!isVisible()) return -1;
		return castAngle(Math.toDegrees(Math.PI/2+perpVec.getAngle()-cubeRotation));
	}
	
	private double castAngle(double angle) {
		angle %= 360;
		if (angle < -180) angle += 360;
		if (angle > 180) angle -= 360;
		return angle;
	}
	
	public void update(double robotX, double robotY) {
		diffVecToCube = new Vector2D(cubeX - robotX, cubeY - robotY);
	}
	
	public void update(Vector2D robotPos) {
		update(robotPos.x, robotPos.y);
	}

	public void updatePerpVec(Vector2D perpVec) {
		this.perpVec = perpVec.copy();
		this.perpVec.multiply(-1);
		updateAngle();
	}
	
	public boolean isVisible() {
		return diffVecToCube.x * perpVec.x + diffVecToCube.y * perpVec.y > diffVecToCube.magnitude() / 2.0;
	}
	
	public void updateAngle() {
		angleToCube = Math.asin((diffVecToCube.y * perpVec.x - diffVecToCube.x * perpVec.y) / diffVecToCube.magnitude());
	}
}
