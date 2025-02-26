package group57.votingsystem.GUI;

import static group57.votingsystem.GUI.MyFrame.icon;
import group57.votingsystem.networking.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class ClientSide extends MyFrame {

    private JPanel pnlLeftO, pnlLeftI, pnlLeftII, pnlRight, pnlPodium;
    private JLabel lblVote, lblRankings, lblSelect, lblYVote, lblYVote2, lblPodium, lblFirst, lblSecond, lblThird;
    private JButton btnUndo, btnSubmit, btnReload, btnFilterAsc;
    private JComboBox cbBrand;
    private DefaultTableModel tblModel;
    private JTable tblRankings;
    private Client client = new Client();
    private ImageIcon reload = new ImageIcon("reload.png");
    private ImageIcon first = new ImageIcon("first.png");
    private ImageIcon second = new ImageIcon("second.png");
    private ImageIcon third = new ImageIcon("third.png");
    private ImageIcon send = new ImageIcon("send.png");

    private JLabel lbl[] = {lblFirst, lblSecond, lblThird};
    private ImageIcon medals[] = {first, second, third};

    boolean asc = false;

    public ClientSide() {
        //Setting up the medal icons
        for (int i = 0; i < medals.length; i++) {
            medals[i] = new ImageIcon(medals[i].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }

        //Setting up send icon
        send = new ImageIcon(send.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));

        //Setting up the labels
        lblVote = new JLabel("Shift The Vote!");
        setLabel(lblVote, true);

        lblRankings = new JLabel("Rankings");
        setLabel(lblRankings, false);
        lblRankings.setIcon(icon2);
        lblRankings.setForeground(white);
        lblRankings.setFont(new Font("Arial", Font.BOLD, 20));

        lblSelect = new JLabel("Select Your Car");
        setLabel(lblSelect, false);

        lblYVote = new JLabel("Your Vote:");
        setLabel(lblYVote, false);

        lblYVote2 = new JLabel("None");
        setLabel(lblYVote2, true);

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

        //Setting up the table
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make all cells non-editable
            }
        };
        tblRankings = new JTable(tblModel);
        setTable(tblRankings);

        //Setting up the buttons
        btnUndo = new JButton("Undo");
        setWhiteButton(btnUndo, 125, 50);

        btnSubmit = new JButton("Submit");
        btnSubmit.setHorizontalTextPosition(SwingConstants.LEFT);
        btnSubmit.setIcon(send);
        setBlackButton(btnSubmit, 150, 50);

        reload = new ImageIcon(reload.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        btnReload = new JButton(reload);
        setWhiteButton(btnReload, 50, 50);
        btnReload.setBorder(null);
        menubar.add(btnReload);

        btnFilterAsc = new JButton("Ascending Order");
        setWhiteButton(btnFilterAsc, 300, 50);
        menubar.add(btnFilterAsc);

        //Setting up the combobox
        cbBrand = new JComboBox();
        cbBrand.addItem("None");
        cbBrand.setBackground(this.getBackground());
        cbBrand.setForeground(red);
        cbBrand.setPreferredSize(new Dimension(250, 25));
        cbBrand.setFont(new Font("Arial", Font.BOLD, 15));

        //Setting up the panels
        pnlLeftO = new JPanel();
        pnlLeftO.setBackground(this.getBackground());
        pnlLeftO.setAlignmentX(JPanel.TOP_ALIGNMENT);
        pnlLeftO.setPreferredSize(new Dimension(584, 675));

        pnlLeftI = new JPanel();
        pnlLeftI.setBackground(this.getBackground());

        pnlLeftII = new JPanel();
        pnlLeftII.setBackground(this.getBackground());

        pnlRight = new JPanel();
        pnlRight.setBackground(black);
        pnlRight.setPreferredSize(new Dimension(400, 675));

        pnlPodium = new JPanel();
        pnlPodium.setBackground(black);
    }

    //Populating the table
    private void popTable() {
        client.receiveTableData(tblModel);
    }

    //Populating everything
    private void populate() {
        cbBrand.removeAllItems();
        cbBrand.addItem("None");
        client.receiveData(lbl, cbBrand);
    }

    //ActionEvent for undo selection
    private void undoAction() {
        btnUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblYVote2.setText("None");
                cbBrand.setSelectedIndex(0);
            }
        });

    }

    //ActionEvent for submitting vote to the server
    private void submitAction() {
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lblYVote2.getText().equals("None")) {
                    JOptionPane.showMessageDialog(null, "Please select a car", "Invalid Selection", 1, icon);
                } else {
                    client.sendData(lblYVote2.getText());
                    JOptionPane.showMessageDialog(null, "Your Vote: " + lblYVote2.getText(), "Your Vote", 1, icon);
                    lblYVote2.setText("None");
                    cbBrand.setSelectedIndex(0);
                    populate();
                    popTable();
                }

            }
        });

    }

    //ActionEvent for reloading the page
    public void reloadAction() {
        btnReload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendData("reload");
                populate();
                popTable();
                cbBrand.setSelectedIndex(0);
            }
        });
    }

    //ActionEvent for filtering votes in ascending order
    public void filterAscAction() {
        btnFilterAsc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (asc != true) {
                    client.sendData("ascending");
                    populate();
                    popTable();
                    cbBrand.setSelectedIndex(0);
                    btnFilterAsc.setText("Descending Order");
                    asc = true;
                } else {
                    client.sendData("ascending");
                    populate();
                    popTable();
                    cbBrand.setSelectedIndex(0);
                    btnFilterAsc.setText("Ascending Order");
                    asc = false;
                }
            }
        });
    }

    //ItemEvent for getting the selected item from combobox
    public void selectionAction() {
        cbBrand.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    lblYVote2.setText((String) cbBrand.getSelectedItem());
                }
            }
        });
    }

    //Calls all the events
    @Override
    public void eventHandler() {
        populate();
        popTable();
        selectionAction();
        reloadAction();
        filterAscAction();
        undoAction();
        submitAction();
    }

    //Where to add all components to appropriate panels
    @Override
    public void addToPanels() {
        GroupLayout grpLayout = new GroupLayout(pnlLeftI);
        pnlLeftI.setLayout(grpLayout);

        grpLayout.setAutoCreateContainerGaps(true);
        grpLayout.setAutoCreateGaps(true);

        grpLayout.setHorizontalGroup(grpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblVote, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblSelect, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(cbBrand, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblYVote, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblYVote2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        grpLayout.setVerticalGroup(grpLayout.createSequentialGroup()
                .addComponent(lblVote, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(50)
                .addComponent(lblSelect, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(cbBrand, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(100)
                .addComponent(lblYVote, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblYVote2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(100)
        );

        pnlLeftII.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlLeftII.add(btnUndo);
        pnlLeftII.add(btnSubmit);

        pnlLeftO.setLayout(new FlowLayout());
        pnlLeftO.add(pnlLeftI);
        pnlLeftO.add(pnlLeftII);

        GroupLayout grpRight = new GroupLayout(pnlRight);
        pnlRight.setLayout(grpRight);

        grpRight.setAutoCreateContainerGaps(true);
        grpRight.setAutoCreateGaps(true);

        grpRight.setHorizontalGroup(grpRight.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblRankings, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(tblRankings, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlPodium, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        grpRight.setVerticalGroup(grpRight.createSequentialGroup()
                .addGap(20)
                .addComponent(lblRankings, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(tblRankings, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlPodium, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pnlPodium.setLayout(new GridLayout(4, 1, 10, 5));
        pnlPodium.add(lblPodium);
        for (int i = 0; i < lbl.length; i++) {
            pnlPodium.add(lbl[i]);
        }

        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.add(pnlLeftO);
        this.add(pnlRight);
    }

    //Setting up the GUI
    @Override
    public void setGUI() {
        setFrame(this, "Car Of The Year: Journalist Voting");
        addToPanels();
        connection();
        eventHandler();
    }

    //Connect to server
    @Override
    public void connection() {
        client.open();
    }

}
