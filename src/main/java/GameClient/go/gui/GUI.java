package GameClient.go.gui;

import Connection.TCPConnection;
import GameClient.go.core.Goban;
import GameClient.go.core.Player;
import GameClient.go.core.StoneChain;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public static final int TOKEN_INITIAL_SIZE = 35;
    private static final int MENU_SIZE = 42;

    private Goban goban;
    private final TCPConnection tcpConnection;
    private int height;
    private int width;

    // Goban
    private JPanel jGoban;
    private boolean jGobanEnable;
    private JButton[][] jIntersections;

    /**
     *
     * @param goban goban that correspond to the actual game
     */
    public GUI(Goban goban, int id, TCPConnection tcpConnection) {
        this.jGobanEnable = true;
        this.tcpConnection = tcpConnection;
        this.goban = goban;
        this.height = TOKEN_INITIAL_SIZE * goban.getHeight() + MENU_SIZE;
        this.width = TOKEN_INITIAL_SIZE * goban.getWidth();

        // creating window
        this.initWindow();
        System.out.println("start draw");
        // printing the goban on screen
        this.drawGoban(tcpConnection);
        if(id == 2){
            setjGobanEnable(false);
        }
        System.out.println("end draw");

        pack();
    }
    public Goban getGoban() {
        return goban;
    }

    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Java Go game");
        setPreferredSize(new Dimension(width, height));
        setResizable(false);
        setVisible(true);
    }


    /**
     * Draw Goban
     */
    public void drawGoban(TCPConnection tcpConnection) {
        // Central layout is a grid
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();

        jGoban = new JPanel(new GridLayout(gobanWidth,gobanHeight));

        // Creating the board
        jIntersections = new JButton[gobanWidth][gobanHeight];

        // Creating the buttons
        for(int x=0;x<gobanWidth;x++) {
            for(int y=0;y<gobanHeight;y++) {
                // creating button at [x,y]

                StoneChain chain = goban.getIntersection(x, y).getStoneChain();
                if (chain != null) {
                    jIntersections[x][y] = new JButton(Sprite.getPlayerIcon(chain.getOwner().getIdentifier()));
                } else {
                    jIntersections[x][y] = new JButton(Sprite.getGridIcon(goban, x, y, 0));
                }

                jIntersections[x][y].setEnabled(true);
                jIntersections[x][y].setBorder(BorderFactory.createEmptyBorder());
                jIntersections[x][y].setContentAreaFilled(false);

                jIntersections[x][y].setPreferredSize(new Dimension(TOKEN_INITIAL_SIZE, TOKEN_INITIAL_SIZE));

                // Adding action
                jIntersections[x][y].addActionListener(new PlayMove(this, x, y, tcpConnection));
                jIntersections[x][y].addMouseListener(new HoverEffect(x, y, jIntersections[x][y],this));

                // Adding button
                jGoban.add(jIntersections[x][y],x,y);
            }
        }
        // Adding goban in Main content panel
        getContentPane().add(jGoban, BorderLayout.CENTER);
    }

    /**
     * Update goban
     */
    public void updateGoban() {
        int gobanWidth = goban.getWidth();
        int gobanHeight = goban.getHeight();
        for(int x=0;x<gobanWidth;x++) {
            for (int y = 0; y < gobanHeight; y++) {
                StoneChain sc = goban.getIntersection(x,y).getStoneChain();
                if (sc != null) {
                    jIntersections[x][y].setIcon(Sprite.getPlayerIcon(sc.getOwner().getIdentifier()));
                } else {
                    jIntersections[x][y].setIcon(Sprite.getGridIcon(goban, x, y, 0));
                }
            }
        }
    }
    public JButton getJIntersection(int x, int y){
        return jIntersections[x][y];
    }

    public void setjGobanEnable(boolean jGobanEnable) {
        this.jGobanEnable = jGobanEnable;
    }

    public boolean isjGobanEnable() {
        return jGobanEnable;
    }

    public void makeMove(int x, int y){
        Player player = getGoban().getPlayer();
        if (getGoban().getSuccessivePassCount() < 3) {
            System.out.println(player + " want to play in [" + x + "-" + y + "]");
            try {
                if (getGoban().play(x, y, player)) {
                    System.out.println("Move applied");
                    getGoban().nextPlayer();
                    updateGoban();
                    setjGobanEnable(true);
                }
            } catch (Exception ex) {

            }
        } else {
            StoneChain chain = getGoban().getIntersection(x, y).getStoneChain();
        }
    }
}