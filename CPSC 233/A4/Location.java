/*
 * Name: Sipeng He
 * 
 * Version: March 31, 2021
 * -Wrapper class to track the locations of critters
 */

public class Location {
	private int c;
	private int r;

	public Location() {
		r = -1;
		c = -1;
	}

	public Location(int inputR, int inputC) {
		r = inputR;
		c = inputC;
	}

	public void setRow(int inputR) {
		r = inputR;
	}

	public void setColumn(int inputC) {
		c = inputC;
	}

	public void setLocation(int inputR, int inputC) {
		r = inputR;
		c = inputC;
	}

	public int getRow() {
		return r;
	}

	public int getColumn() {
		return c;
	}
}
