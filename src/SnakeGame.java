import java.awt.*;
import javax.swing.*;

public class SnakeGame {
    JFrame frame;

    SnakeGame() {
        frame = new JFrame("Snake Feeding Game");
        frame.setBounds(10,10,905,700);
        GamePanel gamepanel = new GamePanel();
        gamepanel.setBackground(Color.gray);
        frame.add(gamepanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}
