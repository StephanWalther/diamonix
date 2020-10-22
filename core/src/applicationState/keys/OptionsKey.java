package applicationState.keys;

import core.*;
import applicationState.implementations.*;
import applicationState.state.*;

public class OptionsKey extends ApplicationStateKey {
    @Override
    protected ApplicationState create(final Core core) {
        return new Options(core);
    }
}
