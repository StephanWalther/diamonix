package gameImplementation.board.boardState.playerBoardState;

import java.util.*;

import gameImplementation.board.boardState.playerBoardState.diamondBackBringState.*;
import gameImplementation.board.boardState.playerBoardState.dragStartState.*;
import gameImplementation.board.boardState.playerBoardState.dragState.*;
import gameImplementation.board.grid.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.input.input.*;
import tools.input.processors.grab.*;
import tools.state.singleState.*;

class PlayerBoardStateController extends StateController<PlayerBoardStateState, Object> {
    PlayerBoardStateController(Grid grid, GameVariant gameVariant,
                               PlayerMoveSucceededHandler playerMoveSucceededHandler) {
        GrabProcessor grabProcessor = new GrabProcessor();
        putToStatePool(new DragStartState(grid, grabProcessor, gameVariant));
        putToStatePool(new DragState(grabProcessor, gameVariant));
        putToStatePool(new DiamondBackBringState(playerMoveSucceededHandler));
        resetState();
    }
    
    private void putToStatePool(PlayerBoardStateState state) {
        statePool.put(state.getClass(), state);
    }
    
    List<Whole> stop() {
        List<Whole> wholes = currentState.stop();
        resetState();
        return wholes;
    }
    
    private void resetState() {
        setCurrentState(DragStartState.class);
    }
}
