package tools.particles;

import com.badlogic.gdx.graphics.*;

import java.util.*;

import tools.component.color.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.Timer;

public class DefaultParticle implements Particle {
    private static final Mapping alphaMapping = getAlphaMapping();
    
    private static Mapping getAlphaMapping() {
        IntervalPartition intervalPartition = new IntervalPartition(0.f, 0.1f, 0.9f, 1.f);
        List<Mapping> mappings = new ArrayList<Mapping>(3);
        mappings.add(PositiveSinWave.halfPeriod);
        mappings.add(Polynomial.one);
        mappings.add(PositiveCosWave.halfPeriod);
        List<Mapping> localToGlobalIntervalMappings = intervalPartition
                                                        .makeLinearLocalToGlobalIntervalMappings();
        return new MultiMapping(intervalPartition,
          Concatenation.pairwise(mappings, localToGlobalIntervalMappings));
    }
    
    private final DefaultPhysicComponent physicComponent;
    private final PhysicAutonomizer physicAutonomizer;
    private final DefaultColorComponent colorComponent;
    private final Timer timer = new Timer(1.f).increase();
    
    public DefaultParticle(PhysicData physicData, DynamicPhysicData dynamicPhysicData, Color
                                                                                         color) {
        physicComponent = new DefaultPhysicComponent(physicData);
        physicAutonomizer = new PhysicAutonomizer();
        physicAutonomizer.addPhysicComponent(physicComponent);
        physicAutonomizer.setDynamicPhysicData(dynamicPhysicData);
        colorComponent = new DefaultColorComponent(color).setColorA(0);
    }
    
    public DefaultParticle setLifeTime(final float lifeTime) {
        timer.setEndTime(lifeTime);
        return this;
    }
    
    @Override
    public PhysicComponent getPhysicComponent() {
        return physicComponent;
    }
    
    @Override
    public ColorComponent getColorComponent() {
        return colorComponent;
    }
    
    @Override
    public void update(final float dt) {
        physicAutonomizer.update(dt);
        timer.update(dt);
        colorComponent.setColorA(alphaMapping.calculate(timer.getProgress()));
    }
    
    @Override
    public boolean isDone() {
        return timer.getTime() == timer.getEndTime();
    }
}
