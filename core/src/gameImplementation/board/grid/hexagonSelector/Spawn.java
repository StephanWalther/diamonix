package gameImplementation.board.grid.hexagonSelector;

import gameImplementation.board.hexagonAndDiamond.*;

public class Spawn implements HexagonSelector {
    public static final Spawn instance = new Spawn();
    
    private Spawn() {}
    
    @Override
    public boolean select(Hexagon hexagon) {
        return hexagon.isSpawningHexagon();
    }
}
