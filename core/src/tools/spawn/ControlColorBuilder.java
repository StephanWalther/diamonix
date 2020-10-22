package tools.spawn;

import java.util.*;

import tools.component.color.*;

public class ControlColorBuilder {
    public final List<DefaultColorComponent> producedColorComponents
      = new ArrayList<DefaultColorComponent>();
    
    public ColorComponent build(ColorComponent spawnColorComponent) {
        DefaultColorComponent defaultColorComponent = new DefaultColorComponent();
        producedColorComponents.add(defaultColorComponent);
        ColorComponentBag colorComponentBag = new ColorComponentBag();
        colorComponentBag.addColorComponent(spawnColorComponent);
        colorComponentBag.addColorComponent(defaultColorComponent);
        return colorComponentBag;
    }
}
