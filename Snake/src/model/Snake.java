/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Gergo
 * Class represents the snake
 */
public class Snake {
    private Head head;
    private ArrayList<Body> body;
    private int newElementX = 0;
    private int newElementY = 0;
    private int startX;
    private int startY;
    private Direction startD;
    
    /*
    Constructor
    */
    public Snake(int xPos, int yPos, Direction direction){
        this.startX = xPos;
        this.startY = yPos;
        this.startD = direction;
        this.head = new Head(xPos, yPos, direction);
        this.body = new ArrayList<Body>();
        getFirstBodyCoordinate(startX, startY, startD);
        addBody();
        
    }
    /*
    This methods reset's the snake to it's starting position with 1 body unit
    */
    public void reset(){
        head.setXPos(startX);
        head.setYPos(startY);
        body.clear();
        getFirstBodyCoordinate(startX, startY, startD);
        addBody();
    }
    
    
    /**
     * Snake's movement
     * @param direction direction snake's need to move (user can change it)
     */
    public void move(Direction direction){
        head.setDirection(direction);
        newElementX = body.get(body.size()-1).getXPos();
        newElementY = body.get(body.size()-1).getYPos();
        for(int i = body.size()-1; i > 0; i--){
            Body prev = body.get(i-1);
            body.get(i).setXPos(prev.getXPos());
            body.get(i).setYPos(prev.getYPos());
        }
        //Mindig van a body-nak 0. indexű eleme
        body.get(0).setXPos(head.getXPos());
        body.get(0).setYPos(head.getYPos());
        
        switch(direction){
            case UP:
                head.setYPos(head.getYPos() - 1);
                break;
            case DOWN:
                head.setYPos(head.getYPos() + 1);
                break;
            case LEFT:
                head.setXPos(head.getXPos() - 1);
                break;
            case RIGHT:
                head.setXPos(head.getXPos() + 1);
                break;
            
        }
        
    }
    
    /*
    Some Getter/Setter methods
    */
    public Head getHead(){
        return head;
    }
    
    public ArrayList<Body> getBody(){
        return body;
    }
    
    public void addBody(){
        this.body.add(new Body(newElementX, newElementY));
    }
    
    public Direction getDirection(){
        return head.getDirection();
    }
    
    public void setDirection(Direction dir){
        head.setDirection(dir);
    }
    /**
     * This methods generates the first body element coordinate based on the random direction snake starts with
     * @param xPos Head's x position
     * @param yPos Head's y positions
     * @param direction Head's direction
     */
    public void getFirstBodyCoordinate(int xPos, int yPos, Direction direction){
        switch(direction){
            case UP:
                newElementX = xPos;
                newElementY = yPos + 1;
                break;
            case DOWN:
                newElementX = xPos;
                newElementY = yPos - 1;
                break;
            case LEFT:
                newElementX = xPos + 1;
                newElementY = yPos;
                break;
            case RIGHT:
                newElementX = xPos - 1;
                newElementY = yPos;
                break;
        }
    }
}
