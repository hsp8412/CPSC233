/*
 * Name: Sipeng He
 * 
 * Version: April 10th, 2021
 * -the image of the company logo is put in a JLabel and added to the JFrame
 * -components are set up, including the JLabels, JButtons, JTextArea and JTextField
 * -imageIcons are added to the JButtons
 * -setting the size, default close operation and the title of the JFrame
 * 
 * Version: April 11th, 2021
 * -gridBagLayout is employed
 * -components are added to the JFrame
 * 
 *  Version: April 12th, 2021
 *  -ActionListeners for the save button and clear button are added as inner classes
 *  -implementing the feature of saving information
 *  -implementing the feature of clearing information 
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyFrame extends JFrame {
	public static final int HEIGHT = 400;
	public static final int WIDTH = 500;
	public static final String DEFAULT_TITLE = "Order Information";
	private JLabel label1;
	private JLabel label2;
	private JLabel logoLabel;
	private JTextField aTextField;
	private JTextArea aTextArea;
	private JScrollPane aScrollPane;
	private JButton saveButton;
	private JButton clearButton;
	private SaveButtonListener saveButtonListener;
	private ClearButtonListener clearButtonListener;
	private GridBagLayout aLayout;
	GridBagConstraints aConstraint;

	private class SaveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			saveOrder();
		}
	}

	private class ClearButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clearText();
		}
	}

	public MyFrame() {
		setSize(WIDTH, HEIGHT);
		setTitle(DEFAULT_TITLE);
		buildLogo();
		buildLabels();
		buildTextFrames();
		buildButtons();
		aConstraint = new GridBagConstraints();
		aLayout = new GridBagLayout();
		addWidgets();
		setLayout(aLayout);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void buildLogo() {
		Icon logoIcon = new ImageIcon("logo.jpg");
		logoLabel = new JLabel();
		logoLabel.setIcon(logoIcon);
	}

	public void buildLabels() {
		label1 = new JLabel("Name");
		label2 = new JLabel("Address");
	}

	public void buildTextFrames() {
		aTextField = new JTextField();
		aTextArea = new JTextArea();
		aScrollPane = new JScrollPane(aTextArea);
	}

	public void buildButtons() {
		ImageIcon saveIcon = new ImageIcon("save.png");
		ImageIcon clearIcon = new ImageIcon("clear.png");
		saveButton = new JButton("Save", saveIcon);
		clearButton = new JButton("Clear", clearIcon);
		saveButtonListener = new SaveButtonListener();
		clearButtonListener = new ClearButtonListener();
		saveButton.addActionListener(saveButtonListener);
		clearButton.addActionListener(clearButtonListener);
	}

	public void addWidgets() {
		addLogo();
		addLabel1();
		addLabel2();
		addTextField();
		addTextArea();
		addSaveButton();
		addClearButton();
	}

	public void addLogo() {
		aConstraint.gridx = 0;
		aConstraint.gridy = 0;
		aConstraint.gridwidth = 2;
		aConstraint.gridheight = 1;
		aLayout.setConstraints(logoLabel, aConstraint);
		add(logoLabel);
	}

	public void addLabel1() {
		aConstraint.gridx = 0;
		aConstraint.gridy = 1;
		aConstraint.gridwidth = 1;
		aConstraint.gridheight = 1;
		aConstraint.weightx = 1;
		aConstraint.insets = new Insets(20, 20, 0, 0);
		aConstraint.anchor = GridBagConstraints.WEST;
		aLayout.setConstraints(label1, aConstraint);
		add(label1);
	}

	public void addLabel2() {
		aConstraint.gridx = 1;
		aConstraint.gridy = 1;
		aConstraint.gridwidth = 1;
		aConstraint.gridheight = 1;
		aLayout.setConstraints(label2, aConstraint);
		add(label2);
	}

	public void addTextField() {
		aConstraint.gridx = 0;
		aConstraint.gridy = 2;
		aConstraint.gridwidth = 1;
		aConstraint.gridheight = 4;
		aConstraint.weightx = 0;
		aConstraint.anchor = GridBagConstraints.NORTH;
		aConstraint.fill = GridBagConstraints.HORIZONTAL;
		aConstraint.insets = new Insets(5, 20, 0, 20);
		aLayout.setConstraints(aTextField, aConstraint);
		add(aTextField);
	}

	public void addTextArea() {
		aConstraint.gridx = 1;
		aConstraint.gridy = 2;
		aConstraint.gridwidth = 1;
		aConstraint.gridheight = 4;
		aConstraint.weighty = 1;
		aConstraint.fill = GridBagConstraints.BOTH;
		aLayout.setConstraints(aScrollPane, aConstraint);
		add(aScrollPane);
	}

	public void addSaveButton() {
		aConstraint.gridx = 0;
		aConstraint.gridy = 6;
		aConstraint.gridwidth = 1;
		aConstraint.gridheight = 1;
		aConstraint.weighty = 0.1;
		aConstraint.insets = new Insets(20, 20, 10, 0);
		aConstraint.fill = GridBagConstraints.NONE;
		aConstraint.anchor = GridBagConstraints.NORTHWEST;
		aLayout.setConstraints(saveButton, aConstraint);
		add(saveButton);
	}

	public void addClearButton() {
		aConstraint.gridx = 1;
		aConstraint.gridy = 6;
		aConstraint.gridwidth = 1;
		aConstraint.gridheight = 1;
		aLayout.setConstraints(clearButton, aConstraint);
		add(clearButton);
	}

	public void clearText() {
		setTitle("Clearing information...");
		aTextField.setText("");
		aTextArea.setText("");
		pause();
		setTitle(DEFAULT_TITLE);
		System.out.println("Order information has been cleared");
		System.out.println("");
	}

	public void saveOrder() {
		String address, name;
		setTitle("Saving information...");
		FileWriter fw = null;
		PrintWriter pw = null;
		name = aTextField.getText();
		System.out.println("Name: " + name);
		address = aTextArea.getText();
		System.out.println("Address: ");
		System.out.println(address);
		try {
			fw = new FileWriter("order.txt");
			pw = new PrintWriter(fw);
			pw.println(name);
			pw.println(address);
			fw.close();
			pause();
			setTitle(DEFAULT_TITLE);
			System.out.println("Order information has been saved");
			System.out.println("");
		} catch (IOException exception) {
			System.out.println("Trouble writing the File");
		}
	}

	public void pause() {
		try {
			Thread.sleep(MyDialog.TIME_DELAY);
		} catch (InterruptedException ex) {
			System.out.println("Pausing of program was interrupted");
		}
	}
}
