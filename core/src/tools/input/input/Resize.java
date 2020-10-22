package tools.input.input;

import tools.input.processors.*;

public final class Resize implements Input {
    private final int width;
    private final int height;
    
    public Resize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        inputProcessor.receiveResize(width, height);
        return false;
    }
}
