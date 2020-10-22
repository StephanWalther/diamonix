package gui.checkBox;

import core.objects.audio.*;
import misc.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.input.input.*;
import tools.input.processors.grab.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

public class CheckBox implements Common, GrabListener {
    private final GrabProcessor grabProcessor = new GrabProcessor(this);
    private final DefaultColorComponent checkMarkExtraColorComponent = new DefaultColorComponent();
    private final TextureComponent backTextureComponent;
    private final TextureComponent symbolTextureComponent;
    private final TextureComponent checkBoxTextureComponent;
    private final TextureComponent checkMarkTextureComponent;
    private final LimitedSound checkSound;
    private final Timer checkmarkTimer = new Timer(Constants.CHANGE_TIME);
    private final CheckBoxListener checkBoxListener;
    
    public CheckBox(CheckBoxConfiguration checkBoxConfiguration, CheckBoxDesign checkBoxDesign) {
        backTextureComponent = new TextureComponent(checkBoxConfiguration.physicComponent,
          checkBoxConfiguration.colorComponent, checkBoxDesign.backTextureOrigin);
        symbolTextureComponent = new TextureComponent(
          PhysicComponentBagBuilder.build(checkBoxDesign.symbolOffset,
            checkBoxConfiguration.physicComponent, new NodePhysicComponentBag()),
          checkBoxConfiguration.colorComponent, checkBoxConfiguration.symbolTextureOrigin);
        checkBoxTextureComponent = new TextureComponent(
          PhysicComponentBagBuilder.build(checkBoxDesign.checkBoxOffset,
            checkBoxConfiguration.physicComponent, new NodePhysicComponentBag()),
          checkBoxConfiguration.colorComponent, checkBoxDesign.checkBoxTextureOrigin);
        ColorComponentBag checkMarkColorComponent = new ColorComponentBag();
        checkMarkColorComponent.addColorComponent(checkBoxConfiguration.colorComponent);
        checkMarkColorComponent.addColorComponent(checkMarkExtraColorComponent);
        checkMarkTextureComponent = new TextureComponent(
          PhysicComponentBagBuilder.build(checkBoxDesign.checkBoxOffset,
            checkBoxConfiguration.physicComponent, new NodePhysicComponentBag()),
          checkMarkColorComponent, checkBoxDesign.checkMarkTextureOrigin);
        checkSound = checkBoxDesign.checkSound;
        if (checkBoxConfiguration.initiallyChecked) {
            checkmarkTimer.increase().setTime(checkmarkTimer.getEndTime());
        }
        checkBoxListener = checkBoxConfiguration.checkBoxListener;
    }
    
    @Override
    public boolean processInput(Input input) {
        return grabProcessor.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        checkmarkTimer.update(dt);
        checkMarkExtraColorComponent.setColorA(
          PositiveSinWave.halfPeriod.calculate(checkmarkTimer.getProgress()));
        checkSound.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(backTextureComponent);
        screen.draw(symbolTextureComponent);
        screen.draw(checkBoxTextureComponent);
        screen.draw(checkMarkTextureComponent);
    }
    
    @Override
    public boolean grabTry(float x, float y, int pointer) {
        return backTextureComponent.contains(x, y);
    }
    
    @Override
    public void grabMoved(float x, float y, int pointer) {}
    
    @Override
    public void grabReleased(float x, float y, int pointer) {
        if (!backTextureComponent.contains(x, y)) return;
        if (checkmarkTimer.isIncreasing()) {
            checkmarkTimer.decrease();
            checkBoxListener.unchecked();
        } else {
            checkmarkTimer.increase();
            checkBoxListener.checked();
        }
        checkSound.play();
    }
    
    @Override
    public void grabReset(int pointer) {}
}
