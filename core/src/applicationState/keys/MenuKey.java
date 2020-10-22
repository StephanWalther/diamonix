package applicationState.keys;

import core.*;
import applicationState.implementations.*;
import applicationState.state.*;

public class MenuKey extends ApplicationStateKey {
    @Override
    protected ApplicationState create(final Core core) {
        return new Menu(core);
    }
}
