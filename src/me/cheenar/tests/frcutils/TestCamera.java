package me.cheenar.tests.frcutils;

import me.cheenar.frcutils.camera.CGCamera;
import me.cheenar.frcutils.camera.CGCameraController;

public class TestCamera 
{

	public void test()
	{
		//create your cameras
		CGCamera cameraLeft = new CGCamera(0, "left");
		CGCamera cameraRight = new CGCamera(1, "right");
		
		//create the controller
		CGCameraController controller = new CGCameraController();
		
		//add the cameras
		controller.addCamera(cameraLeft);
		controller.addCamera(cameraRight);
		
		controller.setCamera("left"); //you can set the camera (enables the acquisition for it)
		controller.setCamera("right");
		controller.setCamera(0); //you can pass the [uid] for the camera or the [port] (exact number, not cam0, instead you would pass 0)
		controller.setCamera(1);
		
		controller.updateCamera(); //call in the periodic function or in a thread that will handle the updating of the camera
	}
	
}
