package core.objects.designs;

import com.badlogic.gdx.math.*;

import core.*;
import gui.checkBox.*;
import tools.component.graphic.*;

public class CheckBoxDesigns {
    public final CheckBoxDesign main;
    
    CheckBoxDesigns() {
        TextureOrigin sliderBack = TextureOrigins.get("sliderBack");
        float sliderBackWidth = sliderBack.textureRegion.getRegionWidth();
        main = new CheckBoxDesign(new Vector2(-0.35f*sliderBackWidth, 0),
          new Vector2(0.3f*sliderBackWidth, 0), sliderBack,
          TextureOrigins.get("checkBoxCheckBox"), TextureOrigins.get("symbolCheckMarkBlue"),
          Sounds.get("beep", 0.04f));
    }
}
