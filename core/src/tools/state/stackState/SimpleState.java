package tools.state.stackState;

import tools.common.*;
import tools.input.input.*;
import tools.screen.*;

public class SimpleState implements Common {
    private SimpleStateWrapper simpleStateWrapper;
    
    void setSimpleStateWrapper(final SimpleStateWrapper simpleStateWrapper) {
        this.simpleStateWrapper = simpleStateWrapper;
    }
    
    @Override
    public boolean processInput(Input input) {
        return false;
    }
    
    @Override
    public void update(final float dt) {}
    
    @Override
    public void draw(Screen screen) {}
    
    protected void pushed() {}
    
    protected void pushedOnStack() {}
    
    protected void pushedBelowStack() {}
    
    protected void statePushed() {}
    
    protected void statePushedOnTop() {}
    
    protected void statePushedBelow() {}
    
    protected void statePopped() {}
    
    protected void stateAbovePopped() {}
    
    protected void stateBelowPopped() {}
    
    protected void popped() {}
    
    protected void poppedOfStack() {}
    
    protected void poppedBelowStack() {}
    
    protected void applicationPaused() {}
    
    protected void applicationResumed() {}
    
    protected final boolean isTopState() {
        return simpleStateWrapper.isTopState();
    }
    
    protected final int getStackSize() {
        return simpleStateWrapper.getStackSize();
    }
    
    protected final boolean processInputStateBelow(final Input input) {
        return simpleStateWrapper.processInputStateBelow(input);
    }
    
    protected final void updateStateBelow(final float dt) {
        simpleStateWrapper.updateStateBelow(dt);
    }
    
    protected final void drawStateBelow(Screen screen) {
        simpleStateWrapper.drawStateBelow(screen);
    }
}
