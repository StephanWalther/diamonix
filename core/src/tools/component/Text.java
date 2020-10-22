package tools.component;

import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.screen.*;

public class Text implements Whole {
    public final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    public final DefaultColorComponent colorComponent = new DefaultColorComponent();
    public final TextComponent textComponent = new TextComponent(null)
                                                 .setPhysicComponent(physicComponent)
                                                 .setColorComponent(colorComponent);
    
    public Text() {}
    
    public Text(Text text) {
        this(text.physicComponent, text.colorComponent, text.textComponent);
    }
    
    public Text(PhysicComponent physicComponent, ColorComponent colorComponent,
                  TextComponent textComponent) {
        this.physicComponent.setPhysicData(physicComponent.getPhysicData());
        this.colorComponent.setColor(colorComponent.getColor());
        this.textComponent.setFont(textComponent.getFont());
        this.textComponent.setString(textComponent.getString());
        this.textComponent.setOrigin(textComponent.getOrigin());
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
        screen.draw(textComponent);
    }
}
