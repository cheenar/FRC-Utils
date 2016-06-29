package me.cheenar.tests.frcutils;

import me.cheenar.frcutils.CGRobot;
import me.cheenar.frcutils.camera.CGCameraController;
import me.cheenar.frcutils.joys.CGJoystick;
import me.cheenar.frcutils.joys.CGJoystick.TophatPosition;
import me.cheenar.frcutils.joys.CGJoystickFunction;

public class TestJoystick
{
	
	CGCameraController controller;
	
	public void test()
	{
		CGJoystick joystickLeft 	= new CGJoystick(0, ""); //port that the joystick appears in the driver station
		CGJoystick joystickRight 	= new CGJoystick(1, "");
		
		joystickLeft.insertMapping("change_camera_center", 0); //easy to insert the mapping
		joystickRight.insertMapping("change_camera_right", 1); //removing the mapping is next to impossible actually. no its not, just add the function yourself if you need it (might add it myself)
		
		//when handling joystick functions
		//its easier to inject a function into so the CGRobot class can automatically handle a fucntion
		//example
		joystickLeft.injectFunction(new CGJoystickFunction() {

			@Override
			public boolean isActivated(CGRobot robot, CGJoystick joy) {
				return joy.isPressed("change_camrea_center"); //just return when you want to
				//joy is the joystick that is being check at that moment
			}

			@Override
			public void run(CGRobot robot, CGJoystick joy) {
				controller.setCamera("center");
			}
			
		});
		
		if(joystickLeft.isPressed("change_camera_center")) //check if pressed
			controller.setCamera("center"); //react to boolean interpretation
		
		if(joystickLeft.isTophatButtonDown(TophatPosition.NORTH)) //measure the tophat
			controller.setCamera("right");

		System.out.print(joystickRight.isLessThanDeadzoneTarget(0.5)); //deadzone detection using magnititude
		System.out.print(joystickRight.isWithinDeadzoneTarget(0.5, 0.7));

		System.out.print(joystickRight.getX()); //wrapper methods for all the helpful joystick functions (normally only useful for the 3axis joys)
		System.out.print(joystickRight.getY());
		System.out.print(joystickRight.getZ());
		System.out.print(joystickRight.getTwist());
		System.out.print(joystickRight.getThrottle());
		System.out.print(joystickRight.isTriggerActuated());
	}

}
