package org.usfirst.frc.team4664.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class ToggleGyro {
	private boolean toggled;
	private double startingAngle;
	private ADXRS450_Gyro gyro;
	
	public ToggleGyro (ADXRS450_Gyro gyro) {
		toggled = false;
		startingAngle = gyro.getAngle();
		this.gyro = gyro;
	}
	
	public double getAngle() {
		return startingAngle;
	}

	public boolean toggle(boolean rawButton) {
		// Passes through boolean value, first time get angle
		// while toggled keep starting angle
		if (!toggled  && rawButton) { // if first time 
			toggled = !toggled;
			startingAngle = gyro.getAngle();
		}
		else if (!rawButton) {
			toggled = false; // Toggled = starting state when not pressed
		}
		return rawButton;
	}
}