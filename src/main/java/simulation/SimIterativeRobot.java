package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.usfirst.frc.team1683.driveTrain.AntiDrift;
import org.usfirst.frc.team1683.driveTrain.MotorGroup;
import org.usfirst.frc.team1683.sensors.Gyro;

import math.Vector2D;

import org.usfirst.frc.team1683.sensors.Encoder;
import org.usfirst.frc.team1683.sensors.VisionLine;
import org.usfirst.frc.team1683.sensors.VisionStripSensor;

public class SimIterativeRobot {
	
	
	@SuppressWarnings("serial")
	private static class RobotSide extends MotorGroup {
		Vector2D talonPos;
		TalonSRX talon;
		Vector2D lastChange;
		int s;
		RobotSide(int s, Gyro gyro) {
			this.s = s; // -1 is left, 1 is right
			talonPos = new Vector2D(Main.FRAME_WIDTH / 2 + X_SIZE / 2 * s,
					Main.FRAME_HEIGHT - 120);
			talon = new TalonSRX((s + 1) / 2, false);
			AntiDrift anti = new AntiDrift(gyro, s);
			super.add(talon);
			super.enableAntiDrift(anti);
		}
		@Override
		public Encoder getEncoder() {
			return talon.getEncoder();
		}
		void update(Vector2D perpVec) {
			talon.updateSpeed();
			lastChange = perpVec.copy();
			lastChange.multiply((talon.isReversed() ? 1 : -1) * talon.getVariationSpeed() * TalonSRX.MAX_TRUE_SPEED / Main.FRAMES_PER_SECOND * Main.PIXELS_PER_INCH);
			talonPos.add(lastChange);
		}
		void shrink(Vector2D normalizedDiffVec, double magDiff) {
			Vector2D change = normalizedDiffVec.copy();
			change.multiply(-magDiff * s);
			lastChange.add(change);
			talonPos.add(change);
			talon.updateEncoders(lastChange.magnitude() / Main.PIXELS_PER_INCH * Math.signum(-lastChange.y * normalizedDiffVec.x + lastChange.x * normalizedDiffVec.y));
		}
	}
	private RobotSide left, right;
	public static Gyro gyro;
	public static VisionStripSensor visionSensor;
	public static VisionLine visionLine;
	private static final int X_SIZE = 180, Y_SIZE = 200;
	private boolean initialized = false;
	
	public SimIterativeRobot() {
		Encoder leftEncoder = new Encoder(), rightEncoder = new Encoder();
		gyro = new Gyro();
		left = new RobotSide(-1, gyro);
		right = new RobotSide(1, gyro);
		left.talon.setEncoder(leftEncoder);
		right.talon.setEncoder(rightEncoder);
		robotInit();
	}
	
	public void robotInit() {
		System.out.println("Override me!");
	}
	
	public MotorGroup getLeftSimGroup() {
		return left;
	}
	
	public MotorGroup getRightSimGroup() {
		return right;
	}
	
	Vector2D getDiffVec() {
		Vector2D diffVec = right.talonPos.copy();
		diffVec.subtract(left.talonPos);
		return diffVec;
	}

	public void initVision() {
		visionSensor.update(left.talonPos.x / 2 + right.talonPos.x / 2, (left.talonPos.y + right.talonPos.y) / 2);
		visionSensor.updatePerpVec(new Vector2D(0, -1));
		visionLine = new VisionLine();
		visionLine.update(getMidFront(new Vector2D(0, -1)));
	}

	Vector2D getMidFront(Vector2D perpVec) {
		Vector2D mid = left.talonPos.copy();
		mid.add(right.talonPos);
		mid.multiply(0.5);
		Vector2D midFront = perpVec.copy();
		midFront.multiply(-Y_SIZE / 2);
		midFront.add(mid);
		return midFront;
	}
	
	public void autonomousInit() {
		System.out.println("Override me! -- auto init");
	}
	
	public void autonomousPeriodic() {
		System.out.println("Override me! -- auto periodic");
	}
	
	public void update() {
		if (!initialized) autonomousInit();
		initialized = true;
		autonomousPeriodic();
		Vector2D diffVec = getDiffVec();
		Vector2D oldDiffVec = diffVec;
		Vector2D perpVec = diffVec.getPerpendicularUnitVector();
		left.update(perpVec);
		right.update(perpVec);
		diffVec = getDiffVec();
		double magDiff = (diffVec.magnitude() - X_SIZE) / 2;
		diffVec.normalize();
		left.shrink(diffVec, magDiff);
		right.shrink(diffVec, magDiff);
		diffVec = getDiffVec();
		gyro.changeAngle(diffVec.getAngle(), oldDiffVec.getAngle());
		visionSensor.update(getMidFront(perpVec));
		visionSensor.updatePerpVec(perpVec);
		visionLine.update(getMidFront(perpVec));
	}
	protected void paintComponent(Graphics g) {
		int x = (int) (left.talonPos.x + right.talonPos.x) / 2,
			y = (int) (left.talonPos.y + right.talonPos.y) / 2;
		Graphics2D gg = (Graphics2D) g;
		AffineTransform oldXForm = gg.getTransform();
		Vector2D diffVec = getDiffVec();
		gg.rotate(diffVec.getAngle(), x, y);
		gg.setColor(Color.BLACK);
	    gg.fillRect(x - X_SIZE / 2, y - Y_SIZE / 2, X_SIZE, Y_SIZE);
	    gg.setColor(Color.WHITE);
	    line(gg, x - X_SIZE / 2.5, y - Y_SIZE / 2.5, x + X_SIZE / 2.5, y - Y_SIZE / 2.5);
	    gg.setTransform(oldXForm);
	    gg.fillOval((int) left.talonPos.x - 3, (int) left.talonPos.y - 3, 6, 6);
	    gg.fillOval((int) right.talonPos.x - 3, (int) right.talonPos.y - 3, 6, 6);
	}
	
	private void line(Graphics g, double x1, double y1, double x2, double y2) {
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}
}
