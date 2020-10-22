package tools.keyCheck;

import com.badlogic.gdx.Input;

import tools.*;

public final class IsMenuKey implements KeyCheck {
    public static final IsMenuKey instance = new IsMenuKey();
    
    private IsMenuKey() {}
    
    @Override
    public boolean check(final int keycode) {
        return Tools.equalsOne(keycode,
          Input.Keys.SPACE,
          Input.Keys.TAB,
          Input.Keys.ENTER,
          Input.Keys.MENU);
    }
}
