package group57.votingsystemserver.database;

import java.sql.*;

public class VotingSystemDBConnection {

    String dbURL = "jdbc:derby://localhost:1527/VotingSystemDatabase";
    String username = "administrator";
    String password = "admin";
    public Connection con;

    public void establishConnection() {
        try {
            System.out.println("About To Get A Connection...");
            con = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connection Established Successfully...");
        } catch (SQLNonTransientConnectionException e) {
            System.out.println("ERROR: " + e);
            closeConnection();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
            closeConnection();
        }
    }

    public void closeConnection() {
        try {
            System.out.println("About To Close Connection...");
            con.close();
            System.out.println("Connection Closed Successfully...");
        } catch (SQLException err) {
            System.out.println("ERROR: " + err);
        }
    }
}
