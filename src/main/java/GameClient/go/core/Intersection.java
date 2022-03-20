package GameClient.go.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Intersection {
    private final Goban goban;

    private final int x,y;

    private StoneChain stoneChain;

    public Intersection(Goban goban, int x, int y) {
        this.goban = goban;
        this.x = x;
        this.y = y;
        this.stoneChain = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public StoneChain getStoneChain() {
        return stoneChain;
    }

    public void setStoneChain(StoneChain stoneChain) {
        this.stoneChain = stoneChain;
    }

    public boolean isEmpty() {
        return stoneChain == null;
    }

    public Set<StoneChain> getAdjacentStoneChains() {
        Set<StoneChain> adjacentStoneChains = new HashSet<StoneChain>();

        int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length ; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goban.isInGoban(newX, newY)) {
                Intersection adjIntersection = goban.getIntersection(newX, newY);
                if (adjIntersection.stoneChain != null) {
                    adjacentStoneChains.add(adjIntersection.stoneChain);
                }
            }
        }

        return adjacentStoneChains;
    }

    /**
     * Empty neighbors getter
     * @return List of empty neighbors intersections
     */
    public List<Intersection> getEmptyNeighbors() {
        List<Intersection> emptyNeighbors = new ArrayList<Intersection>();

        int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goban.isInGoban(newX, newY)) {
                Intersection adjIntersection = goban.getIntersection(newX, newY);
                if (adjIntersection.isEmpty()) {
                    emptyNeighbors.add(adjIntersection);
                }
            }
        }

        return emptyNeighbors;
    }

    /**
     * Empty or dead neighbors getter
     * @param deadChains the Set of dead chains, as referred by the user
     * @return the list of empty or dead neighbors
     */
    public List<Intersection> getEmptyOrDeadNeighbors(Set<StoneChain> deadChains) {
        List<Intersection> emptyNeighbors = new ArrayList<Intersection>();

        int[] dx = {-1,0,1,0}, dy = {0,-1,0,1};
        assert dx.length == dy.length : "dx and dy should have the same length";

        for (int i = 0; i < dx.length; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (goban.isInGoban(newX, newY)) {
                Intersection adjIntersection = goban.getIntersection(newX, newY);
                if (adjIntersection.isEmpty() || deadChains.contains(adjIntersection.getStoneChain())) {
                    emptyNeighbors.add(adjIntersection);
                }
            }
        }
        return emptyNeighbors;
    }
}
