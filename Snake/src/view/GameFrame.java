/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import databases.HighScores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

/**
 *
 * @author Gergo
 */
public class GameFrame extends JFrame {

    private GameView panel;
    private long startTime;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu highScoresMenu;
    private Timer timer;
    private JMenuItem newItem;
    private JMenuItem stop;
    private JMenuItem resume;
    private JMenuItem highScoresMenuItem;
    private JLabel playerStats;
    private HighScores hs;

    public GameFrame() {
        try {
            hs = new HighScores(10);
        } catch (SQLException ex) {
            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        panel = new GameView(hs);

        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // Create a menu
        menu = new JMenu("Menu");
        highScoresMenu = new JMenu("Ranglista");
        menuBar.add(menu);
        menuBar.add(highScoresMenu);
        

        // Create menu items
        newItem = new JMenuItem("Új játék");
        stop = new JMenuItem("Stop");
        resume = new JMenuItem("Folytat");
        
        highScoresMenuItem = new JMenuItem("Top10");

        playerStats = new JLabel("");

        newItem.addActionListener(e -> restartGame());
        stop.addActionListener(e -> stopGame());
        resume.addActionListener(e -> resumeGame());
        highScoresMenuItem.addActionListener(e -> newHighScoresFrame());

        // Add menu items to the menu
        menu.add(newItem);
        menu.add(stop);
        menu.add(resume);
        menu.addSeparator(); // Separator line between items
        highScoresMenu.add(highScoresMenuItem);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(playerStats);
        setupTimer();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
    }

    public void setupTimer() {
        startTime = System.currentTimeMillis();
        timer = new Timer(1000, e -> updateTimer());
        timer.start();
    }

    private void updateTimer() {
        playerStats.setText("Pontszám: " + panel.getPoints() + ", Idő: " + getSeconds() + "      ");
        if(panel.gameEnded()){
            timer.stop();
            
        }
    }

    public long getSeconds() {
        long endTime = System.currentTimeMillis();
        long elapsedTimeInSeconds = (endTime - startTime) / 1000;
        return elapsedTimeInSeconds;
    }

    private void restartGame() {
        panel.restart();
        setupTimer();
        
    }

    private void stopGame() {
        panel.stopGame();
    }

    private void resumeGame() {
        panel.resumeGame();
    }
    
    private void newHighScoresFrame(){
        new HighScoresFrame(hs);
    }    
}
