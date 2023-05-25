package main.spacegame.service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HighScoreService {
    
    public static void main(String[] args) {
    
        //testing new players before game starting.
        HighScore appAdd = new HighScore();
        appAdd.addNewPlayer("Mustafa", 1924,2);
        appAdd.addNewPlayer("İbrahim", 1921,3);
        appAdd.addNewPlayer("ömer", 2023,4);
        appAdd.addNewPlayer("yiğit", 2072,3);
        appAdd.addNewPlayer("alperen", 1980,3);
    
        //testing deleting player when player wants to do.
        HighScore appDelete = new HighScore();
        appDelete.deleteOldPlayer("Mustafa");
        appDelete.deleteOldPlayer("İbrahim");
        appDelete.deleteOldPlayer("ömer");
        appDelete.deleteOldPlayer("yiğit");
        appDelete.deleteOldPlayer("alperen");
    
        //testing update score when killing enemies and gathering items from environment.
        HighScore appUpdateScore = new HighScore();
        appUpdateScore.updatePlayerScore("Mustafa", 1924,2);
        appUpdateScore.updatePlayerScore("İbrahim", 1921,3);
        appUpdateScore.updatePlayerScore("ömer", 2023,4);
        appUpdateScore.updatePlayerScore("yiğit", 2072,3);
        appUpdateScore.updatePlayerScore("alperen", 1980,3);
    
    }

    //finding database location and build according to instructions.
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/mustafacaglar/Desktop/Data/chinook.db";
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //update score when killing enemies and gathering items from environment.
    public void updatePlayerScore(String PlayerName, int PlayerScore,int PlayerCurrentLevel) {
        String sql = "UPDATE PlayerInfo SET PlayerName = ? , "
                + "PlayerScore = ? "
                + "WHERE PlayerCurrentLevel = ?";

        try (Connection conn = this.connect();

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, PlayerName);
            pstmt.setInt(2, PlayerScore);
            pstmt.setInt(3, PlayerCurrentLevel);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //new player adding when starting a new game.
    public void addNewPlayer(String PlayerName, int PlayerScore,int PlayerCurrentLevel) 
    {

        String sql = "INSERT INTO PlayerInfo(PlayerName,PlayerScore,PlayerCurrentLevel) VALUES(?,?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, PlayerName);
            pstmt.setInt(2, PlayerScore);
            pstmt.setInt(3, PlayerCurrentLevel);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //deleting player when player wants to do.
    public void deleteOldPlayer(String PlayerName) {
        
        String sql = "DELETE FROM PlayerInfo WHERE PlayerName = ?";

        try (Connection conn = this.connect();

                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, PlayerName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
