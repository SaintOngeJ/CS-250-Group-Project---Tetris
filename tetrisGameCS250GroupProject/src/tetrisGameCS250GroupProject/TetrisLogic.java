/**
 * Class: TetrisLogic.java
 * Author: Wyatt St. Onge
 * Date: Nov 27, 2024
 * Assignment: tetrisGameCS250GroupProject
 * Goals: 
 * Inputs: 
 * Outputs: 
 * Packages: Point
 */
package tetrisGameCS250GroupProject;

import java.awt.Color;
import java.awt.Point;

public class TetrisLogic {
	
	Point pieceOrigin;
    long score;
	
	/**
	 * This method is used to the clear rows and add score
	 */
    public void clearRows() {
        boolean gap;
        int numClears = 0;

        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) {
                if (TetrisGame.well[i][j] == Color.BLACK) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
            	TetrisGame.deleteRow(j);
                j += 1;
                numClears += 1;
            }
        }

        switch (numClears) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    } // clearRows()

	
} // end TetrisLogic class
