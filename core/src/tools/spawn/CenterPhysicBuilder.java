package tools.spawn;

import tools.component.physic.*;

public class CenterPhysicBuilder implements SpawnPhysicBuilder {
    public float centerX;
    public float centerY;
    
    @Override
    public PhysicComponent build(PhysicComponent spawnPhysicComponent) {
        return PhysicComponentBagBuilder.build(centerX, centerY,
          spawnPhysicComponent, new AdderPhysicComponentBag());
    }
}
