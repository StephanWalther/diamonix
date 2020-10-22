package tools.input.input;

import tools.input.processors.InputProcessor;

public final class KeyTyped implements Input {
    private final char character;
    
    public KeyTyped(final char character) {
        this.character = character;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        return inputProcessor.receiveKeyTyped(character);
    }
}
