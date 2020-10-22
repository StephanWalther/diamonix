package gameImplementation.effects.diamondDestructor;

import com.badlogic.gdx.math.*;

import gameImplementation.board.hexagonAndDiamond.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.Timer;

class DiamondPiecesData implements Updatable {
    private final Timer timer = new Timer(0.75f).increase();
    private final DiamondColor diamondColor;
    private final Vector2 origin;
    
    DiamondPiecesData(Diamond diamond) {
        diamondColor = diamond.diamondColor;
        origin = diamond.physicComponent.getCenter();
    }
    
    @Override
    public void update(float dt) {
        timer.update(dt);
    }
    
    DiamondColor getDiamondColor() {
        return diamondColor;
    }
    
    void apply(DefaultPhysicComponent physicComponent, DefaultColorComponent colorComponent,
               Mapping centerMappingX, Mapping centerMappingY) {
        float timeProgress = timer.getProgress();
        float centerX = origin.x + centerMappingX.calculate(timeProgress);
        float centerY = origin.y + centerMappingY.calculate(timeProgress);
        float alpha = Polynomial.reverseNegativeQuadratic.calculate(timeProgress);
        physicComponent.setCenter(centerX, centerY);
        colorComponent.setColorA(alpha);
    }
}
