/*
 * Author: Sipeng He
 * Version: March 10, 2021
 * -Use a method to get a suitable size of the window
 * 
 * Version: March 9, 2021
 * -Set up a loop to get the locations of the entities
 * -put the Entities on the right place of the grid
 * 
 * Version: March 8, 2021
 * -Set up a JPannel that draws the grid
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	public static final int MARGIN = 30;
	public static final int GRID_SPAN = 50;
	public static final int ROWS = 10;
	public static final int COLUMNS = 10;
	public Entity[][] flag;

	/**
	 * Method: constructor
	 * Features:
	 * -initializing the JPanel
	 */
	public DrawPanel(Entity[][] aWorld) {
		super();
		this.setBackground(Color.WHITE);
		flag = aWorld;
	}

	/**
	 * Method: paint
	 * Features:
	 * -rewrite the paint method inherited from the parent class JPanel
	 * -draw the grid of the world
	 * -loop through the entity array to get the locations of the Entities
	 * -put the image of entities on the right place on the grid
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Image orc;
		Image elf;
		URL orcUrl = DrawPanel.class.getResource("Orc.png");
		orc = Toolkit.getDefaultToolkit().getImage(orcUrl);
		URL elfUrl = DrawPanel.class.getResource("Elf.png");
		elf = Toolkit.getDefaultToolkit().getImage(elfUrl);
		for (int i = 0; i <= ROWS; i++) {
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLUMNS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLUMNS; i++) {
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}
		for (int r = 0; r < World.SIZE; r++) {
			for (int c = 0; c < World.SIZE; c++) {
				if (flag[r][c] != null) {
					if (flag[r][c].getAppearance() == Entity.ORC) {
						g.drawImage(orc, MARGIN + GRID_SPAN * c, MARGIN + GRID_SPAN * r, GRID_SPAN, GRID_SPAN, this);
					} else {
						g.drawImage(elf, MARGIN + GRID_SPAN * c, MARGIN + GRID_SPAN * r, GRID_SPAN, GRID_SPAN, this);
					}
				}
			}
		}
	}

	/**
	 * Method: getPreferredSize
	 * Features:
	 * -set the size of the window to a suitable value
	 */
	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		return new Dimension(MARGIN * 2 + GRID_SPAN * COLUMNS, MARGIN * 2 + GRID_SPAN * ROWS);
	}
}
