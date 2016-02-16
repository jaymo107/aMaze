package com.amaze.main;

import java.sql.*;

public class dbCon {
    //JDBC connection stuff
    private static final String dbDriver = "org.mariadb.jdbc.Driver";
    private static final String dbLoc = "jdbc:mysql://178.62.72.43:3306/aMazeDB";
    //Credentials for the database
    private static final String username = "phpuser";
    private static final String password = "aMaze";
    private Connection conn = null;
    private Statement stmt = null;

    public dbCon() {
        try {
            Class.forName(dbDriver);

            conn = DriverManager.getConnection(dbLoc, username, password);

        } catch(SQLException se){
            //SQL exception handler
            se.printStackTrace();
        } catch(Exception e){
            //Normal exception handler
            e.printStackTrace();
        }
    }

    public void uploadResult(String username, int score, int level, String compTime) {
        int uid = 0;

        String uidGet = "SELECT `uid` FROM `users` WHERE `uName` = '" + username + "';";
        String uploadName = "INSERT INTO `users`" + "(`uName`)" + "VALUES" + "('" + username + "');";
        String uploadData = "INSERT INTO `leaderboard`" + " (`uid`, `levelNo`, `score`, `compTime`)" + "VALUES" + "('" + uid + "','" + level + "','" + score + "','" + compTime + "');";
        
        System.out.println(uidGet);
        System.out.println(uploadName);
        System.out.println(uploadData);

        try {
            stmt = conn.createStatement();
            stmt.executeQuery(uploadName);
            ResultSet rs = stmt.executeQuery(uidGet);
            while(rs.next()) {
                uid = rs.getInt("uid");
            }
            stmt.executeQuery(uploadData);
            System.out.println("Uploaded.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clean() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}