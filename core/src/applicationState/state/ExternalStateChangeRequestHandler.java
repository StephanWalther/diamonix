package applicationState.state;

public interface ExternalStateChangeRequestHandler {
    boolean handleExternalStateChangeRequest(ApplicationStateKey stateKey);
}
