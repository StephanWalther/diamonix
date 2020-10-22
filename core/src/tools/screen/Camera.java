package tools.screen;

import com.badlogic.gdx.math.*;

public interface Camera {
    void setPosition(Vector2 vector2);
    
    Vector2 getPosition();
    
    void setZoom(float zoom);
    
    float getZoom();
    
    void update();
    
    Rectangle getVisibleRectangle();
}
