package tools.component.graphic;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import misc.*;
import tools.common.*;
import tools.component.color.*;
import tools.progressLine.*;
import tools.screen.*;

public class SwitchTextureComponent extends PrototypeTextureComponent implements Updatable {
    private TextureRegion textureRegionOne;
    private TextureRegion textureRegionTwo;
    private TextureRegion currentTextureRegion;
    private final DefaultColorComponent switchColorComponent = new DefaultColorComponent();
    private final Timer timer = new Timer(Constants.SPAWN_TIME);
    public void setTextureRegionOne(TextureRegion textureRegionOne) {
        this.textureRegionOne = textureRegionOne;
        if (!timer.isIncreasing()) currentTextureRegion = textureRegionOne;
    }
    
    public TextureRegion getTextureRegionOne() {
        return textureRegionOne;
    }
    
    public void setTextureRegionTwo(TextureRegion textureRegionTwo) {
        this.textureRegionTwo = textureRegionTwo;
        if (timer.isIncreasing()) currentTextureRegion = textureRegionTwo;
    }
    
    public TextureRegion getTextureRegionTwo() {
        return textureRegionTwo;
    }
    
    public void setSwitchTime(float switchTime) {
        timer.setEndTime(switchTime);
    }
    
    public void switchToTextureOne() {
        timer.decrease();
        currentTextureRegion = textureRegionOne;
    }
    
    public void forceSwitchtoTextureOne() {
        timer.decrease();
        timer.setTime(0);
        currentTextureRegion = textureRegionOne;
    }
    
    public void switchToTextureTwo() {
        timer.increase();
        currentTextureRegion = textureRegionTwo;
    }
    
    public void forceSwitchToTextureTwo() {
        timer.increase();
        timer.setTime(timer.getEndTime());
        currentTextureRegion = textureRegionTwo;
    }
    
    public boolean isTextureOneTheCurrentTexture() {
        return textureRegionOne == currentTextureRegion;
    }
    
    @Override
    public void update(float dt) {
        timer.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        ColorComponent cc = getColorComponent();
        setColorComponent(switchColorComponent);
        switchColorComponent.setColor(cc.getColor());
        switchColorComponent.setColorA(cc.getColorA()*(1.f - timer.getProgress()));
        prototypeDraw(screen, textureRegionOne);
        switchColorComponent.setColorA(cc.getColorA()*timer.getProgress());
        prototypeDraw(screen, textureRegionTwo);
        setColorComponent(cc);
    }
    
    @Override
    public boolean contains(float x, float y) {
        return prototypeContains(x, y, currentTextureRegion);
    }
    
    public Rectangle getBoundingRectangle() {
        return prototypeGetBoundingRectangle(currentTextureRegion);
    }
    
    public float getTextureRegionWidth() {
        return currentTextureRegion.getRegionWidth();
    }
    
    public float getTextureRegionHeight() {
        return currentTextureRegion.getRegionHeight();
    }
}
