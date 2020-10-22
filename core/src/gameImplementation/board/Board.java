package gameImplementation.board;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.board.boardState.*;
import gameImplementation.board.boardState.destructionBoardState.*;
import gameImplementation.board.boardState.diamondSpawnBoardState.*;
import gameImplementation.board.boardState.gameEndBoardState.*;
import gameImplementation.board.boardState.playerBoardState.*;
import gameImplementation.board.grid.*;
import gameImplementation.boardMediator.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.progressLine.*;
import tools.screen.*;
import tools.state.singleState.*;

public class Board extends StateController<BoardState, Object> {
    private final BoardMediator boardMediator;
    private final Grid grid;
    private final SpawnHexagonController spawnHexagonController;
    
    public Board(Grid grid, ProgressLineInfo stateProgressLine,
                 ProgressLineInfo gameEndVisualsProgressLineInfo,
                 BoardMediator boardMediator, GameVariant gameVariant) {
        this.boardMediator = boardMediator;
        this.grid = grid;
        spawnHexagonController = new SpawnHexagonController(boardMediator);
        putBoardStateToPool(new NoneBoardState(gameVariant, grid));
        putBoardStateToPool(new WaitForPreparationBoardState(
          gameVariant, grid, gameEndVisualsProgressLineInfo));
        putBoardStateToPool(new PreparationBoardState(gameVariant, grid, stateProgressLine));
        putBoardStateToPool(new WaitForStartBoardState(gameVariant, grid, stateProgressLine));
        putBoardStateToPool(new DiamondSpawnBoardState(gameVariant, grid, spawnHexagonController));
        putBoardStateToPool(new PlayerBoardState(gameVariant, grid));
        putBoardStateToPool(new FallBoardState(gameVariant, grid));
        putBoardStateToPool(new DestructionBoardState(gameVariant, grid, boardMediator));
        putBoardStateToPool(new GameEndBoardState(gameVariant, grid));
        setCurrentState(NoneBoardState.class);
    }
    
    private void putBoardStateToPool(BoardState boardState) {
        statePool.put(boardState.getClass(), boardState);
    }
    
    @Override
    public boolean receiveTouchDown(float x, float y, int pointer, int button) {
        return super.receiveTouchDown(x, y, pointer, button) || grid.contains(x, y);
    }
    
    @Override
    public void update(float dt) {
        grid.update(dt);
        super.update(dt);
    }
    
    protected void notifyStateEntered(BoardState state) {
        boardMediator.notifyBoardStateEntered(state.getClass());
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(grid);
        super.draw(screen);
    }
    
    public void fillSnapshotData(SnapshotData snapshotData) {
        snapshotData.gridData = grid.extractColors();
        snapshotData.spawningHexagonCount = grid.getSpawningHexagonCount();
        for (BoardState boardState : statePool.values()) {
            boardState.fillSnapshotData(snapshotData);
        }
        snapshotData.boardState = currentState.getClass();
    }
    
    public void loadNewGame() {
        defaultLoad(4);
    }
    
    public void load(SnapshotData snapshotData) {
        for (BoardState boardState : statePool.values()) boardState.apply(snapshotData);
        defaultLoad(snapshotData.spawningHexagonCount);
    }
    
    private void defaultLoad(int hexagonSpawningCount) {
        spawnHexagonController.reset(grid, hexagonSpawningCount);
        setCurrentState(WaitForPreparationBoardState.class);
    }
    
    public List<Whole> stop() {
        List<Whole> wholes = currentState.stop();
        for (BoardState boardState : statePool.values()) {
            boardState.stop();
            boardState.nextState = null;
        }
        currentState = statePool.get(NoneBoardState.class);
        return wholes;
    }
}
