package com.construction.simulator.site;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.construction.simulator.bean.CostPerQuantity;
import com.construction.simulator.bean.Location;
import com.construction.simulator.site.util.ConvertListToArray;
import com.construction.simulator.site.util.MessageConstants;
import com.construction.simulator.site.util.SiteDataConstants;

public class SimulateData {
	
	List<Location> locationTraversed = new ArrayList<>();
	String site[][];

	public boolean callSimulator(String siteData[][], String command, int first, String[][] siteArray, List<String> commandsList) {
		
		ConvertListToArray convertListToArray = new ConvertListToArray();
		//site = convertListToArray.copy2dArray(siteData);
		site = siteArray;
		int totalRow = siteData.length;
		int totalCol = siteData[0].length;
		
		
	//	int first = 0;
		Location location = new Location();
		location.setCol(-1);
		location.setRow(-1);
		location.setType(null);
		boolean isQuit = false;
		
		
		//	first++;
			if(isAdvanceCommand(command)) {
				int step = getStep(command);
		       boolean exitEncountered = setLocationOfSite(location, step, first, totalRow, totalCol);
		       if(exitEncountered) {
		    	   System.out.println("Ended");
		    	  // break;
		    	   isQuit = true;
		    	   
		       } 
			} else if(isLeftCommand(command)) {
				if(first == 1) {
					//System.out.println("quitting");
					//break;
					isQuit = true;
					
				} else {
					Location changePh = changeDirection(command);
					if(changePh != null) {
						locationTraversed.set((locationTraversed.size() -1), changePh);
					}
				}
			} else if(isRightCommand(command)) {
				if(first == 1) {
					//System.out.println("quitting");
					//break;
					isQuit = true;
					
				} else {
					Location changePh = changeDirection(command);
					if(changePh != null) {
						locationTraversed.set((locationTraversed.size() -1), changePh);
					}
				}
			} else if(isQuitCommand(command)) {
				//System.out.println("quitting.....");
				//break;
				isQuit = true;
			} else {
				//System.out.println("wrong command quitting");
				//continue;
				isQuit = true;
			}
	
			if(isQuit) {
				displayResult(commandsList);
			}
			
			return isQuit;
	}

	private void displayResult(List<String> commands) {
		
		int fuel = 0;
		int preTree = 0;
		int paintDamage = 0;
		int unclearedSquare = 0;
		int total = 0;
		
		
		Map<String, CostPerQuantity> result = new LinkedHashMap<>();
		for(Location location : locationTraversed) {
			String type = location.getType();
			
			switch (type) {
			case SiteDataConstants.PLAIN_LAND:
				fuel = fuel + 1;
				break;

			case SiteDataConstants.ROCK_LAND:
				fuel = fuel + 2;
				break;
			
			case SiteDataConstants.TREE_PRESERVED:
				fuel = fuel + 2;
				preTree = preTree + 1 ;
				paintDamage = paintDamage + 1;
				break;

			case SiteDataConstants.TREE:
				fuel = fuel + 2;
				paintDamage =  paintDamage + 1;
				break;

			default:
				fuel = fuel + 1;
				break;
			}
		}
		
		if(commands.get(commands.size()-1).equals(SiteDataConstants.QUIT)) {
			result.put("COM_OVER", new CostPerQuantity(commands.size() -1, commands.size() -1));
		} else {
			result.put("COM_OVER", new CostPerQuantity(commands.size(), commands.size()));
		}
		
		result.put("FUEL", new CostPerQuantity(fuel, fuel));
		
		
		for(String sites[] :site) {
			for(String currentSites : sites) {
				if(currentSites != null) {
					unclearedSquare = unclearedSquare + 1;
				}
			}
		}
		
		result.put("UNCLEARED", new CostPerQuantity(unclearedSquare, unclearedSquare*3));
		
		result.put("PREE_TREE", new CostPerQuantity(preTree, preTree *10));
		result.put("PAINT", new CostPerQuantity(paintDamage, paintDamage * 2));
		
		for(CostPerQuantity cpq : result.values()) {
			total = total + cpq.getCost();
		}
		
		result.put("TOTAL", new CostPerQuantity(total));
		StringBuilder builder = new StringBuilder();
		
		if(!commands.isEmpty()) {
			for(String commandVal : commands) {
				if(commandVal.startsWith(SiteDataConstants.ADVANCE)) {
					builder.append("ADVANCE " +commandVal.charAt(1));
					builder.append(",");
				} else if(commandVal.equals(SiteDataConstants.RIGHT)) {
					builder.append("TURN RIGHT");
					builder.append(",");
				} else if(commandVal.equals(SiteDataConstants.LEFT)) {
					builder.append("TURN LEFT");
					builder.append(",");
				} else {
					builder.append("QUIT");
					
				}
			}
		}
		
		
		System.out.println(MessageConstants.DISPLAY_TEXT1);
		System.out.println(builder);
		System.out.println(MessageConstants.DISPLAY_TEXT2);
		
		
		System.out.printf("%10s %30s %10s",MessageConstants.ITEM,MessageConstants.QUANTITY,MessageConstants.COST);
		System.out.println();
		
		for( String key: result.keySet() ) {
			CostPerQuantity costPerQuantity = result.get(key);
			switch(key) {
			
			case "COM_OVER":
				//System.out.println("Communication overhead " +costPerQuantity.getQuantity() +", " +costPerQuantity.getCost());
				System.out.format("%10s %15s %12s",MessageConstants.COMM_OVERHEAD,costPerQuantity.getQuantity() ,costPerQuantity.getCost() );
				System.out.println();
				break;
			
			case "FUEL":
				//System.out.println("FUEL " +costPerQuantity.getQuantity() +", " +costPerQuantity.getCost());
				System.out.format("%10s %27s %12s",MessageConstants.FUEL_USAGE,costPerQuantity.getQuantity() ,costPerQuantity.getCost() );
				System.out.println();
				break;
				
			case "UNCLEARED":
				System.out.format("%10s %20s %12s",MessageConstants.UNCLEARED_SQUARES,costPerQuantity.getQuantity() ,costPerQuantity.getCost() );
				System.out.println();
				break;
			
			case "PREE_TREE":
				System.out.format("%10s %8s %12s",MessageConstants.DEST_PROT_TREE,costPerQuantity.getQuantity() ,costPerQuantity.getCost() );
				System.out.println();
				break;

			case "PAINT":
				System.out.format("%10s %12s %12s",MessageConstants.PAINT_DAMAGE,costPerQuantity.getQuantity() ,costPerQuantity.getCost() );
				System.out.println();
				break;
				
			case "TOTAL":
				System.out.println("-------------------------------------------------------------");
				System.out.format("%10s %30s %10s",MessageConstants.TOTAL,"" ,costPerQuantity.getCost() );
				System.out.println();
				break;

			default:
				break;
			}
		}
		
		
	}

	private Location changeDirection(String checkCommand) {
		
		if(locationTraversed != null && !locationTraversed.isEmpty()) {
			Location lastPh = locationTraversed.get(locationTraversed.size() - 1);
			String currentDirection = lastPh.getCurrentDirection();
			
			if(currentDirection.equals(SiteDataConstants.EAST)) {
				if(isLeftCommand(checkCommand)) {
					lastPh.setCurrentDirection(SiteDataConstants.NORTH);
				} else {
					lastPh.setCurrentDirection(SiteDataConstants.SOUTH);
				}
				
			}else if(currentDirection.equals(SiteDataConstants.WEST)) {
				if(isLeftCommand(checkCommand)) {
					lastPh.setCurrentDirection(SiteDataConstants.SOUTH);
				} else {
					lastPh.setCurrentDirection(SiteDataConstants.NORTH);
				}
			} else if(currentDirection.equals(SiteDataConstants.NORTH)) {
				if(isLeftCommand(checkCommand)) {
					lastPh.setCurrentDirection(SiteDataConstants.WEST);
				} else {
					lastPh.setCurrentDirection(SiteDataConstants.EAST);
				}
			} else if(currentDirection.equals(SiteDataConstants.SOUTH)) {
				if(isLeftCommand(checkCommand)) {
					lastPh.setCurrentDirection(SiteDataConstants.EAST);
				} else {
					lastPh.setCurrentDirection(SiteDataConstants.WEST);
				}
			}
			return lastPh;
		}
		return null;
	}

	private boolean isQuitCommand(String type) {
		if(type != null && type.equals(SiteDataConstants.QUIT)) {
			return true;
		}
		return false;
	}

	private boolean isRightCommand(String type) {
		if(type != null && type.equals(SiteDataConstants.RIGHT)) {
			return true;
		}
		return false;
	}

	private boolean isLeftCommand(String type) {
		if(type != null && type.equals(SiteDataConstants.LEFT)) {
			return true;
		}
		return false;
	}

	private boolean setLocationOfSite(Location ph, int step, int first, int totalRow, int totalCol) {
		
		for(int counter = 0;counter <step; counter++) {
			ph = new Location();
			if(first == 1) {
				ph.setCol(counter);
				ph.setRow(0);
				ph.setCurrentDirection(SiteDataConstants.EAST);
			} else {
				if(locationTraversed != null && !locationTraversed.isEmpty()) {
					Location lastPath = locationTraversed.get(locationTraversed.size() -1);
					String lastDirection = lastPath.getCurrentDirection();
					int lastrow = lastPath.getRow();
					int lastcol = lastPath.getCol();
					
					if(lastDirection.equals(SiteDataConstants.EAST)) {
						ph.setCol(lastcol + 1);
						ph.setRow(lastrow);
						ph.setCurrentDirection(lastDirection);
					}
					
					if(lastDirection.equals(SiteDataConstants.WEST)) {
						ph.setCol(lastcol - 1);
						ph.setRow(lastrow);
						ph.setCurrentDirection(lastDirection);
					}
					
					if(lastDirection.equals(SiteDataConstants.NORTH)) {
						ph.setCol(lastcol);
						ph.setRow(lastrow - 1);
						ph.setCurrentDirection(lastDirection);
					}
					
					if(lastDirection.equals(SiteDataConstants.SOUTH)) {
						ph.setCol(lastcol);
						ph.setRow(lastrow + 1);
						ph.setCurrentDirection(lastDirection);
					}
				}
			}
			
			if(isOutsideLand(ph.getRow(), ph.getCol(), totalRow, totalCol)) {
				System.out.println("OUTSIDE LAND");
				return true;
			}
			int newRow = ph.getRow();
			int newCol = ph.getCol();
			ph.setType(site[newRow][newCol]);
			locationTraversed.add(ph);
			if(isPreservedTree(ph, step, newRow, newCol)) {
				site[newRow][newCol] = null;
				return true;
			} else {
				site[newRow][newCol] = null;
			}
			
		}
		return false;
	}

	private boolean isPreservedTree(Location ph, int step, int row, int col) {
		String type = site[row][col];
		if(type != null && type.equals(SiteDataConstants.TREE_PRESERVED)) {
			return true;
		}
		return false;
	}

	private boolean isOutsideLand(int currentRow, int currentCol, int totalRow, int totalCol) {
		if(currentCol >= totalCol || currentCol < 0) {
			System.out.println("Outside the land on col at - " +currentCol);
			return true;
		} else if(currentRow >= totalRow || currentRow < 0) {
			System.out.println("Outside the land on Row at - " +currentRow);
			return true;
		}
		return false;
	}

	private int getStep(String command) {
		Character advanceCommmands = command.charAt(1);
		int val = Integer.parseInt(advanceCommmands.toString());
		if(val>0) {
			try {
				return val;
			}catch(NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}

	private boolean isAdvanceCommand(String checkCommand) {
		if(checkCommand.startsWith(SiteDataConstants.ADVANCE) && checkCommand.length() == 2) {
			return true;
		}
		return false;
	}
}
