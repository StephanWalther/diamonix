package gameImplementation.gameVariant.tutorialGameVariant;

import com.badlogic.gdx.math.*;

import gameImplementation.*;
import tools.common.*;
import tools.screen.*;

class Arrows implements Updatable, Drawable {
    private final Cleaner cleaner;
    private Arrow arrow;
    
    Arrows(Cleaner cleaner) {
        this.cleaner = cleaner;
    }
    
    @Override
    public void update(float dt) {
        if (arrow != null) arrow.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        if (arrow != null) screen.draw(arrow);
    }
    
    void removeArrow() {
        if (arrow == null) return;
        cleaner.clean(arrow);
        arrow = null;
    }
    
    void newArrow(Vector2 start, Vector2 end) {
        arrow = new Arrow(start, end);
    }
}
