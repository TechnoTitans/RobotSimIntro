package org.usfirst.frc.team1683.sensors;

import math.Vector2D;
import simulation.Main;

public class VisionLine {
    private double robotX = 0, robotY = 0;
    public boolean isLineDetected() {
        double fov = 5;
        System.out.println(robotX + ", " + robotY);
        return robotX < Main.FRAME_WIDTH / 2 - (Main.targetWidth / 2 - fov) * Main.PIXELS_PER_INCH
                    && robotX > Main.FRAME_WIDTH / 2 + (Main.targetWidth / 2 - fov) * Main.PIXELS_PER_INCH
                    && robotY < Main.topY + Main.targetHeight * Main.PIXELS_PER_INCH
                    && robotY > Main.topY;
    }

    public double getDistanceToLine() {
        if (!isLineDetected()) return 0;
        return (robotX - Main.FRAME_WIDTH / 2) / Main.PIXELS_PER_INCH;
    }

    public void update(double robotX, double robotY) {
        this.robotX = robotX;
        this.robotY = robotY;
    }

    public void update(Vector2D robotPos) {
        update(robotPos.x, robotPos.y);
    }
}
