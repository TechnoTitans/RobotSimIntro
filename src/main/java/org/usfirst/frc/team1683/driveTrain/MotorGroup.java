package org.usfirst.frc.team1683.driveTrain;

import java.util.ArrayList;

import org.usfirst.frc.team1683.sensors.Encoder;

/*
 * 
 * Creates a group of motors (for left and right side)
 * 
 */
public class MotorGroup extends ArrayList<Motor> {

	private static final long serialVersionUID = 1L;
	private Encoder encoder;

	private AntiDrift antiDrift;

	/**
	 * Basically a list of motors.
	 *
	 * @param encoder
	 *            The encoder attached to this MotorGroup; will automatically
	 *            attach this encoder to any TalonSRX motor in the motor group
	 * @param motors
	 *            The motors.
	 */
	public MotorGroup(Encoder encoder, Motor... motors) {
		this.encoder = encoder;
		for (Motor motor : motors) {
			motor.setEncoder(encoder);
			super.add(motor);
		}
	}

	/**
	 * Constructor -- just a list of motors without an encoder
	 *
	 * @param motors
	 *            The motors.
	 */
	public MotorGroup(Motor... motors) {
		for (Motor motor : motors) {
			super.add(motor);
		}
	}

	/**
	 * Set collective speed of motors.
	 *
	 * @param speed
	 *            Speed from 0 to 1.
	 */
	public void set(double speed) {
		for (Motor motor : this) {
			motor.set(speed);
		}
	}

	/**
	 * Gets collective (average) speed of motors from -1 to 1
	 */
	public double getSpeed() {
		double speed = 0;
		for (Motor motor : this) {
			speed += motor.get();
		}
		return speed / this.size();
	}

	/**
	 * Stops group.
	 */
	public void stop() {
		for (Motor motor : this) {
			motor.stop();
		}
	}

	/**
	 * @return If there is an encoder associated with the group.
	 */
	public boolean hasEncoder() {
		return !(encoder == null);
	}

	/**
	 * 
	 * @return The average error of all the motors
	 */
	public double getError() {
		double error = 0;
		for (Motor motor : this) {
			error += motor.getError();
		}
		error /= this.size();
		return error;
	}

	/**
	 * @return The encoder associated with the group.
	 */
	public Encoder getEncoder() {
		return encoder;
	}

	public void enableBrakeMode(boolean enabled) {
		for (Motor motor : this) {
			motor.enableBrakeMode(enabled);
		}
	}

	public void enableAntiDrift(AntiDrift antiDrift) {
		this.antiDrift = antiDrift;
	}

	public void disableAntiDrift() {
		this.antiDrift = null;
	}

	public boolean isAntiDriftEnabled() {
		return !(antiDrift == null);
	}

	public AntiDrift getAntiDrift() {
		return antiDrift;
	}
}
