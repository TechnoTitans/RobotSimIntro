package pid;

import java.util.ArrayDeque;
import java.util.Deque;

import simulation.Main;

public class PIDController {
	private double kP, kI, kD;
	private PIDSource source;
	private PIDOutput output;
	
	private double error = 0;
	private double integralError = 0;
	private double previousError = Double.POSITIVE_INFINITY;
	
	public PIDController(double kP, double kI, double kD, PIDSource source, PIDOutput output) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.source = source;
		this.output = output;
	}
	
	protected void calculate() {
		double curError = source.pidGet();
		if (previousError == Double.POSITIVE_INFINITY) previousError = curError;
		integralError += curError;
		error = kP * curError + kI * integralError + kD * (curError - previousError);
		previousError = curError;
		output.pidWrite(error);
	}
	
	public double getError() {
		return previousError == Double.POSITIVE_INFINITY ? 0 : previousError;
	}
	
	
}
