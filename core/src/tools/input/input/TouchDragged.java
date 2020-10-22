package tools.input.input;

import tools.input.processors.*;

public final class TouchDragged implements Input {
    private final float x;
    private final float y;
    private final int pointer;
    
    public TouchDragged(float x, float y, int pointer) {
        this.x = x;
        this.y = y;
        this.pointer = pointer;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        return inputProcessor.receiveTouchDragged(x, y, pointer);
    }
}
