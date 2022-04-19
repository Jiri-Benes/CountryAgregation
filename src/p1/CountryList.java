/**
 * 
 */
package p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;



/**
 * @author locadmin
 *
 */
public class CountryList {

	private static final String PIPE_DELIMITER = "\\|";
	private static final int TOWN_COLUMN = 0;
	private static final int COUNTRY_COLUMN = 1;
	private static final int VALUE_COLUMN = 2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String firstArg = "";
		if(args.length>0) {
			firstArg = args[0];
		}
		Path file;
		if(firstArg.isEmpty()) {
			file = Paths.get("./input.txt");
		} else {
			file = Paths.get(firstArg);
		}		
		
		HashMap<String, Integer> countryMap = new HashMap<String, Integer>();
		HashMap<String, Integer> countryOrderMap = new HashMap<String, Integer>();
		
		//Charset charset = Charset.forName(StandardCharsets.UTF_8);
		try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
		    String line = null;
		    Integer i = 0;
		    while ((line = reader.readLine()) != null) {
		    	i++;
		        System.out.println(line);
		        String[] values = line.split(PIPE_DELIMITER);
		        if(values.length < 3) {
		        	System.err.println("Wrong format of line("+i+"):"+line);
		        	continue;
		        }
		        String oneCountry = values[COUNTRY_COLUMN];
		        //System.out.println(oneCountry);
		        String oneValue = values[VALUE_COLUMN];
		        //System.out.println(oneValue);
		        Integer oneValueInt;
		        try {
		        	oneValueInt = Integer.valueOf(oneValue);
		    	}
	        	catch (NumberFormatException ex){
	        		System.err.println("Error cannot convert to number:"+oneValue+ " on line "+i);
	        		oneValueInt = Integer.valueOf(0);
	        	}
		        //System.out.println(oneValueInt);
		        //System.out.println(i);
		        Integer currentValue = countryMap.get(oneCountry);
		        if(currentValue == null) {
		        	currentValue = oneValueInt;
		        	countryOrderMap.put(oneCountry, i);	
		        } else {
		        	currentValue = currentValue + oneValueInt;
		        	// remember lowest occurrence
		        	Integer currentOrder = countryOrderMap.get(oneCountry);
		        	if(i < currentOrder) {
		        		countryOrderMap.put(oneCountry, i);
		        	}

		        }
		        countryMap.put(oneCountry, currentValue);		        
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		// sort first of occurrence of country to hold order
		SortedSet<Integer> orderSorted = new TreeSet<Integer>(countryOrderMap.values());
		//System.out.println(orderSorted);
		for (Integer iSorted : orderSorted) {
			// find country with order line number
			for (String countryOrderI : countryOrderMap.keySet()) {
				//System.out.println("key: " + countryOrderI + " value: " + countryOrderMap.get(countryOrderI));
				if(countryOrderMap.get(countryOrderI) == iSorted) {
					System.out.println(countryOrderI + "|" +countryMap.get(countryOrderI));
				}
			}
		}
		/*
		// Print keys and values
		for (String i : countryMap.keySet()) {
			System.out.println("key: " + i + " value: " + countryMap.get(i));
		}
		for (String i : countryOrderMap.keySet()) {
			System.out.println("key: " + i + " value: " + countryOrderMap.get(i));
		}		
		*/
		
		/*
		String s = "xxxxxxxx";
		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
		    writer.write(s, 0, s.length());
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		*/		
	}

}
