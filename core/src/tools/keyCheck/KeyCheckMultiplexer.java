package tools.keyCheck;

import java.util.*;

public class KeyCheckMultiplexer implements KeyCheck {
    private final List<KeyCheck> keyChecks = new ArrayList<KeyCheck>();
    
    public KeyCheckMultiplexer(final List<KeyCheck> keyChecks) {
        this.keyChecks.addAll(keyChecks);
    }
    
    @Override
    public boolean check(final int keycode) {
        for (final KeyCheck keyCheck : keyChecks) {
            if (keyCheck.check(keycode)) return true;
        }
        return false;
    }
}
