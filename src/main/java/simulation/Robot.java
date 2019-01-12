package simulation;

import org.usfirst.frc.team1683.driveTrain.DriveTrain;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.path.LinearEasing;
import org.usfirst.frc.team1683.path.Path;
import org.usfirst.frc.team1683.path.PathPoint;
import org.usfirst.frc.team1683.path.SpeedEasing;

import commands.TestCommand;
import commands.TestCommand2;
import commands.TestCommand3;


public class Robot extends SimIterativeRobot {
	public static TankDrive driveTrain;
	private double prevEnc;
	
	@Override
	public void robotInit() {
		driveTrain = new TankDrive(super.getLeftSimGroup(), super.getRightSimGroup());
	}
	
	public void autonomousInit() {
		driveTrain.set(1);
	}
	
	@Override
	public void autonomousPeriodic() {
		if (driveTrain.getLeftEncoder().getDistance() > 80000) {
			driveTrain.stop();
		}
		Main.debug.put("Vision distance", visionSensor.getDistance());
		Main.debug.put("Vision angle", visionSensor.getAngle());
		Main.debug.put("Skew", visionSensor.getSkew());

	}
}
