package GameClient.go.core;

import GameClient.go.gui.GUI;

public class Main {

    public int id = 1;
    // Main code goes here
    public static void main(String[] args) {
        newGame(50, 0, 1);
    }

    public static void newGame(int size, int handicap, int id) {
        Goban goban = new Goban(size,size, handicap);
        GUI gui = new GUI(goban, id, null);
    }


    public static void loadGame(String filepath, int id) {
        GameRecord gr = GameRecord.load(filepath);
        Goban goban = new Goban(gr);
        GUI gui = new GUI(goban, id, null);
    }
}
