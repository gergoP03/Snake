/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import databases.HighScores;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.*;

/**
 *
 * @author Gergo
 */
public class GameView extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = (int) (SCREEN_WIDTH / 20);
    static final int mapSize = (int) (SCREEN_WIDTH / UNIT_SIZE);
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) * UNIT_SIZE;
    static final int DELAY = 120;
    private GameModel model;
    private boolean running = false;
    private boolean paused = false;
    private Image background;
    private Timer timer;
    private Direction direction;
    private HighScores hs;
    Image appleImg;
    Image headUpImg;
    Image headDownImg;
    Image headLeftImg;
    Image headRightImg;
    Image rockImg;

    /**
     * Constructor
     * @param hs HighsScore class, needs to call a class with this that communicate with database
     */
    public GameView(HighScores hs) {
        this.hs = hs;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        //this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        background = new ImageIcon("data/img/background.png").getImage();
        appleImg = new ImageIcon("data/img/apple.png").getImage();
        headUpImg = new ImageIcon("data/img/head_up.png").getImage();
        headDownImg = new ImageIcon("data/img/head_down.png").getImage();
        headLeftImg = new ImageIcon("data/img/head_left.png").getImage();
        headRightImg = new ImageIcon("data/img/head_right.png").getImage();
        rockImg = new ImageIcon("data/img/rock.png").getImage();
        startGame();
    }

    /**
     * Starts the game for the first time
     * Sets up a model and a timer
     */
    public void startGame() {
        this.model = new GameModel(mapSize);
        direction = model.getSanke().getDirection();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }
    /**
     * Once the game started we always can restart the game
     * Disclaimer: Game starts instantly, so these are never null pointers because startGame method sets it up all
     */
    public void restart() {
        this.setFocusable(true);
        model.restart();
        direction = model.getSanke().getDirection();
        running = true;
        timer.start();
        NameFrame.getInstance(hs, this).disposeWindow();
    }
    /**
     * Overwritten paintComponent method of our panel
     * @param g Graphics we draw on
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        Apple apple = model.getApple();
        ArrayList<Mountain> mountains = model.getMountains();
        Snake snake = model.getSanke();
        Head head = snake.getHead();
        ArrayList<Body> body = snake.getBody();


        g.drawImage(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);

        g.drawImage(appleImg, apple.getXPos() * UNIT_SIZE, apple.getYPos() * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, null);

        g.drawImage(getHeadImage(snake), head.getXPos() * UNIT_SIZE, head.getYPos() * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, null);

        g.setColor(new Color(35, 177, 77));
        for (int i = 0; i < body.size(); i++) {
            g.fillOval(body.get(i).getXPos() * UNIT_SIZE, body.get(i).getYPos() * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        }

        for (int i = 0; i < mountains.size(); i++) {
            g.drawImage(rockImg, mountains.get(i).getXPos() * UNIT_SIZE, mountains.get(i).getYPos() * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, null);
            
        }

    }
    /**
     * We do not use this method, but we could call it when the game is over so I left it here
     */
    /*
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }
    */
    /*
        With these methods we can stop and the resume our game
        Disclaimer: The timer won't stop, so we count the seconds that spent under paused state too
    */
    public void stopGame() {
        paused = true;
    }

    public void resumeGame() {
        paused = false;
    }

    /**
     * 
     * Some Getter methods 
     */
    public int getPoints() {
        return model.getPoints();
    }

    public boolean gameEnded() {
        return !running;
    }
    /**
     * This methods returns the right image for the snake's head based on the snake's direction
     * @param snake Our lovely snake
     * @return The snake's head image
     */
    private Image getHeadImage(Snake snake) {
        switch (snake.getDirection()) {
            case UP:
                return headUpImg;
            case LEFT:
                return headLeftImg;
            case RIGHT:
                return headRightImg;
            case DOWN:
                return headDownImg;
        }
        return headUpImg;
    }

    /**
     * Moving the snake while it did not collide, if it did we summon a window 
     * where user can type their name and we save their results in our database
     * Disclaimer: User does not have to save everytime, if the close the window or
     * start new game we simply close the window and ignoring the saving process
     * @param e action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused) {
            running = model.doMove(direction);
        }
        if (!running) {
            timer.stop();
            NameFrame.getInstance(hs, this).showWindow();

        }
        repaint();
    }

    /**
     * Our custom KeyAdapter class
     * Overwritten keyPressed method changes the snake's direction if the move is legal
     * Disclaimer: We can not do a 180 degree move because it's instant game over
     */
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            Direction snakeDir = model.getSanke().getDirection();
            switch (e.getKeyChar()) {
                case 'a':
                    if (snakeDir != Direction.RIGHT) {
                        direction = Direction.LEFT;
                    }
                    break;
                case 'd':
                    if (snakeDir != Direction.LEFT) {
                        direction = Direction.RIGHT;
                    }
                    break;
                case 'w':
                    if (snakeDir != Direction.DOWN) {
                        direction = Direction.UP;
                    }
                    break;
                case 's':
                    if (snakeDir != Direction.UP) {
                        direction = Direction.DOWN;
                    }
                    break;
            }
        }
    }

}
