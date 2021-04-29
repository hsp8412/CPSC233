/*
 * Name: Sipeng He
 * 
 * Program: "The Game of Life" simulation
 * Features:
 * -Read the input file and initialize the game according to it
 * -Display the 3 states of the game world every turn: previous generation, current and the situation after tamination(if a taminator exists)
 * -Display the states with bounding lines, row and column number and array labels
 * -Critters born, die or remain alive according to the number of neighbours they have
 * -If a taminator exists, it detects and taminates(removes) its neighbours every turn, after the birth and death rules have been applied
 * -If a taminator exists, it teleports to another randomly generated location(empty cell) every turn after taminating its neighbours
 * -A prosperous mode that has different birth and death rules from regular mode
 * -A debug mode to track the action of critters and taminator
 * 
 * Limitations:
 * -Critters at the edges will not consider critters at the edge of opposite side of the world as neighbours
 * -That is to say, the maximum number of neighbours a critter at edges can have is 5, and the number for critters at the corner is 3
 * -Only one taminator should exist in the game world(input file), and it cannot die or spawn
 * -The game won't stop even if the board becomes empty before the user choose the quit option
 * 
 * Version: March 30, 2021
 * -Prompt the user to ask whether to start the game with the regular mode or the prosperous mode
 * 
 * Version: March 31, 2021
 * -Prompt the user to ask whether to toggle on the debug mode at the very beginning of the game
 */
import java.util.Scanner;

/* No additional methods and nor attributes to be added. */

public class GameOfLife {
	public static void main(String[] args) {
		// Start of code written by James Tam, students can freely modify (but still
		// need to
		// fulfill assignment requirements and stylistic approaches).
		String input;
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to the Game of Life!");
		System.out.println("Please select one mode to start the game:");
		System.out.println("-R/r: Regular Mode");
		System.out.println("-P/p: Prosperous Mode");
		while (true) {
			System.out.print(">>");
			input = in.nextLine();
			if (input.equals("R") || input.equals("r") || input.equals("P") || input.equals("p"))
				break;
			System.out.println("Invalid input, please try again.");
		}
		if (input.equals("R") || input.equals("r")) {
			Biosphere regular;
			regular = new Biosphere(FileInitialization.read());
			regular.runTurn();
		} else {
			ProsperousBiosphere prosperous;
			prosperous = new ProsperousBiosphere(FileInitialization.read());
			prosperous.runTurn();
		}
	}
}
