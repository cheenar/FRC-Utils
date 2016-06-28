package me.cheenar.frcutils.joys;

import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;
import me.cheenar.frcutils.logging.Logger;

public class CGJoystick
{
	
	public enum TophatPosition
	{
		NORTH(0),
        SOUTH(180),
        EAST(90),
        WEST(270);
		
		//Cardinal Directions Tutorial
		//North = Up
		//South = Down
		//East = Right
		//West = Left

        TophatPosition(int i)
        {
            this.pos = i;
        }

        private int pos;

        public int getValue()
        {
            return this.pos;
        }
	}
	
	private Joystick joystick;
	private HashMap<String, Integer> buttonMapping = new HashMap<String, Integer>();
	private ArrayList<CGJoystickFunction> functions = new ArrayList<CGJoystickFunction>();
	
	public CGJoystick(int port)
	{
		this.joystick = new Joystick(port);
	}
	
	public Joystick getJoystick()
	{
		return this.joystick;
	}
	
	public void insertMapping(String id, int button)
	{
		this.buttonMapping.put(id, button);
	}
	
	public ArrayList<CGJoystickFunction> getFunctions()
	{
		return functions;
	}
	
	public void injectFunction(CGJoystickFunction function)
	{
		getFunctions().add(function);
	}
	
	/** WRAPPERS **/
	
	public double getY()
	{
		return joystick.getY();
	}
	
	public double getX()
	{
		return joystick.getX();
	}
	
	public double getZ()
	{
		return joystick.getZ();
	}
	
	public double getThrottle()
	{
		return joystick.getThrottle();
	}
	
	public double getTwist()
	{
		return joystick.getTwist();
	}
	
	public boolean isTriggerActuated()
	{
		return joystick.getTrigger();
	}
	
	/** BOOLEAN BUTTON CHECKING **/
	
	public boolean isLessThanDeadzoneTarget(double deadzone)
	{
		return joystick.getMagnitude() < deadzone;
	}
	
	public boolean isWithinDeadzoneTarget(double d1, double d2)
	{
		return (joystick.getMagnitude() > d1) && (joystick.getMagnitude() < d2);
	}
	
	public boolean isRawJoystickButtonDown(int buttonID)
	{
		return this.joystick.getRawButton(buttonID);
	}
	
	public boolean isTophatButtonDown(TophatPosition position)
	{
		return this.joystick.getPOV() == position.getValue();
	}
	
	public boolean isPressed(String mappingName)
	{
		if(buttonMapping.containsKey(mappingName))
		{
			return this.isRawJoystickButtonDown(buttonMapping.get(mappingName));
		}
		else
		{
			Logger.consoleLog("No key in button map exists");
		}
		return false;
	}
	
}
