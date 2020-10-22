package tools.input.input;

import tools.input.processors.*;

public final class KeyUp implements Input {
    private final int keycode;
    
    public KeyUp(int keycode) {
        this.keycode = keycode;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        return inputProcessor.receiveKeyUp(keycode);
    }
}
