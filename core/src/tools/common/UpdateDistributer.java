package tools.common;

import java.util.*;

public final class UpdateDistributer {
    public static <E extends Updatable>
    void updateAll(final E[] updatables, final float dt) {
        for (final Updatable updatable : updatables) {
            if (updatable != null) updatable.update(dt);
        }
    }
    
    public static <E extends Updatable>
    void updateAll(final Collection<E> updatables, final float dt) {
        for (final Updatable updatable : updatables) {
            updatable.update(dt);
        }
    }
}
