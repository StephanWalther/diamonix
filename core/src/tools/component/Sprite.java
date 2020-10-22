package tools.component;

import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.screen.*;

public class Sprite implements Whole {
    public final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    public final DefaultColorComponent colorComponent = new DefaultColorComponent();
    public final TextureComponent textureComponent
      = new TextureComponent(physicComponent, colorComponent, null);
    
    public Sprite() {}
    
    public Sprite(Sprite sprite) {
        this(sprite.physicComponent, sprite.colorComponent, sprite.textureComponent);
    }
    
    public Sprite(PhysicComponent physicComponent, ColorComponent colorComponent,
                  TextureComponent textureComponent) {
        this.physicComponent.setPhysicData(physicComponent.getPhysicData());
        this.colorComponent.setColor(colorComponent.getColor());
        this.textureComponent.setTextureRegion(textureComponent.getTextureRegion());
        this.textureComponent.setOrigin(textureComponent.getOrigin());
    }
    
    @Override
    public DefaultPhysicComponent getDefaultPhysicComponent() {
        return physicComponent;
    }
    
    @Override
    public DefaultColorComponent getDefaultColorComponent() {
        return colorComponent;
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(textureComponent);
    }
}
