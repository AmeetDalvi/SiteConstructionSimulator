package com.construction.simulator.site;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.construction.simulator.site.exception.FileReadException;
import com.construction.simulator.site.util.ConvertListToArray;
import com.construction.simulator.site.util.MessageConstants;

public class ConstructionSimulator {

	public static void main(String[] args) {
		try {
			ReadFile readFile = new ReadFile();
			List<ArrayList<String>> fileData = readFile.readFile();
				String command;
				ConvertListToArray convertListToArray = new ConvertListToArray();
				String[][] siteData  = convertListToArray.getConvertedListToArray(fileData);
				String site[][] = convertListToArray.copy2dArray(siteData);
				List<String> commandsList = new ArrayList<>();
				SimulateData simulateData = new SimulateData();
				
				if(!fileData.isEmpty()) {
					Scanner scanner = new Scanner(System.in);
					int first = 0;
					System.out.println(MessageConstants.DISPLAY_TEXT);
					
					while(true) {
						System.out.println(MessageConstants.ENTER_COMMAND);
						command =scanner.next();
						first++;
						try {
							if(command.equals("l") || command.equals("r") || command.equals("q") || command.startsWith("a")) {
								commandsList.add(command);
								boolean returnVal = simulateData.callSimulator(siteData, command,first,site,commandsList);
								if(returnVal) {
									break;
								}
							} else {
								throw new FileReadException(MessageConstants.INVALID_COMMAND);
							}
						} catch(Exception e) {
							System.out.println(e.getMessage());
							scanner.nextLine();
						}
					}
					
				}
					
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
