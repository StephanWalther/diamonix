package core;

import com.badlogic.gdx.graphics.g2d.*;

import core.objects.assets.*;

public final class Fonts {
    static Assets assets;
    
    private Fonts() {}
    
    public static BitmapFont get(String string) {
        return assets.getFont(string);
    }
}
