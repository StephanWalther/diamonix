package tools.state.stackState;

import java.util.*;

import tools.common.*;
import tools.input.input.*;
import tools.screen.*;

public abstract class StateStack<K, R extends StateRequestHandler<K>, S extends State<K, R>,
                                  L extends StateLifecycleListener<K>>
  implements Common, StateRequestHandler<K> {
    protected final Stack<S> stack = new Stack<S>();
    protected final List<L> stateLifecycleListeners = new ArrayList<L>();
    
    @Override
    public boolean processInput(Input input) {
        return !isEmpty() && stack.lastElement().processInput(input);
    }
    
    @Override
    public void update(float dt) {
        if (!isEmpty()) stack.lastElement().update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        if (!isEmpty()) stack.lastElement().draw(screen);
    }
    
    @Override
    public boolean handleTopStateRequest(State requester) {
        return requester == stack.lastElement();
    }
    
    @Override
    public int handleStackSizeRequest() {
        return stack.size();
    }
    
    @Override
    public void handleStatePopPushRequest(PopPushRequest<K> popPushRequest,
                                          State requester) {
        if (requester == stack.lastElement() && popPushRequest != null){
            if (popPushRequest.popFromTop) popStatesFromTop(popPushRequest.popCount);
            else popStatesBelow(popPushRequest.popCount);
            if (popPushRequest.pushOnTop) pushStatesOnTop(popPushRequest.pushList);
            else pushStatesBelow(popPushRequest.pushList);
        }
    }
    
    private void popStatesFromTop(int count) {
        for (int i = 0; i < count; i++) {
            if (!isEmpty()) {
                S poppedState = stack.pop();
                poppedState.setStateBelow(null);
                poppedState.popped();
                poppedState.poppedOfStack();
                poppedState.setRequestHandler(null);
                if (!stack.isEmpty()) {
                    stack.lastElement().statePopped();
                    stack.lastElement().stateAbovePopped();
                }
                statePopped(poppedState);
            } else return;
        }
    }
    
    private void popStatesBelow(int count) {
        for (int i = 0; i < count; i++) {
            if (!isEmpty()) {
                S poppedState = stack.remove(0);
                poppedState.setStateBelow(null);
                poppedState.popped();
                poppedState.poppedBelowStack();
                poppedState.setRequestHandler(null);
                if (!stack.isEmpty()) {
                    stack.get(0).setStateBelow(null);
                    stack.get(0).statePopped();
                    stack.get(0).stateBelowPopped();
                }
                statePopped(poppedState);
            } else return;
        }
    }
    
    protected void pushStatesOnTop(List<K> pushList) {
        S stateBelow;
        for (K key : pushList) {
            S state = getState(key);
            stateBelow = stack.isEmpty() ? null : stack.lastElement();
            stack.push(state);
            if (stateBelow != null) {
                stack.lastElement().setStateBelow(stateBelow);
                stateBelow.statePushed();
                stateBelow.statePushedOnTop();
            }
            stack.lastElement().pushed(key);
            stack.lastElement().pushedOnStack(key);
            for (L stateLifeCycleListener : stateLifecycleListeners) {
                stateLifeCycleListener.statePushedOnTop(key);
            }
        }
    }
    
    private void pushStatesBelow(List<K> pushList) {
        S stateAbove;
        for (K k : pushList) {
            S state = getState(k);
            stateAbove = stack.isEmpty() ? null : stack.get(0);
            stack.add(0, state);
            if (stateAbove != null) {
                stateAbove.setStateBelow(stack.get(0));
                stateAbove.statePushed();
                stateAbove.statePushedBelow();
            }
            stack.get(0).pushed(k);
            stack.get(0).pushedBelowStack(k);
        }
    }
    
    protected abstract S getState(K key);
    
    protected abstract void statePopped(S state);
    
    public void addStateLifecycleListener(L stateLifecycleListener) {
        stateLifecycleListeners.add(stateLifecycleListener);
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
