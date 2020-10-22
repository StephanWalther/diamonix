package gameImplementation.board.grid.hexagonSelector;

import gameImplementation.board.hexagonAndDiamond.*;

public class WithDiamond implements HexagonSelector {
    public static final WithDiamond instance = new WithDiamond();
    
    private WithDiamond() {}
    
    @Override
    public boolean select(Hexagon hexagon) {
        return hexagon.hasDiamond();
    }
}
