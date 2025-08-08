---

# 🎮 Brick Breaker Game in Java  

A professional-grade implementation of the classic arcade game built with Java Swing, featuring modern gameplay elements and clean OOP design.

## 🚀 Core Features  

### Game Mechanics  
- **3 Progressive Levels**: Each with unique brick layouts and difficulty scaling  
- **Smart Collision System**: Precise ball-paddle/brick interactions  
- **Dynamic Physics**: Realistic ball rebound angles based on impact position  

### Power-Up System  
| Power-Up | Effect | Duration | Visual Indicator |  
|----------|--------|----------|------------------|  
| Speed Boost | +50% ball velocity | 5s | Yellow ball trail |  

### Scoring (Points = Base Value × Level)  
| Brick | Level 1 | Level 2 | Level 3 |  
|-------|---------|---------|---------|  
| 🔴 Red | 50 | 100 | 150 |  
| 🟠 Orange | 40 | 80 | 120 |  
| 🟡 Yellow | 30 | 60 | 90 |  
| 🟢 Green | 20 | 40 | 60 |  
| 🔵 Blue | 10 | 20 | 30 |  

## 🛠 Technical Implementation  

### Class Structure  
```java
// Core Components
class GamePanel extends JPanel {
  // Manages game loop, rendering, and state transitions
}

class Ball {
  // Handles movement, collision detection, and physics
}

class Paddle {
  // Processes keyboard input and boundary checks
}

class Brick {
  // Manages brick properties and power-up states
}
```

### Key Algorithms  
1. **Collision Detection**:  
   ```java
   if (ballRect.intersects(brickRect)) {
       handleCollision(brick);
       score += brick.getValue() * currentLevel;
   }
   ```

2. **Power-Up Timer**:  
   ```java
   Timer boostTimer = new Timer(5000, e -> {
       ball.resetSpeed();
       isBoosted = false;
   });
   ```

## 📦 Dependencies & Setup  

**Requirements**:  
- JDK 8+  
- Swing (included in Java SE)  

**Run Commands**:  
```bash
javac -d bin src/*.java  # Compile
java -cp bin BrickBreaker # Execute
```

## 🎯 Level Design  

| Level | Bricks | Power-Ups | Ball Speed |  
|-------|--------|-----------|------------|  
| 1 | 40 | 10% | 4px/frame |  
| 2 | 60 | 25% | 5px/frame |  
| 3 | 80 | 40% | 6px/frame |  

## 💡 Best Practices Showcased  
- **MVC Pattern**: Separation of game logic (Model) and Swing UI (View)  
- **Encapsulation**: Each game entity manages its own state  
- **Resource Management**: Proper disposal of Timer objects  

## 📸 Screenshot  

*Game interface showing colored bricks, score display, and active power-up*
<img width="996" height="787" alt="Screenshot 2025-08-09 014807" src="https://github.com/user-attachments/assets/e64a8995-bf43-4690-8e88-9d808df574d6" />



---


## 📂 Project Structure

```plaintext
BrickBreaker/
│
├── BrickBreaker.java     # Main entry point
├── GamePanel.java        # Game loop, UI rendering, game state logic
├── Ball.java             # Ball physics, collision logic
├── Brick.java            # Brick properties and visuals
├── Paddle.java           # Paddle movement and collision
└── README.md             # Project documentation (this file)
```
<img width="1918" height="1079" alt="Screenshot 2025-08-09 015122" src="https://github.com/user-attachments/assets/6f80c77e-3446-4cc3-ae6e-f105310d32ea" />


---

## 🧠 Concepts Demonstrated

- Java Swing GUI rendering  
- Timer-based game loop with `ActionListener`  
- Real-time keyboard input  
- Object-Oriented Programming:
  - Classes for each entity (Ball, Brick, Paddle)
  - Encapsulation and separation of concerns  
- Collision detection and physics  
- Dynamic difficulty scaling and game state management  

---

## 🏆 Showcase This Project

This project is a great portfolio piece to demonstrate:

- Strong command over Java and OOP  
- Understanding of basic game physics  
- Ability to design interactive GUI applications  
- Creative and modular coding practices  

> ✅ Add screenshots or a gameplay GIF for extra impact on your GitHub or resume!

---

## 📌 License

This project is open-source and free to use for learning or showcasing. Attribution is appreciated.

---

## 🙌 Credits

Developed by SAI SRIRAM KAMINENI
#

