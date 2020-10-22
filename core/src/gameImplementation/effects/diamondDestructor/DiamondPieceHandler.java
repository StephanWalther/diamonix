package gameImplementation.effects.diamondDestructor;

import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;

public interface DiamondPieceHandler {
    void handle(DefaultPhysicComponent physicComponent, DefaultColorComponent colorComponent,
                TextureComponent textureComponent);
}
