
package group57.votingsystemserver;

import group57.votingsystemserver.GUI.ServerSide;
import group57.votingsystemserver.GUI.MyFrame;

public class Run {

    public static void main(String[] args) {
        MyFrame server = new ServerSide();

        //Build the interface
        server.setGUI();
        
        server.connection();
    }
}
