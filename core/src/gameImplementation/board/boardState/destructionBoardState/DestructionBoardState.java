package gameImplementation.board.boardState.destructionBoardState;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.board.boardState.*;
import gameImplementation.board.boardState.diamondSpawnBoardState.*;
import gameImplementation.board.grid.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.boardMediator.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.screen.*;

public class DestructionBoardState extends BoardState {
    private final DiamondsForDestructionGetter diamondsForDestructionGetter
      = new DiamondsForDestructionGetter();
    private final Marker marker = new Marker();
    private final DiamondDestructor diamondDestructor;
    private List<Hexagon> diamondsForDestruction;
    
    public DestructionBoardState(GameVariant gameVariant, Grid grid, BoardMediator boardMediator) {
        super(gameVariant, grid);
        diamondDestructor = new DiamondDestructor(boardMediator, marker);
    }
    
    @Override
    public void update(float dt) {
        marker.update(dt);
        if (marker.isMarked() && diamondsForDestruction != null) {
            diamondDestructor.destroy(diamondsForDestruction);
            diamondsForDestruction = null;
        } else if (marker.isNotMarked()) nextState = FallBoardState.class;
        else diamondDestructor.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(marker);
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        diamondsForDestruction = diamondsForDestructionGetter.getDiamondsForDestruction(grid);
        if (diamondsForDestruction.isEmpty()) nextState = DiamondSpawnBoardState.class;
        else {
            marker.mark(diamondsForDestruction);
            marker.increase();
        }
    }
    
    @Override
    public List<Whole> stop() {
        diamondDestructor.reset();
        return marker.reset();
    }
}
