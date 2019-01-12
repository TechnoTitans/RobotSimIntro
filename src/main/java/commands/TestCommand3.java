package commands;

import simulation.CommandGroup;

public class TestCommand3 extends CommandGroup {

	public TestCommand3() {
		
		super();
		super.addParallel(new TestCommand());
		super.addParallel(new TestCommand2());
	}

}
