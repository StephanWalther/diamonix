package tools.component.graphic;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

final class TextSize {
    private static final GlyphLayout layout = new GlyphLayout();
    
    private TextSize() {}
    
    static Vector2 getSize(BitmapFont font, String string) {
        layout.setText(font, string);
        return new Vector2(layout.width, layout.height);
    }
    
    static float getWidth(BitmapFont font, String string) {
        layout.setText(font, string);
        return layout.width;
    }
    
    static float getHeight(BitmapFont font, String string) {
        layout.setText(font, string);
        return layout.height;
    }
}
