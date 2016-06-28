package me.cheenar.frcutils.joys;

import me.cheenar.frcutils.CGRobot;

public abstract class CGJoystickFunction
{

	public abstract boolean isActivated(CGRobot robot, CGJoystick joy);
	public abstract void run(CGRobot robot, CGJoystick joy);
	
}
