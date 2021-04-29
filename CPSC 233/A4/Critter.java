/*
 * Name: Sipeng He
 * 
 * Version: March 28, 2021
 * -Attributes and methods are added to track the location of every critter(alive or dead)
 * 
 * Version: March 31, 2021
 * -Modifications are made for the newly implemented Location wrapper class
 */

/* As needed students can write additional methods and add attributes */

public class Critter {
	// Class attributes by James Tam. Students do not make any changes to these 3
	// but
	// additional attributes may be added.
	public static final char DEFAULT_APPEARANCE = '*';
	public static final char EMPTY = ' ';
	private char appearance;
	private Location aLocation;

	// Start of modifiable code written by James Tam
	// Critter(), Critter(char), getAppearance(), setAppearance(char).
	// Students can make any changes.
	public Critter() {
		setAppearance(DEFAULT_APPEARANCE);
		aLocation = new Location(-1, -1);
	}

	public Critter(char ch) {
		setAppearance(ch);
		aLocation = new Location(-1, -1);
	}

	public char getAppearance() {
		return appearance;
	}

	public void setAppearance(char newAppearance) {
		appearance = newAppearance;
	}
	// End of modifiable code written by James Tam

	// Students can add additional methods below as needed.

	public void setLocation(int r, int c) {
		aLocation.setLocation(r, c);
	}

	public int getRow() {
		return aLocation.getRow();
	}

	public int getColumn() {
		return aLocation.getColumn();
	}

	public Location getLocation() {
		return aLocation;
	}

	public void setLocation(Location inputLocation) {
		aLocation = inputLocation;
	}

	// Code written by James Tam. Students: do not modify
	public String toString() {
		String s = "" + appearance;
		return (s);
	}
}
