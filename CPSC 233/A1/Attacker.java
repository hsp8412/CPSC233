/* Author: Sipeng He
 * Class: Attacker
 * Feature: -generate different types of attacks according to the probabilities
 * Limitation: -probabilities of different types of attack can not be reset during before the program stops
                      -probabilities have to be integers
 * Version: 2021/2/5
 * */
import java.util.Random;

public class Attacker {
	private int attHighPr, attMediumPr, attLowPr, attPrTotal; 

	public Attacker(int inputAttHighPr, int inputAttMediumPr, int inputAttLowPr) { // constructor
		attHighPr = inputAttHighPr;
		attMediumPr = inputAttMediumPr;
		attLowPr = inputAttLowPr;
		attPrTotal = attHighPr + attMediumPr + attLowPr;
	}

	/* Method：attack
	 * Feature: -generate a certain type of attack according to the probabilities
	 *               -print the type of attack on the screen
	 *               -return the type of attack
	 * Limitation: -probabilities and their sum have to be integers
	 */
	public int attack() { 
		int i;
		Random generator = new Random();
		i = generator.nextInt(attPrTotal);
		if (i < attLowPr) {
			System.out.print("Attack: Low      ");
			return Manager.LOW;
		} else if (attLowPr <= i && i < attLowPr + attMediumPr) {
			System.out.print("Attack: Medium   ");
			return Manager.MEDIUM;
		} else {
			System.out.print("Attack: High     ");
			return Manager.HIGH;
		}
	}
}
