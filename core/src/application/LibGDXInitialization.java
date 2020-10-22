package application;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

import tools.*;

class LibGDXInitialization {
    static void init(final InputQueue inputInputQueue) {
        initInput(inputInputQueue);
        setMouseCursor();
    }
    
    private static void initInput(final InputQueue inputInputQueue) {
        Gdx.input.setInputProcessor(inputInputQueue);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }
    
    private static void setMouseCursor() {
        Pixmap pm = new Pixmap(Tools.getInternalFileHandle("mouse/mouse.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();
    }
}
