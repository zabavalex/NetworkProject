package GameServer;

import Connection.ConnectionListener;
import Connection.GameMatch;
import Connection.TCPConnection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements ConnectionListener {

    ArrayList <GameMatch> gameMatches;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        gameMatches = new ArrayList<>();
        System.out.println("Server running ...");
        try(ServerSocket serverSocket = new ServerSocket(8081)){
            while (true){
                try {
                    TCPConnection tcpConnection1 = new TCPConnection(serverSocket.accept());
                    System.out.println("Игрок покдлючился");
                    TCPConnection tcpConnection2 = new TCPConnection(serverSocket.accept());
                    System.out.println("Игрок подключился");
                    new GameMatch(tcpConnection1, tcpConnection2, this);
                } catch (IOException e){
                    System.out.println("Exception: " + e);
                }
            }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void connect(GameMatch gameMatch) {
        System.out.println("Партия создана");
        gameMatches.add(gameMatch);
    }

    @Override
    public synchronized void disconnect(GameMatch gameMatch) {
        System.out.println("Партия завершена");
        gameMatches.remove(gameMatch);
    }

    @Override
    public synchronized void exception(GameMatch gameMatch, Exception e) {
        System.out.println("Exception: " + e);
    }
}
