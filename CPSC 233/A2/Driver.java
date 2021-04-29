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
 * Class: Driver
 * Features: (1)initialize a user interface
 *           (2)loop control, quit the loop when user choose the quit option
 *           (3)get the user's option input and pass it to executeOption to execute it
 */
public class Driver {
	public static void main(String args[]) {
		UserInterface aUserInterface = new UserInterface();
		while (aUserInterface.getIfQuit() == false) {
			char Option;
			aUserInterface.displayOptions();
			Option = aUserInterface.promptOption();
			aUserInterface.executeOption(Option);
			System.out.println("");
		}
	}
}
