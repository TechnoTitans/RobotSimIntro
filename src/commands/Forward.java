package commands;

import pid.PIDCommand;
import simulation.Robot;

public class Forward extends PIDCommand {
	private double distance, speed;
	
	private static final double RAMP_DOWN = 4000;
	
	public Forward(double distance, double speed) {
		super(0.05, 0.0005, 0); // P, I, D
		this.distance = distance;
		this.speed = speed;
	}
	
	@Override
	public void initialize() {
		Robot.driveTrain.resetEncoders();
		Robot.gyro.reset();
	}
	
	@Override
	protected double returnPIDInput() {
		return Robot.gyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double error) {
		Robot.driveTrain.set(speed - error, speed + error);
	}

	@Override
	protected boolean isFinished() {
		double averageDistance = (Robot.driveTrain.getLeftEncoder().getDistance() + Robot.driveTrain.getRightEncoder().getDistance()) / 2;
		return Math.abs(averageDistance) > distance - RAMP_DOWN * speed;
	}
	
	@Override
	public void end() {
		Robot.driveTrain.stop();
	}
	
	@Override
	public void interrupted() {
		end();
	}
	
}
