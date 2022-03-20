package Connection;

import java.io.*;
import java.net.Socket;

public class TCPConnection {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public TCPConnection(int port) throws IOException {
        this.socket = new Socket("localhost", port);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    public TCPConnection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    public void sendMessage(String message) throws IOException {
        out.write(message + "\r\n");
        out.flush();
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }
}
