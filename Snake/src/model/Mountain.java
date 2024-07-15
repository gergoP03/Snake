/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gergo
 * Class represents a mountain (rock)
 */
public class Mountain {
    /*
    X and Y positions in the map on the model
    */
    private int xPos;
    private int yPos;
    /*
    Constructor
    */
    public Mountain(int xPos, int yPos){
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