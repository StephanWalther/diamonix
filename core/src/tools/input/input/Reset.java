package tools.input.input;

import tools.input.processors.*;

public final class Reset implements Input {
    @Override
    public boolean process(InputProcessor inputProcessor) {
        inputProcessor.receiveReset();
        return false;
    }
}
