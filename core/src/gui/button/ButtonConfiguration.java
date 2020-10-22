package gui.button;

import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;

public class ButtonConfiguration {
    public final PhysicComponent physicComponent;
    public final ColorComponent colorComponent;
    public final TextureOrigin symbolTextureOrigin;
    public final Action action;
    
    public ButtonConfiguration(ButtonConfiguration buttonConfiguration) {
        physicComponent = buttonConfiguration.physicComponent;
        colorComponent = buttonConfiguration.colorComponent;
        symbolTextureOrigin = buttonConfiguration.symbolTextureOrigin;
        action = buttonConfiguration.action;
    }
    
    public ButtonConfiguration(PhysicComponent physicComponent, ColorComponent colorComponent,
                               TextureOrigin symbolTextureOrigin, Action action) {
        this.physicComponent = physicComponent;
        this.colorComponent = colorComponent;
        this.symbolTextureOrigin = symbolTextureOrigin;
        this.action = action;
    }
}
