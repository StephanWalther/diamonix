package core.objects.screen;

interface ScreenState {
    void enter(Screen screen);
    
    void leave(Screen screen);
}
