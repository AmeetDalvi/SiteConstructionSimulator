package com.construction.simulator.site;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.construction.simulator.site.exception.FileReadException;
import com.construction.simulator.site.util.MessageConstants;

public class ReadFile {
	
	private static final String FILE_PATH = "C:\\\\simulatorMap\\\\";
											
	
	public List<ArrayList<String>> readFile() throws IOException {
		Scanner scanner = new Scanner(System.in);
		String fileName;
		
		List<ArrayList<String>> fileData;
		
		while(true) {
			System.out.println(MessageConstants.FILE_NAME);
			fileName = scanner.next();
			String fileFullPath = FILE_PATH + fileName;
			
			try {
				if(isValidExtension(fileName)) {
					fileData = readDataFromFile(fileFullPath);
					break;
				} else {
					throw new FileReadException(MessageConstants.FILE_EXTENSION);
				}
			} catch (FileReadException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}
		
		System.out.println(MessageConstants.DISPLAY_WELCOME_TEXT);
		displayFileMap(fileData);
		return fileData;
	}
	
	private boolean isValidExtension(String fileName) throws FileReadException {
		if(fileName == null || !(fileName.endsWith(".txt"))) {
			return false;
		}
		return true;
	}
	
	private List<ArrayList<String>> readDataFromFile(String fileName) throws FileReadException {
		List<ArrayList<String>> records = new ArrayList<>();
		Scanner input = null;
		int size = 0;
		try {
			input = new Scanner(new File(fileName));
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		while(input.hasNextLine()) {
			Scanner colReader = new Scanner(input.nextLine());
			ArrayList<String> col = new ArrayList<>();
			while (colReader.hasNext()) {
				String element = colReader.next();
				isValidFileData(col, element);
			}
			
			if(size == 0) {
				size = col.size();
			}
			
			isValidDataSize(size, col);
			records.add(col);
		}
		return records;
	}
	
	
	private void isValidDataSize(int size, ArrayList col)  throws FileReadException{
		if(col.size() != size) {
			throw new FileReadException(MessageConstants.FILE_DIMENSION);
		} 
	}
	
	private void isValidFileData(ArrayList<String> col, String element)  throws FileReadException{
		if(element.equals("o") || element.equals("r") || element.equals("t") || element.equals("T")) {
			col.add(element);
		} else {
			throw new FileReadException(MessageConstants.FILE_INVALID_DATA);
		}
		
	}
	
	private void displayFileMap(List<ArrayList<String>> fileData) {
		for(List<String> data : fileData) {
			for(String value : data) {
				System.out.print(value+ " ");
			}
			System.out.println();
		}
	}

}
