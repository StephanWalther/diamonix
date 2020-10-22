package gameImplementation.board.boardState.playerBoardState.movingPack;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;

public class MovingLine {
    private final List<Hexagon> hexagonLine;
    private final List<Hexagon> beforeHexagonsWithMovableDiamonds = new ArrayList<Hexagon>();
    private final List<Hexagon> afterHexagonsWithMovableDiamonds = new ArrayList<Hexagon>();
    
    private final Vector2 start = new Vector2();
    private final Vector2 end = new Vector2();
    private final Vector2 direction = new Vector2();
    private final float distBetweenStartAndEnd;
    
    public MovingLine(Hexagon startHexagon, List<Hexagon> hexagonLine) {
        this.hexagonLine = hexagonLine;
        boolean previous = true;
        for (Hexagon hexagon : hexagonLine) {
            if (hexagon == startHexagon) previous = false;
            else if (hexagon.hasDiamond()) {
                if (previous) beforeHexagonsWithMovableDiamonds.add(0, hexagon);
                else afterHexagonsWithMovableDiamonds.add(hexagon);
            }
        }
        start.set(hexagonLine.get(beforeHexagonsWithMovableDiamonds.size()).getPhysicComponent()
                    .getCenter());
        end.set(hexagonLine.get(hexagonLine.size() - 1 - afterHexagonsWithMovableDiamonds.size())
                  .getPhysicComponent().getCenter());
        direction.set(end.x - start.x, end.y - start.y);
        distBetweenStartAndEnd = direction.len();
        direction.scl(1.f/distBetweenStartAndEnd);
    }
    
    public Vector2 toLinePoint(Vector2 point) {
        float t = direction.dot(point.sub(start));
        if (t <= 0) return new Vector2(start);
        else if (t >= distBetweenStartAndEnd) return new Vector2(end);
        return new Vector2(direction).scl(t).add(start);
    }
    
    public List<Hexagon> getBeforeHexagonsWithMovableDiamonds() {
        return new ArrayList<Hexagon>(beforeHexagonsWithMovableDiamonds);
    }
    
    public List<Hexagon> getAfterHexagonsWithMovableDiamonds() {
        return new ArrayList<Hexagon>(afterHexagonsWithMovableDiamonds);
    }
    
    public Hexagon removeNearestHexagonWithoutDiamond(Vector2 point) {
        float dist = Float.MAX_VALUE;
        int nearestHexagon = 0;
        for (int i = 0; i < hexagonLine.size(); i++) {
            float newDist = hexagonLine.get(i).getPhysicComponent().getCenter().dst(point);
            if (newDist <= dist) {
                dist = newDist;
                nearestHexagon = i;
            }
        }
        return hexagonLine.remove(nearestHexagon);
    }
}
