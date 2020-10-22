package gameImplementation.board.boardState.playerBoardState.dragState;

import java.util.*;

import core.*;
import core.objects.audio.*;
import gameImplementation.board.boardState.playerBoardState.*;
import gameImplementation.board.boardState.playerBoardState.diamondBackBringState.*;
import gameImplementation.board.boardState.playerBoardState.furtherHexagon.*;
import gameImplementation.board.boardState.playerBoardState.movingPack.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.input.input.*;
import tools.input.processors.grab.*;
import tools.screen.*;

public class DragState extends PlayerBoardStateState implements GrabListener {
    private final DiamondMover diamondMover = new DiamondMover();
    private final GrabProcessor grabProcessor;
    private final GameVariant gameVariant;
    private final Sound releaseSucceedSound = Sounds.get("beep");
    private final Sound releaseFailedSound = Sounds.get("error");
    
    public DragState(GrabProcessor grabProcessor, GameVariant gameVariant) {
        this.grabProcessor = grabProcessor;
        this.gameVariant = gameVariant;
    }
    
    @Override
    public boolean processInput(Input input) {
        return grabProcessor.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        diamondMover.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(diamondMover);
    }
    
    @Override
    public void enter(PlayerBoardStateState lastState, Object data) {
        diamondMover.setMovingPack((MovingPack) data);
        grabProcessor.setGrabListener(this);
    }
    
    @Override
    public boolean grabTry(float x, float y, int pointer) {
        return false;
    }
    
    @Override
    public void grabMoved(float x, float y, int pointer) {
        diamondMover.moveDiamonds(x, y);
    }
    
    @Override
    public void grabReleased(float x, float y, int pointer) {
        grabReset(pointer);
    }
    
    @Override
    public void grabReset(int pointer) {
        MovingPack movingPack = diamondMover.removeMovingPack();
        if (movingPack == null) return;
        Hexagon hexagonBelowGrabbedDiamond
          = movingPack.movingLine.removeNearestHexagonWithoutDiamond(
          movingPack.grabbedDiamond.physicComponent.getCenter());
        boolean lastDiamondMovementSucceeded
          = checkIfLastMovementSucceeded(movingPack, hexagonBelowGrabbedDiamond);
        Hexagon hexagonForGrabbedDiamond = lastDiamondMovementSucceeded ?
                                             hexagonBelowGrabbedDiamond : movingPack.startHexagon;
        nextStateData = new DiamondBackBringData(lastDiamondMovementSucceeded,
          buildBackBringPairs(hexagonForGrabbedDiamond, movingPack));
        nextState = DiamondBackBringState.class;
    }
    
    private boolean checkIfLastMovementSucceeded(MovingPack movingPack,
                                                 Hexagon hexagonBelowGrabbedDiamond) {
        Hexagon grabbedDiamondDestination = gameVariant.getNextGrabbedDiamondDestination();
        if (movingPack.startHexagon != hexagonBelowGrabbedDiamond &&
              (grabbedDiamondDestination == null ||
                 hexagonBelowGrabbedDiamond == grabbedDiamondDestination)) {
            releaseSucceedSound.play();
            return true;
        } else {
            releaseFailedSound.play();
            return false;
        }
    }
    
    private List<HexagonDiamondPair> buildBackBringPairs(Hexagon hexagonForGrabbedDiamond,
                                                         MovingPack movingPack) {
        List<HexagonDiamondPair> pairs = new ArrayList<HexagonDiamondPair>();
        pairs.add(new HexagonDiamondPair(hexagonForGrabbedDiamond, movingPack.grabbedDiamond));
        collectDiamondsFromMovingDiamonds(movingPack, pairs);
        return pairs;
    }
    
    private void collectDiamondsFromMovingDiamonds(MovingPack movingPack,
                                                   List<HexagonDiamondPair> pairs) {
        boolean isPrevious = movingPack.grabbedDiamond.physicComponent.getCenterY()
                               < movingPack.startHexagon.getPhysicComponent().getCenterY();
        FurtherHexagon furtherHexagon
          = isPrevious ? new PreviousHexagon(movingPack.movingDirection)
              : new NextHexagon(movingPack.movingDirection);
        movingPack.movingDiamonds.clear(furtherHexagon, pairs);
    }
    
    @Override
    public List<Whole> stop() {
        grabProcessor.processInput(new Reset());
        if (nextStateData != null) {
            DiamondBackBringData diamondBackBringData = (DiamondBackBringData) nextStateData;
            return HexagonDiamondPair.toWholes(diamondBackBringData.backBringPairs);
        }
        return diamondMover.removeMovingPack().toWholes();
    }
}
