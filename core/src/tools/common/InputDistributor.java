package tools.common;

import java.util.*;

import tools.input.input.*;

public final class InputDistributor {
    private InputDistributor() {}
    
    public static <E extends InputProcessable>
    boolean distribute(final Collection<E> inputProcessables, final Input input) {
        for (final InputProcessable inputProcessable : inputProcessables) {
            if (inputProcessable.processInput(input)) return true;
        }
        return false;
    }
}
