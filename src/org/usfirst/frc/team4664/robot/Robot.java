package org.usfirst.frc.team4664.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot implements Constants {
	// Motor Controller
	private Spark cage = new Spark(CAGEPORT);
	private Spark arm = new Spark(ARMPORT);
	private Spark claw = new Spark(CLAWPORT);

	// Robot Drive Train
	private Victor leftSideMotor = new Victor(LSMOTOR);
	private Victor rightSideMotor = new Victor(RSMOTOR);

	private SpeedControllerGroup leftSideGroup = new SpeedControllerGroup(leftSideMotor);
	private SpeedControllerGroup rightSideGroup = new SpeedControllerGroup(rightSideMotor);

	private DifferentialDrive driveTrain = new DifferentialDrive(leftSideGroup, rightSideGroup);

	// Controllers
	private Joystick gamepad = new Joystick(0);
	private Joystick joystick = new Joystick(1);

	// Sensors
	private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private ToggleGyro toggle = new ToggleGyro(gyro); // Use for DriveStraight

	// runs on startup
	@Override
	public void robotInit() {

		// camera Stuff
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);

		Log();

		gyro.calibrate();
	}

	// chooses following code with the chooser object, you can declare additional
	// functions by editing chooser.
	@Override
	public void autonomousInit() {
	}

	// during auto
	@Override
	public void autonomousPeriodic() {
		// TODO: do autonomous
	}

	// during tele-operated
	@Override
	public void teleopPeriodic() {
		//drive code
		if (toggle.toggle(gamepad.getRawButton(5))) // If gamepad left bumper, drive straight; Need toggle for Drive straight, passes boolean
			DriveStraight(-1);
		else // Normal Drive With Gamepad
			DriveWithController();
		
		
		//cage
		cage.set(joystick.getY());
		//cage.set(deadband(jsDeadband(joystick.getY()), CLAWDB));
		
		//arm
		if(joystick.getRawButton(11)) // pressing upper left button on base (6) pulls arm in. if left hand change to 11.
			arm.set(ARMSPEED_UP);
		else if(joystick.getRawButton(10)) // pressing lower left button on base (7) pushes arm out. if left hand change to 10.
			arm.set(ARMSPEED_DOWN);
		else							  // stops arm
			arm.set(0);
		
		//claw

		if(joystick.getRawButton(1)) {
			claw.set(CLAWSPEED);
		}else if(joystick.getRawButton(7)) {
			claw.set(-CLAWSPEED);
		}else {
			claw.set(0);
		}
		//pushes all the new values to smart dashboard
		UpdateLog();
	}

	// during test
	@Override
	public void testPeriodic() {
		// toggleMotorPolarity(armLiftMotor, ArmClosedSwitch, MotorSpeed);
	}

	public double jsDeadband(double js) {
		js = Limit(js, -1.0, 1.0);
		if (Math.abs(js) <= JOYDB)
			return 0.0;
		if (js > JOYDB)
			return (js - JOYDB) / (1.0 - JOYDB);
		else
			return (js + JOYDB) / (1.0 - JOYDB);
	}

	public double deadband(double input, double motorDeadband) {
		input = Limit(input, -1.0, 1.0);
		if (input == 0.0)
			return 0.0;
		else if (input > 0)
			return (1 - motorDeadband) * input + motorDeadband;
		else
			return (1 - motorDeadband) * input - motorDeadband;
	}

	// Limits a variable to to a given range
	public double Limit(double value, double min, double max) {
		if (value > max)
			return max;
		if (value < min)
			return min;
		return value;
	}

	// DRIVE TRAIN STUFF
	public void DriveWithController() {
		driveTrain.tankDrive(deadband(jsDeadband(gamepad.getRawAxis(3)), DRIVEDB) * maxSpeedDrive,
				deadband(jsDeadband(gamepad.getY()), DRIVEDB) * maxSpeedDrive);
	}

	public void DriveStraightSigmoid(double speed) {
		double error = gyro.getAngle() - toggle.getAngle(); // set error to differance between robot angle and starting
															// angle
		double GyroDriveAngle = -error * KP;
		SmartDashboard.putNumber("GyroDriveAngle before Function", GyroDriveAngle);
		GyroDriveAngle = Sigmoid(2 ,GyroDriveAngle);
		SmartDashboard.putNumber("GyrDriveAngle after function", GyroDriveAngle);
		driveTrain.arcadeDrive(speed, GyroDriveAngle);
	}

	public void DriveStraight(double speed) {
		double error = gyro.getAngle() - toggle.getAngle();

		// driveTrain.arcadeDrive(speed, -error*KP);
		double GyroDriveAngle = -error * KP;
		SmartDashboard.putNumber("GyroDriveAngle before Function", GyroDriveAngle);
		GyroDriveAngle = Limit(GyroDriveAngle, -0.5, 0.5);
		SmartDashboard.putNumber("GyrDriveAngle after function", GyroDriveAngle);
		driveTrain.arcadeDrive(speed, GyroDriveAngle);
	}

	//// INTERNAL STUFF BELOW
	//sigmoid function
	public double Sigmoid(double k, double angle) {
		return (1/Math.pow(2, -k * angle))-1;
	}
	
	
	// inverts digital input boolean
	public boolean returnBool(DigitalInput input) {
		return !input.get();
	}

	// smart dashboard outputs
	public void OutputBoolean(DigitalInput input, String name) {
		SmartDashboard.putBoolean(name, returnBool(input));
	}

	public void OutputNumber(int input, String name) {
		SmartDashboard.putNumber(name, input);
	}

	public void Log() {
		SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
		SmartDashboard.putNumber("Left Motor", leftSideMotor.get());
		SmartDashboard.putNumber("Right Motor", rightSideMotor.get());
		SmartDashboard.putNumber("Cage Motor", cage.get());
		SmartDashboard.putNumber("Arm Motor", arm.get());
		SmartDashboard.putNumber("Claw Motor", claw.get());
		SmartDashboard.updateValues();
	}

	public void UpdateLog() {
		SmartDashboard.updateValues();
	}

}