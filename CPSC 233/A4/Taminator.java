/*
 * Name: Sipeng He
 * 
 * Version: March 28, 2021
 * -Methods and attributes are added to detect and make a list of taminator's neighbours
 * -Methods to randomly generated the location(Row and Column values) for teleportation
 * 
 * Version: March 29, 2021
 * -Added a method to clear the neighbours list after each turn, which is ignored by me yesterday
 * 
 * Version: March 30, 2021
 * -Debug message is added to the method that makes neighbours list
 *
 * Version: March 31, 2021
 * -Modifications are made for the newly implemented Location wrapper class
 */
import java.util.Random;

/* As needed students can write additional methods and add attributes */

public class Taminator extends Critter {
	// Class attribute by James Tam. Students do not make any changes to it but
	// additional attributes may be added.
	public static final char DEFAULT_APPEARANCE = 'T';
	private int numOfNeighbours;
	private Location[] neighbourList;

	// Start of modifiable code written by James Tam
	// Taminator(), Taminator(char)
	// Students can make any changes.
	public Taminator() {
		super(DEFAULT_APPEARANCE);
		numOfNeighbours = 0;
		neighbourList = new Location[8];
	}

	public Taminator(char newAppearance) {
		super(newAppearance);
		numOfNeighbours = 0;
		neighbourList = new Location[8];
	}
	// End of modifiable code written by James Tam

	// Students can add additional methods below as needed.

	public void detectingNeighbours(Critter[][] crittersArray, int r, int c) {
		for (int i = r - 1; i <= r + 1; i++) {
			for (int j = c - 1; j <= c + 1; j++) {
				if (i >= 0 && i <= 9 && j >= 0 && j <= 9) {
					if (i != r || j != c) {
						if (crittersArray[i][j].getAppearance() == Critter.DEFAULT_APPEARANCE) {
							if (Debug.debugModeOn == true) {
								System.out.printf("The taminator found a neighbour at %d,%d%n", i, j);
							}
							addToNeighbourList(crittersArray[i][j]);
						}
					}
				}
			}
		}
	}

	public void addToNeighbourList(Critter aCritter) {
		neighbourList[numOfNeighbours] = aCritter.getLocation();
		numOfNeighbours++;
	}

	public Location[] getNeighbourList() {
		return neighbourList;
	}

	public int getNumOfNeighbours() {
		return numOfNeighbours;
	}

	public Location generateLocation() {
		Location aLocation = new Location();
		Random generator = new Random();
		aLocation.setRow(generator.nextInt(Biosphere.ROWS));
		aLocation.setColumn(generator.nextInt(Biosphere.COLUMNS));
		return aLocation;
	}

	public void clearNeighbourList() {
		for (int i = numOfNeighbours; i > 0; i--) {
			neighbourList[i - 1] = null;
		}
		numOfNeighbours = 0;
	}
}
