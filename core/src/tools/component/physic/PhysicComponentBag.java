package tools.component.physic;

import com.badlogic.gdx.math.*;

import java.util.*;

public abstract class PhysicComponentBag implements PhysicComponent {
    final List<PhysicComponent> physicComponents = new ArrayList<PhysicComponent>();
    
    public PhysicComponentBag addPhysicComponent(PhysicComponent physicComponent) {
        physicComponents.add(physicComponent);
        return this;
    }
    
    @Override
    public PhysicData getPhysicData() {
        PhysicData physicData = new PhysicData();
        physicData.center.set(getCenter());
        physicData.scale.set(getScale());
        physicData.rotation = getRotation();
        return physicData;
    }
    
    @Override
    public float getCenterX() {
        return getCenter().x;
    }
    
    @Override
    public float getCenterY() {
        return getCenter().y;
    }
    
    @Override
    public Vector2 getScale() {
        return new Vector2(getScaleX(), getScaleY());
    }
    
    @Override
    public float getScaleX() {
        float scaleX = 1;
        for (PhysicComponent physicComponent : physicComponents) {
            scaleX *= physicComponent.getScaleX();
        }
        return scaleX;
    }
    
    @Override
    public float getScaleY() {
        float scaleY = 1;
        for (PhysicComponent physicComponent : physicComponents) {
            scaleY *= physicComponent.getScaleY();
        }
        return scaleY;
    }
    
    @Override
    public float getRotation() {
        float rotation = 0;
        for (PhysicComponent physicComponent : physicComponents) {
            rotation += physicComponent.getRotation();
        }
        return rotation;
    }
}
