package me.cheenar.frcutils.camera;

import com.ni.vision.NIVision;

import me.cheenar.frcutils.logging.Logger;

public class CGCamera
{

	//custom name
	private String uid;
	
	//instance of the camera
	private int camera;
	
	//e.g. cam0, cam1
	private String cameraID;
	
	/**
	 * CGCamera
	 * @param port passed as 0 or 1
	 * @param uid custom name, can be anything (e.g. cameraRight)
	 */
	public CGCamera(int port, String uid)
	{
		this.uid = uid;
		this.camera = -1;
		this.cameraID = "cam" + port;
		openCamera();
	}
	
	public String getUID()
	{
		return uid;
	}
	
	public int getCamera()
	{
		return camera;
	}
	
	public String getCameraID()
	{
		return cameraID;
	}
	
	public int getPort()
	{
		return Integer.parseInt(this.getCameraID().replaceAll("cam", ""));
	}
	
	private void openCamera()
	{
		try
		{
			camera = NIVision.IMAQdxOpenCamera(cameraID, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Logger.consoleLog("Failed to open the camera (check the port)");
			Logger.consoleLog("Current cameraID: " + cameraID);
		}
	}
	
	public void startCamera()
	{
		if(camera != -1)
		{
			try
			{
				NIVision.IMAQdxConfigureGrab(camera);
				NIVision.IMAQdxStartAcquisition(camera);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Logger.consoleLog("Failed to start the camera");
			}
		}
	}
	
	/**
	 * stopCamera
	 * Stops the camera acquisition so cameras can be changed
	 */
	public void stopCamera()
	{
		if(camera != -1)
		{
			try
			{
				NIVision.IMAQdxStopAcquisition(camera);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Logger.consoleLog("Failed to stop the camera acquisition (not close the camera stream)");
			}
		}
		else
			Logger.consoleLog("Camera does not exist yet!");
	}
	
	/**
	 * closeCamera
	 * closes the camera stream
	 */
	public void closeCamera()
	{
		if(camera != -1)
			NIVision.IMAQdxCloseCamera(camera);
		else
			Logger.consoleLog("Camera does not exist yet!");
	}
	
	public void onRotation()
	{
		//override me please! For the sake of god, make me do something ;)
	}
	
}
