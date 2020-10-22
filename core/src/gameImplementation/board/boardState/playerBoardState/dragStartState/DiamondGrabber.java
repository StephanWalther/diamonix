package gameImplementation.board.boardState.playerBoardState.dragStartState;

import com.badlogic.gdx.math.*;

import java.util.*;

import core.objects.screen.*;
import gameImplementation.board.boardState.playerBoardState.furtherHexagon.*;
import gameImplementation.board.boardState.playerBoardState.movingPack.*;
import gameImplementation.board.hexagonAndDiamond.*;

class DiamondGrabber {
    private final float squareMovingTolerance
      = (float) Math.pow(SizeGetter.getIdealWorldHeight()*0.05f, 2);
    private final Vector2 startTouch = new Vector2();
    private Hexagon startHexagon = null;
    
    void setStartHexagon(Hexagon startHexagon, float x, float y) {
        this.startHexagon = startHexagon;
        startTouch.set(x, y);
    }
    
    MovingPack checkForDiamondGrab(float x, float y)
      throws NoDiamondGrabbedException, MoveNotPossibleException {
        float squareDist = Vector2.len2(x - startTouch.x, y - startTouch.y);
        if (squareDist > squareMovingTolerance){
            MovingPack movingPack = handleMovingStart(x, y);
            if (movingPack == null) throw new MoveNotPossibleException();
            return movingPack;
        }
        throw new NoDiamondGrabbedException();
    }
    
    private MovingPack handleMovingStart(float x, float y) {
        MovingDirection movingDirection = determineMovingDirection(x, y);
        if (movingDirection == null) return null;
        Vector2 grabOffset = startHexagon.getPhysicComponent().getCenter().sub(x, y);
        Diamond grabbedDiamond = startHexagon.removeDiamond();
        List<Hexagon> hexagonLine = movingDirection.getShrinkedHexagonLine(startHexagon);
        MovingLine movingLine = new MovingLine(startHexagon, hexagonLine);
        return new MovingPack(startHexagon, grabbedDiamond, grabOffset, movingDirection,
          movingLine);
    }
    
    private MovingDirection determineMovingDirection(float x, float y) {
        Vector2 direction = new Vector2(x - startTouch.x, y - startTouch.y);
        float angle = direction.angle();
        if (0 <= angle && angle < 60) {
            return checkIfMovingIsPossible(MovingDirection.upperDiagonal, false);
        } else if (60 <= angle && angle < 120) {
            return checkIfMovingIsPossible(MovingDirection.vertical, false);
        } else if (120 <= angle && angle < 180) {
            return checkIfMovingIsPossible(MovingDirection.lowerDiagonal, false);
        } else if (180 <= angle && angle < 240) {
            return checkIfMovingIsPossible(MovingDirection.upperDiagonal, true);
        } else if (240 <= angle && angle < 300) {
            return checkIfMovingIsPossible(MovingDirection.vertical, true);
        } else return checkIfMovingIsPossible(MovingDirection.lowerDiagonal, true);
    }
    
    private MovingDirection checkIfMovingIsPossible(MovingDirection movingDirection, boolean previous) {
        FurtherHexagon furtherHexagon = previous ? new PreviousHexagon(movingDirection)
                                          : new NextHexagon(movingDirection);
        Hexagon hexagon = furtherHexagon.further(startHexagon);
        while (hexagon != null) {
            if (!hexagon.hasDiamond()) return movingDirection;
            hexagon = furtherHexagon.further(hexagon);
        }
        return null;
    }
}
