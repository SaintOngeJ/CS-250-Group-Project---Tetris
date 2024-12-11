/**
 * Class: TetrisGame.java
 * Author: Wyatt St. Onge, Weston Cory, Everett Rosenow
 * Date: Sep 21, 2024
 * Assignment: tetrisGameCS250GroupProject
 * Goals: Implement the classic Tetris game with a GUI, controls, scoring, and
 * 	 	  title screen.
 * Inputs: Keys: Space (drop piece down), Left Arrow (move piece left), Right
 * 				 Arrow (move piece right), Up Arrow (rotate piece clockwise),
 * 				 Down Arrow (rotate piece counter-clockwise)
 * Outputs: Tetris game screen with falling pieces and score display.
 * Packages: javax.swing, java.awt, java.awt.event.KeyAdapter,
 * 			 java.awt.event.KeyEvent, java.util.ArrayList,
 * 			 java.util.Collections, java.util.List
 * Algorithms: Collision detection, row clearing, piece rotation, and piece
 * 			   movement.
 */
package tetrisGameCS250GroupProject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TetrisGame extends JPanel {

    private static final long serialVersionUID = -8715353373678321308L;
    
    // Matrices of the tetrominoes
    private final Point[][][] tetrominoes = {
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

    private final Color[] tetrominoColors = {
            Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green,
            Color.pink, Color.red
    };

    private Point pieceOrigin;
    private int currentPiece;
    private int rotation;
    private ArrayList<Integer> nextPieces = new ArrayList<>();
    private long score;
    private int lineCount;
    private static int speed;
    private Color[][] well;
    private boolean gameStarted = false;
    private final String leaderboardFile = "leaderboard.txt";
    private List<String> leaderboard = new ArrayList<>();

    /**
	 * @return the timer
	 */
	public static int getSpeed() {
		return speed;
	} // getTimer()

	/**
	 * @param timer the timer to set
	 */
	public void setSpeed(int speed) {
		TetrisGame.speed = speed;
	} // setTimer()

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
        setSpeed(1000); // Set timer length to level one speed
        nextPieces.clear(); // Clear upcoming pieces
        newPiece();
    } // initialize()

    /**
     * This method creates a new piece on the game board
     */
    public void newPiece() {
    	pieceOrigin = new Point(5, 2);
        rotation = 0;

        // Refill the nextPieces list if empty
        if (nextPieces.isEmpty()) {
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
            Collections.shuffle(nextPieces);
        }

        // Set the current piece to the first piece in the list and remove it
        currentPiece = nextPieces.remove(0);

        // Game over check
        if (checkForCollision(pieceOrigin.x, pieceOrigin.y, rotation)) {
            gameOver();
        }
    } // newPiece()

    /**
     * This method rotates the piece on the game board
     * @param i: value of the pieces' current rotation
     */
    public void rotate(int i) {
    	int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (!checkForCollision(pieceOrigin.x, pieceOrigin.y, newRotation)) {
            rotation = newRotation;
        }
        repaint();
    } // rotate()

    /**
     * This method moves the piece left, right, and down on the game board
     * @param i: coordinate value of the current piece
     */
    public void movePiece(int i) {
        if (!checkForCollision(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
            pieceOrigin.x += i;
        }
        repaint();
    } // movePiece()

    /**
     * This method drops the piece down on the game board
     */
    public void dropPieceDown() {
    	if (!checkForCollision(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
            pieceOrigin.y += 1;
        } else {
            fixToBoarder();
        }
        repaint();
    } // dropPieceDown()
    
    /**
     * Instantly drops the current piece to the bottom-most valid position.
     */
    public void instantDrop() {
        while (!checkForCollision(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
            pieceOrigin.y++;
        }
        fixToBoarder();
        repaint();
    } // instantDrop()

    /**
     * This method checks for collision between the current piece and the well,
     * and makes the dropping piece part of the well if it cannot move anymore
     */
    public void fixToBoarder() {
    	for (Point p : tetrominoes[currentPiece][rotation]) {
            well[pieceOrigin.x + p.x][pieceOrigin.y + p.y] =
                    tetrominoColors[currentPiece];
        }
        clearRows();
        newPiece();
    } // fixToBoarder()

    /**
     * This method clears completed rows
     */
    public void clearRows() {
    	boolean gap;
        int numClears = 0;

        // Iterate through the rows from bottom to top
        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) { // Check all cells in the row
                if (well[i][j] == Color.BLACK) {
                    gap = true; // Found an empty cell, not a full row
                    break;
                }
            }
            if (!gap) { // If the row is full
                deleteRow(j);
                j++; // Recheck the same row index as rows above shift down
                numClears++;
            }
        }

        // Update the total number of lines cleared
        lineCount += numClears;

        // Increase speed (decrease interval) after every 20 rows cleared
        int newSpeed = 1000 - (lineCount / 20) * 100; // Decrease speed after every 20 rows
        if (newSpeed < 200) { // Set a lower limit on speed to prevent it from becoming too fast
            newSpeed = 200;
        }
        speed = newSpeed; // Update the game speed

        // Add points based on the number of cleared rows
        switch (numClears) {
            case 1:
                score += 100; // Single row
                break;
            case 2:
                score += 300; // Double row
                break;
            case 3:
                score += 500; // Triple row
                break;
            case 4:
                score += 800; // Tetris!
                break;
        }

        // Repaint to update the score display
        repaint();
    } // clearRows()

    /**
     * This method deletes a row after clearing it
     * @param row: index of the row to be cleared
     */
    public void deleteRow(int row) {
    	for (int j = row - 1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                well[i][j + 1] = well[i][j];
            }
        }
    } // deleteRow()

    /**
     * This method checks for collision at a given position and rotation
     * @param x: x-value of the current piece
     * @param y: y-value of the current piece
     * @param rotation: rotation value of the current piece
     * @return boolean
     */
    public boolean checkForCollision(int x, int y, int rotation) {
    	for (Point p : tetrominoes[currentPiece][rotation]) {
            if (well[x + p.x][y + p.y] != Color.BLACK) {
                return true;
            }
        }
        return false;
    } // checkForCollision()

    /**
	 * This method paints the components on the frame
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        drawGridBackground(g);

        if (!gameStarted) {
            showTitleScreen(g);
            drawLeaderboard(g); // Draw the leaderboard on the title screen
        } else {
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 23; j++) {
                    g.setColor(well[i][j]);
                    g.fillRect(26 * i, 26 * j, 25, 25);
                }
            }
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 26 * 13, 25);
            drawPiece(g); // Draw the current piece on the game board
            drawNextPiece(g); // Draw the next piece
        }
	} // paintComponent()

	/**
     * This method draws the falling piece
     * @param g
     */
    private void drawPiece(Graphics g) {
        for (Point p : tetrominoes[currentPiece][rotation]) {
            g.setColor(tetrominoColors[currentPiece]);
            g.fillRect(26 * (pieceOrigin.x + p.x), 26
            		* (pieceOrigin.y + p.y), 25, 25);
        }
    } // drawPiece()

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
    	if (!nextPieces.isEmpty()) {
            int nextPiece = nextPieces.get(0); // Get next piece from the list

            g.setColor(Color.CYAN);
            g.drawString("Next Piece:", 26 * 13, 50);

            // Draw the next piece using its default rotation (0)
            for (Point p : tetrominoes[nextPiece][0]) {
                g.setColor(tetrominoColors[nextPiece]);
                g.fillRect(26 * 14 + (p.x * 25), 60 + (p.y * 25), 25, 25);
            }
        }

        // Display controls below the "Next Piece"
        g.setColor(Color.CYAN);
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
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("TETRIS", 75, 100);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Use Arrow Keys to Move", 60, 150);
        g.drawString("Press Space to Drop", 60, 180);
        g.drawString("Press Enter to Start", 60, 210);
        g.drawString("Press R to restart", 60, 240);
    } // showTitleScreen()
    
    /**
     * This method prompts the player to enter their name after the game over
     * condition is met
     */
	private void gameOver() {
		gameStarted = false;

        String playerName = JOptionPane.showInputDialog(this,
                "Game Over! Enter your name for the leaderboard:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            leaderboard.add(playerName + " - " + score);
            leaderboard.sort((a, b) ->
                    Integer.compare(Integer.parseInt(b.split(" - ")[1]),
                    Integer.parseInt(a.split(" - ")[1])));
            saveLeaderboard();
        }
        loadLeaderboard();
        repaint();
	} // gameOver()
    
    /**
     * This class handles the loading of the leaderboard
     */
    private void loadLeaderboard() {
    	leaderboard.clear();
        File file = new File(leaderboardFile);

        if (file.exists()) {
            try (BufferedReader reader
                     = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    leaderboard.add(line);
                }
            } catch (IOException e) {
                System.err.println("Error reading leaderboard file: "
                        + e.getMessage());
            }
        }
    } // loadLeaderboard()
    
    /**
     * This method saves the players score to the leaderboard.txt file
     */
    private void saveLeaderboard() {
		try (BufferedWriter writer
				= new BufferedWriter(new FileWriter(leaderboardFile))) {
			for (String entry : leaderboard) {
				writer.write(entry);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing to leaderboard file: "
					+ e.getMessage());
		}
    } // saveLeaderboard()
    
    /**
     * This method draws the leaderboard to the game
     * @param g
     */
    private void drawLeaderboard(Graphics g) {
    	g.setColor(Color.CYAN);
        g.drawString("Leaderboard:", 26 * 13, 250);

        int y = 270;
        for (int i = 0; i < Math.min(5, leaderboard.size()); i++) {
            g.drawString(leaderboard.get(i), 26 * 13, y);
            y += 20;
        }
    } // drawLeaderboard()
    
    /**
     * Main method of the application
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(12 * 41 + 15, 26 * 25 - 13);

        final TetrisGame game = new TetrisGame();
        game.initialize();
        game.loadLeaderboard();

        // Set layout for adding components
        frame.setLayout(new BorderLayout());
        // Add the game JPanel to the center
        frame.add(game, BorderLayout.CENTER);

        // Add a KeyListener for controlling the game
        game.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            	if (e.getKeyCode() == KeyEvent.VK_R) {
            		game.initialize();
            		game.repaint();
            	}
            	else if (game.gameStarted) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP: // Rotate current piece
                            game.rotate(-1);
                            break;
                        case KeyEvent.VK_DOWN: // Soft drop current piece
                            game.dropPieceDown();
                            break;
                        case KeyEvent.VK_LEFT: // Move current piece left
                            game.movePiece(-1);
                            break;
                        case KeyEvent.VK_RIGHT: // Move current piece right
                            game.movePiece(1);
                            break;
                        case KeyEvent.VK_SPACE: // Instant drop current piece
                            game.instantDrop();
                            break;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    game.gameStarted = true;
                    game.repaint();
                }
            }
        });
        
        game.setFocusable(true); // Ensure the game panel can receive key events
        frame.setVisible(true); // Ensure the frame is visible

        // Run the game loop
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (game.gameStarted) {
                            Thread.sleep(getSpeed());
                            game.dropPieceDown();
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
