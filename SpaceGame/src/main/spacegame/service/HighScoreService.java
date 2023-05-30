package main.spacegame.service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.almasb.fxgl.core.EngineService;

public class HighScoreService  extends EngineService{
    
    public static void main(String[] args) {
    
        //testing new players before game starting.
        HighScoreService appAdd = new HighScoreService();
        // insert three new rows
        appAdd.addToDatabase(3000);
        appAdd.addToDatabase(4000);
        appAdd.addToDatabase(5000);
        
        //get all datas from SQLite database
        appAdd.getScore();

    }

    //finding database location and build according to instructions.
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/mustafacaglar/Desktop/Data/player.db";
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


  public void getScore()
    {
        String linkToMethod = "SELECT score FROM player";
        
        try (Connection connection = this.connect();
             Statement statement  = connection.createStatement();
             ResultSet resultSet    = statement.executeQuery(linkToMethod))
             {
            
           
            while (resultSet.next()) 
            {
                //burada
                System.out.println(resultSet.getInt("score"));
            }
        } 
        catch (SQLException Exception) {}
    }
    
    public void updatePlayerScore( int score) 
    {
        String sql = "INSERT INTO player(score) VALUES(?)";

        try (Connection conn = this.connect();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, score);

                    preparedStatement.executeUpdate();
        } 
        catch (SQLException Exception) {}
    }
}
