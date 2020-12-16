package com.qa.ims.utils;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	private static Logger LOGGER = LogManager.getLogger();

	private final Scanner scanner;

	public Utils(Scanner scanner) {
		super();
		this.scanner = scanner;
	}

	public Utils() {
		scanner = new Scanner(System.in);
	}

	public Long getLong() {
		String input = getString();
		Long longInput = null;
		do {
			try {
				longInput = Long.parseLong(input);
			} catch (NumberFormatException nfe) {
				LOGGER.info("Error - Please enter a number.");
			}
		} while (longInput == null);
		return longInput;
	}

	public String getString() {
		return scanner.nextLine().toLowerCase();
	}

	public Double getDouble() {
		String input = getString();
		Double doubleInput = null;
		do {
			try {
				doubleInput = Double.parseDouble(input);
			} catch (NumberFormatException nfe) {
				LOGGER.info("Error - Please enter a number.");
			}
		} while (doubleInput == null);
		return doubleInput;
	}
	
	public int getInt() {
		
		String input;
		int intOut = 0;
		input = getString();
		try {
			intOut = Integer.parseInt(input);
		} 
		catch(NumberFormatException nfe) {
			LOGGER.info("Error - Please enter a number.");
			getInt();
		}
		return intOut;
	}
	
	public boolean getBool() {
		
	    String trueString = "true"; 
	    String yesString = "y";
	    String falseString = "false";
	    String noString = "n";
		boolean boolOut = false;
		
		String input = getString();

			if (input.equals(trueString) || input.equals(yesString)) {
		          return true;
		      } else if (input.equals(falseString) || input.equals(noString)) {
		          return false;
		      }else {
		    	LOGGER.info("Error - Please enter true/false or y/n.");
				getBool();
		      }

		return boolOut;
	}

}
