package tools.event;

import tools.common.*;

public interface Event extends Updatable, Removable {
    Event start();
    
    Event pause();
    
    boolean isPaused();
    
    Event resume();
    
    Event stop();
}
