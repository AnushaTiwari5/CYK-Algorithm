package srcCode;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Driver {

	public static void main(String args[]) throws FileNotFoundException {
		
		Scanner sc = new Scanner(System.in);
		
		//ask user for file
		System.out.println("Enter filename: ");
		String filename = sc.nextLine();
		
		//open file
		File file = new File(filename);
		Scanner filesc = new Scanner(file);
		
		//for loop to read lines in file => form an array of strings (rules)
		ArrayList<String> rules = new ArrayList<String>();
		while(filesc.hasNext()) {
			rules.add(filesc.nextLine());
		}
		
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
				System.out.println(checkStr + " can be obtained through this grammar!");
				
			} else {
				System.out.println(checkStr + " cannot be obtained through this grammar.");
			}
			
			System.out.println();
			
			System.out.println("Would you like to continue checking another String?\n"
					+ "Enter Y/N");
			cont = sc.nextLine();
			
		} while(cont.equalsIgnoreCase("y"));
		
		sc.close();
		filesc.close();
		
	}
}
