package simulation;

import org.usfirst.frc.team1683.driveTrain.TankDrive;

import commands.Forward;
import commands.MoveToObject;
import commands.Square;
import commands.Turn;


public class Robot extends SimIterativeRobot {
	public static TankDrive driveTrain;
	
	@Override
	public void robotInit() {
		driveTrain = new TankDrive(super.getLeftSimGroup(), super.getRightSimGroup());
	}
	
	public void autonomousInit() {
		new Square(20000, 1).start();
	}
	
	@Override
	public void autonomousPeriodic() {
		Command.runAllCommands();
		
		Main.debug.put("Gyro", gyro.getAngle());
		Main.debug.put("Left encoder", driveTrain.getLeftEncoder().getDistance());
		Main.debug.put("Vision distance", vision.getDistance());
		Main.debug.put("Vision angle", vision.getAngle());
		Main.debug.put("Cube skew", vision.getSkew());
	}
}
