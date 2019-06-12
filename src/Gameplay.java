import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private int[] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];

    private boolean left, right, up, down, paused, gameover = false;

    private ImageIcon titleImage, rightmouth, leftmouth, downmouth, upmouth, snakeimage, enemyimage;
    private int moves = 0;
    private String lastDirection = "right";

    private int [] enemyXpos = new int [100];
    private int [] enemyYpos = new int [100];
//        private int [] enemyXpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
//        private int [] enemyYpos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    Random random = new Random();
    //        private int xpos = enemyXpos[(random.nextInt(34))];
//        private int ypos = enemyYpos[(random.nextInt(23))];
    private int xpos = random.nextInt(34);
    private int ypos = random.nextInt(23);

    private Timer timer;
    private int delay = 100;

    private int lengthOfSnake = 3;
    private int score = 0;




    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        createEnemies();
        timer = new Timer(delay, this);
        timer.start();
    }

    private void createEnemies() {
        for (int i = 0; i < 34; i++){
            enemyXpos[i] = 25*i+25;
        }
        for (int i = 0; i < 23; i++){
            enemyYpos[i] = 25*i+75;
        }
    }

    public void paint(Graphics g){

        if(moves == 0){
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;

            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
        }
        //draw titleimage border
        g.setColor(Color.white);
        g.drawRect(24,10, 851, 55);

        //draw the title image
        titleImage = new ImageIcon("assets/snaketitle.jpg");
        titleImage.paintIcon(this, g, 25, 11);

        //draw border for gameplay
        g.setColor(Color.white);
        g.drawRect(24, 74, 851, 576);

        //draw background for the gameplay
        g.setColor(Color.black);
        g.fillRect(25,75, 850, 575);

//                //draw the snake
//                rightmouth = new ImageIcon("assets/rightmouth.png");
//                rightmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);

        //draw scores
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Use arrow keys to move", 50, 30);
        g.drawString("Use SPACE to pause", 50, 50);
        g.drawString("Scores: " + score, 780, 30);
        g.drawString("Length: "+ lengthOfSnake, 780, 50);

        for (int i =0; i < lengthOfSnake; i++){
            if(i == 0 && up){
                upmouth = new ImageIcon("assets/upmouth.png");
                upmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                lastDirection = "up";
            }
            if(i == 0 && down){
                downmouth = new ImageIcon("assets/downmouth.png");
                downmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                lastDirection = "down";
            }
            if(i == 0 && left){
                leftmouth = new ImageIcon("assets/leftmouth.png");
                leftmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                lastDirection = "left";
            }
            if(i == 0 && right){
                rightmouth = new ImageIcon("assets/rightmouth.png");
                rightmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                lastDirection = "right";
            }
            if(i == 0 && !up && !down && !left && !right){
                switch (lastDirection){
                    case "up":
                        upmouth = new ImageIcon("assets/upmouth.png");
                        upmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                        break;
                    case "down":
                        downmouth = new ImageIcon("assets/downmouth.png");
                        downmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                        break;
                    case "left":
                        leftmouth = new ImageIcon("assets/leftmouth.png");
                        leftmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                        break;
                    case "right":
                        rightmouth = new ImageIcon("assets/rightmouth.png");
                        rightmouth.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
                        break;
                }
            }

            if(i != 0){
                snakeimage = new ImageIcon("assets/snakeimage.png");
                snakeimage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
            }
        }

        enemyimage = new ImageIcon("assets/enemy.png");
//                enemyimage.paintIcon(this, g, enemyXpos[eXpos], enemyYpos[eYpos]);
        enemyimage.paintIcon(this, g, enemyXpos[xpos], enemyYpos[ypos]);

        if((enemyXpos[xpos] == snakeXlength[0]) && (enemyYpos[ypos] == snakeYlength[0])){
            score++;
            lengthOfSnake++;
            xpos = random.nextInt(34);
            ypos = random.nextInt(23);
        }

        for(int i = 1; i < lengthOfSnake; i++){
            if(snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0]){
                up = false;
                down = false;
                left = false;
                right = false;
                gameover = true;

                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("Game Over", 300, 300);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.drawString("Press Enter to RESTART", 315, 340);
            }
        }

        if (paused){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Game is paused, press SPACE to restart", 285, 340);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(up){
            for(int i = lengthOfSnake-1; i >= 0; i--){
                snakeXlength[i+1] = snakeXlength[i];
            }
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeYlength[i] = snakeYlength[i] - 25;
                } else {
                    snakeYlength[i] = snakeYlength[i-1];
                }
                if(snakeYlength[i] < 75){
                    snakeYlength[i] = 625;
                }
            }
        }
        if(down){
            for(int i = lengthOfSnake-1; i >= 0; i--){
                snakeXlength[i+1] = snakeXlength[i];
            }
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeYlength[i] = snakeYlength[i] + 25;
                } else {
                    snakeYlength[i] = snakeYlength[i-1];
                }
                if(snakeYlength[i] > 625){
                    snakeYlength[i] = 75;
                }
            }
        }
        if(left){
            for(int i = lengthOfSnake-1; i >= 0; i--){
                snakeYlength[i+1] = snakeYlength[i];
            }
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeXlength[i] = snakeXlength[i] - 25;
                } else {
                    snakeXlength[i] = snakeXlength[i-1];
                }
                if(snakeXlength[i] < 25){
                    snakeXlength[i] = 850;
                }
            }
        }
        if(right){
            for(int i = lengthOfSnake-1; i >= 0; i--){
                snakeYlength[i+1] = snakeYlength[i];
            }
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeXlength[i] = snakeXlength[i] + 25;
                } else {
                    snakeXlength[i] = snakeXlength[i-1];
                }
                if(snakeXlength[i] > 850){
                    snakeXlength[i] = 25;
                }
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                if (!paused && !gameover){
                    moves ++;
                    up = true;
                    down = false;
                    left = false;
                    right = false;
                }
                break;
            case KeyEvent.VK_DOWN:
                if(!paused && !gameover){
                    moves ++;
                    up = false;
                    down = true;
                    left = false;
                    right = false;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!paused && !gameover){
                    moves ++;
                    up = false;
                    down = false;
                    left = true;
                    right = false;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!paused && !gameover){
                    moves ++;
                    up = false;
                    down = false;
                    left = false;
                    right = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                if(!paused && !gameover){
                    up = false;
                    down = false;
                    left = false;
                    right = false;
                    paused = true;
                    break;
                } else {
                    if (!gameover){
                        paused = false;
                        switch(lastDirection){
                            case "up": up = true;
                                break;
                            case "down": down = true;
                                break;
                            case "left": left = true;
                                break;
                            case "right": right = true;
                                break;
                        }
                    }
                }break;
            case KeyEvent.VK_ENTER:
                if (gameover){
                    moves = 0;
                    up = false;
                    down = false;
                    left = false;
                    right = true;
                    lengthOfSnake = 3;
                    score = 0;
                    gameover = false;
                    xpos = random.nextInt(34);
                    ypos = random.nextInt(23);
                    repaint();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
