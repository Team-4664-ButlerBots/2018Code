package org.usfirst.frc.team4664.robot;


//TODO: rewrite all variables and remove unneeded ones.
public interface Constants{
	final int AUTONOMOUSCHOICE= 1;
	final int LSMOTOR	    = 0;
    final int RSMOTOR	    = 1;
    
    final int MOTOR1PORT = 5;//correct
    final int MOTOR2PORT =  3;//correct
    final int MOTOR3PORT =  4;//correct
    final int MOTOR4PORT =  2;//correct
    
//joystick 2 buttons
    final int HOPPERB = 11;
    final int COLLECTINB = 4;
    final int COLLECTOUTB = 5;
    final int COLLECTSTOPB = 3;
    final int SHOOTB = 1; // Uses method .getTrigger()
//speed variables
    final double ARMSPEEDVAL   = 0.25;
    final double WINCHOUT      = 1.0;
    final double WINCHIN       = -.7;
    final double LATTICEUP     = 0.8;
    final double LATTICEDOWN   = -.5;
//dead band variables
    final double DRIVEDB    = -0.2;
    final double CLIMBDB = 0.3;
//Laptop ports
    final int gamepadPort	= 1;
    final int joystickPort  = 0;
//Sensor Variables
    final int rangeFinder   = 0;
    final int gyroSense   = 1;
//Scale Factors
    final double maxSpeedDrive   = 1.0;//negative to flip direction

}
