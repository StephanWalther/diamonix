package tools.screen;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import tools.common.*;

public interface Drawer {
    void draw(TextureRegion region, float x, float y, float width, float height);
    
    void draw(TextureRegion region, float x, float y, float width, float height,
                     Color color);
    
    void draw(float x, float y, float width, float height,
                     Color color1, Color color2,
                     Color color3, Color color4);
    
    void draw(Texture texture, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation,
                     int srcX, int srcY, int srcWidth, int srcHeight,
                     boolean flipX, boolean flipY);
    
    void draw(Texture texture, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation,
                     int srcX, int srcY, int srcWidth, int srcHeight,
                     boolean flipX, boolean flipY, Color color);
    
    void draw(TextureRegion region, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation);
    
    void draw(TextureRegion region, float x, float y, float originX, float originY,
                     float width, float height, float scaleX, float scaleY, float rotation,
                     Color color);
    
    GlyphLayout draw(BitmapFont font, CharSequence str, float x, float y);
    
    GlyphLayout draw(BitmapFont font, CharSequence str, float x, float y, Color color);
}
