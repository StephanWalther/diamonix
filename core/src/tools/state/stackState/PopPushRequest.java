package tools.state.stackState;

import java.util.*;

public class PopPushRequest<K> {
    final int popCount;
    final List<K> pushList;
    final boolean popFromTop;
    final boolean pushOnTop;
    
    public PopPushRequest(final int popCount, final List<K> pushList) {
        this(popCount, pushList, true, true);
    }
    
    public PopPushRequest(final int popCount, final List<K> pushList,
                          final boolean popFromTop, final boolean pushOnTop) {
        this.popCount = popCount;
        this.pushList = pushList == null ? Collections.<K>emptyList() : new ArrayList<K>(pushList);
        this.popFromTop = popFromTop;
        this.pushOnTop = pushOnTop;
    }
}
