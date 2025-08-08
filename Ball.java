import java.awt.*;

public class Ball {
    private double x;
    private double y;
    private int size = 15;
    private double dx = 3;
    private double dy = -3;
    private double baseSpeed = 3;
    private double speedMultiplier = 1.0;
    private int speedPowerUpTimer = 0;
    
    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        resetSpeed();
    }
    
    public void move() {
        x += dx;
        y += dy;
        
        // Handle speed power-up timer
        if (speedPowerUpTimer > 0) {
            speedPowerUpTimer--;
            if (speedPowerUpTimer == 0) {
                resetSpeed();
            }
        }
    }
    
    public void activateSpeedPowerUp() {
        speedMultiplier = 1.5; // 50% speed increase
        speedPowerUpTimer = 500; // About 5 seconds at 10ms per frame
        updateSpeed();
    }
    
    private void resetSpeed() {
        speedMultiplier = 1.0;
        updateSpeed();
    }
    
    private void updateSpeed() {
        double currentAngle = Math.atan2(dy, dx);
        double speed = baseSpeed * speedMultiplier;
        dx = speed * Math.cos(currentAngle);
        dy = speed * Math.sin(currentAngle);
    }
    
    public void reverseX() {
        dx = -dx;
    }
    
    public void reverseY() {
        dy = -dy;
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }
    
    public void draw(Graphics g) {
        g.setColor(speedPowerUpTimer > 0 ? Color.YELLOW : Color.WHITE);
        g.fillOval((int)x, (int)y, size, size);
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public boolean hasPowerUp() { return speedPowerUpTimer > 0; }
} 