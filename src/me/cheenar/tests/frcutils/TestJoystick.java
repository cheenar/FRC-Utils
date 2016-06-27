package me.cheenar.tests.frcutils;

import me.cheenar.frcutils.camera.CGCameraController;
import me.cheenar.frcutils.joys.CGJoystick;
import me.cheenar.frcutils.joys.CGJoystick.TophatPosition;

public class TestJoystick
{
	
	CGCameraController controller;
	
	public void test()
	{
		CGJoystick joystickLeft 	= new CGJoystick(0); //port that the joystick appears in the driver station
		CGJoystick joystickRight 	= new CGJoystick(1);
		
		joystickLeft.insertMapping("change_camera_center", 0); //easy to insert the mapping
		joystickRight.insertMapping("change_camera_right", 1); //removing the mapping is next to impossible actually. no its not, just add the function yourself if you need it (might add it myself)
		
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
