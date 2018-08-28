package pid;

import simulation.Command;

public abstract class PIDCommand extends Command implements PIDSource, PIDOutput {
	private PIDController controller;
	
	public PIDCommand(double kP, double kI, double kD) {
		controller = new PIDController(kP, kI, kD, this, this);
	}
	
	public boolean isFinished() {
		return false;
	}
}
