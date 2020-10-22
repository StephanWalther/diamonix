package gameImplementation.gameVariant.tutorialGameVariant;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;

class GrabbedDiamondController {
    private final List<int[]> origins = new ArrayList<int[]>();
    private final List<int[]> destinations = new ArrayList<int[]>();
    private int current = 0;
    private final List<List<Hexagon>> hexagons;
    
    GrabbedDiamondController(List<List<Hexagon>> hexagons) {
        this.hexagons = hexagons;
        add(3, 0, origins);
        add(1, 1, destinations);
        add(2, 0, origins);
        add(4, 0, destinations);
    
        add(1, 0, origins);
        add(3, 1, destinations);
        add(3, 0, origins);
        add(0, 1, destinations);
    
        add(3, 0, origins);
        add(1, 1, destinations);
        add(0, 1, origins);
        add(3, 3, destinations);
    
        add(3, 0, origins);
        add(3, 4, destinations);
        add(4, 4, origins);
        add(1, 3, destinations);
    
        add(1, 6, origins);
        add(4, 4, destinations);
        add(1, 0, origins);
        add(3, 1, destinations);
    
        add(3, 0, origins);
        add(3, 4, destinations);
        add(1, 0, origins);
        add(3, 1, destinations);
    
        add(3, 4, origins);
        add(1, 3, destinations);
        add(1, 1, origins);
        add(3, 2, destinations);
    }
    
    private void add(int a, int b, List<int[]> list) {
        int[] ints = {a, b};
        list.add(ints);
    }
    
    Hexagon getNextGrabbedDiamondOrigin() {
        int[] origin = origins.get(current);
        return hexagons.get(origin[0]).get(origin[1]);
    }
    
    Hexagon getNextGrabbedDiamondDestination() {
        int[] destination = destinations.get(current);
        return hexagons.get(destination[0]).get(destination[1]);
    }
    
    void notifyPlayerMoveSucceeded() {
        current++;
    }
    
    void reset() {
        current = 0;
    }
}
