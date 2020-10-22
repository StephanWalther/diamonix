package applicationState.state;

import tools.state.stackState.*;

public interface ApplicationStateRequestHandler extends StateRequestHandler<ApplicationStateKey> {
    void handleApplicationExitRequest();
}
