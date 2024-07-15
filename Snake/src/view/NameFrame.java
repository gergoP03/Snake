/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import databases.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Gergo
 * This frame asks the user for their name
 * This class is singleton
 */
public class NameFrame extends JFrame{
    
    private static NameFrame instance;
    JLabel gameOverLabel;
    JLabel pointsLabel;
    JLabel nameLabel;
    
    /**
     * Sinbgleton pattern
     * @param hs Highscores (to communicate with database)
     * @param gv GameView (to get the point)
     * @return the frame
     */
    public static NameFrame getInstance(HighScores hs, GameView gv){
        if(instance == null){
            instance = new NameFrame(hs, gv);
        }
        instance.pointsLabel.setText("Az elért pontszámod: " + gv.getPoints());
        return instance;
    } 
    /**
     * Private constructor
     * Gbc used for design
     * @param hs Highscores to communcate with database
     * @param gv GameView to get the user's points
     */
    private NameFrame(HighScores hs, GameView gv){
        this.setSize(300, 200);
        this.setLayout(new GridBagLayout());
        this.setTitle("Eredmény mentése");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 5, 0); 
        
        gameOverLabel = new JLabel("A játéknak vége!");
        this.add(gameOverLabel, gbc);
        
        pointsLabel = new JLabel("Az elért pontszámod: " + gv.getPoints());
        this.add(pointsLabel, gbc);

        nameLabel = new JLabel("Add meg a játékosneved:");
        this.add(nameLabel, gbc);

        JTextField playerNameField = new JTextField(15);
        this.add(playerNameField, gbc);

        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(20, 0, 0, 0);

        JButton saveButton = new JButton("Mentés");
        saveButton.addActionListener(e -> {
            String playerName = playerNameField.getText();
            try{
                hs.putHighScore(playerName, gv.getPoints());
                dispose();
            }
            catch(SQLException ex){
                Logger.getLogger(NameFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        this.add(saveButton, gbc);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    /*
    Methods to show or dispose the window
    */
    public void showWindow(){
        this.setVisible(true);
    }
    
    public void disposeWindow(){
        dispose();
    }
}
