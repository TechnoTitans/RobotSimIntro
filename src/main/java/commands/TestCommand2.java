package commands;

import simulation.Command;
import simulation.Robot;

public class TestCommand2 extends Command {

	public TestCommand2() {
		super();
		requires(Robot.driveTrain);
	}
	
	protected void execute() {
		Robot.driveTrain.set(-1, 1);
	}
	
	protected void end() {
		Robot.driveTrain.stop();
	}
	
	@Override
	protected boolean isFinished() {
		return Robot.gyro.getAngle() <= -90;
	}
}
