/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import databases.HighScore;
import databases.HighScores;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Gergo
 * This class list us the top 10 player's name and score
 */
public class HighScoresFrame extends JFrame {

    private HighScores highScores;

    public HighScoresFrame(HighScores hs) {
        this.highScores = hs;
        initUI();
    }

    private void initUI() {
        setTitle("High Scores");
        setSize(300, 400);

        // Create a panel to hold the high scores
        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));

        // Get the top 10 high scores
        try {
            ArrayList<HighScore> topScores = highScores.getHighScores();

            // Add each high score to the panel
            for (int i = 0; i < topScores.size(); i++) {
                HighScore score = topScores.get(i);
                JLabel label = new JLabel((i + 1) + ". " + score.getName() + ": " + score.getScore());
                scoresPanel.add(label);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Add the scores panel to the frame
        getContentPane().add(scoresPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
