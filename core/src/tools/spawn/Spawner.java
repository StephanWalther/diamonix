package tools.spawn;

import com.badlogic.gdx.math.*;

import tools.component.color.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.*;

public class Spawner extends PrototypeProgressLineListener {
    private final DefaultPhysicComponent spawnPhysicComponent;
    private final DefaultColorComponent spawnColorComponent;
    private final Mapping spawnMoveMappingX;
    private final Mapping spawnMoveMappingY;
    
    public Spawner(DefaultPhysicComponent spawnPhysicComponent,
                   DefaultColorComponent spawnColorComponent, Vector2 spawnMoveDistance) {
        this.spawnPhysicComponent = spawnPhysicComponent;
        this.spawnColorComponent = spawnColorComponent;
        spawnMoveMappingX = Concatenation.linear(
          -spawnMoveDistance.x, spawnMoveDistance.x, Polynomial.negativeQuadratic);
        spawnMoveMappingY = Concatenation.linear(
          -spawnMoveDistance.y, spawnMoveDistance.y, Polynomial.negativeQuadratic);
        progressChanged(0);
    }
    
    @Override
    protected void progressChanged(float progress) {
        spawnPhysicComponent.setCenter(spawnMoveMappingX.calculate(progress),
          spawnMoveMappingY.calculate(progress));
        spawnPhysicComponent.setScale(Mapping.scaleUp.calculate(progress));
        spawnColorComponent.setColorA(PositiveSinWave.halfPeriod.calculate(progress));
    }
}
