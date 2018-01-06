package org.usfirst.frc.team4664.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Robot extends SampleRobot implements Constants
{
	RobotDrive driveSystem;
	Victor collectMotor;
	Victor shootMotor;
	Victor hopperMotor;
	Victor climbMotor;
	Joystick stick;
	Joystick gamepad;
	AnalogGyro gyro;
	
	public Robot() {
		//Motors
		driveSystem = new RobotDrive(LSMOTOR, RSMOTOR);
		driveSystem.setExpiration(0.1);
		hopperMotor=new Victor(HOPPERPORT);
		collectMotor =new Victor(COLLECTMPORT);
		shootMotor=new Victor(SHOOTMPORT);
		climbMotor=new Victor(CLIMBMPORT);
		//Input
		stick = new Joystick(gamepadPort);
		gamepad = new Joystick(joystickPort);
		
		//Sensors
		gyro = new AnalogGyro(gyroSense);
		gyro.calibrate();
		
		//Camera
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	@Override
	public void robotInit() {
	}

	@Override
	public void autonomous() {
		
	}
	
	@Override
	public void operatorControl() {
		driveSystem.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) 
		{	
			//the deadband function receives the inputs gamepad axis and deadband constant
			//it takes these and makes sure no input is given when under the deadband constant.
			driveSystem.tankDrive(deadBand(gamepad.getRawAxis(3),DRIVEDB)*maxSpeedDrive,deadBand(gamepad.getY(),DRIVEDB)*maxSpeedDrive );
		}
    }
	
	public void Dashboard(){
		//Gyro display
		SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
		//Deadband Display
		SmartDashboard.putNumber("deadBand Left : ", deadBand(gamepad.getY(),DRIVEDB));
		SmartDashboard.putNumber("raw value Left ", gamepad.getY());
		SmartDashboard.putNumber("deadBand Right : ", deadBand(gamepad.getRawAxis(3),DRIVEDB));
		SmartDashboard.putNumber("raw value Right ", gamepad.getRawAxis(3));
		
		SmartDashboard.putNumber("climb power", deadBand(Limit(-stick.getY(),0.0,1.0),CLIMBDB));
		
	}
	
	double deadBand(double AxisInput,double deadband){
		AxisInput=Limit(AxisInput,-1.0,1.0); 
		if(Math.abs(AxisInput)<=deadband) 
			return 0.0;
		if(AxisInput>deadband)
			return (AxisInput - deadband) / (1.0 - deadband);
		// else
			return (AxisInput + deadband) / (1.0 - deadband);
	}
	
	//Limits a varible to to a given range
	double Limit(double value,double min,double max) { 
		if(value > max)  return max;
		if(value < min)  return min;
						 return value;
	}
}
