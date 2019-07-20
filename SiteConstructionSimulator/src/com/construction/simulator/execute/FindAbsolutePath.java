package com.construction.simulator.execute;

import java.io.File;

public class FindAbsolutePath {

	public String getAbsoluteFilePath(String filePath) {
		//File file = new File("src/main/java");
		//File file = new File("target");
		File file = new File(filePath);
		String absolutePath = file.getAbsolutePath();
		return  absolutePath;
	}
}
