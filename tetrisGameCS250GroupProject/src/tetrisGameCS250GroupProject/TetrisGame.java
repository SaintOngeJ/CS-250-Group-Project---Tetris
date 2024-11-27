/**
 * Class: TetrisGame.java
 * Author: Wyatt St. Onge, Weston Cory, Everett Rosenow
 * Date: Sep 21, 2024
 * Assignment: tetrisGameCS250GroupProject
 * Goals: Implement the classic Tetris game with a GUI, controls, scoring, and
 * 	 	  title screen.
 * Inputs: Keys: 
 * Outputs: Tetris game screen with falling pieces and score display.
 * Packages: javax.swing, java.awt, java.awt.event
 * Algorithms: Collision detection, row clearing, piece rotation, and piece
 * 			   movement.
 */
package tetrisGameCS250GroupProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class TetrisGame extends JPanel {

    private static final long serialVersionUID = -8715353373678321308L;

    private long score;
    private static Color[][] well;
    private boolean gameStarted = false;

    private void initialize() {
        well = new Color[12][24];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                if (i == 0 || i == 11 || j == 22) {
                    well[i][j] = Color.GRAY;
                } else {
                    well[i][j] = Color.BLACK;
                }
            }
        }
        score = 0; // Reset the score
        Tetrominoes.nextPieces.clear(); // Clear upcoming pieces
        Tetrominoes tetrominoes = new Tetrominoes();
		tetrominoes.newPiece();
    } // initialize()

    // Make the dropping piece part of the well
    public static void fixToBoarder() {
    	TetrisLogic logic = new TetrisLogic();
        for (Point p : Tetrominoes.tetrominoes[Tetrominoes.currentPiece]
        											   [Tetrominoes.rotation]) {
            well[logic.pieceOrigin.x + p.x][logic.pieceOrigin.y + p.y] =
            		Tetrominoes.tetrominoColors[Tetrominoes.currentPiece];
        }
        clearRows();
        Tetrominoes newPiece = new Tetrominoes();
        newPiece.newPiece();
    } // fixToBoarder()

    // Clear completed rows
    public static void clearRows() {
        boolean gap;
        int numClears = 0;
        Tetrominoes rows = new Tetrominoes();

        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) {
                if (well[i][j] == Color.BLACK) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
            	deleteRow(j);
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

    // Delete a row after clearing it
    public static void deleteRow(int row) {
        for (int j = row - 1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                well[i][j + 1] = well[i][j];
            }
        }
    } // deleteRow()

    // Check for collision at a given position and rotation
    protected static boolean checkForCollision(int x, int y, int rotation) {
        for (Point p : Tetrominoes.tetrominoes[Tetrominoes.currentPiece]
        														   [rotation]) {
            if (well[x + p.x][y + p.y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    } // checkForCollision()

    // Draw the falling piece
    private void drawPiece(Graphics g) {
    	TetrisLogic logic = new TetrisLogic();
        for (Point p : Tetrominoes.tetrominoes[Tetrominoes.currentPiece]
        											   [Tetrominoes.rotation]) {
            g.setColor(Tetrominoes.tetrominoColors[Tetrominoes.currentPiece]);
			g.fillRect(26 * (logic.pieceOrigin.x + p.x), 26
            		* (logic.pieceOrigin.y + p.y), 25, 25);
        }
    } // drawPiece()

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grid background
        drawGridBackground(g);

        if (!gameStarted) {
            showTitleScreen(g);
        } else {
            // Paint the well and game elements
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 23; j++) {
                    g.setColor(well[i][j]);
                    g.fillRect(26 * i, 26 * j, 25, 25);
                }
            }

            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 26 * 13, 25);

            // Draw the current falling piece
            drawPiece(g);

            // Draw the "Next Piece" and controls
            drawNextPiece(g);
        }
    } // paintComponent()
    
    /**
     * This method draws the background of the 
     * @param g
     */
    private void drawGridBackground(Graphics g) {
        int totalWidth = 19 * 26; // Total width with extra column
        int totalHeight = 23 * 26;

        // Define the bounds for the text area
        // Start of the "Next Piece" column
        int textAreaStartX = 13 * 26; 
        // End of the original panel before the extra column
        int textAreaEndX = 18 * 26;   
        // Top of the panel
        int textAreaStartY = 0;       
        // Height to cover text (arbitrary height to fit all text)
        int textAreaEndY = 230;       
        // Draw grid cells
        for (int x = 0; x < totalWidth; x += 26) {
            for (int y = 0; y < totalHeight; y += 26) {
                if (x == 18 * 26) {
                    // Extra rightmost column: fill entirely with grey
                    g.setColor(Color.GRAY);
                } else if (x >= textAreaStartX && x < textAreaEndX
                		&& y >= textAreaStartY && y < textAreaEndY) {
                    // Text area: fill with black
                    g.setColor(Color.BLACK);
                } else {
                    // Normal grid: fill with grey
                    g.setColor(Color.GRAY);
                }
                g.fillRect(x, y, 25, 25);

                // Draw grid lines
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(x, y, 25, 25);
            }
        }
    } // drawGridBackground()
    
    /**
     * Draw the "Next Piece" in a separate area
     * @param g
     */
    private void drawNextPiece(Graphics g) {
    	// Get the next piece
    	Tetrominoes nextPieces = new Tetrominoes();
        int nextPiece = nextPieces.nextPieces.isEmpty()
        			? 0 : nextPieces.nextPieces.get(0);
        g.setColor(Color.WHITE);
        g.drawString("Next Piece:", 26 * 13, 50);

        // Draw the next piece using its default rotation (0)
        for (Point p :Tetrominoes.tetrominoes[nextPiece][0]) {
            g.setColor(Tetrominoes.tetrominoColors[nextPiece]);
            g.fillRect(26 * 14 + (p.x * 25), 60 + (p.y * 25), 25, 25);
        }

        // Display controls below the "Next Piece"
        g.setColor(Color.WHITE);
        g.drawString("Controls:Arrow Keys", 26 * 13, 150);
        g.drawString("Left & Right: Move", 26 * 13, 170);
        g.drawString("Up & Down: Rotate", 26 * 13, 190);
        g.drawString("Space: Drop", 26 * 13, 210);
    } // drawNextPiece()
    
    /**
     * Title screen drawing method
     * @param g
     */
    private void showTitleScreen(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("TETRIS", 75, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Use Arrow Keys to Move", 60, 150);
        g.drawString("Press Space to Drop", 60, 180);
        g.drawString("Press Enter to Start", 60, 210);
    } // showTitleScreen()
    
    /**
     * Main method of the application
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(12 * 41 + 15, 26 * 25 + 25);

        final TetrisGame game = new TetrisGame();
        game.initialize();
        Tetrominoes gamePieces = new Tetrominoes();

        // Set layout for adding components
        frame.setLayout(new BorderLayout());
        // Add the game JPanel to the center
        frame.add(game, BorderLayout.CENTER);

        // Add a KeyListener for controlling the game
        game.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (game.gameStarted) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                        	gamePieces.rotate(-1);
                            break;
                        case KeyEvent.VK_DOWN:
                        	gamePieces.rotate(1);
                            break;
                        case KeyEvent.VK_LEFT:
                        	gamePieces.movePiece(-1);
                            break;
                        case KeyEvent.VK_RIGHT:
                        	gamePieces.movePiece(1);
                            break;
                        case KeyEvent.VK_SPACE:
                        	gamePieces.dropPieceDown();
                            break;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    game.gameStarted = true;
                    game.repaint();
                }
            }
        });
        game.setFocusable(true); // Ensure the game panel can receive key events

        // Add a restart button
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.gameStarted = true;
                game.score = 0;
                game.initialize();
                game.repaint();
            }
        });
        controlPanel.add(restartButton);

        // Add control panel to the frame
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Run the game loop
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (game.gameStarted) {
                            Thread.sleep(500);
                            gamePieces.dropPieceDown();
                        } else {
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    } // main()
    
} // end TetrisGame class
