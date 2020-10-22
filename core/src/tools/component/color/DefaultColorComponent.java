package tools.component.color;

import com.badlogic.gdx.graphics.*;

public class DefaultColorComponent implements ColorComponent {
    private final Color color = new Color(1, 1, 1, 1);
    
    public DefaultColorComponent() {}
    
    public DefaultColorComponent(Color color) {
        this.color.set(color);
    }
    
    public DefaultColorComponent setColor(Color color) {
        this.color.set(color);
        return this;
    }
    
    public ColorComponent setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
        return this;
    }
    
    public ColorComponent setColor(float r, float g, float b) {
        color.r = r;
        color.g = g;
        color.b = b;
        return this;
    }
    
    @Override
    public Color getColor() {
        return new Color(color);
    }
    
    public DefaultColorComponent setColorR(float r) {
        color.r = r;
        return this;
    }
    
    @Override
    public float getColorR() {
        return color.r;
    }
    
    public DefaultColorComponent setColorG(float g) {
        color.g = g;
        return this;
    }
    
    @Override
    public float getColorG() {
        return color.g;
    }
    
    public DefaultColorComponent setColorB(float b) {
        color.b = b;
        return this;
    }
    
    @Override
    public float getColorB() {
        return color.b;
    }
    
    public DefaultColorComponent setColorA(float a) {
        color.a = a;
        return this;
    }
    
    @Override
    public float getColorA() {
        return color.a;
    }
}
