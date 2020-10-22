package gameImplementation.board.boardState.playerBoardState.diamondBackBringState;

import java.util.*;

import gameImplementation.board.boardState.playerBoardState.*;
import gameImplementation.board.boardState.playerBoardState.dragStartState.*;
import tools.common.*;
import tools.screen.*;

public class DiamondBackBringState extends PlayerBoardStateState {
    private final DiamondBackBringer diamondBackBringer = new DiamondBackBringer();
    private final PlayerMoveSucceededHandler playerMoveSucceededHandler;
    private DiamondBackBringData diamondBackBringData;
    
    public DiamondBackBringState(PlayerMoveSucceededHandler playerMoveSucceededHandler) {
        this.playerMoveSucceededHandler = playerMoveSucceededHandler;
    }
    
    @Override
    public void enter(PlayerBoardStateState lastState, Object data) {
        diamondBackBringData = (DiamondBackBringData) data;
        diamondBackBringer.bringBack(diamondBackBringData.backBringPairs);
    }
    
    @Override
    public void update(float dt) {
        diamondBackBringer.update(dt);
        if (!diamondBackBringer.hasDiamonds()) {
            if (diamondBackBringData.lastDiamondMovementSucceeded) {
                playerMoveSucceededHandler.handlePlayerMoveSucceeded();
            }
            nextState = DragStartState.class;
        }
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(diamondBackBringer);
    }
    
    @Override
    public List<Whole> stop() {
        return diamondBackBringer.reset();
    }
}
