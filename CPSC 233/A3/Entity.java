/*
Author: Sipeng He
Version: March 8, 2021
 * Added an attribute and a method to track if one Entity has been moved in one turn
 * Added a method to modify the hit points when being attacked
 *
Version: March 7, 2021
 * Added methods and attributes to track if an Entity gets to move or attack in one turn
 * Added methods to build, print and clear a list of adjacent enemies
 * Added methods to get the locations of a specific enemy in the adjacent enemy list
 * Added a method to get the number of adjacent enemies
 * 
Author:  James Tam

Version Feb 17A, 2021
* Added method so an Entity can damage another Entity.

Version: Feb 12A, 2021
* Added attribute to track if Entity has been moved or not.

Version: Feb 11B, 2021
* Changed references from dwarves to elves.
* Added a new damage attribute.

Version: October 12, 2015
* Original program
* 
*
*/

public class Entity {
	public static final char DEFAULT_APPEARANCE = 'X';
	public static final char ELF = 'E';
	public static final char EMPTY = ' ';
	public static final char ORC = 'O';
	public static final int DEFAULT_HP = 1;
	public static final int ORC_DAMAGE = 3;
	public static final int ELF_DAMAGE = 7;
	public static final int ORC_HP = 10;
	public static final int ELF_HP = 15;

	private char appearance;
	private int hitPoints;
	private int damage;
	private boolean ifMove = false;
	private boolean ifAttack = false;
	private boolean ifHasMoved = false;

	private Location[] enemyList;
	private int numOfEnemy = 0;

	/**
	 * Method: constructor
	 * Features:
	 * -initialize the entity when no parameters are given
	 */
	public Entity() {
		setAppearance(DEFAULT_APPEARANCE);
		setHitPoints(DEFAULT_HP);
	}

	/**
	 * Method: constructor
	 * Features:
	 * -Initialize the Entity when only appearance is given
	 */
	public Entity(char newAppearance) {
		appearance = newAppearance;
		hitPoints = DEFAULT_HP;
		damage = ORC_DAMAGE;
	}

	/**
	 * Method: constructor
	 * Features:
	 * -Initialize the Entity when appearance, hit points and damage are all given
	 */
	public Entity(char newAppearance, int newHitPoints, int newDamage) {
		setAppearance(newAppearance);
		setDamage(newDamage);
		setHitPoints(newHitPoints);
		enemyList = new Location[World.SIZE * World.SIZE];
	}

	/**
	 * Method: getAppearance
	 * Features:
	 * -get the appearance of an Entity
	 */
	public char getAppearance() {
		return (appearance);
	}

	/**
	 * Method: getDamage
	 * Features:
	 * -get the damage of an Entity
	 */
	public int getDamage() {
		return (damage);
	}

	/**
	 * Method: getHItPoints
	 * Features:
	 * -get the hit points of an Entity
	 */
	public int getHitPoints() {
		return (hitPoints);
	}

	/**
	 * Method: setAppearance
	 * Features:
	 * -set the appearance of an Entity
	 */
	private void setAppearance(char newAppearance) {
		appearance = newAppearance;
	}

	/**
	 * Method: setDamage
	 * Features:
	 * -set the damage of an Entity
	 */
	private void setDamage(int newDamage) {
		if (newDamage < 1) {
			System.out.println("Damage must be 1 or greater");
		} else {
			damage = newDamage;
		}
	}

	/**
	 * Method: setHitPoints
	 * Features:
	 * -set the hit points of an Entity
	 */
	private void setHitPoints(int newHitPoints) {
		hitPoints = newHitPoints;
	}

	/**
	 * Method: getEnemyRow
	 * Features:
	 * -get the row location of a specific enemy in the adjacent enemy list
	 */
	public int getEnemyRow(int enemyNum) {
		return enemyList[enemyNum].getRow();
	}

	/**
	 * Method: getEnemyColumn
	 * Features:
	 * -get the column location of a specific enemy in the adjacent enemy list
	 */
	public int getEnemyColumn(int enemyNum) {
		return enemyList[enemyNum].getColumn();
	}

	/**
	 * Method: setIfMove
	 * Features:
	 * -set the flag for whether move or not in a turn
	 */
	public void setIfMove(boolean aBoolean) {
		ifMove = aBoolean;
	}

	/**
	 * Method: setIfAttack
	 * Features:
	 * -set the flag for whether attack or not in a turn
	 */
	public void setIfAttack(boolean aBoolean) {
		ifAttack = aBoolean;
	}

	/**
	 * Method: getIfMove
	 * Features:
	 * -get the flag for whether move or not in a turn
	 */
	public boolean getIfMove() {
		return ifMove;
	}

	/**
	 * Method: getIfAttack
	 * Features:
	 * -get the flag for whether attack or not in a turn
	 */
	public boolean getIfAttack() {
		return ifAttack;
	}

	/**
	 * Method: beAttacked
	 * Features:
	 * -subtract the enemy's damage from the entity's HP when being attacked
	 */
	public void beAttacked(Entity anEntity) {
		if (anEntity.getAppearance() == ORC) {
			this.hitPoints = this.hitPoints - ORC_DAMAGE;
		} else {
			this.hitPoints = this.hitPoints - ELF_DAMAGE;
		}
	}

	/**
	 * Method: setIfHasMoved
	 * Features:
	 * -set the flag for if the entity has been moved
	 */
	public void setIfHasMoved(boolean aboolean) {
		ifHasMoved = aboolean;
	}

	/**
	 * Method: getIfHasMoved
	 * Features:
	 * -get the flag for if the entity has been moved
	 */
	public boolean getIfHasMoved() {
		return ifHasMoved;
	}

	/**
	 * Method: addToEnemyList
	 * Features:
	 * -add the location of an adjacent enemy to the enemy list
	 */
	public void addToEnemyList(int r, int c) {
		enemyList[numOfEnemy] = new Location(r, c);
		numOfEnemy++;
	}

	/**
	 * Method: clearEnemyList
	 * Features:
	 * -clear the adjacent enemy list
	 */
	public void clearEnemyList() {
		for (int i = 0; i < numOfEnemy; i++) {
			enemyList[i] = null;
		}
		numOfEnemy = 0;
	}

	/**
	 * Method: printEnemyList
	 * Features:
	 * -print the adjacent enemy list
	 */
	public void printEnemyList() {
		for (int i = 0; i < numOfEnemy; i++) {
			System.out.printf("(%d/%d) ", enemyList[i].getRow(), enemyList[i].getColumn());
		}
		System.out.println("");
	}

	/**
	 * Method: getNumOfEnemy
	 * Features:
	 * -get the number of adjacent enemies
	 */
	public int getNumOfEnemy() {
		return numOfEnemy;
	}
}
