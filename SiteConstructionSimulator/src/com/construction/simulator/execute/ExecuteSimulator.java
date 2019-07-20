package com.construction.simulator.execute;

import java.io.IOException;

public class ExecuteSimulator {
	
	public static void main(String[] args) {
		
		
		FindAbsolutePath absolutePath = new FindAbsolutePath();
		
		
		String batFilePath = "src/com/construction/simulator/bat";
		batFilePath = absolutePath.getAbsoluteFilePath(batFilePath);
        batFilePath =   batFilePath.replace("\\", "\\\\");
        String completeBatFilePath =  batFilePath.concat("\\\\testbat.bat");
		System.out.println("completeBatFilePath is "+completeBatFilePath);
		
		
		
		
		String jarPath = "src/com/construction/simulator/bat";
		jarPath = absolutePath.getAbsoluteFilePath(jarPath);
		String completeTargetJarPath = jarPath.concat("\\constructionSimulator.jar");
	    
	    try {
			Runtime.getRuntime().exec(new String[] {"cmd.exe","/c","Start",completeBatFilePath, completeTargetJarPath});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
