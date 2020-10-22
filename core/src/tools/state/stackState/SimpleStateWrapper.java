package tools.state.stackState;

import tools.input.input.*;
import tools.screen.*;

public class SimpleStateWrapper<K, R extends StateRequestHandler<K>> implements State<K, R> {
    private final SimpleState simpleState;
    private R requestHandler = null;
    private State stateBelow = null;
    
    public SimpleStateWrapper(SimpleState simpleState) {
        this.simpleState = simpleState;
        simpleState.setSimpleStateWrapper(this);
    }
    
    @Override
    public void setRequestHandler(R requestHandler) {
        this.requestHandler = requestHandler;
    }
    
    @Override
    public void setStateBelow(State stateBelow) {
        this.stateBelow = stateBelow;
    }
    
    @Override
    public boolean processInput(Input input) {
        return simpleState.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        simpleState.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        simpleState.draw(screen);
    }
    
    @Override
    public void pushed(K key) {
        simpleState.pushed();
    }
    
    @Override
    public void pushedOnStack(K key) {
        simpleState.pushedOnStack();
    }
    
    @Override
    public void pushedBelowStack(K key) {
        simpleState.pushedBelowStack();
    }
    
    @Override
    public void statePushed() {
        simpleState.statePushed();
    }
    
    @Override
    public void statePushedOnTop() {
        simpleState.statePushedOnTop();
    }
    
    @Override
    public void statePushedBelow() {
        simpleState.statePushedBelow();
    }
    
    @Override
    public void statePopped() {
        simpleState.statePopped();
    }
    
    @Override
    public void stateAbovePopped() {
        simpleState.stateAbovePopped();
    }
    
    @Override
    public void stateBelowPopped() {
        simpleState.stateBelowPopped();
    }
    
    @Override
    public void popped() {
        simpleState.popped();
    }
    
    @Override
    public void poppedOfStack() {
        simpleState.poppedOfStack();
    }
    
    @Override
    public void poppedBelowStack() {
        simpleState.poppedBelowStack();
    }
    
    boolean isTopState() {
        return requestHandler.handleTopStateRequest(this);
    }
    
    int getStackSize() {
        return requestHandler.handleStackSizeRequest();
    }
    
    boolean processInputStateBelow(Input input) {
        return stateBelow != null && stateBelow.processInput(input);
    }
    
    void updateStateBelow(float dt) {
        if (stateBelow != null) stateBelow.update(dt);
    }
    
    void drawStateBelow(Screen screen) {
        if (stateBelow != null) stateBelow.draw(screen);
    }
}
