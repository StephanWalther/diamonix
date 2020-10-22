package gui.checkBox;

import com.badlogic.gdx.math.*;

import core.objects.audio.*;
import tools.component.graphic.*;

public class CheckBoxDesign {
    public final Vector2 symbolOffset;
    public final Vector2 checkBoxOffset;
    public final TextureOrigin backTextureOrigin;
    public final TextureOrigin checkBoxTextureOrigin;
    public final TextureOrigin checkMarkTextureOrigin;
    public final LimitedSound checkSound;
    
    public CheckBoxDesign(Vector2 symbolOffset, Vector2 checkBoxOffset,
                          TextureOrigin backTextureOrigin, TextureOrigin checkBoxTextureOrigin,
                          TextureOrigin checkMarkTextureOrigin, LimitedSound checkSound) {
        this.symbolOffset = symbolOffset;
        this.checkBoxOffset = checkBoxOffset;
        this.backTextureOrigin = backTextureOrigin;
        this.checkBoxTextureOrigin = checkBoxTextureOrigin;
        this.checkMarkTextureOrigin = checkMarkTextureOrigin;
        this.checkSound = checkSound;
    }
}
