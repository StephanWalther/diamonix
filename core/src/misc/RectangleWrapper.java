package misc;

import com.badlogic.gdx.math.*;

import tools.common.*;

public class RectangleWrapper implements Containable {
    public final Rectangle rectangle;
    
    public RectangleWrapper() {
        rectangle = new Rectangle();
    }
    
    public RectangleWrapper(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    
    @Override
    public boolean contains(float x, float y) {
        return rectangle.contains(x, y);
    }
}
