/**
 * Class: Tetrominoes.java
 * Author: Wyatt St. Onge
 * Date: Nov 27, 2024
 * Assignment: tetrisGameCS250GroupProject
 * Goals: This class handles all the logic with the game pieces (tetrominoes)
 * Inputs: 
 * Outputs: 
 * Packages: Color, Point, ArrayList, Collections
 */
package tetrisGameCS250GroupProject;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class Tetrominoes {
	
	// Matrices of the tetrominoes
    protected final static Point[][][] tetrominoes = {
    	// I-Piece
        {
        	{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        },
        // J-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
        },
        // L-Piece
        {
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
            {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
        },
        // O-Piece
        {
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
        },
        // S-Piece
        {
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
        },
        // T-Piece
        {
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
            {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
        },
        // Z-Piece
        {
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
            {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
        }
    };

    protected final static Color[] tetrominoColors = {
            Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green,
            Color.pink, Color.red
    };
    
    protected static int currentPiece;
    protected static int rotation;
    static ArrayList<Integer> nextPieces = new ArrayList<>();
    
    // Create a new piece
    public void newPiece() {
    	TetrisLogic logic = new TetrisLogic();
    	logic.pieceOrigin = new Point(5, 2);
        rotation = 0;
        if (nextPieces.isEmpty()) {
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
            Collections.shuffle(nextPieces);
        }
        currentPiece = nextPieces.get(0);
        nextPieces.remove(0);
    } // newPiece()
    
    // Rotate the piece
    public void rotate(int i) {
    	TetrisLogic logic = new TetrisLogic();
        int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (!TetrisGame.checkForCollision(logic.pieceOrigin.x,
        									logic.pieceOrigin.y, newRotation)) {
            rotation = newRotation;
        }
        TetrisGame tetrisGame = new TetrisGame();
		tetrisGame.repaint();
    } // rotate()

    // Move the piece
    public void movePiece(int i) {
    	TetrisLogic logic = new TetrisLogic();
        if (!TetrisGame.checkForCollision(logic.pieceOrigin.x + i,
        									   logic.pieceOrigin.y, rotation)) {
        	logic.pieceOrigin.x += i;
        }
        TetrisGame tetrisGame = new TetrisGame();
		tetrisGame.repaint();
    } // movePiece()

    // Drop the piece down
    public void dropPieceDown() {
    	TetrisLogic logic = new TetrisLogic();
        if (!TetrisGame.checkForCollision(logic.pieceOrigin.x,
        								   logic.pieceOrigin.y + 1, rotation)) {
        	logic.pieceOrigin.y += 1;
        } else {
            TetrisGame.fixToBoarder();
        }
        TetrisGame tetrisGame = new TetrisGame();
		tetrisGame.repaint();
    } // dropPieceDown()

} // end Tetrominoes class
