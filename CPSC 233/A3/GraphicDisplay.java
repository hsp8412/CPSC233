/*
 * Author: Sipeng He
 * 
 * Version: March 10, 2021
 * -Modify the title of the JFrame
 * 
 * Version: March 9, 2021
 * -Added a method to refresh the JFrame in each loop 
 * 
 * Version: March 8, 2021
 * -Set up a JFrame to Graphically display the map
 * -Added a JPannel to the JFrame to draw the world and the entities
 * 
 * Limitations:
 * -The graphical displaying window should not be closed when moving on to next turn
 * -Once Closed, the graphical displaying window cannot be reopened while running
 */

import javax.swing.JFrame;

public class GraphicDisplay extends JFrame {
	public final static int HEIGHT = 300;
	public final static int WIDTH = 200;
	
	/**
	 * Method: constructor
	 * Features: 
	 * -initialize a JFrame
	 */
	public GraphicDisplay(Entity[][] aWorld) {
		super();
		initialize(aWorld);
	}
	
	/**
	 * Method: initialize
	 * Features: 
	 * -initialize JFrame
	 * -add a JPannel that draws the World and the Entities to the JFrame
	 */
	public void initialize(Entity[][] aWorld) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		add(new DrawPanel(aWorld));
		setTitle("Orcs vs Elves(Don't close the Window when moving on to next turn!)");
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Method: refresh
	 * Features:
	 * -repaint the JFrame to show the new appearance of the World in each turn
	 */
	public void refresh() {
		repaint();
	}
}
