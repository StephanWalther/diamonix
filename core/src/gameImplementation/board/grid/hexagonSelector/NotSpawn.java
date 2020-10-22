package gameImplementation.board.grid.hexagonSelector;

import gameImplementation.board.hexagonAndDiamond.*;

public class NotSpawn implements HexagonSelector {
    public static final NotSpawn instance = new NotSpawn();
    
    private NotSpawn() {}
    
    @Override
    public boolean select(Hexagon hexagon) {
        return !hexagon.isSpawningHexagon();
    }
}
