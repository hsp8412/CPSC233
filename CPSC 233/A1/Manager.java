/* Author: Sipeng He
 * Program: Fighting Simulator
 * Feature: -prompt users for number of rounds
 *               -prompt users for the probabilities of different types of attack
 *               -run the number of rounds set by the user
 *               -generate attack and defend according to the input of possibilities, display the result
 *               -create a summary of the whole fighting at the end
 * Limitation: -number of rounds has to be between 1 and 100, or default value(10) will be set
 *                   -probabilities of different types of attack have to add up to 100, or default value(equal probability for different types of attack) will be set
 *                   -probabilities input should be integers only
 *                   -probabilities of different types of defense can not be set manually by users
 * Version: 2021/2/5
 * */
import java.util.Scanner;

public class Manager {
	public final static int HIGH = 2, MEDIUM = 1, LOW = 0;

	public static void main(String args[]) {
		int inputAttHighPr = 0, inputAttMediumPr = 0, inputAttLowPr = 0;
		int numOfRounds = 0, i = 0;
		int attType = -1, defType = -1;
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to Kombat Simulator!");
		System.out.print("Enter the number of rounds: ");
		numOfRounds = in.nextInt();
		if (numOfRounds > 100 || numOfRounds < 1)
			numOfRounds = 10; // default number of rounds(10) is set if user input is invalid
		System.out.println("Enter the percentages(integer) for three different types of attack, catogorized by height.");
		System.out.println("The sum of three percentages must be 100%.");
		System.out.print("Enter the precentage of attack that will be aimed low: ");
		inputAttLowPr = in.nextInt();
		System.out.print("Enter the precentage of attack that will be aimed at medium height: ");
		inputAttMediumPr = in.nextInt();
		System.out.print("Enter the precentage of attack that will be aimed high: ");
		inputAttHighPr = in.nextInt();
		System.out.println();
		System.out.println("Kombat begins!");
		if (inputAttHighPr + inputAttLowPr + inputAttMediumPr != 100) { // default if input is invalid
			inputAttHighPr = 1;
			inputAttLowPr = 1;
			inputAttMediumPr = 1;
		}
		Attacker anAttacker = new Attacker(inputAttHighPr, inputAttMediumPr, inputAttLowPr);
		Defender aDefender = new Defender(1, 1, 1); // equal probabilities of defense for the first 5 rounds
		for (i = 0; i < numOfRounds; i++) { // loop control of rounds
			if (i != 0 && i % 5 == 0)
				aDefender.setDef(); // reset probabilities of different types of attack every 5 rounds
			System.out.print("Round " + (i + 1) + "...   ");
			attType = anAttacker.attack(); // generate an attack and return the type of it
			defType = aDefender.defend(); // generate a defense and return the type of it
			aDefender.countAtt(attType); // defender record the occurrence of different types of attack
			aDefender.callResult(attType, defType); // defender report the result of the attack
		}
		System.out.println("");
		aDefender.createSummary(numOfRounds); // defender generate a summary for the whole kombat
	}
}
