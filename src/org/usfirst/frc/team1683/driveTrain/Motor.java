package org.usfirst.frc.team1683.driveTrain;

import org.usfirst.frc.team1683.sensors.Encoder;

public interface Motor {

	/**
	 * Set the speed of the motor.
	 *
	 * @param speed Speed from -1 to 1.
	 */
	public void set(double speed);
	
	/**
	 * Gets the speed of the motor
	 * @return The speed from -1 to 1
	 */
	public double get();
	
	/**
	 * Gets speed of the TalonSRX in RPM
	 * Note: if you want to get the speed as a value from -1 to 1, use the get() method.
	 * @return The speed in rotations per minute
	 * @throws IllegalStateException If no encoder is attached
	 */
	public double getSpeed() throws IllegalStateException;
	

	public void stop();

	/**
	 * Stops the motor and halts any further movement
	 */
	public void brake();

	/**
	 * Stops the motor from supplying more force, but the motor will continue to move until
	 * it stops naturally due to friction
	 */
	public void coast();
	
	/**
	 * @return True if an encoder is attached
	 */
	public boolean hasEncoder();

	/**
	 * @return The encoder attached to this motor, or null if no encoder is attached
	 */
	public Encoder getEncoder();

	/**
	 * @return Sets an encoder for this motor
	 */
	public void setEncoder(Encoder encoder);

	/**
	 * @return The channel that this motor was set on
	 */
	public int getChannel();

	public boolean isReversed();
	
	/**
	 * Returns the difference between the set point (between -1 and 1)
	 * and its current position (between -1 and 1). The result will be between -2 and 2, but
	 * usually close to 0.
	 * @return The difference between the set point and the current position
	 */
	public double getError();
	
	/**
	 * @return The number of rotations the wheel has moved
	 */
	public double getPosition();

	/**
	 * Set the number of rotations that have happened
	 */
	public void setPosition(double position);

	public void enableBrakeMode(boolean enabled);
}
