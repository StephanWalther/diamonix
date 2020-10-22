package tools.component.color;

import com.badlogic.gdx.graphics.*;

public class EasyColorComponent implements ColorComponent {
    public static final EasyColorComponent WHITE = new EasyColorComponent(Color.WHITE);
    
    private final Color color = new Color(1, 1, 1, 1);
    
    public EasyColorComponent(Color color) {
        this.color.set(color);
    }
    
    @Override
    public Color getColor() {
        return new Color(color);
    }
    
    @Override
    public float getColorR() {
        return color.r;
    }
    
    @Override
    public float getColorG() {
        return color.g;
    }
    
    @Override
    public float getColorB() {
        return color.b;
    }
    
    @Override
    public float getColorA() {
        return color.a;
    }
}
