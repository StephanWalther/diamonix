package tools.component.physic;

import com.badlogic.gdx.math.*;

import tools.component.graphic.*;

public class PrototypePhysicComponent implements PhysicComponent {
    final PhysicData physicData = new PhysicData();
    
    public PrototypePhysicComponent() {}
    
    public PrototypePhysicComponent(PhysicData physicData) {
        this.physicData.set(physicData);
    }
    
    public PrototypePhysicComponent setPhysicData(PhysicData physicData) {
        this.physicData.set(physicData);
        return this;
    }
    
    @Override
    public PhysicData getPhysicData() {
        return new PhysicData(physicData);
    }
    
    public PrototypePhysicComponent setCenter(Vector2 center) {
        setCenter(center.x, center.y);
        return this;
    }
    
    public PrototypePhysicComponent setCenter(float centerX, float centerY) {
        physicData.center.set(centerX, centerY);
        return this;
    }
    
    public PrototypePhysicComponent move(Vector2 offset) {
        move(offset.x, offset.y);
        return this;
    }
    
    public PrototypePhysicComponent move(float offsetX, float offsetY) {
        physicData.center.add(offsetX, offsetY);
        return this;
    }
    
    @Override
    public Vector2 getCenter() {
        return new Vector2(physicData.center);
    }
    
    public PrototypePhysicComponent setCenterX(float centerX) {
        physicData.center.x = centerX;
        return this;
    }
    
    @Override
    public float getCenterX() {
        return physicData.center.x;
    }
    
    public PrototypePhysicComponent setCenterY(float centerY) {
        physicData.center.y = centerY;
        return this;
    }
    
    @Override
    public float getCenterY() {
        return physicData.center.y;
    }
    
    @Override
    public Vector2 getScale() {
        return new Vector2(getScaleX(), getScaleY());
    }
    
    @Override
    public float getScaleX() {
        return physicData.scale.x;
    }
    
    @Override
    public float getScaleY() {
        return physicData.scale.y;
    }
    
    public PrototypePhysicComponent setRotation(float rotation) {
        physicData.rotation = rotation;
        return this;
    }
    
    public PrototypePhysicComponent rotate(float angle) {
        physicData.rotation += angle;
        return this;
    }
    
    @Override
    public float getRotation() {
        return physicData.rotation;
    }
}
