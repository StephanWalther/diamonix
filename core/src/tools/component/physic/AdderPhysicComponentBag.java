package tools.component.physic;

import com.badlogic.gdx.math.*;

public class AdderPhysicComponentBag extends PhysicComponentBag {
    @Override
    public Vector2 getCenter() {
        final Vector2 center = new Vector2();
        for (PhysicComponent physicComponent : physicComponents) {
            center.add(physicComponent.getCenter());
        }
        return center;
    }
}
