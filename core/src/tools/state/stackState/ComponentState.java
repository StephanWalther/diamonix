package tools.state.stackState;

import java.util.*;

import tools.common.*;
import tools.input.input.*;
import tools.screen.*;

public abstract class ComponentState
  <K, R extends StateRequestHandler<K>, C extends DefaultState<K, R>>
  extends DefaultState<K, R> implements State<K, R>, StateRequestHandler<K> {
    protected final List<C> components = new ArrayList<C>();
    
    @Override
    public void setStateBelow(State stateBelow) {
        this.stateBelow = stateBelow;
        for (C state : components) state.setStateBelow(stateBelow);
    }
    
    @Override
    public boolean processInput(Input input) {
        return super.processInput(input) || InputDistributor.distribute(components, input);
    }
    
    @Override
    public void update(float dt) {
        for (C state : components) state.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        for (C state : components) state.draw(screen);
    }
    
    @Override
    public void pushed(K key) {
        for (C state : components) state.pushed(key);
    }
    
    @Override
    public void pushedOnStack(K key) {
        for (C state : components) state.pushedOnStack(key);
    }
    
    @Override
    public void pushedBelowStack(K key) {
        for (C state : components) state.pushedBelowStack(key);
    }
    
    @Override
    public void statePushed() {
        for (C state : components) state.statePushed();
    }
    
    @Override
    public void statePushedOnTop() {
        for (C state : components) state.statePushedOnTop();
    }
    
    @Override
    public void statePushedBelow() {
        for (C state : components) state.statePushedBelow();
    }
    
    @Override
    public void statePopped() {
        for (C state : components) state.statePopped();
    }
    
    @Override
    public void stateAbovePopped() {
        for (C state : components) state.stateAbovePopped();
    }
    
    @Override
    public void stateBelowPopped() {
        for (C state : components) state.stateBelowPopped();
    }
    
    @Override
    public void popped() {
        for (C state : components) state.popped();
    }
    
    @Override
    public void poppedOfStack() {
        for (C state : components) state.poppedOfStack();
    }
    
    @Override
    public void poppedBelowStack() {
        for (C state : components) state.poppedBelowStack();
    }
    
    @Override
    public boolean handleTopStateRequest(State requester) {
        return isTopState();
    }
    
    @Override
    public int handleStackSizeRequest() {
        return getStackSize();
    }
    
    @Override
    public void handleStatePopPushRequest(PopPushRequest<K> popPushRequest,
                                          final State requester) {
        requestStatePopPush(popPushRequest);
    }
    
    protected void addComponent(C component) {
        components.add(component);
        setRequestHandlerForAddedComponent(component);
        component.setStateBelow(stateBelow);
    }
    
    protected abstract void setRequestHandlerForAddedComponent(C component);
    
    protected void removeComponent(C component) {
        if (components.remove(component)) {
            component.setRequestHandler(null);
            component.setStateBelow(null);
        }
    }
    
    protected void clearComponents() {
        for (C component : components) {
            component.setRequestHandler(null);
            component.setStateBelow(null);
        }
        components.clear();
    }
}
