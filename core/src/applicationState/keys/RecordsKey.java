package applicationState.keys;

import core.*;
import applicationState.implementations.*;
import applicationState.state.*;

public class RecordsKey extends ApplicationStateKey {
    @Override
    protected ApplicationState create(final Core core) {
        return new Records(core);
    }
}
