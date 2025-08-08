import java.awt.*;

public class Brick {
    private int x;
    private int y;
    private int width = 60;
    private int height = 20;
    private boolean isVisible = true;
    private Color color;
    private int points;
    private boolean hasPowerUp;
    
    public Brick(int x, int y, Color color, int points, boolean hasPowerUp) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.points = points;
        this.hasPowerUp = hasPowerUp;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void draw(Graphics g) {
        if (isVisible) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            
            // Draw a special marker for power-up bricks
            if (hasPowerUp) {
                g.setColor(Color.WHITE);
                g.fillOval(x + width/2 - 5, y + height/2 - 5, 10, 10);
            }
        }
    }
    
    public boolean isVisible() { return isVisible; }
    public void setVisible(boolean visible) { isVisible = visible; }
    public int getPoints() { return points; }
    public boolean hasPowerUp() { return hasPowerUp; }
    public int getX() { return x; }
    public int getY() { return y; }
} 