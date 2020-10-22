package tools.input.processors.grab;

public interface GrabListener {
    boolean grabTry(float x, float y, int pointer);
    
    void grabMoved(float x, float y, int pointer);
    
    void grabReleased(float x, float y, int pointer);
    
    void grabReset(int pointer);
}
