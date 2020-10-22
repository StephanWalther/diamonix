package gameImplementation.board.grid.hexagonSelector;

import gameImplementation.board.hexagonAndDiamond.*;

public class All implements HexagonSelector {
    public static final All instance = new All();
    
    private All() {}
    
    @Override
    public boolean select(Hexagon hexagon) {
        return true;
    }
}
