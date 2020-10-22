package tools.component;

import com.badlogic.gdx.graphics.g2d.*;

import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.screen.*;

public class TwoColumnText implements Drawable {
    private final DefaultPhysicComponent leftDistPhysicComponent = new DefaultPhysicComponent();
    private final DefaultPhysicComponent rightDistPhysicComponent = new DefaultPhysicComponent();
    private final TextComponent leftTextComponent = new TextComponent(null);
    private final TextComponent rightTextComponent = new TextComponent(null);
    
    public TwoColumnText(PhysicComponent physicComponent, ColorComponent colorComponent) {
        PhysicComponent leftPhysicComponent = PhysicComponentBagBuilder.build(
          leftDistPhysicComponent, physicComponent, new NodePhysicComponentBag());
        leftTextComponent.setPhysicComponent(leftPhysicComponent)
          .setColorComponent(colorComponent);
        PhysicComponent rightPhysicComponent = PhysicComponentBagBuilder.build(
          rightDistPhysicComponent, physicComponent, new NodePhysicComponentBag());
        rightTextComponent.setPhysicComponent(rightPhysicComponent)
          .setColorComponent(colorComponent);
    }
    
    public void setOrigin(Origin origin) {
        leftTextComponent.setOrigin(origin);
        rightTextComponent.setOrigin(origin);
    }
    
    public void setColumnDist(float columnDist) {
        leftDistPhysicComponent.setCenterX(-0.5f*columnDist);
        rightDistPhysicComponent.setCenterX(0.5f*columnDist);
    }
    
    public void setLeftString(String string) {
        leftTextComponent.setString(string);
    }
    
    public void setRightString(String string) {
        rightTextComponent.setString(string);
    }
    
    public void setFont(BitmapFont font) {
        leftTextComponent.setFont(font);
        rightTextComponent.setFont(font);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(leftTextComponent);
        screen.draw(rightTextComponent);
    }
}
