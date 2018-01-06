package org.usfirst.frc.team4664.robot;

public interface Constants{
	final int AUTONOMOUSCHOICE= 1;
	final int LSMOTOR	    = 0;
    final int RSMOTOR	    = 1;
    
    final int COLLECTMPORT= 5;//correct
    final int SHOOTMPORT =  3;//correct
    final int HOPPERPORT =  4;//correct
    final int CLIMBMPORT =  2;//correct
    
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
