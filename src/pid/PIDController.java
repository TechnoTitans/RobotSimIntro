package pid;

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
	
	public double get() {
		return error;
	}

	/**
	 * @return the kP
	 */
	public double getP() {
		return kP;
	}

	/**
	 * @param kP the kP to set
	 */
	public void setP(double kP) {
		this.kP = kP;
	}

	/**
	 * @return the kI
	 */
	public double getI() {
		return kI;
	}

	/**
	 * @param kI the kI to set
	 */
	public void setI(double kI) {
		this.kI = kI;
	}

	/**
	 * @return the kD
	 */
	public double getD() {
		return kD;
	}

	/**
	 * @param kD the kD to set
	 */
	public void setD(double kD) {
		this.kD = kD;
	}
	
	
}
