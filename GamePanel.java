import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;
    private static final int TIMER_DELAY = 10;
    private static final int MAX_LEVEL = 3;
    
    private Ball ball;
    private Paddle paddle;
    private ArrayList<Brick> bricks;
    private Timer timer;
    private boolean gameOver = false;
    private boolean victory = false;
    private boolean isPaused = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private int score = 0;
    private int currentLevel = 1;
    private Random random = new Random();
    
    // UI Components
    private JButton restartButton;
    private JButton pauseButton;
    private JButton nextLevelButton;
    
    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);  // Using null layout for absolute positioning
        
        // Create buttons
        createButtons();
        
        initializeGame();
    }
    
    private void createButtons() {
        // Create restart button
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(PANEL_WIDTH/2 - 100, PANEL_HEIGHT/2 + 50, 200, 40);
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);
        
        // Create next level button
        nextLevelButton = new JButton("Next Level");
        nextLevelButton.setBounds(PANEL_WIDTH/2 - 100, PANEL_HEIGHT/2 + 50, 200, 40);
        nextLevelButton.setVisible(false);
        nextLevelButton.addActionListener(e -> startNextLevel());
        add(nextLevelButton);
        
        // Create pause button
        pauseButton = new JButton("Pause");
        pauseButton.setBounds(PANEL_WIDTH - 100, 10, 80, 30);
        pauseButton.addActionListener(e -> togglePause());
        add(pauseButton);
    }
    
    private void startNextLevel() {
        if (currentLevel < MAX_LEVEL) {
            currentLevel++;
            nextLevelButton.setVisible(false);
            initializeLevel();
            requestFocus();
        }
    }
    
    private void restartGame() {
        gameOver = false;
        victory = false;
        score = 0;
        currentLevel = 1;
        restartButton.setVisible(false);
        nextLevelButton.setVisible(false);
        isPaused = false;
        pauseButton.setText("Pause");
        initializeGame();
        requestFocus();
    }
    
    private void initializeGame() {
        initializeLevel();
    }
    
    private void initializeLevel() {
        // Initialize ball and paddle
        ball = new Ball(PANEL_WIDTH / 2, PANEL_HEIGHT - 100);
        paddle = new Paddle(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT - 50);
        
        // Create bricks with different point values and random power-ups
        bricks = new ArrayList<>();
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};
        int[] pointValues = {50, 40, 30, 20, 10}; // Points decrease as rows go down
        
        // Add more rows and increase power-up chance for higher levels
        int rows = 4 + currentLevel;  // 5-7 rows depending on level
        float powerUpChance = 0.15f + (currentLevel * 0.05f);  // 20-30% chance
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < 12; col++) {
                boolean hasPowerUp = random.nextFloat() < powerUpChance;
                Color color = colors[row % colors.length];
                int points = pointValues[row % pointValues.length] * currentLevel;
                bricks.add(new Brick(col * 65 + 10, row * 25 + 40, color, points, hasPowerUp));
            }
        }
        
        // Start the game loop
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw game elements
        ball.draw(g);
        paddle.draw(g);
        for (Brick brick : bricks) {
            brick.draw(g);
        }
        
        // Draw score and level
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Draw score on the left
        g.drawString("Score: " + score, 10, 30);
        
        // Draw level in the center
        String levelText = "Level " + currentLevel;
        FontMetrics fontMetrics = g.getFontMetrics();
        int levelWidth = fontMetrics.stringWidth(levelText);
        g.drawString(levelText, (PANEL_WIDTH - levelWidth) / 2, 30);
        
        // Draw power-up status
        if (ball.hasPowerUp()) {
            g.setColor(Color.YELLOW);
            g.drawString("SPEED BOOST!", 10, 60);
        }
        
        // Draw pause message
        if (isPaused && !gameOver && !victory) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String message = "PAUSED";
            FontMetrics pauseMetrics = g.getFontMetrics();
            g.drawString(message, 
                (PANEL_WIDTH - pauseMetrics.stringWidth(message)) / 2,
                PANEL_HEIGHT / 2);
        }
        
        // Draw game over or victory message
        if (gameOver || victory) {
            g.setColor(victory ? Color.GREEN : Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String message;
            if (victory) {
                message = "Congratulations! You've Won! Final Score: " + score;
            } else {
                message = "Game Over! Final Score: " + score;
            }
            FontMetrics gameOverMetrics = g.getFontMetrics();
            g.drawString(message, 
                (PANEL_WIDTH - gameOverMetrics.stringWidth(message)) / 2,
                PANEL_HEIGHT / 2);
                
            if (victory) {
                g.setFont(new Font("Arial", Font.BOLD, 20));
                String subMessage = "You completed all " + MAX_LEVEL + " levels!";
                FontMetrics victoryMetrics = g.getFontMetrics();
                g.drawString(subMessage,
                    (PANEL_WIDTH - victoryMetrics.stringWidth(subMessage)) / 2,
                    PANEL_HEIGHT / 2 + 40);
            }
            restartButton.setVisible(true);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !isPaused) {
            movePaddle();
            moveBall();
            checkCollisions();
            repaint();
        }
    }
    
    private void movePaddle() {
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight(PANEL_WIDTH);
        }
    }
    
    private void moveBall() {
        ball.move();
        
        // Check wall collisions
        if (ball.getX() <= 0 || ball.getX() >= PANEL_WIDTH - 15) {
            ball.reverseX();
        }
        if (ball.getY() <= 0) {
            ball.reverseY();
        }
        
        // Check if ball is below paddle (game over)
        if (ball.getY() >= PANEL_HEIGHT) {
            gameOver = true;
            timer.stop();
        }
    }
    
    private void checkCollisions() {
        // Check paddle collision
        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.reverseY();
            // Adjust ball position to prevent sticking
            ball.setY(paddle.getY() - 15);
        }
        
        // Check brick collisions
        for (Brick brick : bricks) {
            if (brick.isVisible() && ball.getBounds().intersects(brick.getBounds())) {
                brick.setVisible(false);
                ball.reverseY();
                
                // Add points based on brick's value
                score += brick.getPoints();
                
                // Activate power-up if brick has one
                if (brick.hasPowerUp()) {
                    ball.activateSpeedPowerUp();
                }
                
                // Check if all bricks are destroyed
                boolean allDestroyed = true;
                for (Brick b : bricks) {
                    if (b.isVisible()) {
                        allDestroyed = false;
                        break;
                    }
                }
                if (allDestroyed) {
                    if (currentLevel == MAX_LEVEL) {
                        // Player has completed all levels
                        victory = true;
                        gameOver = true;
                        timer.stop();
                    } else {
                        // Show next level button
                        timer.stop();
                        nextLevelButton.setVisible(true);
                    }
                }
                break;
            }
        }
    }
    
    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {  // Add keyboard shortcut for pause
            togglePause();
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {  // Add keyboard shortcut for restart
            restartGame();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    private void togglePause() {
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "Resume" : "Pause");
        if (isPaused) {
            timer.stop();
        } else {
            timer.start();
        }
    }
} 