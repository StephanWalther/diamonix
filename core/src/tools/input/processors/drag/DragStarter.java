package tools.input.processors.drag;

public interface DragStarter {
    boolean canDragStart(float x, float y, int pointer);
}
