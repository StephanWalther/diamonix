package gui.checkBox;

import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;

public class CheckBoxConfiguration {
    public final PhysicComponent physicComponent;
    public final ColorComponent colorComponent;
    public final TextureOrigin symbolTextureOrigin;
    public final boolean initiallyChecked;
    public final CheckBoxListener checkBoxListener;
    
    public CheckBoxConfiguration(PhysicComponent physicComponent, ColorComponent colorComponent,
                                 TextureOrigin symbolTextureOrigin, boolean initiallyChecked,
                                 CheckBoxListener checkBoxListener) {
        this.physicComponent = physicComponent;
        this.colorComponent = colorComponent;
        this.symbolTextureOrigin = symbolTextureOrigin;
        this.initiallyChecked = initiallyChecked;
        this.checkBoxListener = checkBoxListener;
    }
}
