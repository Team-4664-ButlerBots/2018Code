package org.usfirst.frc.team4664.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot implements Constants {
	private static final String BENCHTOPTEST = "This test case is used for benchtop testing. WARNING: must be elevated, will spin wheels.";
	private static final String DRIVETEST = "This test case is meant to drive the robot. NOTE: can be set on ground for this one.";
	private String autonomousChosen;
	private SendableChooser<String> autoMenu = new SendableChooser<>();
	
//Sensors
//LimitSwitches
	private DigitalInput ArmClosedSwitch;
	private DigitalInput ArmOpenedSwitch;

	
//Motor Controller setup
	
	private Spark liftMotor = new Spark(MOTOR1PORT);
	private Spark armLiftMotor = new Spark(MOTOR2PORT);
	private Victor wew1 = new Victor(MOTOR3PORT);
	private Victor wew2 = new Victor(MOTOR4PORT);
	
//robot drive train setup stuff
	private Spark leftSideMotor = new Spark(LSMOTOR);
	private Spark rightSideMotor = new Spark(RSMOTOR);

	private SpeedControllerGroup leftSideGroup = new SpeedControllerGroup(leftSideMotor);
	private SpeedControllerGroup rightSideGroup = new SpeedControllerGroup(rightSideMotor);
	
	private DifferentialDrive driveTrain = new DifferentialDrive(leftSideGroup, rightSideGroup);
	
//controller
	private Joystick gamepad = new Joystick(0);

	//runs on startup
	@Override
	public void robotInit() {
		//Smart Dashboard stuff
		autoMenu.addDefault("benchtopTestCase", BENCHTOPTEST);
		autoMenu.addObject("standardDriveCase", DRIVETEST);
		SmartDashboard.putData("Autonomous Choices", autoMenu);
		
		//camera Stuff
		CameraServer.getInstance().startAutomaticCapture(1);
		
		//Sensor stuff
		ArmClosedSwitch = new DigitalInput(ARMCLOSESWITCHPORT);
		ArmOpenedSwitch = new DigitalInput(ARMOPENEDSWITCHPORT);

	}

	//chooses following code with the chooser object, you can declare additional functions by editing chooser.
	@Override
	public void autonomousInit() {
		autonomousChosen = autoMenu.getSelected();
		System.out.println("Autonomous selected: " + autonomousChosen);
	}

	//during auto
	@Override
	public void autonomousPeriodic() {
		//FIXME: error "output not updated enough."
		switch (autonomousChosen) {
			case DRIVETEST:
				//
				driveTrain.tankDrive(.3,.3);
				break;
			case BENCHTOPTEST:
			default:
				//TODO: actually write a test case
				break;
		}
	}

	//during tele-operated
	@Override
	public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()) 
		{	
			//the dead band function receives the inputs game pad axis and dead band constant
			//it takes these and makes sure no input is given when under the dead band constant.
			driveTrain.tankDrive(deadBand(gamepad.getRawAxis(3),DRIVEDB)*maxSpeedDrive,deadBand(gamepad.getY(),DRIVEDB)*maxSpeedDrive );
		}
	}

	//during test
	@Override
	public void testPeriodic() {
	}
	
	
	public double deadBand(double AxisInput,double deadband){
		AxisInput=Limit(AxisInput,-1.0,1.0); 
		if(Math.abs(AxisInput)<=deadband) 
			return 0.0;
		if(AxisInput>deadband)
			return (AxisInput - deadband) / (1.0 - deadband);
		else
			return (AxisInput + deadband) / (1.0 - deadband);
	}
	
	//Limits a variable to to a given range
	public double Limit(double value,double min,double max) { 
		if(value > max)  return max;
		if(value < min)  return min;
						 return value;
	}
	
	//takes a specific motor controller (either victor or spark) and 
	//then a limit switch or other digital device, and runs the motor for speed.
	public void runMotor(Spark motor, DigitalInput limitSwitch, double speed){
		if(DigitalInpit.get()){
			motor.setSpeed(speed);
		}
	}

	public void runMotor(Victor motor, DigitalInput limitSwitch, double speed){
		if(!DigitalInput.get()){
			motor.setSpeed(speed);
		}
	}
}
