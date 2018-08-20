package simulation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Command {
	private double timeout = Double.POSITIVE_INFINITY;
	private double initTime;
	private boolean canceled = false;
	boolean ended = false;
	Set<Subsystem> requirements;
	private boolean initialized = false;
	private boolean interruptible = true;
	
	static Set<Command> runningCommands = new HashSet<>();
	
	public Command() {
		requirements = new HashSet<>();
	}
	
	public Command(double timeout) {
		this();
		this.timeout = timeout;
	}
	
	public void cancel() {
		canceled = true;
		interrupted();
	}
	
	protected void clearRequirements() {
		requirements.clear();
	}
	
	public boolean doesRequire(Subsystem system) {
		return requirements.contains(system);
	}
	
	protected void end() {
	}
	
	protected void execute() {
	}
	
	protected void initialize() {
	}
	
	protected void interrupted() {
		
	}
	
	public boolean isCanceled() {
		return canceled;
	}
	
	protected abstract boolean isFinished();
	
	private void run() {
		if (isDone()) return;
		if (!initialized) {
			initTime = Main.getFrames();
			initialize();
		}
		initialized = true;
		execute();
		if (isFinished() || isTimedOut()) {
			ended = true;
			end();
		}
	}
	
	boolean isDone() {
		return ended || canceled;
	}
	
	public boolean isInterruptible() {
		return interruptible;
	}
	
	public boolean isRunning() {
		return initialized && !canceled && !ended;
	}
	
	protected boolean isTimedOut() {
		return timeSinceInitialized() > timeout;
	}
	
	protected void requires(Subsystem system) {
		requirements.add(system);
	}
	
	protected void setInterruptible(boolean interruptible) {
		this.interruptible = interruptible;
	}
	
	protected void setTimeout(double seconds) {
		timeout = seconds;
	}
	
	public void start() {
		if (isRunning()) return;
		for (Command other : runningCommands) {
			for (Subsystem system : requirements) {
				if (other.doesRequire(system)) {
					if (other.isInterruptible()) {
						other.cancel();
						break;
					} else {
						return;
					}
				}
			}
		}
		runningCommands.add(this);
	}
	
	static void runAllCommands() {
		Iterator<Command> iter = runningCommands.iterator();
		while (iter.hasNext()) {
			Command c = iter.next();
			c.run();
			if (c.isDone()) iter.remove();
		}
	}
	
	public double timeSinceInitialized() {
		return (Main.getFrames() - initTime) / Main.FRAMES_PER_SECOND;
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
