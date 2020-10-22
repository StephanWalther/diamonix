package core.objects.screen;

public class Drawing implements ScreenState {
    public static final Drawing instance = new Drawing();
    
    private Drawing() {}
    
    @Override
    public void enter(Screen screen) {
        screen.begin(new VirtualDrawing(screen.virtualScreen));
    }
    
    @Override
    public void leave(Screen screen) {}
}
