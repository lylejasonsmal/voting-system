package group57.votingsystem;

import group57.votingsystem.GUI.*;

public class Run {

    public static void main(String[] args) {
        MyFrame client = new ClientSide();

        //Build the interface
        client.setGUI();
    }
}
