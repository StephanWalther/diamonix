package gameImplementation.board.boardState.playerBoardState.dragState;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.boardState.playerBoardState.movingPack.*;
import gameImplementation.board.hexagonAndDiamond.*;
import tools.common.*;
import tools.screen.*;

class DiamondMover implements Drawable {
    private MovingPack movingPack;
    
    void setMovingPack(MovingPack movingPack) {
        this.movingPack = movingPack;
    }
    
    void update(float dt) {
        if (movingPack == null) return;
        movingPack.grabbedDiamond.update(dt);
        movingPack.movingDiamonds.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        if (movingPack == null) return;
        screen.draw(movingPack.grabbedDiamond);
        screen.draw(movingPack.movingDiamonds);
    }
    
    void moveDiamonds(float x, float y) {
        Vector2 linePoint = movingPack.movingLine
                              .toLinePoint(new Vector2(x, y).add(movingPack.grabOffset));
        movingPack.grabbedDiamond.physicComponent.setCenter(linePoint);
        List<Hexagon> beforeHexagonsWithMovableDiamonds
          = movingPack.movingLine.getBeforeHexagonsWithMovableDiamonds();
        List<Hexagon> afterHexagonsWithMovableDiamonds
          = movingPack.movingLine.getAfterHexagonsWithMovableDiamonds();
        if (linePoint.y < movingPack.startHexagon.getPhysicComponent().getCenterY()) {
            fixDiamonds(afterHexagonsWithMovableDiamonds);
            adjustDiamondsBefore(beforeHexagonsWithMovableDiamonds);
        } else {
            fixDiamonds(beforeHexagonsWithMovableDiamonds);
            adjustDiamondsAfter(afterHexagonsWithMovableDiamonds);
        }
    }
    
    private void fixDiamonds(List<Hexagon> hexagons) {
        for (Hexagon hexagon : hexagons) {
            if (!hexagon.hasDiamond()) {
                hexagon.attachDiamond(movingPack.movingDiamonds.remove(hexagon));
            }
        }
    }
    
    private void adjustDiamondsBefore(List<Hexagon> beforeHexagonsWithMovableDiamonds) {
        Vector2 center = movingPack.grabbedDiamond.physicComponent.getCenter();
        while (!beforeHexagonsWithMovableDiamonds.isEmpty() &&
                 center.y < movingPack.movingDirection
                              .next(beforeHexagonsWithMovableDiamonds.get(0))
                              .getPhysicComponent().getCenterY()) {
            Hexagon hexagon = beforeHexagonsWithMovableDiamonds.remove(0);
            center.add(hexagon.getPhysicComponent().getCenter());
            center.sub(movingPack.movingDirection.next(hexagon).getPhysicComponent().getCenter());
            if (hexagon.hasDiamond()) {
                Diamond diamond = hexagon.removeDiamond();
                diamond.physicComponent.setCenter(center);
                movingPack.movingDiamonds.add(new HexagonDiamondPair(hexagon, diamond));
            } else movingPack.movingDiamonds.setCenter(hexagon, center);
        }
        fixDiamonds(beforeHexagonsWithMovableDiamonds);
    }
    
    private void adjustDiamondsAfter(List<Hexagon> afterHexagonsWithMovableDiamonds) {
        Vector2 center = movingPack.grabbedDiamond.physicComponent.getCenter();
        while (!afterHexagonsWithMovableDiamonds.isEmpty() &&
                 center.y > movingPack.movingDirection
                              .previous(afterHexagonsWithMovableDiamonds.get(0))
                              .getPhysicComponent().getCenterY()) {
            Hexagon hexagon = afterHexagonsWithMovableDiamonds.remove(0);
            center.add(hexagon.getPhysicComponent().getCenter());
            center.sub(movingPack.movingDirection.previous(hexagon)
                         .getPhysicComponent().getCenter());
            if (hexagon.hasDiamond()) {
                Diamond diamond = hexagon.removeDiamond();
                diamond.physicComponent.setCenter(center);
                movingPack.movingDiamonds.add(new HexagonDiamondPair(hexagon, diamond));
            } else movingPack.movingDiamonds.setCenter(hexagon, center);
        }
        fixDiamonds(afterHexagonsWithMovableDiamonds);
    }
    
    MovingPack removeMovingPack() {
        MovingPack temp = movingPack;
        movingPack = null;
        return temp;
    }
}
