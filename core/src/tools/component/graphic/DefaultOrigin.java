package tools.component.graphic;

public class DefaultOrigin implements Origin {
    private final float percentageX;
    private final float percentageY;
    
    public DefaultOrigin(float percentageX, float percentageY) {
        this.percentageX = percentageX;
        this.percentageY = percentageY;
    }
    
    @Override
    public float toX(float width) {
        return percentageX*width;
    }
    
    @Override
    public float toY(float height) {
        return percentageY*height;
    }
}
