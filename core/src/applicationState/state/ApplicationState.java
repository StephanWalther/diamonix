package applicationState.state;

import tools.progressLine.*;
import tools.state.stackState.*;

public interface ApplicationState
  extends State<ApplicationStateKey, ApplicationStateRequestHandler> {
    boolean handleExternalStateChangeRequest(ApplicationStateKey stateKey);
    
    ProgressLineInfo getSpawnProgressLineInfo();
    
    Class<? extends ApplicationStateKey> getApplicationStateKeyClass();
}
