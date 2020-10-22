package tools.component.physic;

import com.badlogic.gdx.math.*;

public class DefaultPhysicComponent extends PrototypePhysicComponent {
    public DefaultPhysicComponent() {}
    
    public DefaultPhysicComponent(PhysicData physicData) {
        super(physicData);
    }
    
    public DefaultPhysicComponent setScale(Vector2 scale) {
        setScale(scale.x, scale.y);
        return this;
    }
    
    public DefaultPhysicComponent setScale(float scale) {
        setScale(scale, scale);
        return this;
    }
    
    public DefaultPhysicComponent setScale(float scaleX, float scaleY) {
        physicData.scale.set(scaleX, scaleY);
        return this;
    }
    
    public DefaultPhysicComponent setScaleX(float scaleX) {
        physicData.scale.x = scaleX;
        return this;
    }
    
    public DefaultPhysicComponent setScaleY(float scaleY) {
        physicData.scale.y = scaleY;
        return this;
    }
    
    public DefaultPhysicComponent scale(Vector2 factor) {
        return scale(factor.x, factor.y);
    }
    
    public DefaultPhysicComponent scale(float factor) {
        return scale(factor, factor);
    }
    
    public DefaultPhysicComponent scale(float factorX, float factorY) {
        physicData.scale.scl(factorX, factorY);
        return this;
    }
}
