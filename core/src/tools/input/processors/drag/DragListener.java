package tools.input.processors.drag;

public interface DragListener {
    void dragStarted(float x, float y, int pointer);
    
    void dragged(float x, float y, int pointer);
    
    void dragEnded(float x, float y, int pointer);
    
    void dragReset(int pointer);
}
