package tools.component.physic;

import com.badlogic.gdx.math.*;

public class NodePhysicComponentBag extends PhysicComponentBag {
    @Override
    public Vector2 getCenter() {
        final Vector2 center = new Vector2();
        for (PhysicComponent physicComponent : physicComponents) {
            center.scl(physicComponent.getScaleX(), physicComponent.getScaleY());
            center.rotate(physicComponent.getRotation());
            center.add(physicComponent.getCenter());
        }
        return center;
    }
}
