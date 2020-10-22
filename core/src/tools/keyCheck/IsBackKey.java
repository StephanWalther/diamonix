package tools.keyCheck;

import com.badlogic.gdx.Input;

import tools.*;

public final class IsBackKey implements KeyCheck {
    public static final IsBackKey instance = new IsBackKey();
    
    private IsBackKey() {}
    
    @Override
    public boolean check(final int keycode) {
        return Tools.equalsOne(keycode,
          Input.Keys.ESCAPE,
          Input.Keys.BACKSPACE,
          Input.Keys.BACK);
    }
}
