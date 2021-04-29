/*
 * Author: Sipeng He
 * 
 * Version:2021/2/11
 * Features:(1) Creating an array, initializing all the elements with a start value of -1
 *          (2) Display the main menu
 *          (3) Get user selection
 *          (4) Run until the user selects the option to quit the program
 *          (5) Display the contents of the list
 *          (6) Assign a fixed grade of 2.0 to all array elements
 *          (7) Assign grades to the first 10 list element randomly
 *          (8) Calculate and display the average grade
 *          (9) Determine and display the highest grade in the list
 *          (10) Determine and display the lowest grade in the list
 * Limitations:(1) Users cannot input grades manually
 *             (2) The maximum number of elements is 40
 */

/*
 * Class: Manager
 * Features: (1) assign random grades between 0 and 4 to the first 10 elements
 *           (2) assign the fixed grade of 2.0 to all the elements
 *           (3) find the lowest grade stored in the list and display it
 *           (4) find the highest grade stored in the list and display it
 *           (5) calculate the average of the grades in the list
 *           (6) display all the grades that stored in the list
 */
import java.util.Random;

public class Manager {
	public static final int MAXIMUM = 40;
	private double[] grades;
	private int occupiedElemNum; // record the number of elements that have been occupied

	public Manager() { // constructor
		grades = new double[MAXIMUM];
		for (int i = 0; i < MAXIMUM; i++) {
			grades[i] = -1;
			occupiedElemNum = 0;
		}
	}

	/*
	 * Method: randomList
	 * Feature: (1) assign random grades between 0 and 4 to the first 10 elements
	 */
	public void randomList() {
		Random generator = new Random();
		for (int i = 0; i < 10; i++) {
			grades[i] = ((double) generator.nextInt(41)) / 10;
			if (occupiedElemNum < 10)
				occupiedElemNum = 10;
		}
		System.out.println("Random grades have been assigned to first 10 elements.");
	}

	/*
	 * Method: assignFixedGrade
	 * Feature: (1) assign the fixed grade of 2.0 to all the elements
	 */
	public void assignFixedGrade() {
		for (int i = 0; i < MAXIMUM; i++) {
			grades[i] = 2.0;
			occupiedElemNum = MAXIMUM;
		}
		System.out.println("The fixed grade of 2.0 has been assigned to all the elements.");
	}

	/*
	 * Method: lowestGrade
	 * Feature:(1) find the lowest grade stored in the list and display it
	 *         (2) remind the user if the list is empty
	 */
	public void lowestGrade() {
		int lowestTemp = 0;
		for (int i = 0; i < occupiedElemNum; i++) {
			if (grades[i] < grades[lowestTemp])
				lowestTemp = i;
		}
		if (occupiedElemNum == 0)
			System.out.println("No grade stored in the list.");
		else {
			System.out.printf("The lowest grade in the list is: %.1f", grades[lowestTemp]);
			System.out.println("");
		}
	}

	/*
	 * Method: highestGrade
	 * Feature: (1) find the highest grade stored in the list and display it
	 *          (2) remind user if the list is empty
	 */
	public void highestGrade() {
		int highestTemp = 0;
		for (int i = 0; i < occupiedElemNum; i++) {
			if (grades[i] > grades[highestTemp])
				highestTemp = i;
		}
		if (occupiedElemNum == 0)
			System.out.println("No grade stored in the list.");
		else {
			System.out.printf("The highest grade in the list is: %.1f", grades[highestTemp]);
			System.out.println("");
		}
	}

	/*
	 * Method: average
	 * Feature: (1) calculate the average of the grades in the list
	 *          (2) remind user if the list is empty
	 */
	public void average() {
		double total = 0;
		double average = -1;
		for (int i = 0; i < occupiedElemNum; i++) {
			total = total + grades[i];
		}
		if (total == 0)
			System.out.println("No grade stored in the list.");
		else {
			average = total / occupiedElemNum;
			System.out.printf("The average grade is: %.1f", average);
			System.out.println("");
		}
	}

	/*
	 * Method: display
	 * Feature: (1) display all the grades that stored in the list
	 *          (2) remind user if the list is empty
	 */
	public void display() {
		if (occupiedElemNum == 0) {
			System.out.println("No grade stored in the list.");
		} else {
			System.out.println("The grades in the list: ");
			for (int i = 0; i < occupiedElemNum; i++) {
				System.out.printf("%.1f ", grades[i]);
			}
			System.out.println("");
		}
	}
}
