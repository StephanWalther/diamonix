package tools.component.graphic;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import tools.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.physic.*;
import tools.screen.*;

public class TextureComponent extends PrototypeTextureComponent implements Drawable, Containable {
    private TextureRegion textureRegion;
    
    public TextureComponent(TextureOrigin textureOrigin) {
        this(null, null, textureOrigin);
    }
    
    public TextureComponent(PhysicComponent physicComponent,
                            ColorComponent colorComponent,
                            TextureOrigin textureOrigin) {
        setPhysicComponent(physicComponent);
        setColorComponent(colorComponent);
        setTextureOrigin(textureOrigin);
    }
    
    public void setTextureOrigin(TextureOrigin textureOrigin) {
        if (textureOrigin == null) return;
        setTextureRegion(textureOrigin.textureRegion);
        setOrigin(textureOrigin.origin);
    }
    
    public TextureOrigin getTextureOrigin() {
        return new TextureOrigin(textureRegion, getOrigin());
    }
    
    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
    
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
    
    @Override
    public void draw(Screen screen) {
        prototypeDraw(screen, textureRegion);
    }
    
    @Override
    public boolean contains(float x, float y) {
        return prototypeContains(x, y, textureRegion);
    }
    
    public Rectangle getBoundingRectangle() {
        return prototypeGetBoundingRectangle(textureRegion);
    }
    
    public Rectangle getNotModifiedBoundingRectangle() {
        return Tools.buildRectangle(new Vector2(
            getTextureRegion().getRegionWidth(), getTextureRegion().getRegionHeight()),
          getPhysicComponent().getCenter(), getOrigin());
    }
}
