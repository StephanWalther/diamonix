package tools.component.physic;

import com.badlogic.gdx.math.*;

public interface PhysicComponent {
    PhysicData getPhysicData();
    
    Vector2 getCenter();
    
    float getCenterX();
    
    float getCenterY();
    
    Vector2 getScale();
    
    float getScaleX();
    
    float getScaleY();
    
    float getRotation();
}
