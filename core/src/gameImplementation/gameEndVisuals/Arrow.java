package gameImplementation.gameEndVisuals;

import core.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

class Arrow extends PrototypeProgressLineListener implements Drawable {
    private final TextureComponent textureComponent
      = new TextureComponent(TextureOrigins.get("indicationArrow"));
    private final DefaultColorComponent spawnColorComponent = new DefaultColorComponent();
    private final DefaultColorComponent controlColorComponent = new DefaultColorComponent();
    
    Arrow(float targetX, float targetY, float radius, float angle) {
        DefaultPhysicComponent globalPhysicComponent = new DefaultPhysicComponent();
        globalPhysicComponent.setCenter(targetX, targetY);
        globalPhysicComponent.setRotation(angle);
        textureComponent.setPhysicComponent(PhysicComponentBagBuilder.build(radius, 0,
          globalPhysicComponent, new NodePhysicComponentBag()));
        
        ColorComponentBag colorComponentBag = new ColorComponentBag();
        colorComponentBag.addColorComponent(spawnColorComponent);
        colorComponentBag.addColorComponent(controlColorComponent);
        textureComponent.setColorComponent(colorComponentBag);
        
        spawnColorComponent.setColorA(0);
        controlColorComponent.setColorA(0);
    }
    
    @Override
    protected void progressChanged(float progress) {
        spawnColorComponent.setColorA(PositiveSinWave.halfPeriod.calculate(progress));
    }
    
    void setAlpha(float alpha) {
        controlColorComponent.setColorA(alpha);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(textureComponent);
    }
}
