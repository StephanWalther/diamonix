package gameImplementation.effects.flyingPoints;

import com.badlogic.gdx.math.*;

import java.util.*;

import core.*;
import core.objects.screen.*;
import gameImplementation.board.hexagonAndDiamond.*;
import tools.*;
import tools.common.*;
import tools.mapping.*;
import tools.progressLine.Timer;

class PointPack implements Updatable, Removable {
    private final Timer timer;
    private final Mapping mainPositionMappingX;
    private final Mapping mainPositionMappingY;
    private final Mapping localPositionMapping = new MirrorY(Polynomial.quadraticPeak(4.f), 0.5f);
    private final List<Vector2> initialLocalPositions;
    
    PointPack(Vector2 start, Vector2 end, int count) {
        float desiredVelocity = SizeGetter.getIdealWorldHeight()*0.5f;
        timer = new Timer(Vector2.dst(start.x, start.y, end.x, end.y)/desiredVelocity).increase();
        mainPositionMappingX = Concatenation.linear(end.x - start.x, start.x, Polynomial.quadratic);
        mainPositionMappingY = Concatenation.linear(end.y - start.y, start.y, Polynomial.quadratic);
        initialLocalPositions = new ArrayList<Vector2>(count);
        float maxLocalRadius = TextureOrigins.get(
          "diamond" + Tools.capitalize(DiamondColor.values()[0].toString()))
                                 .textureRegion.getRegionHeight()*0.5f;
        float minLocalRadius = maxLocalRadius*0.4f;
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            Vector2 initialLocalPosition = new Vector2();
            initialLocalPosition.x = minLocalRadius +
                                       random.nextInt(1000)*0.001f*(maxLocalRadius -
                                                                      minLocalRadius);
            initialLocalPosition.rotate(random.nextInt(36000)*0.01f);
            initialLocalPositions.add(initialLocalPosition);
        }
    }
    
    @Override
    public void update(float dt) {
        timer.update(dt);
    }
    
    List<Vector2> getPositions() {
        List<Vector2> positions = new ArrayList<Vector2>(initialLocalPositions.size());
        for (Vector2 initialLocalPosition : initialLocalPositions) {
            float timeProgress = timer.getProgress();
            Vector2 position = new Vector2(initialLocalPosition);
            position.scl(localPositionMapping.calculate(timeProgress));
            position.add(mainPositionMappingX.calculate(timeProgress),
              mainPositionMappingY.calculate(timeProgress));
            positions.add(position);
        }
        return positions;
    }
    
    float getTimeProgress() {
        return timer.getProgress();
    }
    
    int size() {
        return initialLocalPositions.size();
    }
    
    @Override
    public boolean isDone() {
        return timer.getTime() == timer.getEndTime();
    }
}
