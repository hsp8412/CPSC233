/* Author: Sipeng He
 * Class: Defender
 * Feature: -generate defense according to probabilities and return the type of it
 *               -count the occurrence of different types of attack
 *               -call the result of one attack, and count the occurrence of different results
 *               -create a summary of the whole kombat
 *               -reset the probabilities of different types of defense every 5 rounds according to the attack records
 * Limitation: -the probabilities of different types of defense have to be integers
                      -the sum of proportions in the summary may possibly be 99.99% or 100.01%
 * Version: 2021/2/5
 * */
import java.util.Random;

public class Defender {
	private int defHighPr, defLowPr, defMediumPr, DefPrTotal;
	private int countAttHigh = 0, countAttMedium = 0, countAttLow = 0;
	private int countDefHigh = 0, countDefMedium = 0, countDefLow = 0;
	private int countHit = 0, countBlock = 0;

	public Defender(int inputDefHighPr, int inputDefMediumPr, int inputDefLowPr) { // constructor
		defHighPr = inputDefHighPr;
		defLowPr = inputDefLowPr;
		defMediumPr = inputDefMediumPr;
		DefPrTotal = defLowPr + defMediumPr + defHighPr;
	}

	/* Method: defend 
	 * Feature: -generate a certain type of defense according to probabilities 
	 *               -count different types of defense 
	 *               -print the defense type on the screen 
	 *               -return the type of defense 
	 * Limitation: -The probabilities and their sum have to be integers
	 */
	public int defend() {
		int i;
		Random generator = new Random();
		i = generator.nextInt(DefPrTotal);
		if (i < defLowPr) {
			System.out.print("Defend: Low      ");
			countDefLow++;
			return Manager.LOW;
		} else if (defLowPr <= i && i < defLowPr + defMediumPr) {
			System.out.print("Defend: Medium   ");
			countDefMedium++;
			return Manager.MEDIUM;
		} else {
			System.out.print("Defend: High     ");
			countDefHigh++;
			return Manager.HIGH;
		}
	}

	/* Method: countAtt 
	 * Feature: -count the occurrence of different types of attack
	 * Limitation: not found
	 */
	public void countAtt(int attType) {
		if (attType == Manager.LOW)
			countAttLow++;
		else if (attType == Manager.MEDIUM)
			countAttMedium++;
		else
			countAttHigh++;
	}

	/* Method: callResult 
	 * Feature: -call the result of an attack(Block or Hit)
	 *               -print the result on the screen 
	 *               -count the occurrence of Block and Hit
	 * Limitation: not found
	 */
	public void callResult(int attType, int DefType) {
		if (attType == DefType) {
			System.out.println("Block!");
			countBlock++;
		} else {
			System.out.println("Hit!");
			countHit++;
		}
	}

	/* Method: createSummary 
	 * Features: -print the total number of hits and blocks
	 *                 -print the proportions of different types of attack and defense 
	 * Limitations: -the proportions may not add up to exactly 100.00% 
	 *                     -the sum of proportions may possibly be 99.99% or 100.01%
	 */
	public void createSummary(int numOfRounds) { // make a summary of the whole kombat
		System.out.println("Summary");
		System.out.print("Total hits: " + countHit + "   ");
		System.out.println("Total blocks: " + countBlock);
		System.out.print("Attacker proportions: ");
		System.out.printf("Low: " + "%.2f%%" + "  ", ((double) countAttLow / numOfRounds) * 100);
		System.out.printf("Medium: " + "%.2f%%" + "  ", ((double) countAttMedium / numOfRounds) * 100);
		System.out.printf("High: " + "%.2f%%" + "%n", ((double) countAttHigh / numOfRounds) * 100);
		System.out.print("Defender proportions: ");
		System.out.printf("Low: " + "%.2f%%" + "  ", ((double) countDefLow / numOfRounds) * 100);
		System.out.printf("Medium: " + "%.2f%%" + "  ", ((double) countDefMedium / numOfRounds) * 100);
		System.out.printf("High: " + "%.2f%%" + "%n", ((double) countDefHigh / numOfRounds) * 100);
	}

	/* Method: setDef 
	 * Feature: -reset defense probabilities according to occurrence of different types of attacks 
	 *               -print a message on the screen to inform users of the adjustment
	 * Limitations: not found
	 */
	public void setDef() {
		defLowPr = countAttLow;
		defMediumPr = countAttMedium;
		defHighPr = countAttHigh;
		DefPrTotal = defLowPr + defMediumPr + defHighPr;
		System.out.println("Defender is adjusting...");
	}
}
