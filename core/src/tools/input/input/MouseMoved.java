package tools.input.input;

import tools.input.processors.InputProcessor;

public final class MouseMoved implements Input {
    private final float x;
    private final float y;
    
    public MouseMoved(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        return inputProcessor.receiveMouseMoved(x, y);
    }
}
