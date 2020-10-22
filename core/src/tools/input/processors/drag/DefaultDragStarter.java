package tools.input.processors.drag;

public final class DefaultDragStarter implements DragStarter {
    public static final DefaultDragStarter instance = new DefaultDragStarter();
    
    private DefaultDragStarter() {}
    
    @Override
    public boolean canDragStart(final float x, final float y, final int pointer) {
        return true;
    }
}
