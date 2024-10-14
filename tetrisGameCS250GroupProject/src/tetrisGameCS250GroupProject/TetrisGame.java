/**
 * Class: TetrisGame.java
 * Author: Wyatt St. Onge, Weston Cory, Everett Rosenow
 * Date: Sep 21, 2024
 * Assignment: tetrisGameCS250GroupProject
 * Goals: This class handles all of the tetris game logic
 * Inputs:
 * Outputs:
 * Packages: tetrisGameCS250GroupProject,
 * Algorithms:
 */
package tetrisGameCS250GroupProject;

import java.awt.Point;

public class TetrisGame {
	
	private final Point[][][] Tetraminoes = {
		
		// O-piece
		{
			{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1)},
			{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1)},
			{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1)},
			{new Point(0, 0),new Point(0, 1),new Point(1, 0),new Point(1, 1)}
		},
		
		// I-piece
		{
			{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(3, 1)},
			{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(1, 3)},
			{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(3, 1)},
			{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(1, 3)},
		},
		
		// J-piece
		{
			{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(2, 0)},
			{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(2, 2)},
			{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(0, 2)},
			{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(0, 0)}
		},
		
		// L-piece
		{
			{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(2, 2)},
			{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(0, 2)},
			{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(0, 0)},
			{new Point(1, 0),new Point(1, 1),new Point(1, 2),new Point(2, 0)}
		},
		
		// S-piece
		{
			{new Point(1, 0),new Point(2, 0),new Point(0, 1),new Point(1, 1)},
			{new Point(0, 0),new Point(0, 1),new Point(1, 1),new Point(1, 2)},
			{new Point(1, 0),new Point(2, 0),new Point(0, 1),new Point(1, 1)},
			{new Point(0, 0),new Point(0, 1),new Point(1, 1),new Point(1, 2)}
		},
		
		// T-piece
		{
			{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(2, 1)},
			{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(1, 2)},
			{new Point(0, 1),new Point(1, 1),new Point(2, 1),new Point(1, 2)},
			{new Point(1, 0),new Point(1, 1),new Point(2, 1),new Point(1, 2)}
		},
		
		// Z-piece
		{
			{new Point(0, 0),new Point(1, 0),new Point(1, 1),new Point(2, 1)},
			{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(0, 2)},
			{new Point(0, 0),new Point(1, 0),new Point(1, 1),new Point(2, 1)},
			{new Point(1, 0),new Point(0, 1),new Point(1, 1),new Point(0, 2)}
		}
	};
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}//main()

}//end class TetrisGame
