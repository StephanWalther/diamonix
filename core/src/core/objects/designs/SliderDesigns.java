package core.objects.designs;

import com.badlogic.gdx.math.*;

import core.*;
import gui.slider.*;
import tools.component.graphic.*;

public class SliderDesigns {
    public final SliderDesign main;
    
    SliderDesigns() {
        TextureOrigin sliderBack = TextureOrigins.get("sliderBack");
        float sliderBackWidth = sliderBack.textureRegion.getRegionWidth();
        main = new SliderDesign(new Vector2(-0.35f*sliderBackWidth, 0),
          new Vector2(0.1f*sliderBackWidth, 0),
          sliderBack, TextureOrigins.get("sliderLine"), TextureOrigins.get("sliderKnob"),
          Sounds.get("beep", 0.2f));
    }
}
