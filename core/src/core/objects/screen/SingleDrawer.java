package core.objects.screen;

import com.badlogic.gdx.graphics.*;

import tools.common.*;
import tools.screen.Screen;

class SingleDrawer implements Drawable {
    private Texture texture;
    
    SingleDrawer setTexture(final Texture texture) {
        this.texture = texture;
        return this;
    }
    
    @Override
    public void draw(Screen screen) {
        screen.drawer().draw(texture, -texture.getWidth()*0.5f, -texture.getHeight()*0.5f,
          0, 0, texture.getWidth(), texture.getHeight(), 1, 1, 0, 0, 0,
          texture.getWidth(), texture.getHeight(), false, true);
    }
}
