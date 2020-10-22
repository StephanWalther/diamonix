package tools.state.stackState;

import tools.input.input.*;
import tools.input.processors.*;
import tools.screen.*;

public class DefaultState<K, R extends StateRequestHandler<K>>
  extends DefaultInputProcessor implements State<K, R> {
    protected R requestHandler = null;
    State stateBelow = null;
    
    @Override
    public void setRequestHandler(R requestHandler) {
        this.requestHandler = requestHandler;
    }
    
    @Override
    public void setStateBelow(State stateBelow) {
        this.stateBelow = stateBelow;
    }
    
    @Override
    public void update(float dt) {}
    
    @Override
    public void draw(Screen screen) {}
    
    @Override
    public void pushed(K key) {}
    
    @Override
    public void pushedOnStack(K key) {}
    
    @Override
    public void pushedBelowStack(K key) {}
    
    @Override
    public void statePushed() {}
    
    @Override
    public void statePushedOnTop() {}
    
    @Override
    public void statePushedBelow() {}
    
    @Override
    public void statePopped() {}
    
    @Override
    public void stateAbovePopped() {}
    
    @Override
    public void stateBelowPopped() {}
    
    @Override
    public void popped() {}
    
    @Override
    public void poppedOfStack() {}
    
    @Override
    public void poppedBelowStack() {}
    
    protected final boolean isTopState() {
        return requestHandler.handleTopStateRequest(this);
    }
    
    protected final int getStackSize() {
        return requestHandler.handleStackSizeRequest();
    }
    
    protected final void requestStatePopPush(PopPushRequest<K> popPushRequest) {
        requestHandler.handleStatePopPushRequest(popPushRequest, this);
    }
    
    protected final boolean processInputStateBelow(Input input) {
        return stateBelow != null && stateBelow.processInput(input);
    }
    
    protected final void updateStateBelow(float dt) {
        if (stateBelow != null) stateBelow.update(dt);
    }
    
    protected final void drawStateBelow(Screen screen) {
        if (stateBelow != null) stateBelow.draw(screen);
    }
}
