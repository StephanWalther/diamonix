package applicationState.components;

import core.*;
import core.objects.audio.*;
import applicationState.state.*;
import tools.common.*;
import tools.keyCheck.*;

public class KeyComponent extends ApplicationStateComponent {
    private final KeyCheck keyCheck;
    private final Action action;
    private final LimitedSound keyUpSound = Sounds.get("beep", 0.1f);
    
    public KeyComponent(KeyCheck keyCheck, Action action) {
        this.keyCheck = keyCheck;
        this.action = action;
    }
    
    @Override
    public boolean receiveKeyUp(int keycode) {
        if (keyCheck.check(keycode)) {
            keyUpSound.play();
            action.perform();
            return true;
        }
        return false;
    }
    
    @Override
    public void update(float dt) {
        keyUpSound.update(dt);
    }
}
