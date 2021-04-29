/*
 * Name: Sipeng He
 * 
 * Program: Simple Shopping Program
 * Features:
 * -Create a JDialog for login
 * -A JPasswordField is added to the JDialog for users to type in the password
 * -The program will check if the input password is correct when clicking the login button or hit enter
 * -The correct password encrypted by Caesar cipher is stored in a file named "password.txt"
 * -The program can read the encrypted password from "password.txt" and decrypt it before making the comparison with the input password
 * -If the input password is incorrect, messages will appear at the title of the JDialog, informing the user about the maximum number of attemps allowed
 * -The program will end if the number of failed login attempts exceeds the limitation(3)
 * -When exiting, a message appears at the title bar of the JDialog
 * -The login dialog will be disposed and the main shopping window(JFrame) will appear once successfully logged in
 * -A company logo is put in a JLabel and is added to the shopping window
 * -JLabels, JTextField and JTextArea for the name and address input are added to the window
 * -The save button and clear button with imageIcons are added
 * -When the save button is clicked, a file named "order.txt" will be created, which contains the current name and address input information
 * -If the "order.txt" file already exists, the program will rewrite it
 * -When the clear button is clicked, the JTextField and JTextArea for name and address will both become empty 
 * -Messages will appear on the title of the JFrame when saving or clearing information
 * 
 * Limitations:
 * -The main shopping window cannot read from files
 * -The encrypted password stored in password.txt should only contain lower case letters
 * -The main shopping window cannot read from order.txt
 */
public class Driver {
	public static void main(String[] args) {
		MyDialog aDialog = new MyDialog();
	}
}
