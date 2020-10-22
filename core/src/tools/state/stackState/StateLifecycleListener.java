package tools.state.stackState;

public interface StateLifecycleListener<K> {
    void statePushedOnTop(K key);
}
