package org.usfirst.frc.team1683.sensors;

import java.util.ArrayDeque;
import java.util.Deque;

import simulation.Main;
import simulation.TalonSRX;

public class Encoder {

	private double encoderTicks = 0;
	private double totalDistanceSpeedSample;
	private Deque<Double> distances = new ArrayDeque<>(10);
	public static final double TICKS_PER_REVOLUTION = 4096;
	
	public void update(double rev) {
		System.out.println(rev);
		double ticks = rev * TICKS_PER_REVOLUTION;
		totalDistanceSpeedSample += ticks;
		distances.addLast(ticks);
		if (distances.size() >= 10) {
			totalDistanceSpeedSample -= distances.removeFirst();
		}
		encoderTicks += ticks;
	}

	public void reset() {
		encoderTicks = 0;
	}
	
	public void set(double newTicks) {
		encoderTicks = newTicks;
	}
	
	public double get() {
		return encoderTicks;
	}

	public double getDistance() {
		return encoderTicks;
	}

	public double getSpeed() {
		return totalDistanceSpeedSample / distances.size();
	}

}
