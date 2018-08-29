package commands;

import pid.PIDCommand;
import simulation.Robot;

public class Turn extends PIDCommand {
	private double angle, speed;
	
	public Turn(double angle, double speed) {
		super(0.05, 0, 0.6);
		this.angle = angle;
		this.speed = speed;
	}
	
	@Override
	public void initialize() {
		Robot.gyro.reset();
	}

	@Override
	protected double returnPIDInput() {
		return angle - Robot.gyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double error) {
		double motorSpeed = error * speed;
		// Ensure that the robot is at least beating friction if there is still error
		if (0 < motorSpeed && motorSpeed < 0.1) motorSpeed = 0.1;
		if (-0.1 < motorSpeed && motorSpeed < 0) motorSpeed = -0.1;
		Robot.driveTrain.set(motorSpeed, -motorSpeed);
	}

	@Override
	protected boolean isFinished() {
		return angle > 0 ? Robot.gyro.getAngle() >= angle : Robot.gyro.getAngle() <= angle;
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
