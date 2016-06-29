package me.cheenar.frcutils.motors;

import me.cheenar.frcutils.CGRobot;

public enum CGDriveTrainSpeedConfiguration {

	CONSTANT(CGRobot.CONST_DRIVETRAIN_SPEED), //always one speed
	DYNAMIC; //changes via the joystick or other input

	double speed;
	
	CGDriveTrainSpeedConfiguration() { }
	
	CGDriveTrainSpeedConfiguration(double speed) 
	{ 
		this.speed = speed;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
}
