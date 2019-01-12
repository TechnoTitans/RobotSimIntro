package org.usfirst.frc.team1683.sensors;

import math.Vector2D;

public class VisionStripSensor {
    private double stripX,
            stripY;

    private double angleToStrip = 0;
    private Vector2D diffVecToStrip;

    private Vector2D perpVec;

    public VisionStripSensor(double stripX, double cubeY) {
        this.stripX = stripX;
        this.stripY = cubeY;
    }

    public double getAngle() {
        if (!isVisible()) return -1;
        return Math.toDegrees(angleToStrip);
    }

    public double getDistance() {
        if (!isVisible()) return -1;
        return diffVecToStrip.magnitude();
    }

    public double getSkew() {
        if (!isVisible()) return -1;
        return castAngle(Math.toDegrees(Math.PI/2+perpVec.getAngle()));
    }

    private double castAngle(double angle) {
        angle %= 360;
        if (angle < -180) angle += 360;
        if (angle > 180) angle -= 360;
        return angle;
    }

    public void update(double robotX, double robotY) {
        diffVecToStrip = new Vector2D(stripX - robotX, stripY - robotY);
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
        return diffVecToStrip.x * perpVec.x + diffVecToStrip.y * perpVec.y > diffVecToStrip.magnitude() / 2.0;
    }

    public void updateAngle() {
        angleToStrip = Math.asin((diffVecToStrip.y * perpVec.x - diffVecToStrip.x * perpVec.y) / diffVecToStrip.magnitude());
    }
}
