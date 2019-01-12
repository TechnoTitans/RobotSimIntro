package simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommandGroup extends Command {
	private static class CommandInGroup {
		Command command;
		boolean isSequential;
		public CommandInGroup(Command command, boolean isSequential) {
			this.command = command;
			this.isSequential = isSequential;
		}
	}
	
	private int index = 0;
	List<CommandInGroup> commands;
	
	public CommandGroup() {
		commands = new ArrayList<>();
		runningCommands = new HashSet<>();
	}
	
	public void addParallel(Command command) {
		commands.add(new CommandInGroup(command, false));
	}
	
	public void addSequential(Command command) {
		commands.add(new CommandInGroup(command, true));
	}
	
	private void startNext() {
		while (true) {
			if (index >= commands.size()) break;
			CommandInGroup c = commands.get(index);
			c.command.start();
			if (c.isSequential) break;
			index += 1;
		}
	}
	
	protected void initialize() {
		startNext();
	}
	
	protected void execute() {
		if (index >= commands.size()) return;
		if (commands.get(index).command.isDone()) {
			index += 1;
			startNext();
		}
	}
	
	protected boolean isFinished() {
		if (index < commands.size()) return false;
		for (CommandInGroup c : commands) {
			if (!c.command.isDone()) return false;
		}
		return true;
	}
	
	protected void interrupted() {
		for (CommandInGroup c : commands) {
			if (c.command.isRunning()) {
				c.command.cancel();
			}
		}
	}
}
