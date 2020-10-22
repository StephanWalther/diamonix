package gui.slider;

import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;

public class SliderConfiguration {
    public PhysicComponent physicComponent;
    public ColorComponent colorComponent;
    public TextureOrigin symbolTextureOrigin;
    public float initialValue;
    public SliderListener sliderListener;
    
    public SliderConfiguration(PhysicComponent physicComponent, ColorComponent colorComponent,
                               TextureOrigin symbolTextureOrigin, float initialValue,
                               SliderListener sliderListener) {
        this.physicComponent = physicComponent;
        this.colorComponent = colorComponent;
        this.symbolTextureOrigin = symbolTextureOrigin;
        this.initialValue = initialValue;
        this.sliderListener = sliderListener;
    }
}
