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
 * Class: UserInterface
 * Features:(1) display the main menu
 *          (2) prompt the user for an option from the menu
 *          (3) determine what the user's option is and execute
 *          (4) give a value of true to the ifQuit if user choose to quit
 *          (5) get the value of ifQuit
 */
import java.util.Scanner;

public class UserInterface {
	private Manager aManager;
	private boolean ifQuit; // indicate if the user choose to quit or not

	public UserInterface() { // constructor
		aManager = new Manager();
		ifQuit = false;
	}

	/*
	 * Method: displayOptions
	 * Feature: (1) display the main menu
	 */
	public void displayOptions() {
		System.out.println("Welcome to Grade Manager.");
		System.out.println("Please select one option from the menu:");
		System.out.println("D - Display the list of grades");
		System.out.println("F - Assign a fixed grade of 2.0 to all array elements");
		System.out.println("R - Assign grades to the first 10 list elements randomly");
		System.out.println("A - Calculate and display the average grade");
		System.out.println("H - Determine and display the highest grade in the list");
		System.out.println("L - Determine and display the lowest grade in the list");
		System.out.println("Q - Quit");
	}
	
	/*
	 * Method: promptOption
	 * Feature: (1) prompt the user for an option from the menu
	 *          (2) get the user's input of option and return it
	 * Limitation: (1) Only capital letters are valid input
	 */
	public char promptOption() {
		System.out.print("Select an Option: ");
		char option;
		String temp;
		Scanner in = new Scanner(System.in);
		temp = in.nextLine();
		option = temp.charAt(0);
		return option;
	}
	
	/*
	 * Method: executeOption
	 * Feature: (1) determine what the user's option is and execute
	 *          (2) remind the user if the option input is invalid
	 */
	public void executeOption(char option) {
		switch (option) {
		case 'D':
			aManager.display();
			break;
		case 'F':
			aManager.assignFixedGrade();
			break;
		case 'R':
			aManager.randomList();
			break;
		case 'A':
			aManager.average();
			break;
		case 'H':
			aManager.highestGrade();
			break;
		case 'L':
			aManager.lowestGrade();
			break;
		case 'Q':
			setQuit();
			break;
		default:
			System.out.println("Invalid input, please try again.");
		}
	}
	
	/*
	 * Method: setQuit
	 * Feature:(1) give a value of true to the ifQuit if user choose to quit
	 */
	public void setQuit() {
		ifQuit = true;
	}
	
	/*
	 * Method: getIfQuit
	 * Feature:(1) return the value of boolean ifQuit to determine if the loops continue
	 */
	public boolean getIfQuit() {
		return ifQuit;
	}
}
