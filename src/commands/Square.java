package commands;

import simulation.CommandGroup;

public class Square extends CommandGroup {
	public Square(double sideLength, double speed) {
		for (int i = 0; i < 4; ++i) {
			addSequential(new Forward(sideLength, speed));
			addSequential(new Turn(90, speed));
		}
	}
}
