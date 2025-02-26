package group57.votingsystem.networking;

import group57.votingsystem.GUI.MyFrame;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Client {

    private Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ArrayList<String> results = new ArrayList();
    private MyFrame frame;

    public Client() {

    }

    public void sendData(String message) {
        try {
            System.out.println(message);
            out.writeObject(message);
            out.flush();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }

    public void receiveData(JLabel[] lbl, JComboBox cBox) {
        try {
            results = (ArrayList<String>) in.readObject();
            for (int i = 0; i < 3; i++) {
                lbl[i].setText(results.get(i));
            }
            for (int i = 0; i < results.size(); i++) {
                cBox.addItem(results.get(i));
            }
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Run VotingSystemServer.\nRun VotingSystem first.", "Error", 0, frame.icon);
            System.exit(0);
        }
    }

    public void receiveTableData(DefaultTableModel tblModel) {
        try {
            results = (ArrayList<String>) in.readObject();

            tblModel.setColumnCount(0);
            tblModel.setRowCount(0);

            tblModel.addColumn("Car Brand");
            tblModel.addColumn("No. Of Votes");

            for (int i = 0; i < results.size(); i += 2) {
                Object[] rowData = new Object[2];
                rowData[0] = results.get(i);
                rowData[1] = results.get(i + 1);
                tblModel.addRow(rowData);
            }

        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found: " + ex.getMessage());
        }
    }

    public void open() {
        try {
            // Create socket
            server = new Socket("127.0.0.1", 1234);
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }
    }

    public void close() {
        try {
            out.close();
            in.close();
            server.close();
        } catch (IOException ex) {
            System.out.println("Error" + ex.getMessage());
        }
    }
}
