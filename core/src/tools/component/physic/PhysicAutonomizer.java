package tools.component.physic;

import com.badlogic.gdx.math.*;

import java.util.*;

import tools.common.*;

public class PhysicAutonomizer implements Updatable {
    private final DynamicPhysicData dynamicPhysicData = new DynamicPhysicData();
    private final List<PrototypePhysicComponent> prototypePhysicComponents
      = new ArrayList<PrototypePhysicComponent>();
    
    @Override
    public void update(float dt) {
        for (PrototypePhysicComponent prototypePhysicComponent : prototypePhysicComponents) {
            updatePrototypePhysicComponent(prototypePhysicComponent, dt);
        }
        addVelocity(dynamicPhysicData.acceleration.x*dt,
          dynamicPhysicData.acceleration.y*dt);
        addRotationVelocity(dynamicPhysicData.rotationAcceleration*dt);
    }
    
    private void updatePrototypePhysicComponent(PrototypePhysicComponent prototypePhysicComponent,
                                                float dt) {
        prototypePhysicComponent.move(
          dynamicPhysicData.acceleration.x*dt*dt*0.5f + dynamicPhysicData.velocity.x*dt,
          dynamicPhysicData.acceleration.y*dt*dt*0.5f + dynamicPhysicData.velocity.y*dt);
        prototypePhysicComponent.rotate(dynamicPhysicData.rotationAcceleration*dt*dt*0.5f
                                          + dynamicPhysicData.rotationVelocity*dt);
    }
    
    public PhysicAutonomizer addPhysicComponent(PrototypePhysicComponent prototypePhysicComponent) {
        prototypePhysicComponents.add(prototypePhysicComponent);
        return this;
    }
    
    public PhysicAutonomizer removePhysicComponent(
      PrototypePhysicComponent prototypePhysicComponent) {
        prototypePhysicComponents.remove(prototypePhysicComponent);
        return this;
    }
    
    public PhysicAutonomizer setDynamicPhysicData(DynamicPhysicData dynamicPhysicData) {
        this.dynamicPhysicData.set(dynamicPhysicData);
        return this;
    }
    
    public DynamicPhysicData getDynamicPhysicData() {
        return new DynamicPhysicData(dynamicPhysicData);
    }
    
    public PhysicAutonomizer setVelocity(Vector2 velocity) {
        dynamicPhysicData.velocity.set(velocity);
        return this;
    }
    
    public PhysicAutonomizer setVelocityX(float velocityX) {
        dynamicPhysicData.velocity.x = velocityX;
        return this;
    }
    
    public PhysicAutonomizer setVelocityY(float velocityY) {
        dynamicPhysicData.velocity.y = velocityY;
        return this;
    }
    
    public PhysicAutonomizer addVelocity(float addVelocityX, float addVelocityY) {
        dynamicPhysicData.velocity.add(addVelocityX, addVelocityY);
        return this;
    }
    
    public Vector2 getVelocity() {
        return new Vector2(dynamicPhysicData.velocity);
    }
    
    public float getVelocityX() {
        return dynamicPhysicData.velocity.x;
    }
    
    public float getVelocityY() {
        return dynamicPhysicData.velocity.y;
    }
    
    public PhysicAutonomizer setAcceleration(Vector2 acceleration) {
        dynamicPhysicData.acceleration.set(acceleration);
        return this;
    }
    
    public PhysicAutonomizer setAccelerationX(float accelerationX) {
        dynamicPhysicData.acceleration.x = accelerationX;
        return this;
    }
    
    public PhysicAutonomizer setAccelerationY(float accelerationY) {
        dynamicPhysicData.acceleration.y = accelerationY;
        return this;
    }
    
    public Vector2 getAcceleration() {
        return new Vector2(dynamicPhysicData.acceleration);
    }
    
    public float getAccelerationX() {
        return dynamicPhysicData.acceleration.x;
    }
    
    public float getAccelerationY() {
        return dynamicPhysicData.acceleration.y;
    }
    
    public PhysicAutonomizer setRotationVelocity(float rotationVelocity) {
        dynamicPhysicData.rotationVelocity = rotationVelocity;
        return this;
    }
    
    public PhysicAutonomizer addRotationVelocity(float addRotationVelocity) {
        dynamicPhysicData.rotationVelocity += addRotationVelocity;
        return this;
    }
    
    public float getRotationVelocity() {
        return dynamicPhysicData.rotationVelocity;
    }
    
    public PhysicAutonomizer setRotationAcceleration(float rotationAcceleration) {
        dynamicPhysicData.rotationAcceleration = rotationAcceleration;
        return this;
    }
    
    public float getRotationAcceleration() {
        return dynamicPhysicData.rotationAcceleration;
    }
}
