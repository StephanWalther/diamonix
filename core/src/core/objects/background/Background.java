package core.objects.background;

import applicationState.state.*;
import core.*;
import core.objects.progress.*;
import tools.common.*;
import tools.component.graphic.*;
import tools.input.input.*;
import tools.input.processors.*;
import tools.particles.*;
import tools.screen.*;

public class Background extends DefaultInputProcessor implements Common {
    private final MovingTexture movingTexture = new MovingTexture();
    private final Particles particles = new Particles(new TextureComponent(
      TextureOrigins.get("particle")));
    private final ParticleEmitter particleEmitter = new BackgroundParticleEmitter(particles);
    private final StateChanger stateChanger;
    
    public Background(Progress progress) {
        stateChanger  = new StateChanger(progress);
    }
    
    public void setExternalStateChangeRequestHandler(ExternalStateChangeRequestHandler e) {
        stateChanger.setExternalStateChangeRequestHandler(e);
    }
    
    @Override
    public boolean processInput(Input input) {
        return super.processInput(input) || stateChanger.processInput(input);
    }
    
    @Override
    public void receiveResize(int width, int height) {
        movingTexture.resize(width, height);
    }
    
    @Override
    public void update(float dt) {
        movingTexture.update(dt);
        particles.update(dt);
        particleEmitter.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(movingTexture);
        screen.draw(particles);
    }
}
