package gameImplementation.gameVariant.tutorialGameVariant;

import com.badlogic.gdx.math.*;

import core.*;
import misc.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.misc.*;
import tools.progressLine.*;
import tools.screen.*;

class Arrow implements Updatable, Drawable, Whole {
    private final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    private final DefaultColorComponent colorComponent = new DefaultColorComponent();
    private final TextureComponent lineTextureComponent;
    private final TextureComponent headTextureComponent;
    private final Indicator indicator = new Indicator();
    
    Arrow(Vector2 start, Vector2 end) {
        physicComponent.setCenter(start);
        Vector2 diff = new Vector2(end).sub(start);
        physicComponent.setRotation(diff.angle());
        colorComponent.setColorA(0);
        float dist = diff.len();
        
        TextureOrigin lineTextureOrigin = TextureOrigins.get("arrowLine");
        TextureOrigin headTextureOrigin = TextureOrigins.get("arrowHead");
        
        float scaleX = (dist - headTextureOrigin.textureRegion.getRegionWidth()*0.99f)
                         /(lineTextureOrigin.textureRegion.getRegionWidth());
        DefaultPhysicComponent linePhysicComponent = new DefaultPhysicComponent();
        linePhysicComponent.setScale(scaleX, 1);
        
        lineTextureComponent = new TextureComponent(PhysicComponentBagBuilder.build(
          linePhysicComponent, physicComponent, new NodePhysicComponentBag()), colorComponent,
          lineTextureOrigin);
    
        headTextureComponent = new TextureComponent(PhysicComponentBagBuilder.build(
          new Vector2(dist, 0), physicComponent, new NodePhysicComponentBag()), colorComponent,
          headTextureOrigin);
        
        indicator.setPeriodTime(1.5f);
        indicator.setAlphaBounds(0.2f, 1.f);
    }
    
    @Override
    public void update(float dt) {
        indicator.update(dt);
        colorComponent.setColorA(indicator.getAlpha());
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(lineTextureComponent);
        screen.draw(headTextureComponent);
    }
    
    @Override
    public DefaultPhysicComponent getDefaultPhysicComponent() {
        return physicComponent;
    }
    
    @Override
    public DefaultColorComponent getDefaultColorComponent() {
        return colorComponent;
    }
}
