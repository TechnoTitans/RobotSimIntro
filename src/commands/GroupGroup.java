package commands;
import simulation.CommandGroup;

public class GroupGroup extends CommandGroup {
	public GroupGroup() {
		addSequential(new Square(20000, 0.7));
		addSequential(new Square(4000, 1));
	}
}
