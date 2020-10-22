package tools.component.graphic;

import com.badlogic.gdx.graphics.g2d.*;

public class TextureOrigin {
    public final TextureRegion textureRegion;
    public final Origin origin;
    
    public TextureOrigin(TextureRegion textureRegion, Origin origin) {
        this.textureRegion = textureRegion;
        this.origin = origin;
    }
}
