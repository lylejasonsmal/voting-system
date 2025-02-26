package group57.votingsystemserver.GUI;

import group57.votingsystemserver.database.VotingSystemDAO;
import group57.votingsystemserver.database.VotingSystemDBConnection;
import group57.votingsystemserver.networking.Server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class ServerSide extends MyFrame {

    private JTextArea txtArea;
    private JLabel lblAdd, lblRanking, lblCarBrand, lblClientLog, lblPodium, lblFirst, lblSecond, lblThird;
    private JButton btnAdd;
    private JTextField txtAdd;
    private DefaultTableModel tblModel;
    private JTable tblRanking;
    private JPanel pnlLeft, pnlRight, pnlAdd, pnlLog, pnlPodium;
    private JScrollPane scrPane;
    private ImageIcon first = new ImageIcon("first.png");
    private ImageIcon second = new ImageIcon("second.png");
    private ImageIcon third = new ImageIcon("third.png");

    private JLabel lbl[] = {lblFirst, lblSecond, lblThird};
    private ImageIcon medals[] = {first, second, third};

    public Server server = new Server();

    private boolean connected = false;

    public ServerSide() {
        //Setting up the medal icons
        for (int i = 0; i < medals.length; i++) {
            medals[i] = new ImageIcon(medals[i].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }

        //Setting up textarea
        txtArea = new JTextArea(30, 40);
        setTextArea(txtArea);

        //Setting up textfield
        txtAdd = new JTextField();
        txtAdd.setPreferredSize(new Dimension(300, 35));

        //Setting up labels
        lblAdd = new JLabel("Add New Vehicle");
        setLabel(lblAdd, true);

        lblRanking = new JLabel("Rankings");
        setLabel(lblRanking, false);
        lblRanking.setIcon(icon2);
        lblRanking.setForeground(white);
        lblRanking.setFont(new Font("Arial", Font.BOLD, 20));

        lblCarBrand = new JLabel("Car Brand");
        setLabel(lblCarBrand, false);

        lblClientLog = new JLabel("Client Log");
        setLabel(lblClientLog, false);

        lblPodium = new JLabel("Podium");
        setLabel(lblPodium, false);
        lblPodium.setIcon(icon2);
        lblPodium.setForeground(white);
        lblPodium.setFont(new Font("Arial", Font.BOLD, 20));
        lblPodium.setHorizontalAlignment(JLabel.CENTER);

        //Setting up the podium labels
        for (int i = 0; i < lbl.length; i++) {
            lbl[i] = new JLabel(medals[i]);
            setLabel(lbl[i], false);
            lbl[i].setForeground(white);
            lbl[i].setFont(new Font("Arial", Font.BOLD, 20));
            lbl[i].setHorizontalAlignment(JLabel.LEFT);
        }

        //Setting up button
        btnAdd = new JButton("+ Add");
        setBlackButton(btnAdd, 125, 40);

        //Setting up table
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };
        tblRanking = new JTable(tblModel);
        setTable(tblRanking);

        //Setting up panels
        pnlAdd = new JPanel();

        pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(500, 675));

        pnlLog = new JPanel();

        pnlRight = new JPanel();
        pnlRight.setBackground(this.getForeground());
        pnlRight.setPreferredSize(new Dimension(400, 675));

        pnlPodium = new JPanel();
        pnlPodium.setBackground(black);

        //Setting up scrollpanes
        scrPane = new JScrollPane(txtArea);
    }

    //Populating the table
    public void popTable() {
        VotingSystemDBConnection database = new VotingSystemDBConnection();
        VotingSystemDAO sql = new VotingSystemDAO();
        database.establishConnection();
        sql.createRetrieveStatementObj(database);
        sql.execRetrieveStatement(tblModel);
        database.closeConnection();
    }

    //Populating the table
    public void popPodium() {
        VotingSystemDBConnection database = new VotingSystemDBConnection();
        VotingSystemDAO sql = new VotingSystemDAO();
        database.establishConnection();
        sql.createRetrieveStatementObj(database);
        sql.execRetrieveStatement(lbl);
        database.closeConnection();
    }

    //ActionEvent for adding a car to the database
    public void addAction() {
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtAdd.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter a car name", "Invalid Input", 1, icon);
                } else {
                    VotingSystemDBConnection database = new VotingSystemDBConnection();
                    VotingSystemDAO sql = new VotingSystemDAO();
                    database.establishConnection();
                    sql.createInsertStatementObj(database);
                    sql.execInsertStatement(txtAdd.getText(), 0);
                    database.establishConnection();

                    JOptionPane.showMessageDialog(null, "New Car Has Been Added", "New Car", 1, icon);

                    txtAdd.setText("");

                    server.sendData(txtArea);
                    server.sendTableData(txtArea, 1);

                    popTable();
                    popPodium();
                }
            }
        });
    }

    //Calls all the events and functionality
    @Override
    public void eventHandler() {
        popTable();
        popPodium();
        addAction();
    }

    //Adds the components to their appropriate panels
    @Override
    public void addToPanels() {
        //Setting the layout for pnlRight
        GroupLayout grpRight = new GroupLayout(pnlRight);

        pnlRight.setLayout(grpRight);

        grpRight.setAutoCreateContainerGaps(true);
        grpRight.setAutoCreateGaps(true);

        grpRight.setHorizontalGroup(grpRight.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblRanking, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(tblRanking, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlPodium, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        grpRight.setVerticalGroup(grpRight.createSequentialGroup()
                .addComponent(lblRanking, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(tblRanking, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlPodium, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        //Setting the layout for pnlAdd
        GroupLayout grpAdd = new GroupLayout(pnlAdd);
        pnlAdd.setLayout(grpAdd);

        grpAdd.setAutoCreateContainerGaps(true);
        grpAdd.setAutoCreateGaps(true);

        grpAdd.setHorizontalGroup(grpAdd.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblCarBrand, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        grpAdd.setVerticalGroup(grpAdd.createSequentialGroup()
                .addComponent(lblCarBrand, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        //Setting the layout for pnlLog
        GroupLayout grpLog = new GroupLayout(pnlLog);
        pnlLog.setLayout(grpLog);

        grpLog.setAutoCreateContainerGaps(true);
        grpLog.setAutoCreateGaps(true);

        grpLog.setHorizontalGroup(grpLog.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblClientLog, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(scrPane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        grpLog.setVerticalGroup(grpLog.createSequentialGroup()
                .addComponent(lblClientLog, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(scrPane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        //Setting the layout for pnlLeft
        GroupLayout grpLeft = new GroupLayout(pnlLeft);
        pnlLeft.setLayout(grpLeft);

        grpLeft.setAutoCreateContainerGaps(false);
        grpLeft.setAutoCreateGaps(false);

        grpLeft.setHorizontalGroup(grpLeft.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlLog, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        grpLeft.setVerticalGroup(grpLeft.createSequentialGroup()
                .addComponent(lblAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlLog, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pnlPodium.setLayout(new GridLayout(4, 1, 10, 5));
        pnlPodium.add(lblPodium);
        for (int i = 0; i < lbl.length; i++) {
            pnlPodium.add(lbl[i]);
        }

        //Adding the appropiate panels to the frame
        this.setLayout(new GridLayout(1, 2));
        this.add(pnlLeft);
        this.add(pnlRight);

    }

    //Calls all the methods required to build the GUI
    @Override
    public void setGUI() {
        setFrame(this, "Car Of The Year: Server");
        addToPanels();
        eventHandler();
    }

    //Facilitates connectivity between a client and server
    @Override
    public void connection() {
        if (connected != true) {
            server.listenForClients(txtArea);
            txtArea.append("Server is connected to the client\n");
            connected = true;
        }

        while (connected!=false) {
            server.sendData(txtArea);
            server.sendTableData(txtArea, 1);
            server.receiveData(txtArea);
            popTable();
            popPodium();
        }
    }
}
