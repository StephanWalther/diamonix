package applicationState.components;

import java.util.*;

import applicationState.state.*;
import tools.common.*;
import tools.screen.*;

public class DrawComponent extends ApplicationStateComponent {
    private final List<Drawable> drawables = new ArrayList<Drawable>();
    
    public void add(Drawable drawable) {
        drawables.add(drawable);
    }
    
    @Override
    public void draw(Screen screen) {
        DrawDistributer.drawAll(drawables, screen);
    }
}
