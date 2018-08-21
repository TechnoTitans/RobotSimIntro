package simulation;

import org.usfirst.frc.team1683.driveTrain.Motor;
import org.usfirst.frc.team1683.sensors.Encoder;

/**
 * 
 * Motor control (talonSRX)
 * 
 */
public class TalonSRX implements Motor {

	private Encoder encoder;
	
	private double speed = 0;
	private double targetSpeed = 0;
	private boolean reversed, disabled, brakeMode;
	private static double MOTOR_VARIATION = 0.1;
	private static double PHYSICAL_VARIATION = 0.1;
	private static final double RESPONSIVENESS = 0.05; // 0=unresponsive, 1=immediate
	private double bias;
	public static double MAX_TRUE_SPEED = 20; // inches per second
	public static final double WHEEL_RADIUS = 2.0235;
	
	/**
	 * Constructor for a TalonSRX motor
	 *
	 * @param channel
	 *            The port where the TalonSRX is plugged in.
	 * @param reversed
	 *            If the TalonSRX should invert the signal.
	 */
	public TalonSRX(int channel, boolean reversed) {
		this.reversed = reversed;
		bias = (Main.r.nextDouble() - 0.5) * MOTOR_VARIATION + 1;
	}

	@Override
	public void set(double speed) {
		if (speed > 1) targetSpeed = 1;
		else if (speed < -1) targetSpeed = -1;
		else targetSpeed = speed;
		disabled = false;
	}
	
	public void updateEncoders(double dist) {
		encoder.update(dist / (2 * Math.PI * WHEEL_RADIUS));
	}
	
	public void updateSpeed() {
		speed = targetSpeed * RESPONSIVENESS + (1 - RESPONSIVENESS) * speed;
	}
	
	// speed = enc counts / 100 ms
	// (speed * 60 secs)
	// --------------------------------------
	// 4096 encoder counts * 100 milliseconds

	@Override
	public double getSpeed() throws IllegalStateException {
		if (!hasEncoder()) {
			throw new IllegalStateException("Cannot get speed when no encoder is attached."
					+ " Use the setEncoder method to attach an encoder.");
		}
		return encoder.getSpeed();
	}

	/**
	 * Stops the TalonSRX using whichever brake mode was used last
	 * @deprecated You should explicitly write brake() or coast() depending on your expected behavior
	 */
	@Override
	public void stop() {
		disabled = true;
		targetSpeed = 0;
	}

	@Override
	public void brake() {
		enableBrakeMode(true);
		disabled = true;
		targetSpeed = 0;
	}

	@Override
	public void coast() {
		enableBrakeMode(false);
		disabled = true;
		targetSpeed *= 0.99;
	}
	
	@Override
	public boolean hasEncoder() {
		return !(encoder == null);
	}
	
	public double getVariationSpeed() {
		
		if (-0.05 < speed && speed < 0.05) {
			return 0;
		}
		return speed * bias * (1 + (Main.r.nextDouble() - 0.5) * PHYSICAL_VARIATION);
	}

	@Override
	public Encoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Encoder encoder) {
		this.encoder = encoder;
	}

	// TODO: make sure this works.
	@Override
	public int getChannel() {
		return 0;
	}

	/**
	 * @return If the signal is being inverted
	 */
	@Override
	public boolean isReversed() {
		return reversed;
	}

	@Override
	public double get() {
		return targetSpeed;
	}

	@Override
	public double getError() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPosition() {
		return encoder.get();
	}

	@Override
	public void setPosition(double position) {
		encoder.set(position);
	}

	@Override
	public void enableBrakeMode(boolean enabled) {
		brakeMode = enabled;
	}
	
	public boolean isBrakeModeEnabled() {
		return brakeMode;
	}
}
