package tools.input.input;

import tools.input.processors.*;

public final class KeyDown implements Input {
    private final int keycode;
    
    public KeyDown(final int keycode) {
        this.keycode = keycode;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        return inputProcessor.receiveKeyDown(keycode);
    }
}
