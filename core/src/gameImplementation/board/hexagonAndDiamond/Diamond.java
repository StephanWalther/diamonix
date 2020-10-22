package gameImplementation.board.hexagonAndDiamond;

import core.*;
import tools.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

public class Diamond implements Updatable, Whole {
    public final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    private final DefaultPhysicComponent attachedPhysicComponent = new DefaultPhysicComponent();
    public final DefaultColorComponent colorComponent = new DefaultColorComponent();
    public final DiamondColor diamondColor;
    private final TextureComponent textureComponent;
    private final Mapping scaleMapping = Concatenation.linear(
      0.6f, 0.4f, PositiveSinWave.halfPeriod);
    private final Timer attachedScaleTimer = new Timer(0.2f);
    
    public Diamond(DiamondColor diamondColor) {
        this.diamondColor = diamondColor;
        PhysicComponent pc = new AdderPhysicComponentBag().addPhysicComponent(physicComponent)
                               .addPhysicComponent(attachedPhysicComponent);
        colorComponent.setColorA(0);
        textureComponent = new TextureComponent(pc, colorComponent,
          TextureOrigins.get("diamond" + Tools.capitalize(diamondColor.toString())));
        attachedScaleTimer.setTime(attachedScaleTimer.getEndTime()).increase();
    }
    
    @Override
    public void update(float dt) {
        attachedScaleTimer.update(dt);
        attachedPhysicComponent.setScale(scaleMapping.calculate(attachedScaleTimer.getProgress()));
    }
    
    @Override
    public DefaultPhysicComponent getDefaultPhysicComponent() {
        return physicComponent;
    }
    
    @Override
    public DefaultColorComponent getDefaultColorComponent() {
        return colorComponent;
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(textureComponent);
    }
    
    void attachedToHexagon() {
        attachedScaleTimer.increase();
    }
    
    void removedFromHexagon() {
        attachedScaleTimer.decrease();
    }
}
