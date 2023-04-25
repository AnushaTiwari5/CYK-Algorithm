package srcCode;

import java.util.ArrayList;

/**
 * Class which holds a Rule
 * @author anush
 */
public class Rule {
	
	/**
	 * The lhs of the rule
	 */
	private String lhs;
	
	/**
	 * A list of the rhs that can be obtained through this rule
	 */
	private ArrayList<String> rhs;
	
	/**
	 * Default constructor initializing an "empty" rule
	 */
	public Rule() {
		this.lhs = "";
		this.rhs = new ArrayList<String>();
	}
	
	/**
	 * Constructor that initializes a rule
	 * @param left The lhs of the rule
	 * @param right A list of the rhs that can be obtained through this rule
	 */
	public Rule(String left, ArrayList<String> right) {
		this.lhs = left;
		this.rhs = right;
	}
	
	/**
	 * Returns the lhs of the rule
	 * @return The lhs of the rule
	 */
	public String getLeft() {
		return this.lhs;
	}
	
	/**
	 * Returns the list of rhs that can be obtained by this rule
	 * @return The list of rhs that can be obtained by this rule
	 */
	public ArrayList<String> getRight() {
		return this.rhs;
	}
	
	/**
	 * Adds a string to the list of rhs that can be obtained by this rule
	 * @param right A string that can be obtained by this rule
	 */
	public void addRight(String right) {
		rhs.add(right);
	}
	
	/**
	 * Returns the number of strings that can be obtained by this rule
	 * @return The number of strings that can be obtained by this rule
	 */
	public int ruleLegth() {
		return rhs.size();
	}
	
	/**
	 * Checks whether a string can be obtained from a rule 
	 * @param right The string that we want to check whether it can be obtained by this rule
	 * @return True, if the string can be obtained by this rule. Else, false
	 */
	public boolean inRule(String right) {
		for(int i = 0; i < this.rhs.size(); i++) {
			//check if the string is in the list of rhs of this rule
			if(rhs.get(i).equals(right)) {
				return true;
			}
		}
		return false;
	}
	
}

