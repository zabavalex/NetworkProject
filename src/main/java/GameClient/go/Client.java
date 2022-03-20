package GameClient.go;

import Connection.TCPConnection;
import GameClient.go.core.Goban;
import GameClient.go.gui.GUI;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client {
    TCPConnection tcpConnection;

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new Client();
//            }
//        });
        new Client();
    }

    private Client() {
        try {
            tcpConnection = new TCPConnection(8081);
            int id = Integer.parseInt(tcpConnection.receiveMessage());
            System.out.println(id);
            Goban goban = new Goban(15,15, 0);
            GUI gui = new GUI(goban, id, tcpConnection);
            System.out.println(id);
            while (true){
                String message = tcpConnection.receiveMessage();
                System.out.println(message);
                String[] xAndY = message.split(" ");
                gui.makeMove(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
