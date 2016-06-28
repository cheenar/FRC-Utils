package me.cheenar.frcutils.motors;

import edu.wpi.first.wpilibj.SafePWM;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;

public class CGMotor
{
	
	private Object motor;
	
	public CGMotor(CGMotorType type, int port)
	{
		if(type == CGMotorType.TALON)
		{
			this.motor = new TalonSRX(port);
		}
		if(type == CGMotorType.VICTOR)
		{
			this.motor = new VictorSP(port);
		}
	}
	
	public Object getMotor()
	{
		return motor;
	}
	
	public boolean isVictor()
	{
		if(motor instanceof VictorSP)
		{
			return true;
		}
		return false;
	}
	
	public VictorSP toVictor()
	{
		if(isVictor())
		{
			return (VictorSP)motor;
		}
		return null;
	}
	
	public TalonSRX toTalon()
	{
		if(!isVictor())
		{
			return (TalonSRX)motor;
		}
		return null;
	}
	
	public SafePWM toSafePWM()
	{
		return (SafePWM)motor;
	}
	
	public void setExpiration(double exp)
	{
		toSafePWM().setExpiration(exp);
	}
	
	public void setTimeout(double exp)
	{
		toSafePWM().setExpiration(exp);
	}
	
	public void stop()
	{
		setSpeed(0.0);
	}
	
	public void setSpeed(double speed)
	{
		if(isVictor())
		{
			toVictor().set(speed);
		}
		else
		{
			toTalon().set(speed);
		}
	}
	
	public void setInverted(boolean bool)
	{
		if(isVictor())
		{
			toVictor().setInverted(bool);
		}
		else
		{
			toTalon().setInverted(bool);
		}
	}
	
}
