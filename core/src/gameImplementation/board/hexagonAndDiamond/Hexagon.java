package gameImplementation.board.hexagonAndDiamond;

import com.badlogic.gdx.math.*;

import core.*;
import tools.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

public class Hexagon implements Physical, Containable, Updatable, Drawable {
    private final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    private final DefaultColorComponent colorComponent = new DefaultColorComponent();
    private final SwitchTextureComponent switchTextureComponent = new SwitchTextureComponent();
    private ProgressLineInfo progressLineInfo;
    
    public Hexagon upperLeft;
    public Hexagon upper;
    public Hexagon upperRight;
    public Hexagon lowerRight;
    public Hexagon lower;
    public Hexagon lowerLeft;
    
    private Diamond diamond;
    
    public Hexagon(float centerX, float centerY) {
        physicComponent.setCenter(centerX, centerY);
        colorComponent.setColorA(0);
        switchTextureComponent.setPhysicComponent(physicComponent);
        switchTextureComponent.setColorComponent(colorComponent);
        switchTextureComponent.setTextureRegionOne(TextureOrigins.get("hexagon").textureRegion);
        switchTextureComponent.setTextureRegionTwo(TextureOrigins.get("hexagonSpawning")
                                                     .textureRegion);
    }
    
    public void setProgressLineInfo(ProgressLineInfo progressLineInfo) {
        this.progressLineInfo = progressLineInfo;
    }
    
    @Override
    public PhysicComponent getPhysicComponent() {
        return physicComponent;
    }
    
    @Override
    public boolean contains(float x, float y) {
        Vector2 center = physicComponent.getCenter();
        x -= center.x;
        y -= center.y;
        float a = switchTextureComponent.getTextureRegionWidth()*0.5f;
        if (x < -a || a < x) return false;
        float b = switchTextureComponent.getTextureRegionHeight()*0.5f;
        if (x >= 0.5f*a) {
            float f = 2.f*b*(1 - x/a);
            return -f <= y && y <= f;
        }
        if (x <= -0.5f*a) {
            float f = 2.f*b*(1 + x/a);
            return -f <= y && y <= f;
        }
        return -b <= y && y <= b;
    }
    
    @Override
    public void update(float dt) {
        physicComponent.setScale(Mapping.scaleUp.calculate(progressLineInfo.getProgress()));
        colorComponent.setColorA(
          PositiveSinWave.halfPeriod.calculate(progressLineInfo.getProgress()));
        switchTextureComponent.update(dt);
        if (diamond != null) {
            diamond.update(dt);
            diamond.physicComponent.setScale(physicComponent.getScale());
            diamond.colorComponent.setColorA(colorComponent.getColorA());
        }
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(switchTextureComponent);
        if (diamond != null) screen.draw(diamond);
    }
    
    public boolean hasDiamond() {
        return diamond != null;
    }
    
    public void attachDiamond(Diamond diamond) {
        if (hasDiamond()) throw new java.lang.IllegalStateException("This hexagon has a diamond.");
        this.diamond = diamond;
        diamond.physicComponent.setPhysicData(physicComponent.getPhysicData());
        diamond.colorComponent.setColorA(
          PositiveSinWave.halfPeriod.calculate(progressLineInfo.getProgress()));
        diamond.attachedToHexagon();
    }
    
    public Diamond getDiamond() {
        return diamond;
    }
    
    public Diamond removeDiamond() {
        if (diamond == null) return null;
        diamond.removedFromHexagon();
        return takeDiamond();
    }
    
    public Diamond takeDiamond() {
        Diamond temp = diamond;
        diamond = null;
        return temp;
    }
    
    public void setToSpawning() {
        if (progressLineInfo.getProgress() == 0) switchTextureComponent.forceSwitchToTextureTwo();
        else switchTextureComponent.switchToTextureTwo();
    }
    
    public void setToNotSpawning() {
        if (progressLineInfo.getProgress() == 0) switchTextureComponent.forceSwitchtoTextureOne();
        else switchTextureComponent.switchToTextureOne();
    }
    
    public boolean isSpawningHexagon() {
        return !switchTextureComponent.isTextureOneTheCurrentTexture();
    }
    
    public float effectiveDiameter2() {
        float height = switchTextureComponent.getTextureRegionHeight();
        return height*height;
    }
    
    public boolean isVisible() {
        return colorComponent.getColorA() > Tools.EPS;
    }
}
