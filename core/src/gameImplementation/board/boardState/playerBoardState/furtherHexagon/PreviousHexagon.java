package gameImplementation.board.boardState.playerBoardState.furtherHexagon;

import gameImplementation.board.boardState.playerBoardState.movingPack.*;
import gameImplementation.board.hexagonAndDiamond.*;

public class PreviousHexagon implements FurtherHexagon {
    private final MovingDirection movingDirection;
    
    public PreviousHexagon(MovingDirection movingDirection) {
        this.movingDirection = movingDirection;
    }
    
    @Override
    public Hexagon further(Hexagon hexagon) {
        return movingDirection.previous(hexagon);
    }
}
