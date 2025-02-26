package group57.votingsystemserver.networking;

import group57.votingsystemserver.database.VotingSystemDAO;
import group57.votingsystemserver.database.VotingSystemDBConnection;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Server {

    private ServerSocket listener;
    private String msg = "";
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket client;
    private ArrayList<String> results = new ArrayList();
    private VotingSystemDBConnection database = new VotingSystemDBConnection();
    private VotingSystemDAO sql = new VotingSystemDAO();

    public Server() {
        try {
            listener = new ServerSocket(1234, 1);
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    private void open() {
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    public void listenForClients(JTextArea txtArea) {
        try {
            client = listener.accept();
            open();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    private void close() {
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    public void sendData(JTextArea txtArea) {
        try {
            retrieveDatabase2();
            out.writeObject(results);
            txtArea.append("Server sent data for the podium and combobox\n");
            out.flush();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    public void sendTableData(JTextArea txtArea, int dataRequest) {
        try {
            switch (dataRequest) {
                case 1:
                    retrieveDatabase();
                    out.writeObject(results);
                    txtArea.append("Server sent data for the table \n");
                    out.flush();
                    break;
                case 2:
                    retrieveDatabase3();
                    out.writeObject(results);
                    txtArea.append("Server sent data for the table \n");
                    out.flush();
                    break;
            }
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    public void receiveData(JTextArea txtArea) {
        try {
            msg = (String) in.readObject();
            if (msg.equals("reload")) {
                txtArea.append("Client requested data from server\n");
                sendData(txtArea);
                sendTableData(txtArea, 1);
                out.flush();
            } else if (msg.equals("ascending")) {
                txtArea.append("Client requested filtered data\n");
                sendData(txtArea);
                sendTableData(txtArea, 2);
                out.flush();
            } else {
                updateDatabase(msg);
                txtArea.append("Client has voted for " + msg + "\n");
            }
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found: " + cnfe.getMessage());
        }
    }

    public void retrieveDatabase() {
        //Connect to the database
        database.establishConnection();
        sql.createRetrieveStatementObj(database);
        results = sql.execRetrieveStatement();
        database.closeConnection();
    }

    public void retrieveDatabase2() {
        //Connect to the database
        database.establishConnection();
        sql.createRetrieveStatementObj(database);
        results = sql.execRetrieveStatement2();
        database.closeConnection();
    }
    
    public void retrieveDatabase3() {
        //Connect to the database
        database.establishConnection();
        sql.createRetrieveStatementObj2(database);
        results = sql.execRetrieveStatement3();
        database.closeConnection();
    }

    public void updateDatabase(String msg) {
        //Connect to the database
        database.establishConnection();
        sql.createUpdateStatementObj(database);
        sql.execUpdateStatement(msg);
        database.closeConnection();
    }
}
