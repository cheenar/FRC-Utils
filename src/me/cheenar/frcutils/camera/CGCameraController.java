package me.cheenar.frcutils.camera;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import me.cheenar.frcutils.logging.Logger;

/**
 * 
 * @author cheenar
 * @version 1.0
 *
 */

//Notes
/*
 * 
 * The way that ports and names are handled is kinda jank
 * maybe I should fix it, maybe I shouldn't
 * Figure out during the season
 * 
 */

public class CGCameraController 
{
	
	private CameraServer server;
	private Image frame;
	
	private CGCamera currentCamera;
	
	private ArrayList<CGCamera> cameras;
	
	public CGCameraController()
	{
		this.cameras = new ArrayList<CGCamera>();
		this.frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		this.server = CameraServer.getInstance();
		this.server.setQuality(50);
		this.server.setSize(300);
	}
	
	public CameraServer getServer()
	{
		return this.server;
	}
	
	public Image getFrame()
	{
		return this.frame;
	}
	
	public ArrayList<CGCamera> getCameras()
	{
		return this.cameras;
	}
	
	public boolean camerasContain(Object o)
	{
		for(CGCamera cam : cameras)
		{
			if(o instanceof String)
			{
				if(o instanceof String)
				{
					if(cam.getUID().equals((String)o))
					{
						return true;
					}
				}
				if(o instanceof Integer)
				{
					if(cam.getPort() == (int)o)
					{
						return true;
					}
				}
				if(o instanceof CGCamera)
				{
					if(cam == (CGCamera)o)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean moreThanZeroCameras()
	{
		if(getCameras() == null)
			return false;
		return getCameras().size() > 0;
	}
	
	public void addCamera(CGCamera camera)
	{
		getCameras().add(camera);
	}
	
	public void removeCamera(CGCamera camera)
	{
		getCameras().remove(camera);
	}

	public void removeCamera(int camera)
	{
		getCameras().remove(camera);
	}
	
	private void startAcquisition(CGCamera newCamera)
	{
		stopAcquisition();
		
		this.currentCamera = newCamera;
		
		if(getCameras().size() > 0)
		{
			if(currentCamera == null)
			{
				currentCamera = getCameras().get(0);
			}
			currentCamera.startCamera();
		}
	}
	
	private void stopAcquisition()
	{
		if(currentCamera != null)
		{
			currentCamera.stopCamera();
		}
	}
	
	/**
	 * updateCamera
	 * put me in the periodic function for teleop or autonomous
	 */
	public void updateCamera()
	{
		if(currentCamera == null)
		{
			if(getCameras().size() > 0)
				currentCamera = getCameras().get(0);
			else
				return;
		}
		
		try
		{
			NIVision.IMAQdxGrab(currentCamera.getCamera(), frame, 1);
			
			//Any transformation information can be applied here
			currentCamera.onRotation();
			
			server.setImage(frame);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Logger.consoleLog("Camera failed during update loop");
		}
	}
	
	public CGCamera getCamera(Object o)
	{
		if(!((o instanceof String) || (o instanceof Integer)))
		{
			Logger.consoleLog("Not a string or integer (getCamera)");
		}
		
		for(CGCamera cam : cameras)
		{
			if(o instanceof String)
			{
				if(cam.getUID().equals((String)o))
				{
					return cam;
				}
			}
			if(o instanceof Integer)
			{
				if(cam.getPort() == (int)o)
				{
					return cam;
				}
			}
		}
		return null;
	}
	
	/**
	 * setCamera
	 * USE THIS METHOD WHEN YOU WANT TO SET THE CAMERA AND BEGIN ACQUISITION
	 * @param o takes either a string or integer or CGCamera
	 */
	public void setCamera(Object o)
	{
		if(!((o instanceof String) || (o instanceof Integer)))
		{
			Logger.consoleLog("Not a string or integer (changeCamera)");
		}
		
		for(CGCamera cam : cameras)
		{
			if(o instanceof String)
			{
				if(cam.getUID().equals((String)o))
				{
					this.startAcquisition(cam);
				}
			}
			if(o instanceof Integer)
			{
				if(cam.getPort() == (int)o)
				{
					this.startAcquisition(cam);
				}
			}
			if(o instanceof CGCamera)
			{
				if(cam == (CGCamera)o)
				{
					this.startAcquisition(cam);
				}
			}
		}
	}
	
}
