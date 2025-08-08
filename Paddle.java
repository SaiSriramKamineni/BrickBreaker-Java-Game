import java.awt.*;

public class Paddle {
    private int x;
    private int y;
    private int width = 100;
    private int height = 20;
    private int speed = 5;
    
    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void moveLeft() {
        if (x > 0) {
            x -= speed;
        }
    }
    
    public void moveRight(int panelWidth) {
        if (x + width < panelWidth) {
            x += speed;
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
} 