package gameImplementation.effects;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.boardMediator.*;
import gameImplementation.effects.diamondDestructor.*;
import gameImplementation.effects.flyingPoints.*;
import gameImplementation.effects.pointNumbers.*;
import gameImplementation.points.*;
import tools.common.*;
import tools.screen.*;

public class Effects implements BoardListener, Drawable {
    private int pointsForDiamondDestruction = 0;
    private boolean increasePoints = true;
    private final FlyingPoints flyingPoints;
    private final DiamondDestructor diamondDestructor;
    private final PointNumbers pointNumbers = new PointNumbers();
    
    public Effects(Points points) {
        flyingPoints = new FlyingPoints(points);
        diamondDestructor = new DiamondDestructor();
    }
    
    public void update(float dt) {
        flyingPoints.update(dt);
        diamondDestructor.update(dt);
        pointNumbers.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(flyingPoints);
        screen.draw(diamondDestructor);
        screen.draw(pointNumbers);
    }
    
    @Override
    public void newDestruction(int destructionCountThisRound) {
        increasePoints = destructionCountThisRound == 1;
    }
    
    @Override
    public void diamondDestroyed(Diamond diamond) {
        if (increasePoints) pointsForDiamondDestruction++;
        handleDiamondDestruction(diamond);
    }
    
    @Override
    public void newRound() {
        pointsForDiamondDestruction = 0;
    }
    
    private void handleDiamondDestruction(Diamond diamond) {
        Vector2 diamondCenter = diamond.physicComponent.getCenter();
        flyingPoints.notifyDiamondDestruction(diamondCenter, pointsForDiamondDestruction);
        diamondDestructor.destroy(diamond);
        pointNumbers.addPointNumber(new PointNumber(diamondCenter, pointsForDiamondDestruction));
    }
    
    public int getPoints() {
        return flyingPoints.getPoints();
    }
    
    public List<Whole> stop() {
        pointsForDiamondDestruction = 0;
        increasePoints = true;
        List<Whole> wholes = flyingPoints.stop();
        wholes.addAll(diamondDestructor.stop());
        wholes.addAll(pointNumbers.stop());
        return wholes;
    }
}
