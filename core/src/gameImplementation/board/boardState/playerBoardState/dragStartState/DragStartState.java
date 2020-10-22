package gameImplementation.board.boardState.playerBoardState.dragStartState;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.boardState.playerBoardState.*;
import gameImplementation.board.boardState.playerBoardState.dragState.*;
import gameImplementation.board.boardState.playerBoardState.movingPack.*;
import gameImplementation.board.grid.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.input.input.*;
import tools.input.processors.grab.*;

public class DragStartState extends PlayerBoardStateState implements GrabListener {
    private final DiamondGrabber diamondGrabber = new DiamondGrabber();
    private final Grid grid;
    private final GrabProcessor grabProcessor;
    private final GameVariant gameVariant;
    
    public DragStartState(Grid grid, GrabProcessor grabProcessor, GameVariant gameVariant) {
        this.grid = grid;
        this.grabProcessor = grabProcessor;
        this.gameVariant = gameVariant;
    }
    
    @Override
    public boolean processInput(Input input) {
        return grabProcessor.processInput(input);
    }
    
    @Override
    public void enter(PlayerBoardStateState lastState, Object data) {
        grabProcessor.setGrabListener(this);
    }
    
    @Override
    public boolean grabTry(float x, float y, int pointer) {
        Hexagon hexagon = grid.getContainingHexagon(new Vector2(x, y));
        Hexagon origin = gameVariant.getNextGrabbedDiamondOrigin();
        if (origin != null && hexagon != origin) return false;
        if (hexagon != null && hexagon.hasDiamond()) {
            diamondGrabber.setStartHexagon(hexagon, x, y);
            return true;
        }
        return false;
    }
    
    @Override
    public void grabMoved(float x, float y, int pointer) {
        if (nextState != null) return;
        try {
            nextStateData = diamondGrabber.checkForDiamondGrab(x, y);
            nextState = DragState.class;
        } catch (NoDiamondGrabbedException e) {} catch (MoveNotPossibleException e) {
            grabProcessor.processInput(new Reset());
        }
    }
    
    @Override
    public void grabReleased(float x, float y, int pointer) {}
    
    @Override
    public void grabReset(int pointer) {}
    
    @Override
    public List<Whole> stop() {
        grabProcessor.processInput(new Reset());
        if (nextStateData != null) {
            MovingPack movingPack = (MovingPack) nextStateData;
            return movingPack.toWholes();
        }
        return null;
    }
}
