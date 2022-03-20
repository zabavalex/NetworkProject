package GameClient.go.core;

import java.util.Collections;


public class Player {

    private final int identifier;

    private int capturedStones;

    public Player(int identifier) {
        this.identifier = identifier;
        this.capturedStones = 0;
    }

    public int getIdentifier() {
        return identifier;
    }

    public int getCapturedStones() {
        return capturedStones;
    }

    public void addCapturedStones(int nb) {
        capturedStones += nb;
    }

    public void removeCapturedStones(int nb) { capturedStones -= nb; }
    public boolean play(Goban goban, int x, int y) {
        if (x == -1 && y == -1) {
            GameRecord record = goban.getGameRecord();
            record.apply(record.getLastTurn().toNext(-1, -1, this.getIdentifier(), goban.getHandicap(), Collections.<Intersection>emptySet()));
            goban.updatePassCount(true);
            return true;
        } else {
            return goban.play(goban.getIntersection(x,y),this);
        }
    }

    @Override
    public String toString() {
        return "Player "+identifier;
    }
}
