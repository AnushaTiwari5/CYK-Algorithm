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
		
		//accept string to be checked from user
		//call CYK(checkstring, rules[])
		System.out.println("Enter string that you want to check: ");
		String checkStr = sc.nextLine();
		
		//Build a cyk table for the given grammar and string 
		CYK cyk = new CYK(checkStr, rules);
		
		//Check if the given string can be formed through this grammar
		if(cyk.checkInGrammar()) {
			System.out.println(checkStr + " can be obtained through this grammar!");
			
		} else {
			System.out.println(checkStr + " cannot be obtained through this grammar.");
		}
		
		sc.close();
		filesc.close();
		
	}
}
