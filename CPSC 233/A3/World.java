/*
  Author: Sipeng He
  
  Version: March 10, 2021
  * Small modifications on debug messages
  * 
  Version: March 9, 2021
  * The initialization of JFrame for graphical display
  * The JFrame is set to refresh at the end of each loop
  * 
  Version: March 8, 2021
  * Elves and Orcs can attack an adjacent enemy at each turn
  * Unconscious entities are removed from the battlefield when their HP reaches 0
  * Debug messages for checking move, moving and attack are added
  * A counter is added to track the peaceful turns
  * 
  Version: March 7, 2021
  * Locations of Orcs and Elves are tracked by two arrays(elvesLocations, orcsLocations)
  * Each Orc and Elf can detect if there are adjacent enemies and note them down
  * Each Orc and Elf can check if there is an obstacle in its way and if it is at the boundary and cannot move
  * Each Orc and Elf can move diagonally once in each turn when conditions are met
  * 
  Version: March 6, 2021
  * A basic loop that runs the program until end game conditions are met
  * End game condition checking system(Elves won, Orcs won, draw or cease fire)
  * Checking whether entering the debug mode or not at the end of each loop
  * The result of each loop properly displayed with bounding lines in the form of text output
  * 
 Author:  James Tam
  Version: Feb 18, 2021
  * New version of the starting code included.

  Version: October 12, 2015
  * Original program
  * 
  Limitations:
  * The graphical displaying window should not be closed when moving on to next turn
  * Once Closed, the graphical displaying window cannot be reopened while running
  * Entities choose which enemy to attack only in a fashion starting from top to the bottom, left to the right
*/

import java.util.Scanner;

public class World {
	public static final int SIZE = 10;
	public static final int ORCS_WIN = 0;
	public static final int ELVES_WIN = 1;
	public static final int DRAW = 2;
	public static final int CEASE_FIRE = 3;
	public static final int NOT_OVER = 4;

	private Entity[][] aWorld;
	private Location[] elvesLocations;
	private Location[] orcsLocations;
	private int numOfElves = 0;
	private int numOfOrcs = 0;
	private int numOfRounds = 0;
	private int numOfPeacefulRounds = 0;
	private boolean ifPeaceful = true;
	private GraphicDisplay aGraphicDisplay;

	// Post-condition: the position of the characters in the
	// starting input file will determine the position of the
	// objects in the array. The type of character in the file
	// determines the appearance of the Entity(O or D) or if the
	// element is empty (spaces become null elements)

	/**
	 * Method: constructor 
	 * Features: 
	 * -initialize an Entity array as the map of the game 
	 * -initialize the two Location arrays that track the locations of Orcs and Elves
	 */
	public World() {
		aWorld = new Entity[SIZE][SIZE];
		int r;
		int c;
		for (r = 0; r < SIZE; r++) {
			for (c = 0; c < SIZE; c++) {
				aWorld[r][c] = null;
			}
		}
		aWorld = FileInitialization.read();
		elvesLocations = new Location[SIZE * SIZE];
		orcsLocations = new Location[SIZE * SIZE];
	}

	/**
	 * Method: updateLocations 
	 * Features: -update the two Location arrays that tracking the locations of Elves and Orcs after some changes
	 */
	public void updateLocations() {
		int r = -1;
		int c = -1;
		for (r = 0; r < SIZE; r++) {
			for (c = 0; c < SIZE; c++) {
				if (aWorld[r][c] != null) {
					switch (aWorld[r][c].getAppearance()) {
					case Entity.ELF:
						elvesLocations[numOfElves] = new Location(r, c);
						numOfElves++;
						break;
					case Entity.ORC:
						orcsLocations[numOfOrcs] = new Location(r, c);
						numOfOrcs++;
						break;
					}
				}
			}
		}
	}

	// Displays array elements on at a time one row per line
	// Each element is bound above, below, left and right by
	// bounding lines
	/**
	 * Method: display 
	 * Features: -Display the map with bounding lines
	 */
	public void display() {
		int r = -1;
		int c = -1;
		int b = -1;
		for (r = 0; r < SIZE; r++) {
			for (b = 0; b < SIZE; b++)
				System.out.print(" -");
			System.out.println("");
			for (c = 0; c < SIZE; c++) {
				if (aWorld[r][c] == null) {
					System.out.print("|");
					System.out.print(" ");
				}
				else {
					System.out.printf("|" );
					System.out.print(aWorld[r][c].getAppearance());
				}
			}
			System.out.println("|");
		}
		for (b = 0; b < SIZE; b++) {
			System.out.print(" ");
			System.out.print("-");
		}
		System.out.println();
	}

	/**
	 * Method: start
	 * Features:
	 * -containing the loop that controls the game
	 */
	public void start() {
		String ifDebug;
		Scanner in = new Scanner(System.in);
		initialization();
		while (true) {
			if (checkIfEnd())
				break;
			System.out.printf("Round %d%n", numOfRounds + 1);
			resetIfHasMoved();
			entitiesAction();
			clearLocations();
			updateLocations();
			display();
			countPeacefulRounds();
			numOfRounds++;
			aGraphicDisplay.refresh();
			if (GameStatus.debugModeOn == true) {
				System.out.printf("Number of turns since the last attack: %d%n", numOfPeacefulRounds);
			}
			System.out.println("Press enter to continue: ");
			System.out.println("-Type in 'D'/'d' and press enter to toggle on/off debug mode and continue");
			System.out.print("Your choice: ");
			ifDebug = in.nextLine();
			checkDebugMode(ifDebug);
			continue;
		}
		System.out.println("Game ends.");
	}

	/**
	 * Method: checkDebugMode
	 * Features:
	 * -toggle the debug mode on/off according to the input
	 */
	public void checkDebugMode(String ifDebug) {
		if (ifDebug.equals("d") || ifDebug.equals("D")) {
			if (GameStatus.debugModeOn == false) {
				GameStatus.debugModeOn = true;
				System.out.println("Debug Mode is on!");
			} else {
				GameStatus.debugModeOn = false;
				System.out.println("Debug Mode is off!");
			}
		}
		System.out.println("");
	}

	/**
	 * Method: entitiesAction
	 * Features:
	 * -looping through every entities in each turn
	 * -entities take actions(move, attack) one by one
	 */
	public void entitiesAction() {
		boolean ifEnemy;
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (aWorld[r][c] != null && aWorld[r][c].getIfHasMoved() == false) {
					if (GameStatus.debugModeOn == true) {
						System.out.printf("Checking move for (r/c) = (%d/%d)%n", r, c);
						System.out.println("");
					}
					ifEnemy = detectEnemy(r, c);
					checkIfMove(r, c, ifEnemy);
					move(r, c);
					if (ifEnemy == true) {
						attack(r, c);
					}
				}
			}
		}
	}

	/**
	 * Method: initialization
	 * Features:
	 * -display the initial state of the map
	 * -initializing the two Location arrays to track the locations of Orcs and Elves
	 * -asking the user whether to turn on the debug mode or not at the beginning of the simulation
	 */
	public void initialization() {
		String ifDebug;
		Scanner in = new Scanner(System.in);
		aGraphicDisplay = new GraphicDisplay(aWorld);
		aGraphicDisplay.initialize(aWorld);
		System.out.println("Initial State");
		updateLocations();
		display();
		System.out.println("Press enter to continue: ");
		System.out.println("-Type in D/d and press enter to toggle on Debug Mode and continue");
		System.out.print("Your choice: ");
		ifDebug = in.nextLine();
		checkDebugMode(ifDebug);
	}

	/**
	 * Method:countPeacefulRounds
	 * Feature:
	 * -count the number of peaceful turns that have passed
	 */
	public void countPeacefulRounds() {
		if (ifPeaceful == true) {
			numOfPeacefulRounds++;
		} else {
			numOfPeacefulRounds = 0;
		}
		ifPeaceful = true;
	}

	/**
	 * Method: checkIfEnd
	 * Features:
	 * -check if the end game conditions are met at the end of each turn
	 */
	public boolean checkIfEnd() {
		if (checkOrcsWon()) {
			return true;
		} else if (checkElvesWon()) {
			return true;
		} else if (checkDraw()) {
			return true;
		} else if (checkCeasefire()) {
			return true;
		} else
			return false;
	}

	/**
	 * Method: checkCeasefire
	 * Features:
	 * -check if the conditions for cease fire have been met
	 */
	public boolean checkCeasefire() {
		if (numOfPeacefulRounds == 10) {
			System.out.println("Ceasefire!");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method: checkOrcsWon
	 * Features:
	 * -check if the conditions for Orcs to win have been met
	 */
	public boolean checkOrcsWon() {
		if (numOfOrcs != 0 && numOfElves == 0) {
			System.out.println("Orcs won!");
			return true;
		} else
			return false;
	}

	/**
	 * Method: checkElvesWon
	 * Features:
	 * -check if the conditions for Elves to win have been met
	 */
	public boolean checkElvesWon() {
		if (numOfOrcs == 0 && numOfElves != 0) {
			System.out.println("Elves won!");
			return true;
		} else
			return false;
	}

	/**
	 * Method: checkDraw
	 * Features:
	 * -check if the conditions for a draw have been met
	 */
	public boolean checkDraw() {
		if (numOfOrcs == 0 && numOfElves == 0) {
			System.out.println("Draw!");
			return true;
		} else
			return false;
	}

	/**
	 * Method: move
	 * Features:
	 * -move the entity and display the debug message when conditions are met
	 * -when conditions are not move, display the according debug message
	 */
	public void move(int r, int c) {
		Location newLocation = null;
		if (aWorld[r][c].getIfMove() == true) {
			newLocation = moveEntity(r, c);
			if (GameStatus.debugModeOn == true) {
				System.out.printf("The Entity %c at (r/c): (%d/%d) %n",
						aWorld[newLocation.getRow()][newLocation.getColumn()].getAppearance(), r, c);
				System.out.printf("moves to location (r/c): (%d/%d) %n", newLocation.getRow(), newLocation.getColumn());
				System.out.println("");
			}
			aWorld[newLocation.getRow()][newLocation.getColumn()].setIfHasMoved(true);
		} else {
			if (GameStatus.debugModeOn == true)
				notMovingDebugMessage(r, c);
		}
	}

	/**
	 * Method: notMovingDebugMessage
	 * Features:
	 * -displaying different debug message according to conditions
	 */
	public void notMovingDebugMessage(int r, int c) {
		if (aWorld[r][c].getIfAttack() == true) {
			System.out.printf("The Entity %c @ (r/c): (%d/%d)%n", aWorld[r][c].getAppearance(), r, c);
			System.out.printf("cannot move because of %d enemies(enemy) @ (r/c): ", aWorld[r][c].getNumOfEnemy());
			aWorld[r][c].printEnemyList();
			System.out.println("");
		} else if (detectObstacle(r, c) == true) {
			System.out.printf("The Entity %c @ (r/c): (%d/%d)%n", aWorld[r][c].getAppearance(), r, c);
			if (aWorld[r][c].getAppearance() == Entity.ORC)
				System.out.printf("cannot move because of an obstacle @ (r/c): (%d/%d)%n", r + 1, c + 1);
			else
				System.out.printf("cannot move because of an obstacle @ (r/c): (%d/%d)%n", r - 1, c - 1);
			System.out.println("");
		} else {
			System.out.printf("The Entity %c at (r/c): (%d/%d)%n", aWorld[r][c].getAppearance(), r, c);
			System.out.printf("cannot move because it has reached the boundary.");
			System.out.println("");
		}
	}

	/**
	 * Method: attack
	 * Features:
	 * -attack the recorded enemy when conditions are met
	 * -display debug attacking message
	 */
	public void attack(int r, int c) {
		int enemyR = -1, enemyC = -1, tempHP = -1;
		enemyR = aWorld[r][c].getEnemyRow(0);
		enemyC = aWorld[r][c].getEnemyColumn(0);
		tempHP = aWorld[enemyR][enemyC].getHitPoints();
		aWorld[enemyR][enemyC].beAttacked(aWorld[r][c]);
		ifPeaceful = false;
		if (GameStatus.debugModeOn == true) {
			System.out.printf("Entity %c @ (r/c) (%d/%d) attacking for %d damage%n", aWorld[r][c].getAppearance(), r, c,
					aWorld[r][c].getDamage());
			System.out.printf("Opponent %c with (hp): %d @ (r/c): (%d/%d) Damage = %d new hp: %d%n",
					aWorld[enemyR][enemyC].getAppearance(), tempHP, enemyR, enemyC, aWorld[r][c].getDamage(),
					aWorld[enemyR][enemyC].getHitPoints());
			System.out.println("");
		}
		if (aWorld[enemyR][enemyC].getHitPoints() <= 0) {
			if (GameStatus.debugModeOn == true) {
				System.out.printf("Opponent %c @ (r/c): (%d/%d) became unconscious%n",
						aWorld[enemyR][enemyC].getAppearance(), enemyR, enemyC);
				System.out.println("");
			}
			removeEntity(enemyR, enemyC);
		}
	}

	/**
	 * Method: removeEntity
	 * Features:
	 * -remove an unconscious entity from the map
	 */
	public void removeEntity(int r, int c) {
		aWorld[r][c] = null;
		clearLocations();
		updateLocations();
	}

	/**
	 * Method: checkIfMove
	 * Features:
	 * -check the conditions and determine if move or not this turn
	 */
	public void checkIfMove(int r, int c, boolean ifEnemy) {
		if (detectObstacle(r, c) == false && checkBoundary(r, c) == false && ifEnemy == false) {
			aWorld[r][c].setIfMove(true);
		} else {
			aWorld[r][c].setIfMove(false);
		}
	}

	/**
	 * Method: detectEnemy
	 * Features:
	 * -detect if there are adjacent enemies to determine if attack or not
	 */
	public boolean detectEnemy(int r, int c) {
		if (aWorld[r][c].getAppearance() == Entity.ELF) {
			if (detectOrcs(r, c) == true) {
				aWorld[r][c].setIfAttack(true);
				return true;
			} else {
				aWorld[r][c].setIfAttack(false);
				return false;
			}
		} else {
			if (detectElves(r, c) == true) {
				aWorld[r][c].setIfAttack(true);
				;
				return true;
			} else {
				aWorld[r][c].setIfAttack(false);
				return false;
			}
		}
	}

	/**
	 * Method: detectOrcs
	 * Features:
	 * -detect if there are adjacent Orcs
	 * -record the locations of adjacent Orcs
	 */
	public boolean detectOrcs(int r, int c) {
		int i;
		aWorld[r][c].clearEnemyList();
		for (i = 0; i < numOfOrcs; i++) {
			if (orcsLocations[i].getRow() >= r - 1 && orcsLocations[i].getRow() <= r + 1) {
				if (orcsLocations[i].getColumn() >= c - 1 && orcsLocations[i].getColumn() <= c + 1) {
					aWorld[r][c].addToEnemyList(orcsLocations[i].getRow(), orcsLocations[i].getColumn());
				}
			}
		}
		if (aWorld[r][c].getNumOfEnemy() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method: detectElves
	 * Features: 
	 * -detect if there are adjacent Elves
	 * -record the locations of the adjacent Elves
	 */
	public boolean detectElves(int r, int c) {
		int i;
		aWorld[r][c].clearEnemyList();
		for (i = 0; i < numOfElves; i++) {
			if (elvesLocations[i].getRow() >= r - 1 && elvesLocations[i].getRow() <= r + 1) {
				if (elvesLocations[i].getColumn() >= c - 1 && elvesLocations[i].getColumn() <= c + 1) {
					aWorld[r][c].addToEnemyList(elvesLocations[i].getRow(), elvesLocations[i].getColumn());
				}
			}
		}
		if (aWorld[r][c].getNumOfEnemy() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method: moveEntity
	 * Features:
	 * -move an entity diagonally
	 * -update the Location arrays
	 * -return the new location of the entity
	 */
	public Location moveEntity(int r, int c) {
		if (aWorld[r][c].getAppearance() == Entity.ORC) {
			aWorld[r + 1][c + 1] = aWorld[r][c];
			aWorld[r][c] = null;
			clearLocations();
			updateLocations();
			Location newLocation = new Location(r + 1, c + 1);
			return newLocation;
		} else {
			aWorld[r - 1][c - 1] = aWorld[r][c];
			aWorld[r][c] = null;
			clearLocations();
			updateLocations();
			Location newLocation = new Location(r - 1, c - 1);
			return newLocation;
		}
	}

	/**
	 * Method: detectObstacle
	 * Features:
	 * -check if there is an obstacle in the entity's way
	 */
	public boolean detectObstacle(int r, int c) {
		if (aWorld[r][c].getAppearance() == Entity.ORC) {
			if (r + 1 < SIZE && c + 1 < SIZE && aWorld[r + 1][c + 1] != null)
				return true;
			else
				return false;
		} else {
			if (r - 1 >= 0 && c - 1 >= 0 && aWorld[r - 1][c - 1] != null)
				return true;
			else
				return false;
		}
	}

	/**
	 * Method: checkBoundary
	 * Features: check if the entity has reached the boundary
	 */
	public boolean checkBoundary(int r, int c) {
		if (aWorld[r][c].getAppearance() == Entity.ELF) {
			if (r == 0 || c == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (r == 9 || c == 9) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Method: clearLocations
	 * Features:
	 * -clear the locations stored in the Location array
	 */
	public void clearLocations() {
		int i;
		for (i = 0; i < numOfElves; i++) {
			elvesLocations[i] = null;
		}
		for (i = 0; i < numOfOrcs; i++) {
			orcsLocations[i] = null;
		}
		numOfElves = 0;
		numOfOrcs = 0;
	}

	/**
	 * Method: resetIfHasMoved
	 * Features:
	 * -reset ifHasMoved flag of every entity to false before looping through them
	 */
	public void resetIfHasMoved() {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				if (aWorld[r][c] != null) {
					aWorld[r][c].setIfHasMoved(false);
				}
			}
		}
	}
}
	
	
