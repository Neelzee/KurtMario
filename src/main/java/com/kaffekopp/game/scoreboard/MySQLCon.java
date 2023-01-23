package com.kaffekopp.game.scoreboard;
import com.badlogic.gdx.Gdx;
import com.kaffekopp.game.constants.FilePaths;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class which handles all communication with MySQL server
 *
 * @author Thomas Børdal
 */
public class MySQLCon {
    private PreparedStatement stmt;
    private Connection con;

    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";

    private static final String jdbcDbUrl = "jdbc:mysql://34.88.70.11:3306/kurtmario_scoreboard?useSSL=true&sslMode=VERIFY_CA"; // Enforces SSL and Verifies CA
    private static final String jdbcUser = "scoreboard";
    private static final String jdbcPass = "2QbS*FddwNa9M@AYIj6UwLmb";

    /**
     * Establishes a secure connection with the MySQL server
     * Uses the truststore to authenticate the server, and keystore to authenticate the client
     * Needs two different file paths for the stores, one if it's ran in an IDE and another one if it's built with Maven in terminal
     *
     * Hardening done: The MySQL server only allows SSL connections and 'scoreboard' db user has only least privilege needed to be able to
     * perform the queries and updates which is stated in this class.
     * We wanted to find a way to avoid cleartext credentials in the source code, but we ran out of time.
     *
     * @author Thomas Børdal
     */
    private Connection con() {
        try {
            if (Gdx.files.internal(FilePaths.TRUSTSTORE).exists()) {
                System.setProperty("javax.net.ssl.trustStore", FilePaths.TRUSTSTORE);
                System.setProperty("javax.net.ssl.keyStore", FilePaths.KEYSTORE);
            } else {
                System.setProperty("javax.net.ssl.trustStore", FilePaths.TRUSTSTORE_IDE);
                System.setProperty("javax.net.ssl.keyStore", FilePaths.KEYSTORE_IDE);
            }
            System.setProperty("javax.net.ssl.trustStorePassword", "Jn3ICCShIXqKpaBY");
            System.setProperty("javax.net.ssl.keyStorePassword","oShACvV1f4134AKf");
            Class.forName(jdbcDriver);
            return DriverManager.getConnection(jdbcDbUrl, jdbcUser, jdbcPass);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the top 10 on the scoreboard
     * List is sorted by time ascending, then sorted by score descending. Sorting happens on serverside.
     *
     * @return ArrayList<ArrayList<Object>>
     * @author Thomas Børdal & Idar Hansen
     */
    public ArrayList<ArrayList<Object>> getScoreBoard() {
        try {
            con = con();
            stmt = con.prepareStatement("SELECT username, time, score FROM high_scores ORDER BY time, score DESC LIMIT 10");
            ResultSet rs = stmt.executeQuery();
            ArrayList<ArrayList<Object>> list = new ArrayList<>();
            while(rs.next()) {
                ArrayList<Object> subList = new ArrayList<>();
                subList.add(rs.getString(1));
                subList.add(rs.getFloat(2));
                subList.add(rs.getInt(3));
                list.add(subList);
            }
            con.close();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Method for submitting the player's score to the scoreboard
     *
     * @author Thomas Børdal
     */
    public void submitScore(UserSRInfo user) {
        try {
            String username = user.username;
            float time = user.time;
            int score = user.score;
            con = con();
            stmt = con.prepareStatement("INSERT INTO high_scores VALUES (NULL ,'"+username+"','"+time+"','"+score+"')");
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method for updating the player's score to the scoreboard
     * (only used if it is better than the one already there)
     *
     * @author Thomas Børdal
     */
    public void updateScore(UserSRInfo user) {
        try {
            String username = user.username;
            float time = user.time;
            int score = user.score;
            con = con();
            stmt = con.prepareStatement("UPDATE high_scores SET time = '"+time+"', score = '"+score+"' WHERE username = '"+username+"'");
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
