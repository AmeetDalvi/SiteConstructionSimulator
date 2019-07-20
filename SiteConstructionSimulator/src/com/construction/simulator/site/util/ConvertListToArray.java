package com.construction.simulator.site.util;

import java.util.ArrayList;
import java.util.List;

public class ConvertListToArray {
	
	
	
	
	
	
	
	public String[][] getConvertedListToArray(List<ArrayList<String>> fileData) {
		String[][] array = new String[fileData.size()][];
		
		for(int i=0;i<array.length;i++) {
			array[i] = new String[fileData.get(i).size()];
		}
		
		for(int i=0;i<fileData.size();i++) {
			for(int j=0;j<fileData.get(i).size();j++) {
				array[i][j] = fileData.get(i).get(j);
			}
		}
		
		return array;
	}
	
	public String[][] copy2dArray(String siteData[][]) {
		String[][] copyArray = new String[siteData.length][];
		
		for(int i=0;i<copyArray.length;i++) {
			copyArray[i] = new String[siteData[i].length];
			for(int j=0;j < copyArray[i].length;++j ) {
				copyArray[i][j] = siteData[i][j];
			}
		}
		return copyArray;
	}

}
