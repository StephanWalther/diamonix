package tools.common;

import java.util.*;

import tools.screen.*;

public final class DrawDistributer {
    private DrawDistributer() {}
    
    public static <E extends Drawable>
    void drawAll(E[] drawables, Screen screen) {
        for (Drawable drawable : drawables) {
            drawable.draw(screen);
        }
    }
    
    public static <E extends Drawable>
    void drawAll(Collection<E> drawables, Screen screen) {
        for (Drawable drawable : drawables) {
            drawable.draw(screen);
        }
    }
}
