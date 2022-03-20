package GameClient.go.gui;

import Connection.TCPConnection;
import GameClient.go.core.Player;
import GameClient.go.core.StoneChain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayMove implements ActionListener {

    private int x, y;
    private GUI gui;
    private final TCPConnection tcpConnection;

    public PlayMove(GUI gui, int x, int y, TCPConnection tcpConnection) {
        super();

        this.tcpConnection = tcpConnection;
        this.gui = gui;
        this.x = x;
        this.y = y;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Player player = gui.getGoban().getPlayer();
        if(gui.isjGobanEnable()) {
            if (gui.getGoban().getSuccessivePassCount() < 3) {
                System.out.println(player + " want to play in [" + x + "-" + y + "]");
                try {
                    if (gui.getGoban().play(x, y, player)) {
                        System.out.println("Move applied");
                        gui.getGoban().nextPlayer();
                        gui.updateGoban();
                        tcpConnection.sendMessage(x + " " + y);
                        gui.setjGobanEnable(false);
                    }
                } catch (Exception ex) {

                }
            } else {
                StoneChain chain = gui.getGoban().getIntersection(x, y).getStoneChain();
            }
        }
    }
}

