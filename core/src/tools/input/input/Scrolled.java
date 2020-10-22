package tools.input.input;

import tools.input.processors.*;

public final class Scrolled implements Input {
    final private int amount;
    
    public Scrolled(int amount) {
        this.amount = amount;
    }
    
    @Override
    public boolean process(InputProcessor inputProcessor) {
        return inputProcessor.receiveScrolled(amount);
    }
}
