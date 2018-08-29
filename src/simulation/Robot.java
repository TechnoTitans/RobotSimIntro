package simulation;

import org.usfirst.frc.team1683.driveTrain.TankDrive;

import commands.MoveToObject;


public class Robot extends SimIterativeRobot {
	public static TankDrive driveTrain;
	
	@Override
	public void robotInit() {
		driveTrain = new TankDrive(super.getLeftSimGroup(), super.getRightSimGroup());
	}
	
	public void autonomousInit() {
//		new MoveToObject().start();
	}
	
	@Override
	public void autonomousPeriodic() {
		if (gyro.getAngle() < 10) driveTrain.set(0.5, -0.5);
		else driveTrain.stop();
		Main.debug.put("Vision distance", vision.getDistance());
		Main.debug.put("Vision angle", vision.getAngle());
		Main.debug.put("Cube skew", vision.getSkew());
	}
}
