package srcCode;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * Main class used to run the program
 * @author anush
 */
public class Driver {
	
	/**
	 * Read a given file and parse the rules of the CNF grammar from the file
	 * @param filename The name of the file to read
	 * @return The list of cnf rules of the grammar contained in the file
	 */
	public static ArrayList<String> readFile(String filename) {
		
		try {
			//open file
			File file = new File(filename);
			Scanner filesc = new Scanner(file);
			
			//for loop to read lines in file and form an array of strings (rules)
			ArrayList<String> lines = new ArrayList<String>();
			while(filesc.hasNext()) {
				lines.add(filesc.nextLine());
			}
			
			filesc.close();
			return lines;
			
		} catch (FileNotFoundException e) {
			//Catch exception if the file is not found
			System.out.println("Specified file not found. Please try again.");
			
		} catch (Exception e) {
			//Catch any other errors and display the error message to user
			System.out.println(e.getMessage());
		}
		
		return null;
		
	}
	
	/**
	 * Main function which is called when the program is run
	 * @param args A string of arguments
	 */
	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		ArrayList<String> rules;
		String filename;
		
		do {
			//ask user for file name
			System.out.println("Enter name of file containing grammar in CNF");
			 filename = sc.nextLine();
			
			 //Read and parse the rules in the file
			rules = readFile(filename);
			
		} 
		//loop continues to run until a file has been read and parsed successfuly
		while (rules == null);
		
		
		String cont = "n";
		
		do {
			//accept string to be checked from user
			//call CYK(checkstring, rules[])
			System.out.println("Enter string that you want to check: ");
			String checkStr = sc.nextLine();
			
			//If the string is empty, give it E terminal for empty string
			if(checkStr.length() == 0) {
				checkStr = "E";
			}
			
			//Build a cyk table for the given grammar and string 
			CYK cyk = new CYK(checkStr, rules);
			
			//Check if the given string can be formed through this grammar
			if(cyk.checkInGrammar()) {
				System.out.println(checkStr + " CAN be obtained through this grammar!");
				
			} else {
				System.out.println(checkStr + " CANNOT be obtained through this grammar.");
			}
			
			System.out.println("------------------------------------------------------------------------");
			
			//Ask if the user would like to continue checking more strings		
			System.out.println("Would you like to continue checking another String?\n"
					+ "Enter Y/N");
			cont = sc.nextLine();
			
			if(cont.equalsIgnoreCase("y")) {
				//Ask if the user would like to use a different grammar			
				System.out.println("Would you like to use a different grammar?\n"
						+ "Enter Y/N");
				filename = sc.nextLine();
				
				//Parse the rules of the new grammar
				if(filename.equalsIgnoreCase("y")) {
					do {
						//ask user for file name
						System.out.println("Enter name of file containing grammar in CNF");
						 filename = sc.nextLine();
						
						//Read and parse the rules in the file
						rules = readFile(filename);
						
					} 
					//loop continues to run until a file has been read and parsed successfuly
					while (rules == null);
				} 
			}
			
		} 
		//Loop continues to run until the user indicates they wish to quit the program
		while(cont.equalsIgnoreCase("y"));
		
		sc.close();
		
	}
}
