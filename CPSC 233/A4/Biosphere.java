/*
 * Name: Sipeng He
 * 
 * Version: March 27, 2021
 * -Finish the display method, including the bounding lines, array labels and labels of numbers
 * -The initial input array can be properly displayed
 * 
 * Version: March 28, 2021
 * -Methods for counting the number of neighbours of one single cell, empty or occupied
 * -Methods to make a critter die or born according to its surroundings
 * -Implement some basic operations of arrays, such as copy, clear, initialize and give location information to every cell
 * 
 * Version: March 29, 2021
 * -Finish the loop of turns
 * -Methods to remove critters according to the information provided by the Taminator
 * -Methods for the taminator to do teleportation after every round
 * 
 * Version: March 30, 2021
 * -Debug mode is added
 * -Mutators and accessors are added for the ProsperousBiosphere Class to access the attributes of its parent class
 * 
 * Version: March 31, 2021
 * -Modifications are made for the newly implemented Location wrapper class
 */
import java.util.Scanner;

/* As needed students can write additional methods and add attributes */

public class Biosphere {
	// Constant declarations and attributes by James Tam, don't change.
	public static final int ROWS = 10;
	public static final int COLUMNS = 10;
	public static final String HORIZONTAL_LINE = "  - - - - - - - - - -";
	public static final String HORIZONTAL_COUNT = "  0 1 2 3 4 5 6 7 8 9 ";
	private Critter[][] current;
	private Critter[][] previous;
	private Critter[][] afterTamination;
	private boolean tamExists;
	private Location tamLocation;

	// Original code written by James Tam, don't modify
	public Biosphere(Critter[][] aWorld) {
		// Original code
		current = aWorld;

		// Student code
		previous = current;
		setArrayLocations(previous);
		afterTamination = initializeArray();
		current = initializeArray();
		tamExists = false;
		tamLocation = new Location();
	}

	// The code used by the student was based on the display code written by James
	// Tam
	public void display() {
		// Original code below: student can freely modify to fulfill assignment
		// requirements
		// (add, delete, change existing code).
		int r;
		System.out.println();
		System.out.println("  PREVIOUS GENERATION     BIRTHS&DEATHS           TAMINATOR");
		System.out.println(HORIZONTAL_COUNT + "  " + HORIZONTAL_COUNT + "  " + HORIZONTAL_COUNT);
		printHorizontalLine();
		for (r = 0; r < ROWS; r++) {
			printALine(r, previous);
			printALine(r, current);
			printALine(r, afterTamination);
			System.out.println();
			printHorizontalLine();
		}
	}

	// Original code written by James Tam, don't modify
	public Critter[][] getCurrent() {
		return (current);
	}

	// Original code written by James Tam
	public void runTurn() {
		// Original code below: student can freely modify to fulfill assignment
		// requirements
		// (add, delete, change existing code).
		String input;
		Scanner in = new Scanner(System.in);
		initialDebugPrompt();
		while (true) {
			createCurrentState();
			taminatorAction();
			display();
			updateArrays();
			if (tamExists) {
				if (previous[tamLocation.getRow()][tamLocation.getColumn()] instanceof Taminator) {
					((Taminator) previous[tamLocation.getRow()][tamLocation.getColumn()])
							.clearNeighbourList();
				}
			}
			promptMessages();
			input = in.nextLine();
			if (input.equals("q") || input.equals("Q")) {
				System.out.println("Game ends.");
				break;
			}
			if (input.equals("D") || input.equals("d")) {
				toggleDebugMode();
			}
		}
	}

	public void initialDebugPrompt() {
		String input;
		Scanner in = new Scanner(System.in);
		System.out.println("Would you like to toggle on the debug mode? (Y/N)");
		System.out.print(">>");
		input = in.nextLine();
		if (input.equals("Y") || input.equals("y"))
			Debug.debugModeOn = true;
	}

	public void promptMessages() {
		System.out.println("Please select one option to continue:");
		System.out.println("-Q/q: Quit");
		System.out.println("-D/d: Toggle the debug mode on/off");
		System.out.println("-Others: Continue to next turn");
		System.out.print(">>");
	}

	public void toggleDebugMode() {
		if (Debug.debugModeOn == false)
			Debug.debugModeOn = true;
		else
			Debug.debugModeOn = false;
	}

	public Critter[][] getPrevious() {
		return (previous);
	}

	public Critter[][] getAfterTamination() {
		return (afterTamination);
	}

	public void printALine(int r, Critter[][] anArray) {
		System.out.print(r);
		for (int c = 0; c < COLUMNS; c++) {
			System.out.print("|" + anArray[r][c]);
		}
		System.out.print("|  ");
	}

	public void printHorizontalLine() {
		for (int x = 0; x < 3; x++) {
			System.out.print(HORIZONTAL_LINE); // Line of dashes before the array
			System.out.print("   ");
		}
		System.out.println();
	}

	public void createCurrentState() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				if (previous[r][c].getAppearance() == Critter.EMPTY) {
					checkEmptyCell(r, c);
				} else {
					if (Debug.debugModeOn == true) {
						System.out.printf("<<<Cell %d,%d: Occupied>>>%n", r, c);
					}
					checkOccupiedCell(r, c);
				}
			}
		}
	}

	public void setArrayLocations(Critter[][] anArray) {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				anArray[r][c].setLocation(r, c);
			}
		}
	}

	public void checkEmptyCell(int r, int c) {
		int numOfNeighbours = -1;
		numOfNeighbours = countNeighbourCritters(r, c);
		if (numOfNeighbours == 3) {
			if (Debug.debugModeOn == true) {
				System.out.printf("<<<Cell %d,%d: Empty>>>%n", r, c);
				System.out.printf("Cell %d,%d has %d neighbours%n", r, c, numOfNeighbours);
				System.out.printf("New critter is born at %d,%d%n", r, c);
				System.out.println();
			}
			current[r][c].setAppearance(Critter.DEFAULT_APPEARANCE);
		}
	}

	public void checkOccupiedCell(int r, int c) {
		int numOfNeighbours = -1;
		numOfNeighbours = countNeighbourCritters(r, c);
		if (previous[r][c].getAppearance() == Taminator.DEFAULT_APPEARANCE) {
			if (Debug.debugModeOn == true) {
				System.out.printf("Critter at %d,%d is a taminator%n", r, c);
                                                                System.out.println();
			}
			current[r][c] = previous[r][c];
			tamLocation.setLocation(r, c);
			tamExists = true;
		} else {
			if (numOfNeighbours == 2 || numOfNeighbours == 3) {
				if (Debug.debugModeOn == true) {
					System.out.printf("Cell %d,%d has %d neighbours%n", r, c, numOfNeighbours);
					System.out.printf("Critter at %d,%d remains alive%n", r, c);
					System.out.println();
				}
				current[r][c].setAppearance(Critter.DEFAULT_APPEARANCE);
			} else {
				if (Debug.debugModeOn == true) {
					System.out.printf("Cell %d,%d has %d neighbours%n", r, c, numOfNeighbours);
					System.out.printf("Critter at %d,%d dies%n", r, c);
					System.out.println();
				}
			}
		}
	}

	public Critter[][] initializeArray() {
		Critter[][] anArray;
		anArray = new Critter[ROWS][COLUMNS];
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				anArray[r][c] = new Critter(Critter.EMPTY);
				anArray[r][c].setLocation(r, c);
			}
		}
		return anArray;
	}

	public int countNeighbourCritters(int r, int c) {
		int numOfNeighbours = 0;
		for (int i = r - 1; i <= r + 1; i++) {
			for (int j = c - 1; j <= c + 1; j++) {
				if (i >= 0 && i <= 9 && j >= 0 && j <= 9) {
					if (i != r || j != c) {
						if (previous[i][j].getAppearance() == Critter.DEFAULT_APPEARANCE)
							numOfNeighbours++;
					}
				}
			}
		}
		return numOfNeighbours;
	}

	public void taminatorAction() {
		copyArray(current, afterTamination);
		if (tamExists == true) {
			if (Debug.debugModeOn == true) {
				System.out.printf("<<<Taminator at %d,%d>>>%n", tamLocation.getRow(),tamLocation.getColumn());
			}
			if (current[tamLocation.getRow()][tamLocation.getColumn()] instanceof Taminator) {
				((Taminator) current[tamLocation.getRow()][tamLocation.getColumn()])
						.detectingNeighbours(current, tamLocation.getRow(), tamLocation.getColumn());
			}
			tamination();
			teleportation();
		}
	}

	public void tamination() {
		int tempR;
		int tempC;
		int numOfTaminatorNeighbours = -1;
		Location[] TaminatorNeighbourList = new Location[8];
		if (current[tamLocation.getRow()][tamLocation.getColumn()] instanceof Taminator) {
			numOfTaminatorNeighbours = ((Taminator) current[tamLocation.getRow()][tamLocation.getColumn()])
					.getNumOfNeighbours();
			TaminatorNeighbourList = ((Taminator) current[tamLocation.getRow()][tamLocation.getColumn()])
					.getNeighbourList();
		}
		for (int i = 0; i < numOfTaminatorNeighbours; i++) {
			tempR = TaminatorNeighbourList[i].getRow();
			tempC = TaminatorNeighbourList[i].getColumn();
			afterTamination[tempR][tempC].setAppearance(Critter.EMPTY);
			if (Debug.debugModeOn == true) {
				System.out.printf("Critter at %d,%d has been taminated%n", tempR, tempC);
			}
		}
	}

	public void copyArray(Critter[][] array1, Critter[][] array2) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (array1[i][j].getAppearance() == Critter.DEFAULT_APPEARANCE) {
					array2[i][j].setAppearance(Critter.DEFAULT_APPEARANCE);
				} else if (array1[i][j].getAppearance() == Taminator.DEFAULT_APPEARANCE) {
					array2[i][j] = array1[i][j];
				} else {
					array2[i][j].setAppearance(Critter.EMPTY);
				}
			}
		}
	}

	public void teleportation() {
		Location temp = new Location();
		while (true) {
			if (afterTamination[tamLocation.getRow()][tamLocation.getColumn()] instanceof Taminator) {
				temp = ((Taminator) afterTamination[tamLocation.getRow()][tamLocation.getColumn()])
						.generateLocation();
			}
			if (checkLocation(temp)) {
				break;
			}
			if (Debug.debugModeOn == true) {
				System.out.printf("The taminator tries to teleport to %d,%d, but it is not a valid location%n",
						temp.getRow(), temp.getColumn());
			}
		}
		afterTamination[temp.getRow()][temp.getColumn()] = afterTamination[tamLocation.getRow()][tamLocation
				.getColumn()];
		afterTamination[tamLocation.getRow()][tamLocation.getColumn()] = new Critter(Critter.EMPTY);
		afterTamination[tamLocation.getRow()][tamLocation.getColumn()].setLocation(tamLocation.getRow(),
				tamLocation.getColumn());
		tamLocation = temp;
		afterTamination[tamLocation.getRow()][tamLocation.getColumn()].setLocation(temp);
		if (Debug.debugModeOn == true) {
			System.out.printf("The taminator has teleported to %d,%d%n", temp.getRow(), temp.getColumn());
			System.out.println();
		}
	}

	public boolean checkLocation(Location aLocation) {
		if (afterTamination[aLocation.getRow()][aLocation.getColumn()].getAppearance() == Critter.EMPTY) {
			return true;
		} else
			return false;
	}

	public void updateArrays() {
		clearArray(previous);
		copyArray(afterTamination, previous);
		clearArray(current);
		clearArray(afterTamination);
	}

	public void clearArray(Critter[][] anArray) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (anArray[i][j].getAppearance() == Taminator.DEFAULT_APPEARANCE) {
					anArray[i][j] = new Critter(Critter.EMPTY);
					anArray[i][j].setLocation(i, j);
				}
				if (anArray[i][j].getAppearance() == Critter.DEFAULT_APPEARANCE) {
					anArray[i][j].setAppearance(Critter.EMPTY);
				}
			}
		}
	}

	public int getTaminatorR() {
		return tamLocation.getRow();
	}

	public int getTaminatorC() {
		return tamLocation.getColumn();
	}

	public void setTaminatorR(int r) {
		tamLocation.setRow(r);
	}

	public void setTaminatorC(int c) {
		tamLocation.setColumn(c);
	}

	public void setTaminatorExists(boolean aBoolean) {
		tamExists = aBoolean;
	}

	public boolean getTaminatorExists() {
		return tamExists;
	}
}
