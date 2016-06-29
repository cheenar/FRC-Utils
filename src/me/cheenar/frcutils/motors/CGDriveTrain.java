package me.cheenar.frcutils.motors;

import java.util.HashMap;

import me.cheenar.frcutils.CGRobot;
import me.cheenar.frcutils.joys.CGJoystick;
import me.cheenar.frcutils.joys.CGJoystickName;

public abstract class CGDriveTrain 
{
	
	public static final String RIGHT_FRONT 			= "right_front";
	public static final String RIGHT_BACK 			= "right_back";
	
	public static final String LEFT_FRONT 			= "left_front";
	public static final String LEFT_BACK 			= "left_back";
	

	private HashMap<String, CGMotor> motors;
	private CGDriveTrainLayout layout;
	
	public CGDriveTrain(CGDriveTrainLayout layout)
	{
		this.motors = new HashMap<String, CGMotor>();
		this.layout = layout;
	}
	
	/** ABSTRACTS **/
	public abstract double getDriveSpeed();
	public abstract CGDriveTrainSpeedConfiguration getSpeedConfiguration();
	
	public HashMap<String, CGMotor> getMotors()
	{
		return this.motors;
	}
	
	/** 
	 * injectMotor
	 * @param id This is super specific... 
	 * 
	 * for the 'id' param you'll want to refer to the source code and figure it out 
	 * tbh this was designed by a ghost
	 * 
	 * @param motor this just your motor config
	 */
	public void injectMotor(String id, CGMotor motor)
	{
		getMotors().put(id, motor);
	}
	
	public void configureMotorsToDriveSpec()
	{
		if(layout == CGDriveTrainLayout.LEFT_RIGHT_SIMPLE_FOUR_MOTOR)
		{
			//probably setup some more stuff or something
			this.motors.get(RIGHT_FRONT).setInverted(true);
			this.motors.get(RIGHT_BACK).setInverted(true);
		}
	}
	
	/**
	 * drive()
	 * called during update loop
	 */
	public void drive(CGRobot robot)
	{
		if(layout == CGDriveTrainLayout.LEFT_RIGHT_SIMPLE_FOUR_MOTOR)
		{
			if(getSpeedConfiguration() == CGDriveTrainSpeedConfiguration.DYNAMIC)
			{
				if(robot.getJoystick(CGJoystickName.JOY_RIGHT) != null)
				{
					CGJoystick joy = robot.getJoystick(CGJoystickName.JOY_RIGHT);
					this.motors.get(RIGHT_FRONT).setSpeed(-joy.getY()); //JOYSTICK SHOULD BE INVERTED, IF NOT, OVERRIDE AND ADD CUSTOM CODE
					this.motors.get(RIGHT_BACK).setSpeed(-joy.getY());
				}
				if(robot.getJoystick(CGJoystickName.JOY_LEFT) != null)
				{
					CGJoystick joy = robot.getJoystick(CGJoystickName.JOY_LEFT);
					this.motors.get(LEFT_FRONT).setSpeed(-joy.getY()); //JOYSTICK SHOULD BE INVERTED, IF NOT, OVERRIDE AND ADD CUSTOM CODE
					this.motors.get(LEFT_BACK).setSpeed(-joy.getY());
				}
			}
		}
	}
	
}
