package me.cheenar.tests.frcutils;

import me.cheenar.frcutils.CGRobot;
import me.cheenar.frcutils.camera.CGCamera;
import me.cheenar.frcutils.camera.CGCameraController;
import me.cheenar.frcutils.joys.CGJoystick;
import me.cheenar.frcutils.joys.CGJoystickFunction;

public class TestRobot extends CGRobot
{

	public TestRobot() 
	{
		super(true);
		this.setCameraController(new CGCameraController());
		this.injectCamera(new CGCamera(0, "left"));
		this.injectCamera(new CGCamera(1, "right"));
		this.getCameraController().setCamera("left");
		
		CGJoystick leftStick = new CGJoystick(0);
		leftStick.insertMapping("change_camera_left", 0);
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
		this.injectJoystick(leftStick);
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
