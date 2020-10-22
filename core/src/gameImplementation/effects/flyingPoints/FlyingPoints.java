package gameImplementation.effects.flyingPoints;

import com.badlogic.gdx.math.*;

import java.util.*;

import core.*;
import gameImplementation.points.*;
import tools.common.*;
import tools.component.*;
import tools.mapping.*;
import tools.screen.*;

public class FlyingPoints implements Drawable {
    private final Sprite sprite = new Sprite();
    private final Mapping alphaMapping;
    private final List<PointPack> pointPacks = new ArrayList<PointPack>();
    private final Points points;
    
    public FlyingPoints(Points points) {
        sprite.textureComponent.setTextureOrigin(TextureOrigins.get("starSmall"));
        this.points = points;
        IntervalPartition intervalPartition = new IntervalPartition(0.f, 0.1f, 0.9f, 1.f);
        List<Mapping> mappings = new ArrayList<Mapping>(2);
        mappings.add(PositiveSinWave.halfPeriod);
        mappings.add(Polynomial.one);
        mappings.add(PositiveCosWave.halfPeriod);
        mappings = Concatenation.pairwise(mappings,
          intervalPartition.makeLinearLocalToGlobalIntervalMappings());
        alphaMapping = new MultiMapping(intervalPartition, mappings);
    }
    
    public void update(float dt) {
        for (Iterator<PointPack> iterator = pointPacks.iterator(); iterator.hasNext(); ) {
            PointPack pointPack = iterator.next();
            pointPack.update(dt);
            if (pointPack.isDone()) {
                points.addPoints(pointPack.size());
                iterator.remove();
            }
        }
    }
    
    @Override
    public void draw(final Screen screen) {
        traversePoints(new PointHandler() {
            @Override
            public void handle(Sprite sprite) {
                screen.draw(sprite);
            }
        });
    }
    
    public void notifyDiamondDestruction(Vector2 center, int pointCount) {
        pointPacks.add(new PointPack(center, points.getPointDestination(), pointCount));
    }
    
    public List<Whole> stop() {
        final List<Whole> wholes = new ArrayList<Whole>();
        traversePoints(new PointHandler() {
            @Override
            public void handle(Sprite sprite) {
                wholes.add(new Sprite(sprite));
            }
        });
        pointPacks.clear();
        return wholes;
    }
    
    private void traversePoints(PointHandler pointHandler) {
        for (PointPack pointPack : pointPacks) {
            List<Vector2> positions = pointPack.getPositions();
            sprite.colorComponent.setColorA(alphaMapping.calculate(pointPack.getTimeProgress()));
            for (Vector2 position : positions) {
                sprite.physicComponent.setCenter(position);
                pointHandler.handle(sprite);
            }
        }
    }
    
    public int getPoints() {
        int points = 0;
        for (PointPack pointPack : pointPacks) points += pointPack.size();
        return points;
    }
}
