package Connection;

import java.io.IOException;

public class GameMatch{
    private final TCPConnection tcpConnection1;
    private final TCPConnection tcpConnection2;
    private  final ConnectionListener connectionListener;
    private Boolean goesFirst;
    private final Thread thread;



    public GameMatch(TCPConnection tcpConnection1, TCPConnection tcpConnection2, ConnectionListener connectionListener) {
        this.goesFirst = true;
        this.connectionListener = connectionListener;
        this.tcpConnection1 = tcpConnection1;
        this.tcpConnection2 = tcpConnection2;

        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tcpConnection2.sendMessage("2");
                    tcpConnection1.sendMessage("1");
                    connectionListener.connect(GameMatch.this);
                    while(!thread.isInterrupted()){
                        if(goesFirst){
                            tcpConnection2.sendMessage(tcpConnection1.receiveMessage());
                            System.out.println();
                            goesFirst = false;
                        } else {
                            tcpConnection1.sendMessage(tcpConnection2.receiveMessage());
                            goesFirst = true;
                        }
                    }
                } catch (IOException e){
                    connectionListener.exception(GameMatch.this, e);
                } finally {
                    connectionListener.disconnect(GameMatch.this);
                }
            }
        });
        thread.start();
    }

}
