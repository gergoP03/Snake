/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gergo
 * Class represents the snake's head
 */
public class Head {
    /*
    X and Y positions in the map on the model
    Head's direction for the model and the view
    */
    private int xPos;
    private int yPos;
    private Direction direction;
    
    /*
    Constructor
    */
    public Head(int xPos, int yPos, Direction direction){
        this.direction = direction;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    /*
    Getter/Setter methods
    */
    public Direction getDirection(){
        return direction;
    }
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    public void setYPos(int yPos){
        this.yPos = yPos;
    }
}
