package tools.input.processors.tap;

public interface TapListener {
    void tapStarted();
    
    void tapMovedInside();
    
    void tapMovedOutside();
    
    void tapEndedInside();
    
    void tapEndedOutside();
    
    void tapReset();
}
