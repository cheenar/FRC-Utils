package me.cheenar.frcutils;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import me.cheenar.frcutils.camera.CGCamera;
import me.cheenar.frcutils.camera.CGCameraController;
import me.cheenar.frcutils.joys.CGJoystick;
import me.cheenar.frcutils.joys.CGJoystickFunction;
import me.cheenar.frcutils.logging.CGLogger;
import me.cheenar.frcutils.motors.CGDriveTrain;

public abstract class CGRobot extends IterativeRobot
{

	/** SETTINGS **/
	public static final double CONST_DRIVETRAIN_SPEED = 0.5;
	
	/** WPILIB SPECIFICATION **/
	private Command autonomousCommand;
    private SendableChooser chooser;
    
	/** CGRobot SPECIFICATION **/
	private boolean helperMode;
	private CGCameraController cameraController;
	private Object defaultCamera;
	private ArrayList<CGJoystick> joysticks;
	private CGDriveTrain driveTrain;
	
	public CGRobot(boolean helperMode)
	{
		this.helperMode = helperMode;
		this.joysticks = new ArrayList<CGJoystick>();
	}

	/** ABSTRACTS **/
	public abstract void onRobotInit();
	public abstract void onoTeleopInit();
	public abstract void onTeleopPeriodic();
	
	/** GETTERS & SETTERS **/
	
	//cameras
	public CGCameraController getCameraController()
	{
		return this.cameraController;
	}
	
	public void setCameraController(CGCameraController controller)
	{
		if(controller == null)
		{
			CGLogger.consoleLog("Warning! CameraController object is null");
		}
		this.cameraController = controller;
	}
	
	public Object getDefaultCamera()
	{
		return this.defaultCamera;
	}
	
	public void setDefaultCamera(Object o)
	{
		this.defaultCamera = o;
	}
	
	//joys
	public ArrayList<CGJoystick> getJoysticks()
	{
		return this.joysticks;
	}
	
	public CGJoystick getJoystick(String uid)
	{
		for(CGJoystick joy : joysticks)
		{
			if(joy.getUID().equals(uid))
				return joy;
		}
		return null;
	}
	
	public void injectJoystick(CGJoystick joy)
	{
		if(joy == null)
		{
			CGLogger.consoleLog("Warning! CGJoystick object is null");
		}
		getJoysticks().add(joy);
	}
	
	//drivetrain
	public void injectDrivetrain(CGDriveTrain driveTrain)
	{
		if(driveTrain == null)
			CGLogger.consoleLog("Warning! DriveTrain object in injectDrivetrain() was null!");
		this.driveTrain = driveTrain;
	}
	
	public CGDriveTrain getDriveTrain()
	{
		return this.driveTrain;
	}
	
	/** CAMERA CONTROLLER **/
	
	public void injectCamera(CGCamera camera)
	{
		if(camera == null)
		{
			System.err.println("Error! Injection failed! Null camera object passed to \"injectCamera\"");
			return;
		}
		if(cameraController == null)
		{
			System.err.println("Error! Camera controller null! Failed injectCamera operation");
			if(helperMode)
			{
				System.err.println("As a consideration, camera controller will be automatically instanciated to prevent mission critical error");
				cameraController = new CGCameraController();
			}
		}
		if(cameraController != null)
		{
			cameraController.addCamera(camera);
		}
	}
	
	/** JOYSTICKS **/
	
	public void clearJoysticks()
	{
		if(getJoysticks() != null)
			getJoysticks().clear();
		else
		{
			CGLogger.consoleLog("Error! Tried clearing a null joystick array!");
			CGLogger.consoleLog("Critical error has occured, automatically instanciating array");
			this.joysticks = new ArrayList<CGJoystick>();
		}
	}
	
	/** WPILIB Robot Code **/

	//Helper Functions
	private void setupDefaultCamera()
	{
		if(cameraController != null)
        {
        	if(cameraController.moreThanZeroCameras())
        	{
        		if(defaultCamera != null)
        		{
        			if(cameraController.camerasContain(defaultCamera))
        			{
        				cameraController.setCamera(defaultCamera);
        			}
        		}
        		else
        		{
        			cameraController.setCamera(cameraController.getCameras().get(0));
        		}
        	}
        }
	}
	
	private void handleJoysticks()
	{
		for(CGJoystick joy : joysticks)
        {
        	for(CGJoystickFunction func : joy.getFunctions())
        	{
        		if(func.isActivated(this, joy))
        		{
        			func.run(this, joy);
        		}
        	}
        }
	}
	
	//OVERRIDES
	@Override
    public void robotInit() 
    {
    	this.onRobotInit();
    	
        chooser = new SendableChooser();
        SmartDashboard.putData("Auto mode", chooser);
    }
	
	@Override
    public void teleopInit() 
    {	
        if (autonomousCommand != null) autonomousCommand.cancel();
     
        this.onoTeleopInit();
        
        //automatically setting the camera
        setupDefaultCamera();
    }
    
    @Override
    public void teleopPeriodic()
    {
        Scheduler.getInstance().run();
        
        this.onTeleopPeriodic();
        
        //update the camera
        if(cameraController != null)
        	cameraController.updateCamera();
        
        //handle inputs
        handleJoysticks();
        
        //handle driveTrain
        if(driveTrain != null)
        	driveTrain.drive(this);
    }
	
}
