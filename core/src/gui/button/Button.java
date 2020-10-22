package gui.button;

import com.badlogic.gdx.math.*;

import core.objects.audio.*;
import misc.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.input.input.*;
import tools.input.processors.tap.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

public class Button implements Lockable, Containable, TapListener {
    private final PhysicComponent physicComponent;
    private final ColorComponent colorComponent;
    private final DefaultPhysicComponent tapPhysicComponent = new DefaultPhysicComponent();
    private final DefaultColorComponent tapColorComponent = new DefaultColorComponent();
    private TextureComponent backTextureComponent;
    private TextureComponent highlightTextureComponent;
    private final TextureComponent symbolTextureComponent;
    private Sound tapUpSucceedSound;
    private Sound tapUpFailSound;
    private TapProcessor tapProcessor;
    private final Timer tapTimer = new Timer(Constants.CHANGE_TIME);
    private final Mapping tapScaleMapping = new Concatenation(
      new Polynomial(-0.1f, 1.f), PositiveSinWave.halfPeriod);
    private Action action;
    
    public Button(ButtonConfiguration buttonConfiguration, ButtonDesign buttonDesign) {
        physicComponent = buttonConfiguration.physicComponent;
        colorComponent = buttonConfiguration.colorComponent;
        PhysicComponentBag physicComponentBag = new NodePhysicComponentBag();
        physicComponentBag.addPhysicComponent(tapPhysicComponent);
        physicComponentBag.addPhysicComponent(buttonConfiguration.physicComponent);
        tapColorComponent.setColorA(0);
        ColorComponentBag tapColorComponentBag = new ColorComponentBag();
        tapColorComponentBag.addColorComponent(buttonConfiguration.colorComponent);
        tapColorComponentBag.addColorComponent(tapColorComponent);
        backTextureComponent = new TextureComponent(physicComponentBag,
          buttonConfiguration.colorComponent, buttonDesign.backTextureOrigin);
        highlightTextureComponent = new TextureComponent(physicComponentBag,
          tapColorComponentBag, buttonDesign.highlightTextureOrigin);
        symbolTextureComponent = new TextureComponent(physicComponentBag,
          buttonConfiguration.colorComponent, buttonConfiguration.symbolTextureOrigin);
        tapUpSucceedSound = buttonDesign.tapUpSucceedSound;
        tapUpFailSound = buttonDesign.tapUpFailSound;
        tapProcessor = new TapProcessor(this).addTapListener(this);
        action = buttonConfiguration.action;
    }
    
    @Override
    public boolean processInput(Input input) {
        return tapProcessor.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        tapProcessor.update(dt);
        tapTimer.update(dt);
        float progress = tapTimer.getProgress();
        tapPhysicComponent.setScale(tapScaleMapping.calculate(progress));
        tapColorComponent.setColorA(PositiveSinWave.halfPeriod.calculate(progress));
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(backTextureComponent);
        screen.draw(highlightTextureComponent);
        screen.draw(symbolTextureComponent);
    }
    
    @Override
    public PhysicComponent getPhysicComponent() {
        return physicComponent;
    }
    
    @Override
    public ColorComponent getColorComponent() {
        return colorComponent;
    }
    
    @Override
    public boolean contains(float x, float y) {
        return backTextureComponent.getNotModifiedBoundingRectangle().contains(x, y);
    }
    
    @Override
    public void tapStarted() {
        letTapTimerIncrease();
    }
    
    @Override
    public void tapMovedInside() {
        letTapTimerIncrease();
    }
    
    private void letTapTimerIncrease() {
        tapTimer.increase();
        tapTimer.setTime(tapTimer.getEndTime());
    }
    
    @Override
    public void tapMovedOutside() {
        tapUpFailSound.play();
        tapTimer.decrease();
    }
    
    @Override
    public void tapEndedInside() {
        tapUpSucceedSound.play();
        tapReset();
        action.perform();
    }
    
    @Override
    public void tapEndedOutside() {
        tapReset();
    }
    
    @Override
    public void tapReset() {
        tapTimer.decrease();
    }
}
