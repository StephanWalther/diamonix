package tools.spawn;

import tools.component.color.*;
import tools.component.physic.*;
import tools.progressLine.*;

public class SpawnPack {
    public final PhysicComponent physicComponent;
    public final ColorComponent colorComponent;
    public final ProgressLine progressLine;
    
    SpawnPack(PhysicComponent physicComponent, ColorComponent colorComponent,
              ProgressLine progressLine) {
        this.physicComponent = physicComponent;
        this.colorComponent = colorComponent;
        this.progressLine = progressLine;
    }
}
