package gameImplementation.board.boardState;

import java.util.*;

import gameImplementation.board.grid.*;
import core.objects.snapshot.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.state.singleState.*;

public abstract class BoardState extends State<BoardState, Object> {
    protected final GameVariant gameVariant;
    protected final Grid grid;
    
    public BoardState(GameVariant gameVariant, Grid grid) {
        this.gameVariant = gameVariant;
        this.grid = grid;
    }
    
    public void apply(SnapshotData snapshotData) {}
    
    public void fillSnapshotData(SnapshotData snapshotData) {}
    
    public List<Whole> stop() {
        return null;
    }
}
