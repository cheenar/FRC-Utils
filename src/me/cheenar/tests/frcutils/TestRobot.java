package me.cheenar.tests.frcutils;

import me.cheenar.frcutils.CGRobot;
import me.cheenar.frcutils.camera.CGCamera;
import me.cheenar.frcutils.camera.CGCameraController;
import me.cheenar.frcutils.joys.CGJoystick;
import me.cheenar.frcutils.joys.CGJoystickFunction;
import me.cheenar.frcutils.joys.CGJoystickName;
import me.cheenar.frcutils.motors.CGDriveTrain;
import me.cheenar.frcutils.motors.CGDriveTrainLayout;
import me.cheenar.frcutils.motors.CGDriveTrainSpeedConfiguration;
import me.cheenar.frcutils.motors.CGMotor;
import me.cheenar.frcutils.motors.CGMotorType;

public class TestRobot extends CGRobot
{

	public TestRobot() 
	{
		super(true);
		this.setCameraController(new CGCameraController());
		this.injectCamera(new CGCamera(0, "left"));
		this.injectCamera(new CGCamera(1, "right"));
		this.getCameraController().setCamera("left");
		
		CGJoystick leftStick = new CGJoystick(0, CGJoystickName.JOY_LEFT);
		CGJoystick rightStick = new CGJoystick(0, CGJoystickName.JOY_RIGHT);
		
		CGMotor motor = new CGMotor(CGMotorType.VICTOR, 4);
		
		CGDriveTrain driveTrain = new CGDriveTrain(CGDriveTrainLayout.LEFT_RIGHT_SIMPLE_FOUR_MOTOR) {
			@Override
			public double getDriveSpeed() {
				return -1; //this is going to be a dynamic control setup
			}
			@Override
			public CGDriveTrainSpeedConfiguration getSpeedConfiguration() {
				return CGDriveTrainSpeedConfiguration.DYNAMIC;
			}
		};
		driveTrain.injectMotor(CGDriveTrain.LEFT_FRONT, new CGMotor(CGMotorType.VICTOR, 0));
		driveTrain.injectMotor(CGDriveTrain.LEFT_BACK, new CGMotor(CGMotorType.VICTOR, 1));
		driveTrain.injectMotor(CGDriveTrain.RIGHT_FRONT, new CGMotor(CGMotorType.VICTOR, 2));
		driveTrain.injectMotor(CGDriveTrain.RIGHT_BACK, new CGMotor(CGMotorType.VICTOR, 3));
		
		leftStick.insertMapping("change_camera_left", 0);
		leftStick.insertMapping("motor_foward", 1);
		leftStick.insertMapping("motor_backward", 2);
		
		leftStick.injectFunction(new CGJoystickFunction() {
			@Override
			public boolean isActivated(CGRobot robot, CGJoystick joy) {
				return joy.isPressed("change_camera_left");
			}

			@Override
			public void run(CGRobot robot, CGJoystick joy) {
				robot.getCameraController().setCamera("left");
			}
		});
		
		leftStick.injectFunction(new CGJoystickFunction() {
			@Override
			public boolean isActivated(CGRobot robot, CGJoystick joy) {
				return true; //always on but we have some checks
			}

			@Override
			public void run(CGRobot robot, CGJoystick joy) {
				if(joy.isPressed("motor_foward"))
				{
					motor.setSpeed(1.0);
				}
				else if(joy.isPressed("motor_backward"))
				{
					motor.setSpeed(-1.0);
				}
				else
				{
					motor.setSpeed(0);
				}
			}
		});
		
		this.injectJoystick(leftStick);
		this.injectJoystick(rightStick);
		this.injectDrivetrain(driveTrain);
	}

	@Override
	public void onRobotInit() 
	{
		
	}

	@Override
	public void onoTeleopInit() 
	{
		
	}

	@Override
	public void onTeleopPeriodic()
	{
		
	}
	
}
