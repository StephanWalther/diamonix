package tools.component.graphic;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import tools.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.physic.*;
import tools.screen.*;

public abstract class PrototypeTextureComponent implements Drawable, Containable {
    private PhysicComponent physicComponent;
    private ColorComponent colorComponent;
    private Origin origin = Origin.center;
    
    public void setPhysicComponent(PhysicComponent physicComponent) {
        this.physicComponent = physicComponent;
    }
    
    public PhysicComponent getPhysicComponent() {
        return physicComponent;
    }
    
    public void setColorComponent(ColorComponent colorComponent) {
        this.colorComponent = colorComponent;
    }
    
    public ColorComponent getColorComponent() {
        return colorComponent;
    }
    
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }
    
    public Origin getOrigin() {
        return origin;
    }
    
    void prototypeDraw(Screen screen, TextureRegion textureRegion) {
        if (colorComponent.getColorA() < Tools.EPS) return;
        screen.drawer().draw(textureRegion,
          physicComponent.getCenterX()
            - origin.toX(textureRegion.getRegionWidth()),
          physicComponent.getCenterY()
            - origin.toY(textureRegion.getRegionHeight()),
          origin.toX(textureRegion.getRegionWidth()),
          origin.toY(textureRegion.getRegionHeight()),
          textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
          physicComponent.getScaleX(), physicComponent.getScaleY(),
          physicComponent.getRotation(), colorComponent.getColor());
    }
    
    boolean prototypeContains(float x, float y, TextureRegion textureRegion) {
        return prototypeGetBoundingRectangle(textureRegion).contains(x, y);
    }
    
    Rectangle prototypeGetBoundingRectangle(TextureRegion textureRegion) {
        Vector2 size = new Vector2(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        return Tools.buildRectangle(size, physicComponent, origin);
    }
}
