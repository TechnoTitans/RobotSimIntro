package simulation;

import org.usfirst.frc.team1683.sensors.VisionStripSensor;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
	private static SimIterativeRobot robot;
	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 1000;
	public static final double PIXELS_PER_INCH =  5;
	public static final double FRAMES_PER_SECOND = 30;
	public static Random r = new Random();
	private static int frames = 0;
	public static Map<Object, Object> debug = new HashMap<>();

	public static final double targetWidth = 1.75; // in
	public static final double targetHeight = 18;
	public static final double topY = FRAME_HEIGHT * 0.2;

	private static VisionStrips strips;

	public static void startSim() {
		JFrame frame = new JFrame("simulation");
		JComponent drawer = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				for (double x = 0; x < FRAME_WIDTH; x += PIXELS_PER_INCH * 12) {
					g.drawLine((int) x, 0, (int) x, FRAME_HEIGHT);
				}
				for (double y = 0; y < FRAME_HEIGHT; y += PIXELS_PER_INCH * 12) {
					g.drawLine(0, (int) y, FRAME_WIDTH, (int) y);
				}
				int xDebug = 20;
				for (Object d : debug.keySet()) {
					g.drawString(d.toString() + ": " + debug.get(d).toString(), 20, xDebug);
					xDebug += 20;
				}
				robot.paintComponent(g);

				Graphics2D gg = (Graphics2D) g;
				gg.setColor(Color.WHITE);
				gg.fillRect((int) (FRAME_WIDTH / 2 - targetWidth / 2 * PIXELS_PER_INCH), (int) topY,
						(int) (targetWidth * PIXELS_PER_INCH), (int) (targetHeight * PIXELS_PER_INCH));
			}
		};
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        robot = new Robot();
        strips = new VisionStrips();
        SimIterativeRobot.visionSensor = new VisionStripSensor(strips.getX(), strips.getY());
        robot.initVision();
        //Display the window.
        frame.getContentPane().add(drawer);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
        java.util.Timer t2 = new java.util.Timer();
        t2.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				robot.update();
				frames++;
				frame.repaint();
			}
		}, 0, 10);
        //t.start();
	}
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() 
	    {
	        public void run() 
	        {
	        	startSim();
	        }
	    });
	}
	
	public static int getFrames() {
		return frames;
	}

	public static double getTime() {
		return Main.getFrames() / Main.FRAMES_PER_SECOND;
	}

}
