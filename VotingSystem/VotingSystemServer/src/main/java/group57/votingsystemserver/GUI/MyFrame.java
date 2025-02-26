package group57.votingsystemserver.GUI;

import java.awt.*;
import javax.swing.*;
import static javax.swing.WindowConstants.*;
import javax.swing.table.*;

public abstract class MyFrame extends JFrame {

    public static ImageIcon icon = new ImageIcon("carmag.png");
    public static ImageIcon icon2 = new ImageIcon("carmag2.png");
    public static Image image = icon.getImage();
    public static Image image2 = icon2.getImage();

    public static Color black = new Color(25, 25, 25);
    public static Color white = new Color(245, 245, 245);
    public static Color red = new Color(237, 28, 36, 255);

    private JLabel logo;
    public JMenuBar menubar;

    public MyFrame() {
        this.setBackground(white);
        this.setForeground(black);

        //Setting the logo
        icon = new ImageIcon(image.getScaledInstance(180, 30, Image.SCALE_SMOOTH));
        icon2 = new ImageIcon(image2.getScaledInstance(144, 24, Image.SCALE_SMOOTH));
        logo = new JLabel(icon);

        //Setting the menubar
        menubar = new JMenuBar();
        menubar.setBackground(this.getBackground());
        menubar.setBorder(null);
        menubar.setBorderPainted(true);

        menubar.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        menubar.add(logo);
    }

    //Setting up buttons
    public void setBlackButton(JButton btn, int width, int height) {
        int fontSize = height / 2;
        Font font = new Font("Arial", Font.BOLD, fontSize);

        btn.setBorder(null);
        btn.setBackground(this.getForeground());
        btn.setForeground(this.getBackground());
        btn.setFont(font);
        btn.setPreferredSize(new Dimension(width, height));
    }

    public void setWhiteButton(JButton btn, int width, int height) {
        int fontSize = height / 2;
        Font font = new Font("Arial", Font.BOLD, fontSize);

        btn.setBackground(this.getBackground());
        btn.setForeground(this.getForeground());
        btn.setFont(font);
        btn.setPreferredSize(new Dimension(width, height));
    }

    //Setting up labels
    public void setLabel(JLabel lbl, boolean isHeading) {
        Font fontNormal = new Font("Arial", Font.PLAIN, 20);
        Font fontHeading = new Font("Arial", Font.BOLD, 40);

        lbl.setForeground(this.getForeground());

        if (isHeading != true) {
            lbl.setFont(fontNormal);
        } else {
            lbl.setFont(fontHeading);
        }
    }

    //Setting up textArea
    public void setTextArea(JTextArea txtArea) {
        txtArea.setBackground(black);
        txtArea.setForeground(red);
        txtArea.setFont(new Font("Arial", Font.BOLD, 12));
        txtArea.setEnabled(false);
    }

    //Setting up tables
    public void setTable(JTable tbl) {
        tbl.setCellSelectionEnabled(false);
        tbl.setAutoResizeMode(1);
        tbl.setShowGrid(false);
        tbl.setBackground(black);
        tbl.setForeground(white);
        tbl.setPreferredSize(new Dimension(350, 325));
        tbl.setFont(new Font("Arial", Font.BOLD, 16));
    }

    //Setting up frames
    public void setFrame(JFrame frame, String title) {
        frame.pack();
        frame.setTitle(title);
        frame.setJMenuBar(menubar);
        frame.setSize(1000, 750);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(icon.getImage());
    }

    public abstract void connection();

    public abstract void eventHandler();

    public abstract void addToPanels();

    public abstract void setGUI();

}
