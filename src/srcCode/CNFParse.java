package srcCode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class which parses the CNF rules.
 * @author anush
 */
public class CNFParse {

	/*	 * 
	 * An array of Linked Lists will be used to store the rules of the grammar
	 * The head of each Linked List will be the LHS of the rule
	 * The rest of the nodes will be the RHS of the rule
	 */	
	Rule[] cnfRules;

	/*
	 * The rules of the grammar
	 */
	ArrayList<String> ruleset = null;

	/*
	 * The start symbol of this grammar
	 */
	String start;

	/**
	 * Constructor of the parser class
	 * @param rules An array containing the rules of the grammar.
	 */
	public CNFParse(ArrayList<String> rules) {
		this.ruleset = rules;

		//The start symbol is the first lhs in the ruleset
		this.start = rules.get(0).split("->")[0].trim();

		//Number of linked lists = number of rules because each rule will be a linked list
		this.cnfRules = new Rule[rules.size()];

		//Loop to go through all the rules and store them as individual pairs
		for(int i = 0; i < rules.size(); i++) {
			/*
			 * Each line will be a rule in the form lhs -> rhs1 | rhs2 ...
			 * The lhs and rhs are separated by "->"
			 * The strings in the rhs are separated by "|"
			 * Break down each rule to get the lhs of the rule,
			 * and an array of all the possible rhs using this rule
			 */
			String lhs = ruleset.get(i).split("->")[0].trim(); 
			String[] rhs = ruleset.get(i).split("->")[1].split("[|]");                                         


			for(int j = 0; j < rhs.length; j++) { 
				//Get rid of any leading and trailing spaces in the strings 
				//This ensures that only the exact strings of the grammar are added to the list 
				rhs[j] = rhs[j].trim(); 
			}


			this.cnfRules[i] = new Rule(lhs, new ArrayList<String>(Arrays.asList(rhs)));
		}
	}

	/**
	 * Returns the start symbol of this grammar
	 * @return The start symbol of this grammar
	 */
	public String getStart() {
		return this.start;
	}

	/**
	 * Returns the rhs of the rules for a given lhs
	 * @param left The given lhs that we want the rules of
	 * @return The rhs of the rules 
	 */
	public ArrayList<String> getRule(String left) {
		for(int i = 0; i < this.cnfRules.length; i++) {
			//Find the lhs in the set of rules we have
			if(this.cnfRules[i].getLeft().equals(left)) {
				//if specific lhs found, return the rhs
				return this.cnfRules[i].getRight();
			}
		}

		//if lhs not found, return null
		return null;
	}


	/**
	 * Return a list of lhs of rules that a given rhs can be obtained from 
	 * @param right The rhs that we want to know how it can be obtained
	 * @return A list of lhs of rules from which this rhs can be obtained
	 */
	public ArrayList<String> belongsToRules(String right) {
		ArrayList<String> lefts = new ArrayList<String>();

		for(int i = 0; i < this.cnfRules.length; i++) {
			//check whether the given string is an rhs of the rule
			if (this.cnfRules[i].inRule(right)) {
				//add the lhs of the rule to the list
				lefts.add(this.cnfRules[i].getLeft());
			}
		}

		//if the list is empty, rhs cannot be obtained through any rules
		//return null
		if(lefts.isEmpty()) {
			return null;
		}

		return lefts;
	}



}
