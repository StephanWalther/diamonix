package tools.particles;

import java.util.*;

import tools.common.*;
import tools.misc.*;

public abstract class ParticleEmitter implements Updatable {
    private final Accumulator timeAccumulator = new Accumulator();
    private final Random rnd = new Random();
    private final Particles particles;
    
    public ParticleEmitter(Particles particles) {
        this.particles = particles;
        timeAccumulator.setSubtractionAmount(1.f);
    }
    
    public ParticleEmitter setParticlesPerSecond(float particlesPerSecond) {
        timeAccumulator.setSubtractionAmount(1.f/particlesPerSecond);
        return this;
    }
    
    @Override
    public void update(float dt) {
        timeAccumulator.add(dt);
        int count = timeAccumulator.subtract();
        for (int i = 0; i < count; i++) particles.add(getParticle());
    }
    
    protected abstract Particle getParticle();
    
    protected final int randomInt(int limit) {
        return rnd.nextInt(limit);
    }
    
    protected final float randomFloat() {
        return randomInt(100000)*0.00001f;
    }
    
    protected final float randomFloat(float min, float max) {
        return randomFloat()*(max - min) + min;
    }
}
