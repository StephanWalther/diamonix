package tools.component.graphic;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import tools.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.physic.*;
import tools.screen.*;

public class TextComponent implements Drawable, Containable {
    private PhysicComponent physicComponent;
    private ColorComponent colorComponent = EasyColorComponent.WHITE;
    private Origin origin = Origin.center;
    private String string = "No string supplied";
    private BitmapFont font;
    
    public TextComponent() {}
    
    public TextComponent(BitmapFont font) {
        this.font = font;
    }
    
    public TextComponent setPhysicComponent(PhysicComponent physicComponent) {
        this.physicComponent = physicComponent;
        return this;
    }
    
    public PhysicComponent getPhysicComponent() {
        return physicComponent;
    }
    
    public TextComponent setColorComponent(ColorComponent colorComponent) {
        this.colorComponent = colorComponent;
        return this;
    }
    
    public ColorComponent getColorComponent() {
        return colorComponent;
    }
    
    public TextComponent setOrigin(Origin origin) {
        this.origin = origin;
        return this;
    }
    
    public Origin getOrigin() {
        return origin;
    }
    
    public TextComponent setString(String string) {
        this.string = string;
        return this;
    }
    
    public String getString() {
        return string;
    }
    
    public TextComponent setFont(BitmapFont font) {
        this.font = font;
        return this;
    }
    
    public BitmapFont getFont() {
        return font;
    }
    
    @Override
    public void draw(Screen screen) {
        Color color = Color.WHITE;
        if (colorComponent != null) {
            if (colorComponent.getColorA() < Tools.EPS) return;
            color = colorComponent.getColor();
        }
        setFontValues();
        screen.drawer().draw(font, string, physicComponent.getCenterX() - origin.toX(getWidth()),
          physicComponent.getCenterY() + origin.toY(getHeight()), color);
    }
    
    @Override
    public boolean contains(float x, float y) {
        return getBoundingRectangle().contains(x, y);
    }
    
    public Rectangle getBoundingRectangle() {
        // The scale is already considered in the size.
        return Tools.buildRectangle(getSize(), physicComponent.getCenter(), origin);
    }
    
    public Vector2 getSize() {
        setFontValues();
        return TextSize.getSize(font, string);
    }
    
    public float getWidth() {
        setFontValues();
        return TextSize.getWidth(font, string);
    }
    
    public float getHeight() {
        setFontValues();
        return TextSize.getHeight(font, string);
    }
    
    private void setFontValues() {
        font.getData().setScale(Tools.positiveQuasiNull(physicComponent.getScaleX()),
          Tools.positiveQuasiNull(physicComponent.getScaleY()));
    }
}
