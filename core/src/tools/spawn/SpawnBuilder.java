package tools.spawn;

import com.badlogic.gdx.math.*;

import tools.component.color.*;
import tools.component.physic.*;
import tools.progressLine.*;

public class SpawnBuilder {
    public SpawnPhysicBuilder spawnPhysicBuilder;
    public ControlColorBuilder controlColorBuilder = null;
    public final Vector2 spawnMoveDistance = new Vector2();
    public ProgressLine progressLine;
    
    protected SpawnPack build() {
        DefaultPhysicComponent spawnPhysicComponent = new DefaultPhysicComponent();
        PhysicComponent physicComponent = spawnPhysicBuilder.build(spawnPhysicComponent);
        DefaultColorComponent spawnColorComponent = new DefaultColorComponent();
        ColorComponent colorComponent = controlColorBuilder == null ? spawnColorComponent :
                                          controlColorBuilder.build(spawnColorComponent);
        Spawner spawner = new Spawner(spawnPhysicComponent, spawnColorComponent, spawnMoveDistance);
        ProgressLineObserver progressLineObserver
          = new ProgressLineObserver(progressLine, spawner);
        return new SpawnPack(physicComponent, colorComponent, progressLineObserver);
    }
}
