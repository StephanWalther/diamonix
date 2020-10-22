package gui.slider;

import com.badlogic.gdx.math.*;

import core.objects.audio.*;
import tools.component.graphic.*;

public class SliderDesign {
    public final Vector2 symbolOffset;
    public final Vector2 lineOffset;
    public final TextureOrigin backTextureOrigin;
    public final TextureOrigin lineTextureOrigin;
    public final TextureOrigin knobTextureOrigin;
    public final LimitedSound valueChangedSound;
    
    public SliderDesign(Vector2 symbolOffset, Vector2 lineOffset,
                        TextureOrigin backTextureOrigin, TextureOrigin lineTextureOrigin,
                        TextureOrigin knobTextureOrigin, LimitedSound valueChangedSound) {
        this.symbolOffset = symbolOffset;
        this.lineOffset = lineOffset;
        this.backTextureOrigin = backTextureOrigin;
        this.lineTextureOrigin = lineTextureOrigin;
        this.knobTextureOrigin = knobTextureOrigin;
        this.valueChangedSound = valueChangedSound;
    }
}
