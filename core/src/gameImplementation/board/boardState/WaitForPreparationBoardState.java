package gameImplementation.board.boardState;

import gameImplementation.board.grid.*;
import gameImplementation.gameVariant.*;
import misc.*;
import tools.progressLine.*;

public class WaitForPreparationBoardState extends BoardState {
    private ProgressLineInfo progressLineInfo;
    
    public WaitForPreparationBoardState(GameVariant gameVariant, Grid grid,
                                        ProgressLineInfo progressLineInfo) {
        super(gameVariant, grid);
        this.progressLineInfo = progressLineInfo;
    }
    
    @Override
    public void update(float dt) {
        checkForNextBoardState();
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        checkForNextBoardState();
    }
    
    private void checkForNextBoardState() {
        if (progressLineInfo.getProgress()
              <= Constants.GAME_END_VISUALS_PROGRESS_TILL_OTHER_CHANGES) {
            nextState = PreparationBoardState.class;
        }
    }
}
