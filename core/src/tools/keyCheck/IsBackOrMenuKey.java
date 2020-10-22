package tools.keyCheck;

import java.util.*;

public class IsBackOrMenuKey extends KeyCheckMultiplexer {
    public static final IsBackOrMenuKey instance = new IsBackOrMenuKey();
    
    private IsBackOrMenuKey() {
        super(new ArrayList<KeyCheck>(Arrays.asList(IsBackKey.instance, IsMenuKey.instance)));
    }
}
