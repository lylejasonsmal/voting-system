package group57.votingsystemserver.database;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class VotingSystemDAO {

    Statement statement;
    PreparedStatement prepStatement;
    VotingSystemDBConnection database;

    private void createStatementObj(VotingSystemDBConnection database) {
        try {
            this.database = database;
            statement = database.con.createStatement();
        } catch (SQLException e) {
            System.out.println("ERROR:" + e);
        }
    }

    public void createInsertStatementObj(VotingSystemDBConnection database) {
        try {
            this.database = database;
            String sql = "INSERT INTO CARS (CAR_BRAND, NO_OF_VOTES)"
                    + "VALUES (?, ?)";
            prepStatement = database.con.prepareStatement(sql);
        } catch (SQLSyntaxErrorException e) {
            createTable();
        } catch (SQLException e) {
            System.out.println("ERROR:" + e);
        }
    }

    public void createRetrieveStatementObj(VotingSystemDBConnection database) {
        try {
            this.database = database;
            String sql = "SELECT * FROM CARS ORDER BY NO_OF_VOTES DESC";
            prepStatement = database.con.prepareStatement(sql);
        } catch (SQLSyntaxErrorException e) {
            createTable();
        } catch (SQLException e) {
            System.out.println("ERROR:" + e);
        }
    }
    
    public void createRetrieveStatementObj2(VotingSystemDBConnection database) {
        try {
            this.database = database;
            String sql = "SELECT * FROM CARS ORDER BY NO_OF_VOTES ASC";
            prepStatement = database.con.prepareStatement(sql);
        } catch (SQLSyntaxErrorException e) {
            createTable();
        } catch (SQLException e) {
            System.out.println("ERROR:" + e);
        }
    }

    public void createUpdateStatementObj(VotingSystemDBConnection database) {
        try {
            this.database = database;
            String sql = "UPDATE CARS SET NO_OF_VOTES = NO_OF_VOTES + 1 WHERE CAR_BRAND = ?";
            prepStatement = database.con.prepareStatement(sql);
        } catch (SQLSyntaxErrorException e) {
            System.out.println(e.toString());
        } catch (SQLException e) {
            System.out.println("ERROR:" + e);
        }
    }

    private void createTable() {
        try {
            createStatementObj(database);
            statement.executeUpdate("CREATE TABLE CARS(CAR_BRAND VARCHAR(50) PRIMARY KEY, NO_OF_VOTES INTEGER)");
        } catch (SQLException e) {
            System.out.println("ERROR:" + e);
        }
    }

    public void execInsertStatement(String carBrand, int noOfVotes) {
        try {
            createInsertStatementObj(database);
            prepStatement.setString(1, carBrand);
            prepStatement.setInt(2, noOfVotes);
            prepStatement.executeUpdate();
        } catch (SQLDataException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (SQLSyntaxErrorException e) {
            createTable();
            execInsertStatement(carBrand, noOfVotes);
        } catch (SQLException e) {
            System.out.println("ERROR:" + e);;
        }
    }

    public void execRetrieveStatement(DefaultTableModel tblModel) {
        try {
            createRetrieveStatementObj(database);
            ResultSet results = prepStatement.executeQuery();

            //get meta data to fetch column names
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear existing data and columns in the table model
            tblModel.setRowCount(0);
            tblModel.setColumnCount(0);

            //set column names
            for (int i = 1; i <= columnCount; i++) {
                tblModel.addColumn(metaData.getColumnName(i));
                System.out.println("Column names set");
            }
            //set rows data
            while (results.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = results.getObject(i);
                }
                tblModel.addRow(rowData);
                System.out.println("Row data added");
            }

        } catch (SQLException e) {
            System.out.println("ERROR:" + e);;
        }
    }

    public void execRetrieveStatement(JLabel[] lbl) {
        try {
            createRetrieveStatementObj(database);
            ResultSet results = prepStatement.executeQuery();

            int i = 0;
            while (results.next() & i < lbl.length) {
                lbl[i].setText(results.getString(1));
                i++;
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
    }

    public ArrayList<String> execRetrieveStatement() {
        ArrayList<String> resultsArr = new ArrayList();
        try {
            createRetrieveStatementObj(database);
            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                resultsArr.add(results.getString("CAR_BRAND"));
                resultsArr.add(results.getString("NO_OF_vOTES"));
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
        return resultsArr;
    }
    

    public ArrayList<String> execRetrieveStatement2() {
        ArrayList<String> resultsArr = new ArrayList();
        try {
            createRetrieveStatementObj(database);
            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                resultsArr.add(results.getString(1));
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
        return resultsArr;
    }

    public ArrayList<String> execRetrieveStatement3() {
        ArrayList<String> resultsArr = new ArrayList();
        try {
            createRetrieveStatementObj2(database);
            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                resultsArr.add(results.getString("CAR_BRAND"));
                resultsArr.add(results.getString("NO_OF_vOTES"));
            }

        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
        return resultsArr;
    }

    public void execUpdateStatement(String carBrand) {
        try {
            createUpdateStatementObj(database);
            prepStatement.setString(1, carBrand);
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR:" + e);;
        }
    }
}
