/*
 * Name: Sipeng He
 * 
 * Version: March 30, 2021
 * -Overriding methods to loop through every cell with a different birth and death rule from its parent class Biosphere
 * -Finish the loop of turns
 *
 * Version: march 31, 2021
 * -Debug Mode is added
 * 
 * Version: March 31, 2021
 * -Modifications are made for the newly implemented Location wrapper class
 */
/* As needed students can write additional methods and add attributes */
import java.util.Scanner;

public class ProsperousBiosphere extends Biosphere // Students can't change this inheritance relation.
{
	// Students can make any changes except for the relationship.
	public ProsperousBiosphere(Critter[][] aWorld) {
		super(aWorld);
	}

	public void runTurn() {
		String input;
		Scanner in = new Scanner(System.in);
		initialDebugPrompt();
		while (true) {
			createCurrentState();
			taminatorAction();
			display();
			updateArrays();
			if (getTaminatorExists()) {
				if (getPrevious()[getTaminatorR()][getTaminatorC()] instanceof Taminator) {
					((Taminator) getPrevious()[getTaminatorR()][getTaminatorC()]).clearNeighbourList();
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

	public void createCurrentState() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS; c++) {
				if (super.getPrevious()[r][c].getAppearance() == Critter.EMPTY) {
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

	public void checkOccupiedCell(int r, int c) {
		int numOfNeighbours = -1;
		numOfNeighbours = countNeighbourCritters(r, c);
		if (getPrevious()[r][c].getAppearance() == Taminator.DEFAULT_APPEARANCE) {
			if (Debug.debugModeOn == true) {
				System.out.printf("Critter at %d,%d is a taminator%n", r, c);
				System.out.println();
			}
			getCurrent()[r][c] = getPrevious()[r][c];
			setTaminatorR(r);
			setTaminatorC(c);
			setTaminatorExists(true);
		} else {
			if (numOfNeighbours == 1 || numOfNeighbours == 2 || numOfNeighbours == 3 || numOfNeighbours == 4) {
				if (Debug.debugModeOn == true) {
					System.out.printf("Cell %d,%d has %d neighbours%n", r, c, numOfNeighbours);
					System.out.printf("Critter at %d,%d remains alive%n", r, c);
					System.out.println();
				}
				getCurrent()[r][c].setAppearance(Critter.DEFAULT_APPEARANCE);
			} else {
				if (Debug.debugModeOn == true) {
					System.out.printf("Cell %d,%d has %d neighbours%n", r, c, numOfNeighbours);
					System.out.printf("Critter at %d,%d dies%n", r, c);
					System.out.println();
				}
			}
		}
	}

	public void checkEmptyCell(int r, int c) {
		int numOfNeighbours = -1;
		numOfNeighbours = countNeighbourCritters(r, c);
		if (numOfNeighbours == 4) {
			if (Debug.debugModeOn == true) {
				System.out.printf("<<<Cell %d,%d: Empty>>>%n", r, c);
				System.out.printf("Cell %d,%d has %d neighbours%n", r, c, numOfNeighbours);
				System.out.printf("New critter is born at %d,%d%n", r, c);
				System.out.println();
			}
			getCurrent()[r][c].setAppearance(Critter.DEFAULT_APPEARANCE);
		}
	}
}
