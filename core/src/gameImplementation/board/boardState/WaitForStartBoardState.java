package gameImplementation.board.boardState;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.board.boardState.diamondSpawnBoardState.*;
import gameImplementation.board.grid.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.progressLine.*;

public class WaitForStartBoardState extends BoardState {
    private ProgressLineInfo progressLineInfo;
    private Class<? extends BoardState> startBoardState;
    
    public WaitForStartBoardState(GameVariant gameVariant, Grid grid,
                                  ProgressLineInfo progressLineInfo) {
        super(gameVariant, grid);
        this.progressLineInfo = progressLineInfo;
        setStartBoardStateToDefault();
    }
    
    private void setStartBoardStateToDefault() {
        startBoardState = DiamondSpawnBoardState.class;
    }
    
    @Override
    public void update(float dt) {
        checkForNextBoardState();
    }
    
    @Override
    public void apply(SnapshotData snapshotData) {
        startBoardState = snapshotData.boardState;
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        checkForNextBoardState();
    }
    
    private void checkForNextBoardState() {
        if (progressLineInfo.getProgress() == 1.f && progressLineInfo.isIncreasing()) {
            nextState = startBoardState;
        }
    }
    
    @Override
    public List<Whole> stop() {
        setStartBoardStateToDefault();
        return null;
    }
}
