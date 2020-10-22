package tools.component.physic;

import com.badlogic.gdx.math.*;

public final class PhysicComponentBagBuilder {
    private PhysicComponentBagBuilder() {}
    
    public static <E extends PhysicComponentBag> E
    build(Vector2 center, PhysicComponent physicComponent, E physicComponentBag) {
        return build(center.x, center.y, physicComponent, physicComponentBag);
    }
    
    public static <E extends PhysicComponentBag> E
    build(float centerX, float centerY, PhysicComponent physicComponent, E physicComponentBag) {
        DefaultPhysicComponent defaultPhysicComponent = new DefaultPhysicComponent();
        defaultPhysicComponent.setCenter(centerX, centerY);
        return build(defaultPhysicComponent, physicComponent, physicComponentBag);
    }
    
    public static <E extends PhysicComponentBag> E
    build(PhysicComponent physicComponentOne, PhysicComponent physicComponentTwo,
          E physicComponentBag) {
        physicComponentBag.addPhysicComponent(physicComponentOne);
        physicComponentBag.addPhysicComponent(physicComponentTwo);
        return physicComponentBag;
    }
}
