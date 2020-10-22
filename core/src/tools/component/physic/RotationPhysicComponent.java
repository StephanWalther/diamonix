package tools.component.physic;

import com.badlogic.gdx.math.*;

public class RotationPhysicComponent extends PrototypePhysicComponent {
    private float rotationX = 0;
    private float rotationY = 0;
    
    public RotationPhysicComponent() {}
    
    public RotationPhysicComponent(PhysicData physicData) {
        super(physicData);
    }
    
    public RotationPhysicComponent setRotationX(float rotationX) {
        this.rotationX = rotationX;
        updateScaleY();
        return this;
    }
    
    public RotationPhysicComponent rotateX(float angleX) {
        rotationX += angleX;
        updateScaleY();
        return this;
    }
    
    public float getRotationX() {
        return rotationX;
    }
    
    public RotationPhysicComponent setRotationY(float rotationY) {
        this.rotationY = rotationY;
        updateScaleX();
        return this;
    }
    
    public RotationPhysicComponent rotateY(float angleY) {
        rotationY += angleY;
        updateScaleX();
        return this;
    }
    
    public float getRotationY() {
        return rotationY;
    }
    
    private RotationPhysicComponent updateScaleY() {
        rotationX = rotationX%360.f;
        if (rotationX < 0) rotationX += 360.f;
        physicData.scale.y = MathUtils.cosDeg(rotationX);
        return this;
    }
    
    private RotationPhysicComponent updateScaleX() {
        rotationY = rotationY%360.f;
        if (rotationY < 0) rotationY += 360.f;
        physicData.scale.x = MathUtils.cosDeg(rotationY);
        return this;
    }
}
