package pid;

import simulation.Command;

public abstract class PIDCommand extends Command {
	private PIDController controller;
	
	public PIDCommand(double kP, double kI, double kD) {
		controller = new PIDController(kP, kI, kD, this::returnPIDInput, this::usePIDOutput);
	}
	
	@Override
	public void execute() {
		controller.calculate();
	}
	
	@Override
	public void end() {
		usePIDOutput(0);
	}
	
	@Override
	public void interrupted() {
		usePIDOutput(0);
	}
	
	protected abstract double returnPIDInput();
	
	protected abstract void usePIDOutput(double error);
}
