package main.spacegame.service;


import com.almasb.fxgl.core.EngineService;
import com.almasb.fxgl.core.serialization.Bundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class HighScoreService extends EngineService {

    private IntegerProperty score = new SimpleIntegerProperty();
    private int numScoresToKeep = 10;

    private ArrayList<HighScoreData> highScores = new ArrayList<>();

    public IntegerProperty scoreProperty() {
        return score;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public void incrementScore(int value) {
        setScore(getScore() + value);
    }

    /**
     * Remember current score with given tag and reset score to 0.
     */
    public void commit(String tag) {
        highScores.add(new HighScoreData(tag, getScore()));

        score.unbind();
        setScore(0);

        updateScores();
    }

    /**
     * @return list of high scores sorted in descending order
     */
    public void getHighScores()
    {
        String linkToMethod = "Select * From player ORDER BY score desc";
        
        String nameAndScore=" ";
        
        try (Connection connection = this.connect();
             Statement statement  = connection.createStatement();
             ResultSet resultSet    = statement.executeQuery(linkToMethod))
             {
            
           
            while (resultSet.next()) 
            {
                System.out.println(resultSet.getString("playername")+" "+resultSet.getInt("score"));
                nameAndScore=resultSet.getString("playername")+" "+resultSet.getInt("score")+" "+nameAndScore;
            }
            return nameAndScore;
        } 
        catch (SQLException Exception) {System.out.println("sıkıntı");}
    }

    public int getNumScoresToKeep() {
        return numScoresToKeep;
    }

    public void setNumScoresToKeep(int numScoresToKeep) {
        this.numScoresToKeep = numScoresToKeep;

        updateScores();
    }

    public void updateScores( String playername,int score) 
    {
        String sql = "INSERT INTO player(playername,score) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1,playername);
                    preparedStatement.setInt(2, score);

                    preparedStatement.executeUpdate();
        } 
        catch (SQLException Exception) {}
    }

    public static class HighScoreData implements Serializable {
        private static final long serialVersionUID = 1;

        private final String playername;
        private final int score;

        private HighScoreData(String playername, int score) {
            this.playername = playernameplayername;
            this.score = score;
        }

        public String getplayername() {
            return playername;
        }

        public int getScore() {
            return score;
        }
    }
}
