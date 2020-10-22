package tools.state.stackState;

import tools.common.*;

// TODO: Make it more like a List interface.
public interface State<K, R extends StateRequestHandler<K>> extends Common {
    void setRequestHandler(final R requestHandler);
    
    void setStateBelow(final State stateBelow);
    
    void pushed(final K key);
    
    void pushedOnStack(final K key);
    
    void pushedBelowStack(final K key);
    
    void statePushed();
    
    void statePushedOnTop();
    
    void statePushedBelow();
    
    void statePopped();
    
    void stateAbovePopped();
    
    void stateBelowPopped();
    
    void popped();
    
    void poppedOfStack();
    
    void poppedBelowStack();
}
