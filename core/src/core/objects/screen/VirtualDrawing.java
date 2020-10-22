package core.objects.screen;

class VirtualDrawing implements ScreenState {
    private final VirtualScreen virtualScreen;
    
    VirtualDrawing(VirtualScreen virtualScreen) {
        this.virtualScreen = virtualScreen;
    }
    
    @Override
    public void enter(Screen screen) {
        virtualScreen.begin();
        screen.drawer().begin();
    }
    
    @Override
    public void leave(Screen screen) {
        screen.drawer().end();
        virtualScreen.end();
    }
}
