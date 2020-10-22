package tools.common;

import java.util.*;

public final class Remover {
    private Remover() {}
    
    public static <E extends Removable>
    int removeAllDoneElements(final List<E> removables) {
        int removeCount = 0;
        for (Iterator<E> iterator = removables.iterator(); iterator.hasNext();) {
            if (iterator.next().isDone()) {
                iterator.remove();
                removeCount++;
            }
        }
        return removeCount;
    }
}
