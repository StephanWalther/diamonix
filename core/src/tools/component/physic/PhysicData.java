package tools.component.physic;

import com.badlogic.gdx.math.*;

import tools.component.graphic.*;

public class PhysicData {
    public final Vector2 center = new Vector2(0, 0);
    public final Vector2 scale = new Vector2(1, 1);
    public float rotation = 0;
    
    public PhysicData() {}
    
    public PhysicData(PhysicData physicData) {
        set(physicData);
    }
    
    public void set(PhysicData physicData) {
        center.set(physicData.center);
        scale.set(physicData.scale);
        rotation = physicData.rotation;
    }
}
