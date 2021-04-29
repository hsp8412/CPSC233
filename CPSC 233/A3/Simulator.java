/* Name: Sipeng He
 * Program: Orcs and Elves combat simulator

 * Features: 
 * -March 6, 2021
 * -Program runs with a prompt to hit enter at end of each turn
 * -Displays the array with bounding lines around each element: above, below, left and right
 * -The user can toggle debugging mode along with appropriate message as the debugging state changes.
 * -Can determine when the orcs have won
 * -Can determine when the elves have won
 * -Can determine when there is draw
 * -March 7, 2021
 * -Orcs move diagonally downward (to the right and down a row)
 * -Elves move diagonally upward (to the left and up a row)
 * -Boundary checking prevents orcs and elves from moving past the bounds of the array
 * -Elves and orcs will only move onto empty squares
 * -Entities only move if they are not adjacent to an enemy
 * -March 8, 2021
 * -Entities can attack members of the opposing side
 * -Unconscious entities (with zero or fewer hit points) removed
 * -Can determine when there is a ceasefire
 * -March 9, 2021
 * -Array elements are displayed and animated using graphics
 * -March 10, 2021
 * -Program plays a sound effect or music when the program is running
 * 
 * Limitations: 
 * -The background music cannot be turned off when program is running
 * -The graphical displaying window should not be closed when moving on to next turn
 * -Once Closed, the graphical displaying window cannot be reopened while running
 * -Entities choose which enemy to attack only in a fashion starting from top to the bottom, left to the right
 */

/*
Author: Sipeng He
UCID: 30113342
Tutorial: T03
Version: March 10, 2021

 * Adding the starting point and ending point for the music player

Author:  James Tam
Version: October 14, 2015

 * Starting execution point for the program.
 */


import java.util.Scanner;

public class Simulator
{
  public static void main(String [] args)
  {
	  Scanner in = new Scanner(System.in);
	  System.out.println("Welcome to Orcs vs Elves combat simulator!");
	  MusicPlayer player = new MusicPlayer();
	  player.playMusic();
      World aWorld = new World();
      aWorld.start();
      player.endMusic();
      System.out.println("Press enter to quit...");
      in.nextLine();
      System.exit(0);
  }
}