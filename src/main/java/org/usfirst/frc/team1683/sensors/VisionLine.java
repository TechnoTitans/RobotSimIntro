package org.usfirst.frc.team1683.sensors;

import simulation.Main;

public class VisionLine {
    public boolean isLineDetected() {
        double robotX = 0, robotY = 0;
        double fov = 5;
        return robotX > Main.FRAME_WIDTH / 2 + Main.targetWidth / 2 - fov
                    && robotX < Main.FRAME_WIDTH / 2 - Main.targetWidth / 2 + fov
                    && robotY > 3;
    }

    public double  getAngleToLine() {
        if (!isLineDetected()) return 0;
        return 1;
    }
}
