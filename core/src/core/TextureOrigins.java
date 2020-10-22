package core;

import java.lang.String;

import core.objects.assets.*;
import tools.component.graphic.*;

public final class TextureOrigins {
    static Assets assets;
    
    private TextureOrigins() {}
    
    public static TextureOrigin get(String string) {
        return assets.getTextureOrigin(string);
    }
}
