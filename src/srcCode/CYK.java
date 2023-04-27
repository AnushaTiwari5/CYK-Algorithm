package srcCode;

import java.util.ArrayList;

/**
 * Class which builds a CYK table given a grammar to check whether the String belongs to that grammar
 * @author anush
 */
public class CYK {

	/**
	 * A Jagged 2D array which will be used to implement the CYK algorithm dynamically
	 * Each cell in the array will store a list of lhs of the rules of this grammar 
	 */
	ArrayList<String>[][] cykTable;
	
	/**
	 * The string that we want to check can be formed through this grammar
	 */
	String checkString;
	
	/**
	 * Parser object used to parse and implement cnf rules
	 */
	CNFParse cnf;
	
	/**
	 * The constructor to implement this CYK
	 * @param str The string to be checked
	 * @param rules The rules of the grammar (in CNF)
	 */
	@SuppressWarnings("unchecked")
	public CYK(String str, ArrayList<String> rules) {
		this.checkString = str;
		cnf = new CNFParse(rules);
		
		//Number of rows = length of the String
		this.cykTable = new ArrayList[str.length()][];
		
		//Filling in number of columns for each row
		for(int i = 0; i < str.length(); i++) {
			cykTable[i] = new ArrayList[str.length()-i];
		}
		
		this.fillTable();
	}
	
	/**
	 * Method that fills the CYK Table
	 */
	public void fillTable() {
		
		//i = Row variable
		for(int i = 0; i < checkString.length(); i++) {
			//j = Column variable
			for(int j = 0; j < checkString.length() - i; j++) {
				cykTable[i][j] =  fillCell(j, i);
			}
		}
	}
		
	/**
	 * Returns a list of Strings that should fill a specific cell of the array
	 * @param col The column number of the cell
	 * @param row The row number of the cell
	 * @return A list of Strings to fill this cell
	 */
	public ArrayList<String> fillCell(int col, int row) {
		ArrayList<String> rules = new ArrayList<String>();
		
		if(row == 0) {
			/*
			 * First row
			 * Fill the cell with the rule used to obtain the literal corresponding to this column
			 * Get the character of this column => index is column number - 1
			 */
			
			//The literal is in the same position as the column number
			//Indexing starts from 0 so the position is actually column number - 1
			String c = this.checkString.charAt(col) + ""; 
			
			//Get the list of rules that can be used to obtain the literal
			rules = cnf.belongsToRules(c);
			return rules;
		}
		
		else {
			
			//List of Strings returned after doing Cartesian Product of 2 cells
			ArrayList<String> prodList = new ArrayList<String>();
			
			//List of Strings that are can be obtained in a cell
			ArrayList<String> strList = new ArrayList<String>();
			
			for(int i = 0; i < row; i++) {
				//Get the Cartesian Product of the cells
				prodList = cartProd(getCell(col, i), getCell(col+1+i, row - (i+1)));
				
				if(prodList == null) {
					//One or more of the cells were null. 
					//The product is also null
					continue;
				}
				
				if(i == 0) {
					//There are no duplicates from the first product
					strList.addAll(prodList);
					
				} else {
					//Check for the existing list for duplicates; only fill in unique pairs
					for(int j = 0; j < prodList.size(); j++) {
						if(!(strList.contains(prodList.get(j)))) {
							strList.add(prodList.get(j));
						}
					}
				}
			}
			
			//List of rules that a String belongs to
			ArrayList<String> strRules;
			
			for(int i = 0; i < strList.size(); i++) {
				//Get the list of rules that each String in the produced list can be produced through
				strRules = cnf.belongsToRules(strList.get(i));
				
				if(strRules == null) {
					//If there are no rules producing this String, it will be null. 
					//Don't fill in anything in the rule list
					continue;
				}
				
				if(i == 0) {
					//There are no duplicates for the rules producing the first String
					rules.addAll(strRules);
					
				} else {
					for(int j = 0; j < strRules.size(); j++) {
						//Check for duplicates
						if(!(rules.contains(strRules.get(j)))) {
							rules.add(strRules.get(j));
						}
					}
				}
				
			}
			
			//Return the list of rules that can produce Strings of this cell
			return rules;
		}
	}
	
	/**
	 * Returns the list of Strings in the given cell of the table
	 * @param col The column number of the cell
	 * @param row The row number of the cell
	 * @return The list of Strings in the given cell of the table
	 */
	public ArrayList<String> getCell(int col, int row) {
		ArrayList<String> str = this.cykTable[row][col];
		if(str == null || str.isEmpty()) {
			return null;
		}
		return str;
	}
	
	/**
	 * Returns a list of ordered pairs formed as a result of a Cartesian product between 2 sets of strings
	 * @param str1 The first set of Strings
	 * @param str2 The second set of Strings
	 * @return The resulting Cartesian product of the 2 strings
	 */
	public ArrayList<String> cartProd(ArrayList<String> str1, ArrayList<String> str2) {
		//The "product" of 2 Strings
		String prod;
		//The list resulting from the Cartesian Product of the given list of Strings
		ArrayList<String> prodList = new ArrayList<String>();
		
		//If either of the Strings are null, the resulting product is null
		if(str1 == null || str2 == null) {
			return null;
		}
		
		//Perform Cartesian product of the Strings in the list
		for(int i = 0; i < str1.size(); i++) {
			for(int j = 0; j < str2.size(); j++) {
				//The Cartesian product is the concatenation of the Strings
				prod = str1.get(i) + str2.get(j);
				
				//Check if the pair is already in the list. If not, add it to the list
				if(!(prodList.contains(prod))) {
					prodList.add(prod);
				}
			}
		}
		
		//return list of Cartesian products
		return prodList;
	}
	
	/**
	 * Check whether given String can be obtained through this grammar
	 * @return True, if the String can be obtained from this grammar. Else, false
	 */
	public boolean checkInGrammar() {
		//Get the topmost (0, n-1) cell of the table
		ArrayList<String> topCell = this.getCell(0, checkString.length() - 1);
		
		//The String can only be obtained through this grammar if the cell contains the start state.
		//Check whether the cell is null, or contains the start state. Returns true or false accordingly
		
		if(topCell == null) {
			return false;
		}
		return topCell.contains(cnf.getStart());
	}
}
