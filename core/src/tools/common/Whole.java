package tools.common;

import tools.component.color.*;
import tools.component.physic.*;

public interface Whole extends Drawable {
    DefaultPhysicComponent getDefaultPhysicComponent();
    
    DefaultColorComponent getDefaultColorComponent();
}
