package tools.component.color;

import com.badlogic.gdx.graphics.*;

import java.util.*;

public class ColorComponentBag implements ColorComponent {
    private List<ColorComponent> colorComponents = new ArrayList<ColorComponent>();
    
    public ColorComponentBag addColorComponent(ColorComponent colorComponent) {
        colorComponents.add(colorComponent);
        return this;
    }
    
    @Override
    public Color getColor() {
        final Color color = new Color(1, 1, 1, 1);
        for (final ColorComponent colorComponent : colorComponents) {
            color.mul(colorComponent.getColor());
        }
        return color;
    }
    
    @Override
    public float getColorR() {
        float colorR = 1;
        for (final ColorComponent colorComponent : colorComponents) {
            colorR *= colorComponent.getColorR();
        }
        return colorR;
    }
    
    @Override
    public float getColorG() {
        float colorG = 1;
        for (final ColorComponent colorComponent : colorComponents) {
            colorG *= colorComponent.getColorG();
        }
        return colorG;
    }
    
    @Override
    public float getColorB() {
        float colorB = 1;
        for (final ColorComponent colorComponent : colorComponents) {
            colorB *= colorComponent.getColorB();
        }
        return colorB;
    }
    
    @Override
    public float getColorA() {
        float colorA = 1;
        for (final ColorComponent colorComponent : colorComponents) {
            colorA *= colorComponent.getColorA();
        }
        return colorA;
    }
}
