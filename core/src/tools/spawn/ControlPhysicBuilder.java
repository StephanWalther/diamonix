package tools.spawn;

import java.util.*;

import tools.component.physic.*;

public class ControlPhysicBuilder implements SpawnPhysicBuilder {
    public final List<DefaultPhysicComponent> producedControlComponents
      = new ArrayList<DefaultPhysicComponent>();
    
    @Override
    public PhysicComponent build(PhysicComponent spawnPhysicComponent) {
        DefaultPhysicComponent defaultPhysicComponent = new DefaultPhysicComponent();
        producedControlComponents.add(defaultPhysicComponent);
        return PhysicComponentBagBuilder.build(spawnPhysicComponent, defaultPhysicComponent,
          new AdderPhysicComponentBag());
    }
}
