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
		driveTrain.set(0.5, 0.5);
		prevEnc = driveTrain.getLeftEncoder().getDistance();
	}
	
	@Override
	public void autonomousPeriodic() {
		Command.runAllCommands();
		double encoderLeft = driveTrain.getLeftEncoder().getDistance();
		Main.debug.put("Distance", encoderLeft);
		if (encoderLeft > 8000) driveTrain.stop();
	}
}
