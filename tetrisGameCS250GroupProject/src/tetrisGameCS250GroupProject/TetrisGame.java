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

public class TetrisGame extends JPanel {

    private static final long serialVersionUID = -8715353373678321308L;

    static Color[][] well;
    private boolean gameStarted = false;
    
    /**
     * This method instantiates the game board
     */
    private void initialize() {
    	TetrisLogic logic = new TetrisLogic();
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
        logic.score = 0; // Reset the score
        Tetrominoes.nextPieces.clear(); // Clear upcoming pieces
        Tetrominoes tetrominoes = new Tetrominoes();
		tetrominoes.newPiece();
    } // initialize()
    
    /**
     * This methods adds the current piece to part of the well by checking
     * collision
     */
    public static void fixToBoarder() {
    	TetrisLogic logic = new TetrisLogic();
        for (Point p : Tetrominoes.tetrominoes[Tetrominoes.currentPiece]
        											   [Tetrominoes.rotation]) {
            well[logic.pieceOrigin.x + p.x][logic.pieceOrigin.y + p.y] =
            		Tetrominoes.tetrominoColors[Tetrominoes.currentPiece];
        }
        logic.clearRows();
        Tetrominoes newPiece = new Tetrominoes();
        newPiece.newPiece();
    } // fixToBoarder()
    
    /**
     * This method deletes a row after clearing it
     * @param row: keeps track of the current row
     */
    public static void deleteRow(int row) {
        for (int j = row - 1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                well[i][j + 1] = well[i][j];
            }
        }
    } // deleteRow()
    
    /**
     * This method checks for collision at a given position and rotation
     * @param x: value of the current piece at the x axis
     * @param y: value of the current piece at the y value
     * @param rotation: value of the current pieces' rotation
     * @return boolean
     */
    protected static boolean checkForCollision(int x, int y, int rotation) {
        for (Point p : Tetrominoes.tetrominoes[Tetrominoes.currentPiece]
        														   [rotation]) {
            if (well[x + p.x][y + p.y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    } // checkForCollision()
    
    /**
     * This method draws the falling piece
     * @param g
     */
    private void drawPiece(Graphics g) {
    	TetrisLogic logic = new TetrisLogic();
        for (Point p : Tetrominoes.tetrominoes[Tetrominoes.currentPiece]
        											   [Tetrominoes.rotation]) {
            g.setColor(Tetrominoes.tetrominoColors[Tetrominoes.currentPiece]);
			g.fillRect(26 * (logic.pieceOrigin.x + p.x), 26
            		* (logic.pieceOrigin.y + p.y), 25, 25);
        }
    } // drawPiece()
    
    /**
     * This methods draws all the components on the game board
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        TetrisLogic logic = new TetrisLogic();

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
            g.drawString("Score: " + logic.score, 26 * 13, 25);

            // Draw the current falling piece
            drawPiece(g);

            // Draw the "Next Piece" and controls
            drawNextPiece(g);
        }
    } // paintComponent()
    
    /**
     * This method draws the background of the game board
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
        int nextPiece = Tetrominoes.nextPieces.isEmpty()
        			? 0 : Tetrominoes.nextPieces.get(0);
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
        TetrisLogic logic = new TetrisLogic();

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
                logic.score = 0;
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
