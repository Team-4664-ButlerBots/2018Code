package org.usfirst.frc.team4664.robot;

public interface Constants{
//Motor Ports
	final int LSMOTOR	= 4; //Port for left side motor controller
    final int RSMOTOR	= 5; //Port for right side motor controller
    final int CAGEPORT 	= 0;
    final int ARMPORT 	= 2;
    final int CLAWPORT 	= 1; 
    final int CLIMBPORT = 3;
//Laptop Ports
    final int gamepadPort	= 1;
    final int joystickPort  = 0;
    
//Sensor Ports
    final int ARMCLOSESWITCHPORT	= 0;
    final int ARMOPENEDSWITCHPORT 	= 1;
    final int LIFTUPSWITCHPORT 		= 2;
    final int LIFTDOWNSWITCHPORT 	= 3;
    
//Speed Variables: Button Press Speeds
    final double ARMSPEED_UP   = 0.5;
    final double ARMSPEED_DOWN = -0.5;
    final double CLAWSPEED     = 0.5;
    
//Scale Factors: Joystick 
    final double maxSpeedDrive   = 1.0;//negative to flip direction
    
//Deadband Variables: For Joystick
    final double JOYDB 		= 0.05;
    final double DRIVEDB    = 0.2;
    final double TURNDB		= 0.2;
    final double CLAWDB 	= 0.3;
    
    
//Tunings, Gyro/Drive
    final double KP = 0.15;
}
