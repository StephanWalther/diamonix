package tools.particles;

import java.util.*;

import tools.common.*;
import tools.component.graphic.*;
import tools.screen.*;

public class Particles implements Updatable, Drawable {
    private final List<Particle> particles = new ArrayList<Particle>();
    private final TextureComponent textureComponent;
    
    public Particles(TextureComponent textureComponent) {
        this.textureComponent = textureComponent;
    }
    
    public void add(Particle particle) {
        particles.add(particle);
    }
    
    public void clear() {
        particles.clear();
    }
    
    @Override
    public void update(float dt) {
        UpdateDistributer.updateAll(particles, dt);
        Remover.removeAllDoneElements(particles);
    }
    
    @Override
    public void draw(Screen screen) {
        for (Particle particle : particles) {
            textureComponent.setPhysicComponent(particle.getPhysicComponent());
            textureComponent.setColorComponent(particle.getColorComponent());
            screen.draw(textureComponent);
        }
    }
    
    public int size() {
        return particles.size();
    }
}
