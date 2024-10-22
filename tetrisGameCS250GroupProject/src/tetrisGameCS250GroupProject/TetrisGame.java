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

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

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
	
	private final Color[] tetramino_colors = {
			Color.CYAN, Color.GREEN, Color.MAGENTA,
			Color.RED, Color.YELLOW, Color.YELLOW, Color.ORANGE};
	
	private Point piece_origin;
	private int current_piece;
	private int rotation;
	private ArrayList<Integer> next_pieces = new ArrayList<Integer>();
	private long score;
	private Color[][] game_boarder;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}//main()

}//end class TetrisGame
