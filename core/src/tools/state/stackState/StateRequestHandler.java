package tools.state.stackState;

public interface StateRequestHandler<K> {
    boolean handleTopStateRequest(final State requester);
    
    int handleStackSizeRequest();
    
    void handleStatePopPushRequest(final PopPushRequest<K> popPushRequest, final State requester);
}
