package core.objects.background;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

import java.util.*;

import core.objects.screen.*;
import tools.component.physic.*;
import tools.particles.*;

class BackgroundParticleEmitter extends ParticleEmitter {
    private final List<Color> colors = new ArrayList<Color>();
    
    BackgroundParticleEmitter(Particles particles) {
        super(particles);
        setParticlesPerSecond(1);
        colors.add(new Color(0.95f, 1.f, 1, 1));
        colors.add(new Color(1, 1, 0.9f, 1));
        colors.add(new Color(1, 1, 0.8f, 1));
    }
    
    @Override
    protected Particle getParticle() {
        PhysicData physicData = new PhysicData();
        float positionX = randomFloat(-0.5f, 0.5f)*SizeGetter.getWorldWidth();
        float positionY = randomFloat(-0.5f, -0.25f)*SizeGetter.getIdealWorldHeight();
        physicData.center.set(positionX, positionY);
        float scale = randomFloat(0.4f, 1.f);
        physicData.scale.set(scale, scale);
        
        DynamicPhysicData dynamicPhysicData = new DynamicPhysicData();
        Vector2 velocity = new Vector2(0,
          randomFloat(0.035f, 0.05f)*SizeGetter.getIdealWorldWidth());
        velocity.rotate(randomFloat(-20.f, 20.f));
        dynamicPhysicData.velocity.set(velocity);
        
        float lifetime = randomFloat(35.f, 40.f);
        
        return new DefaultParticle(physicData, dynamicPhysicData,
          colors.get(randomInt(colors.size()))).setLifeTime(lifetime);
    }
}
