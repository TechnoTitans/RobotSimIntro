package commands;


import simulation.Command;
import simulation.Main;
import simulation.Robot;

public class TestCommand extends Command {

	public TestCommand() {
		super();
		requires(Robot.driveTrain);
		setTimeout(5);
	}
	
	protected void initialize() {
		System.out.println("Initializing");
	}
	
	protected void execute() {
		Robot.driveTrain.set(0.3, 0.3);
		Main.debug.put("Time", Main.getTime());
	}
	
	protected void end() {
		Robot.driveTrain.stop();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
