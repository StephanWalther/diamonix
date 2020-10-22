package gui.button;

import core.objects.audio.*;
import tools.component.graphic.*;

public class ButtonDesign {
    public final TextureOrigin backTextureOrigin;
    public final TextureOrigin highlightTextureOrigin;
    public final Sound tapUpSucceedSound;
    public final Sound tapUpFailSound;
    
    public ButtonDesign(TextureOrigin backTextureOrigin, TextureOrigin highlightTextureOrigin,
                        Sound tapUpSucceedSound, Sound tapUpFailSound) {
        this.backTextureOrigin = backTextureOrigin;
        this.highlightTextureOrigin = highlightTextureOrigin;
        this.tapUpSucceedSound = tapUpSucceedSound;
        this.tapUpFailSound = tapUpFailSound;
    }
}
