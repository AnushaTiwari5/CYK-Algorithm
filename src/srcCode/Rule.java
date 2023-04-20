package srcCode;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class which holds a Rule
 * @author anush
 */
public class Rule {
	private String lhs;
	private ArrayList<String> rhs;
	
	public Rule() {
		this.lhs = "";
		this.rhs = new ArrayList<String>();
	}
	
	public Rule(String left, ArrayList<String> right) {
		this.lhs = left;
		this.rhs = right;
	}
	
	public String getLeft() {
		return this.lhs;
	}
	
	public ArrayList<String> getRight() {
		return this.rhs;
	}
	
	public void addRight(String right) {
		rhs.add(right);
	}
	
	public int ruleLegth() {
		return rhs.size();
	}
	
	public boolean inRule(String right) {
		for(int i = 0; i < this.rhs.size(); i++) {
			if(rhs.get(i).equalsIgnoreCase(right)) {
				return true;
			}
		}
		return false;
	}
}
