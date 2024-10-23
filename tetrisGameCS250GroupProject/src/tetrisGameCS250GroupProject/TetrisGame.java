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
	
	// array of piece colors
	private final Color[] tetramino_colors = {
			Color.CYAN, Color.GREEN, Color.MAGENTA,
			Color.RED, Color.YELLOW, Color.YELLOW, Color.ORANGE};
	
	// piece values
	// starting point of the pieces on the game board
	private Point piece_origin;
	// value of the current piece that the user is manipulating
	private int current_piece;
	// value of the current pieces' rotation
	private int rotation;
	
	// array list of the next pieces to be placed on the game board
	private ArrayList<Integer> next_pieces = new ArrayList<>();
	
	// value that tracks the players' score
	private long score;
	
	// game board
	// array that defines the game boarder
	private Color[][] game_boarder;
	
	// game board width and height
	private static final int board_width = 12;
	private static final int board_height = 22;
	
	/**
	 * This method initializes the game boarder
	 */
	public void initializeWell() {
		game_boarder = new Color[12][22];
		for(int row = 0; row < board_width; row++)
			for(int col = 0; col < board_height; col++)
				if(row == 0 || row == 11 || col == 22) {
					game_boarder[row][col] = Color.GRAY;
				} else {
					game_boarder[row][col] = Color.BLACK;
				}
		//newPiece();
	} // initializeWell()
	
	/**
	 * This method places a new piece into the dropping position
	 */
	public void newPiece() {
		
	} // newPiece()

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}//main()

}//end class TetrisGame
