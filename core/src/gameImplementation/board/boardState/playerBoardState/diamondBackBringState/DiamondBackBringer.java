package gameImplementation.board.boardState.playerBoardState.diamondBackBringState;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;
import tools.common.*;
import tools.mapping.*;
import tools.progressLine.Timer;
import tools.screen.*;

class DiamondBackBringer implements Drawable {
    private List<HexagonDiamondPair> pairs;
    private Mapping moveMappingX;
    private Mapping moveMappingY;
    private final Timer timer = new Timer().increase();
    
    void bringBack(List<HexagonDiamondPair> pairs) {
        this.pairs = pairs;
        Vector2 centerDiff = pairs.get(0).diamond.physicComponent.getCenter()
                               .sub(pairs.get(0).hexagon.getPhysicComponent()
                                      .getCenter());
        moveMappingX = Concatenation.linear(-centerDiff.x, centerDiff.x,
          PositiveSinWave.halfPeriod);
        moveMappingY = Concatenation.linear(-centerDiff.y, centerDiff.y,
          PositiveSinWave.halfPeriod);
        float distFactor = 4.f*centerDiff.len2()/pairs.get(0).hexagon.effectiveDiameter2();
        distFactor = (float) Math.pow(distFactor, 0.25);
        timer.setEndTime(0.2f*distFactor);
        timer.setTime(0);
    }
    
    void update(float dt) {
        timer.update(dt);
        for (HexagonDiamondPair hexagonDiamondPair : pairs) {
            hexagonDiamondPair.diamond.update(dt);
        }
        if (timer.getTime() == timer.getEndTime()) {
            attachAllDiamonds();
        } else updateAllDiamondCenters();
    }
    
    private void attachAllDiamonds() {
        for (HexagonDiamondPair hdp : pairs) hdp.hexagon.attachDiamond(hdp.diamond);
        pairs = null;
    }
    
    private void updateAllDiamondCenters() {
        float centerX = moveMappingX.calculate(timer.getProgress());
        float centerY = moveMappingY.calculate(timer.getProgress());
        for (HexagonDiamondPair hdp : pairs) {
            Vector2 newCenter = hdp.hexagon.getPhysicComponent().getCenter().add(centerX, centerY);
            hdp.diamond.physicComponent.setCenter(newCenter);
        }
    }
    
    @Override
    public void draw(Screen screen) {
        for (HexagonDiamondPair hdp : pairs) screen.draw(hdp.diamond);
    }
    
    boolean hasDiamonds() {
        return pairs != null;
    }
    
    List<Whole> reset() {
        List<Whole> wholes = new ArrayList<Whole>(HexagonDiamondPair.toWholes(pairs));
        pairs.clear();
        return wholes;
    }
}
