/*
 * Name: Sipeng He
 * UCID: 30113342
 * Tutorial: T03
 * 
 * Version: April 8th, 2021
 * -building up the GUI, including the title, label, PasswordField and Button
 * -setting the size, closing mode of the JFrame
 * -setting the size of the label and the login button
 * 
 * Version: April 9th, 2021
 * -adding an actionListener to the PasswordField(The class itself implements ActionListener)
 * -adding an actionListener to the Login button(inner class)
 * 
 * Version: April 10th, 2021
 * -login feature: the main window for shopping will be opened and the dialog will be closed if the correct password is typed in
 * -login feature: the program will exit if the number of attempts exceeds the limitation
 * 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class MyDialog extends JDialog implements ActionListener {
	public static final int X_DIALOG = 100;
	public static final int Y_DIALOG = 200;
	public static final int HEIGHT_DIALOG = 180;
	public static final int WIDTH_DIALOG = 300;

	public static final int X_LABEl = 50;
	public static final int Y_LABEL = 20;
	public static final int HEIGHT_LABEL = 20;
	public static final int WIDTH_LABEL = 120;

	public static final int X_PASSWORDFIELD = 50;
	public static final int Y_PASSWORDFIELD = 40;
	public static final int HEIGHT_PASSWORDFIELD = 20;
	public static final int WIDTH_PASSWORDFIELD = 120;

	public static final int X_BUTTON = 50;
	public static final int Y_BUTTON = 70;
	public static final int HEIGHT_BUTTON = 30;
	public static final int WIDTH_BUTTON = 100;

	public static final int TIME_DELAY = 1500;
	public static final String DEFAULT_TITLE = "Login";
	public static final String FAILED_LOGIN_TITLE = "No. incorrect login attempts(Max=";
	public static final String LOGIN_FAILED_EXIT = "Max attempts exceeded, exiting...";
	public static final int MAX_ATTEMPTS = 3;

	private int numOfFailedLogin;
	private JPasswordField aPasswordField;
	private JLabel aLabel;
	private JButton aButton;
	private PasswordReader pr;
	private LoginButtonListener aListener;

	private class LoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String password = getPassword();
			String input = getInput();
			if (input.equals(password)) {
				loginSuccessfully();
			} else {
				loginFailed();
			}
		}
	}

	public MyDialog() {
		numOfFailedLogin = 0;
		setBounds(X_DIALOG, Y_DIALOG, WIDTH_DIALOG, HEIGHT_DIALOG);
		setTitle(DEFAULT_TITLE);
		aLabel = new JLabel("Enter Password");
		aButton = new JButton("Login");
		aPasswordField = new JPasswordField();
		aListener = new LoginButtonListener();
		pr = new PasswordReader();
		aLabel.setBounds(X_LABEl, Y_LABEL, WIDTH_LABEL, HEIGHT_LABEL);
		aButton.setBounds(X_BUTTON, Y_BUTTON, WIDTH_BUTTON, HEIGHT_BUTTON);
		aPasswordField.setBounds(X_PASSWORDFIELD, Y_PASSWORDFIELD, WIDTH_PASSWORDFIELD, HEIGHT_PASSWORDFIELD);
		aPasswordField.addActionListener(this);
		aButton.addActionListener(aListener);
		add(aLabel);
		add(aButton);
		add(aPasswordField);
		setLayout(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String password = getPassword();
		if (getInput().equals(password)) {
			loginSuccessfully();
		} else {
			loginFailed();
		}
	}

	public String getInput() {
		String input;
		input = new String(aPasswordField.getPassword());
		return input;
	}

	public void loginSuccessfully() {
		System.out.println("Login successful");
		System.out.println("");
		MyFrame aFrame = new MyFrame();
		this.dispose();
	}

	public void loginFailed() {
		System.out.println("Login Failed: Wrong Password");
		System.out.println("");
		numOfFailedLogin++;
		if (numOfFailedLogin < MAX_ATTEMPTS) {
			setTitle(FAILED_LOGIN_TITLE + MAX_ATTEMPTS + "): " + numOfFailedLogin);
		} else {
			System.out.println("Program Exits");
			setTitle(LOGIN_FAILED_EXIT);
			pause();
			this.dispose();
                                                System.exit(0);
		}
	}

	public void pause() {
		try {
			Thread.sleep(TIME_DELAY);
		} catch (InterruptedException ex) {
			System.out.println("Pausing of program was interrupted");
		}
	}

	public String getPassword() {
		return pr.getPassword();
	}
}
