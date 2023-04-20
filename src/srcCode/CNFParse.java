package srcCode;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class which parses the CNF rules.
 * @author anush
 */
public class CNFParse {
	
	/*
	 * A dictionary used to store all the CNF rules of the grammar.
	 * Each rule will be broken down into a single LHS -> RHS pair.
	 * The LHS will be the Key, and the RHS will be the Value.
	 * 
	 * An array of Linked Lists will be used to store the rules of the grammar
	 * The head of each Linked List will be the LHS of the rule
	 * The rest of the nodes will be the RHS of the rule
	 */
	//Map<String, String> cnfRules = null;
	
	Rule[] cnfRules;
	
	/*
	 * The rules of the grammar. 
	 */
	String[] ruleset = null;
	
	/**
	 * Constructor of the parser class
	 * @param rules An array containing the rules of the grammar.
	 */
	public CNFParse(String[] rules) {
		this.ruleset = rules;
		//cnfRules = new HashMap<String, String>();
		
		//Number of linked lists = number of rules because each rule will be a linked list
		this.cnfRules = new Rule[rules.length];
		
		//Loop to go through all the rules and store them as individual pairs
		for(int i = 0; i < rules.length; i++) {
			/*
			 * Each line will be a rule in the form lhs -> rhs1 | rhs2 ...
			 * The lhs and rhs are separated by "->"
			 * The rules in the rhs are separated by "|"
			 * Break down each rule to get the lhs of the rule,
			 * and an array of all the possible rhs using this rule
			 */
			String lhs = ruleset[i].split("->")[0]; 
			String[] rhs = ruleset[i].split("->")[1].split("|");
			
			this.cnfRules[i] = new Rule(lhs, new ArrayList<String>(Arrays.asList(rhs)));
		}
	}
	
	public ArrayList<String> getRule(String left) {
		for(int i = 0; i < this.cnfRules.length; i++) {
			if(this.cnfRules[i].getLeft().equalsIgnoreCase(left)) {
				return this.cnfRules[i].getRight();
			}
		}
		
		return null;
	}
	
	public ArrayList<String> belongsToRules(String right) {
		ArrayList<String> lefts = new ArrayList<String>();
		
		for(int i = 0; i < this.cnfRules.length; i++) {
			if (this.cnfRules[i].inRule(right)) {
				lefts.add(this.cnfRules[i].getLeft());
			}
		}
		
		if(lefts.isEmpty()) {
			return null;
		}
		return lefts;
	}
}
