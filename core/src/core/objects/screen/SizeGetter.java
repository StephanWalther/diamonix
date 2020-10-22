package core.objects.screen;

import tools.screen.Sizes;

public class SizeGetter {
    static Sizes sizes;
    
    private SizeGetter() {}
    
    public static int getScreenWidth() {
        return sizes.getScreenWidth();
    }
    
    public static int getScreenHeight() {
        return sizes.getScreenHeight();
    }
    
    public static int getIdealWorldWidth() {
        return sizes.getIdealWorldWidth();
    }
    
    public static int getIdealWorldHeight() {
        return sizes.getIdealWorldHeight();
    }
    
    public static int getWorldWidth() {
        return sizes.getWorldWidth();
    }
    
    public static int getWorldHeight() {
        return sizes.getWorldHeight();
    }
    
    public static int getMaxWorldWidth() {
        return sizes.getMaxWorldWidth();
    }
    
    public static int getMaxWorldHeight() {
        return sizes.getMaxWorldHeight();
    }
}
