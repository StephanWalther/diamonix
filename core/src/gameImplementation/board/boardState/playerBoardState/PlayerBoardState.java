package gameImplementation.board.boardState.playerBoardState;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.board.boardState.*;
import gameImplementation.board.boardState.destructionBoardState.*;
import gameImplementation.board.grid.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.input.input.*;
import tools.screen.*;

// TODO: Visualization of moveCount?
// TODO: Visualization of the original Place of the grabbed diamond?
public class PlayerBoardState extends BoardState implements PlayerMoveSucceededHandler {
    private final PlayerBoardStateController playerBoardStateController;
    private final MoveCounter moveCounter = new MoveCounter();
    
    public PlayerBoardState(GameVariant gameVariant, Grid grid) {
        super(gameVariant, grid);
        playerBoardStateController = new PlayerBoardStateController(grid, gameVariant, this);
    }
    
    @Override
    public boolean processInput(Input input) {
        return playerBoardStateController.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        playerBoardStateController.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        playerBoardStateController.draw(screen);
    }
    
    @Override
    public void apply(SnapshotData snapshotData) {
        moveCounter.setMoveCount(snapshotData.playerMoveCount);
    }
    
    @Override
    public void fillSnapshotData(SnapshotData snapshotData) {
        snapshotData.playerMoveCount = moveCounter.getMoveCount();
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        if (grid.isFull()) nextState = DestructionBoardState.class;
        else if (moveCounter.getMoveCount() == 0) {
            if (lastBoardState.getClass() == WaitForStartBoardState.class) {
                nextState = FallBoardState.class;
                return;
            } moveCounter.reset();
        } gameVariant.notifyPlayerMoveStarted();
    }
    
    @Override
    public void handlePlayerMoveSucceeded() {
        moveCounter.reduce();
        gameVariant.requestSnapshot();
        gameVariant.notifyPlayerMoveSucceeded();
        if (moveCounter.getMoveCount() == 0) nextState = FallBoardState.class;
        else gameVariant.notifyPlayerMoveStarted();
    }
    
    @Override
    public List<Whole> stop() {
        moveCounter.reset();
        return playerBoardStateController.stop();
    }
}
