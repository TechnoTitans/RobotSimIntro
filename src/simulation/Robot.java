package simulation;

import org.usfirst.frc.team1683.driveTrain.DriveTrain;
import org.usfirst.frc.team1683.driveTrain.TankDrive;
import org.usfirst.frc.team1683.path.LinearEasing;
import org.usfirst.frc.team1683.path.Path;
import org.usfirst.frc.team1683.path.PathPoint;
import org.usfirst.frc.team1683.path.SpeedEasing;


public class Robot extends SimIterativeRobot {
	private TankDrive driveTrain;
	
	@Override
	public void robotInit() {
		driveTrain = new TankDrive(super.getLeftSimGroup(), super.getRightSimGroup());
	}
	
	@Override
	public void autonomousPeriodic() {
		Main.debug.put("Gyro", gyro.getAngle());
		driveTrain.set(0.5);
	}
}