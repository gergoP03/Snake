/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Gergo This class functions as the brain of the game
 */
public class GameModel {

    private final Snake snake;
    private final Apple apple;
    private final int mapSize;
    private ArrayList<Mountain> mountains;
    private int points;

    /**
     * Constructor
     *
     * @param mapSize Size of the map (maximum y and x coordinates)
     */
    public GameModel(int mapSize) {
        this.mapSize = mapSize;
        this.mountains = new ArrayList<Mountain>();
        snake = new Snake(mapSize / 2, mapSize / 2, getRandomDirection());
        readMountainsCoordinates();
        apple = new Apple(0, 0);
        newApple();
        points = 0;

    }

    /**
     * This method called when user restarts the game, it resets everything
     */
    public void restart() {
        points = 0;
        snake.reset();
        snake.setDirection(getRandomDirection());
        mountains.clear();
        readMountainsCoordinates();
        newApple();
    }

    /**
     * This method makes the move of the snake and also checks for collisions
     * when the game ends and also when it eats an apple
     *
     * @param direction Direction we get from the Game View (user input)
     * @return Collision that ends the game (if it's true then the game is still
     * running)
     */
    public boolean doMove(Direction direction) {
        snake.move(direction);
        if (checkApple()) {
            points++;
            snake.addBody();
            newApple();
        }
        return !checkCollisions();

    }

    /**
     * Checks the collisions
     *
     * @return True if snake's head collided with something, otherwise it
     * returns false
     */
    public boolean checkCollisions() {
        if (snakeOutOfMap()) {
            return true;
        }
        Head head = snake.getHead();
        ArrayList<Body> body = snake.getBody();
        for (Body b : body) {
            if (b.getXPos() == head.getXPos() && b.getYPos() == head.getYPos()) {
                return true;
            }
        }
        for (Mountain m : mountains) {
            if (m.getXPos() == head.getXPos() && m.getYPos() == head.getYPos()) {
                return true;
            }
        }
        return false;

    }

    /**
     * Checks if the snake left the map
     *
     * @return snake left the map or not
     */
    public boolean snakeOutOfMap() {
        return (snake.getHead().getXPos() < 0 || snake.getHead().getXPos() >= mapSize
                || snake.getHead().getYPos() < 0 || snake.getHead().getYPos() >= mapSize);
    }

    /**
     * Checks if the snake ate the apple
     *
     * @return snake's head have the same pos with current apple
     */
    public boolean checkApple() {
        if (snake.getHead().getXPos() == apple.getXPos()
                && snake.getHead().getYPos() == apple.getYPos()) {
            return true;
        }
        return false;
    }

    /**
     * This method is for generating mountains and apple, we need to check what
     * field is free
     *
     * @param row field's Y pos
     * @param col field's X pos
     * @return true if the field is free, otherwise false
     */
    public boolean isFieldOccupied(int row, int col) {
        Head head = snake.getHead();
        ArrayList<Body> body = snake.getBody();

        if (head.getYPos() == row && head.getXPos() == col) {
            return true;
        }
        for (Body b : body) {
            if (b.getYPos() == row && b.getXPos() == col) {
                return true;
            }
        }
        for (Mountain m : mountains) {
            if (m.getYPos() == row && m.getXPos() == col) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get's a random direction for the snake at the start
     *
     * @return Generated direction
     */
    private Direction getRandomDirection() {
        Random random = new Random();
        int rndnum = random.nextInt(4);

        switch (rndnum) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
        }
        return Direction.UP;
    }

    /**
     * This method generates new apple
     */
    public void newApple() {
        ArrayList<Integer> avaibleXs = new ArrayList<Integer>();
        ArrayList<Integer> avaibleYs = new ArrayList<Integer>();

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (!isFieldOccupied(i, j)) {
                    avaibleXs.add(j);
                    avaibleYs.add(i);
                }
            }
        }
        if (avaibleXs.size() > 0) {
            Random random = new Random();
            int index = random.nextInt(avaibleXs.size());

            int generatedX = avaibleXs.get(index);
            int generatedY = avaibleYs.get(index);

            if (this.apple == null) {
                new Apple(generatedX, generatedY);
            } else {
                apple.setXPos(generatedX);
                apple.setYPos(generatedY);
            }
        }

    }

    /**
     * this method reads a map from a file (1-10) which contains mountain
     * coordinates and fills the map with mountains Disclaimer: The file
     * contains only valid coordinates that is not nearby the snake (checked
     * manually)
     */
    public void readMountainsCoordinates() {
        Random rnd = new Random();
        int mapNum = rnd.nextInt(10) + 1;
        String filePath = "data/txtk/map" + mapNum + ".txt";

        File file = new File(filePath);

        try (Scanner sc = new Scanner(file)) {
            String line;
            int xCoord;
            int yCoord;
            // Read each line from the file until the end (each line contains 2 numbers, checked manually)
            while (sc.hasNextInt()) {
                yCoord = sc.nextInt();
                xCoord = sc.nextInt();
                mountains.add(new Mountain(xCoord, yCoord));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // Handle the exception appropriately (e.g., print an error message)
            e.printStackTrace();
        }
    }

    /*
    public void generateMountains(){   
        Random random = new Random();
        int numOfMountains = random.nextInt((int)Math.floor(mapSize/3)) + 3;
        System.out.println("hegyek szama: " + numOfMountains);
        int randomX;
        int randomY;
        do{
            randomX = random.nextInt(mapSize);
            randomY = random.nextInt(mapSize);
            Head head = snake.getHead();
            if((randomX > 12 || randomX < 8) && (randomX > 12 || randomX < 8)
                && !isFieldOccupied(randomX-1, randomY-1) && !isFieldOccupied(randomX-1, randomY) && !isFieldOccupied(randomX-1, randomY+1)
                && !isFieldOccupied(randomX, randomY-1) && !isFieldOccupied(randomX, randomY) && !isFieldOccupied(randomX, randomY+1)
                && !isFieldOccupied(randomX+1, randomY-1) && !isFieldOccupied(randomX+1, randomY) && !isFieldOccupied(randomX+1, randomY+1))
            {
                mountains.add(new Mountain(randomX, randomY));    
            }
        }while(mountains.size() < numOfMountains);        
    }
     */

    /**
     *
     * Some Getter/Setter methods
     */
    public ArrayList<Mountain> getMountains() {
        return mountains;
    }

    public Snake getSanke() {
        return snake;
    }

    public Apple getApple() {
        return apple;
    }

    public int getPoints() {
        return points;
    }
}
