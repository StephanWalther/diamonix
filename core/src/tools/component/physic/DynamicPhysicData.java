package tools.component.physic;

import com.badlogic.gdx.math.*;

public class DynamicPhysicData {
    public final Vector2 velocity = new Vector2(0, 0);
    public final Vector2 acceleration = new Vector2(0, 0);
    public float rotationVelocity = 0;
    public float rotationAcceleration = 0;
    
    public DynamicPhysicData() {}
    
    public DynamicPhysicData(DynamicPhysicData dynamicPhysicData) {
        set(dynamicPhysicData);
    }
    
    public void set(DynamicPhysicData dynamicPhysicData) {
        velocity.set(dynamicPhysicData.velocity);
        acceleration.set(dynamicPhysicData.acceleration);
        rotationVelocity = dynamicPhysicData.rotationVelocity;
        rotationAcceleration = dynamicPhysicData.rotationAcceleration;
    }
}
