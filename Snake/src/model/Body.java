/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Gergo
 * Class that represents the snake's body in the model
 */
public class Body {
    /*
    X and Y positions in the map on the model
    */
    private int xPos;
    private int yPos;
    
    
    /*
    Constructor
    */
    public Body(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    
    /*
    Getter/Setter methods
    */
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
