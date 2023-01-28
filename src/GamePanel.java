import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener,ActionListener {
    //    Creating Image Objects
    ImageIcon snakeTitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon snakeBody = new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon right = new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon left = new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon up = new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon down = new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon food = new ImageIcon(getClass().getResource("enemy.png"));

    int[] snakeX = new int[750];
    int[] snakeY = new int[750];
    int move = 0;
    int lengthOfSnake = 3;

    boolean isUp = false;
    boolean isDown = false;
    boolean isLeft = false;
    boolean isRight = true;

    int[] posX = {100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] posY = {100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    Random random = new Random();
    //    int foodX = random.nextInt(800 - 100)+100;
//    int foodY = random.nextInt(800 - 100)+100;
    int foodX = 150;
    int foodY = 150;

    int score = 0;
    Timer time;

    boolean isGameOver = false;

    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        time = new Timer(190,this);
        time.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
//      setup Title rectangle
        g.drawRect(24,10,851,55);
//      setup playing field
        g.drawRect(24,80,851,575);
        snakeTitle.paintIcon(this,g,25,11);
        g.setColor(Color.BLACK);
        g.fillRect(4,80,851,575);
        if(move == 0){
            snakeX[0] = 100;
            snakeX[1] = 75;
            snakeX[2] = 50;

            snakeY[0] = 100;
            snakeY[1] = 100;
            snakeY[2] = 100;
        }
//      Initialized Snake heads for right, left, up and down
        if(isRight)
            right.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isLeft)
            left.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isDown)
            down.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isUp)
            up.paintIcon(this,g,snakeX[0],snakeY[0]);
//      Starting of the Game length of the Snake has fixed
//      already assigned the body of the snakes, this loop is used for adding the image of the body
        for (int i = 1; i < lengthOfSnake; i++){
            snakeBody.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
///     Generate foods For Snake
        food.paintIcon(this,g,foodX,foodY);
        if(isGameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("Game Over", 380, 300);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 20));
            g.drawString("Press Space To Restart The Game", 320,360);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("ITALIC",Font.PLAIN, 15));
        g.drawString("Score "+score, 750, 30);
        g.drawString("Length "+lengthOfSnake, 750, 50);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = lengthOfSnake-1; i > 0; i--){
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }
        if(isLeft){
            snakeX[0] = snakeX[0] - 25;
        }
        if(isRight)
        {
            snakeX[0] = snakeX[0] + 25;
        }
        if(isUp){
            snakeY[0] = snakeY[0] - 25;
        }
        if(isDown)
        {
            snakeY[0] = snakeY[0] + 25;
        }
        if(snakeX[0]>850) snakeX[0]=25;
        if(snakeX[0]<25)  snakeX[0]=850;
        if(snakeY[0]>625) snakeY[0]=75;
        if(snakeY[0]<75)  snakeY[0]=625;
        CollisionWithFood();
        CollisionWithBody();
        repaint();
    }

    // Moving the Snake By Key - Left, Right, Up and Down arrow and Space For Restart the Game
    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE && isGameOver){
            restart();
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!isLeft)){
            isLeft = false;
            isUp = false;
            isDown = false;
            isRight = true;
            move++;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && (!isRight)){
            isLeft = true;
            isUp = false;
            isDown = false;
            isRight = false;
            move++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && (!isDown)){
            isLeft = false;
            isUp = true;
            isDown = false;
            isRight = false;
            move++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && (!isUp)){
            isLeft = false;
            isUp = false;
            isDown = true;
            isRight = false;
            move++;
        }
    }

    private  void restart(){
        isGameOver = false;
        move = 0;
        score = 0;
        lengthOfSnake = 3;
        isLeft = false;
        isRight = true;
        isUp = false;
        isDown = false;
        time.start();
        newFood();
        repaint();
    }

    private void CollisionWithBody() {
        for(int i = lengthOfSnake - 1; i > 0; i--){
            if(snakeX[i] == snakeX[0] && snakeY[i] == snakeY[0]){
                time.stop();
                isGameOver = true;
            }
        }
    }

    private void CollisionWithFood() {
        if(snakeX[0] == foodX && snakeY[0] == foodY){
            newFood();
            lengthOfSnake++;
            score++;
//          Copy image for making the body of the snake
            snakeX[lengthOfSnake-1] = snakeX[lengthOfSnake-2];
            snakeY[lengthOfSnake-1] = snakeY[lengthOfSnake-2];
        }
    }

    private void newFood() {
        foodX = posX[random.nextInt(posX.length-1)];
        foodY = posX[random.nextInt(posY.length-1)];
        for (int i = lengthOfSnake - 1; i >= 0; i--){
            if(snakeX[i] == foodX && snakeY[i] == foodY){
                newFood();
            }
        }
    }
}
