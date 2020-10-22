package gui.slider;

import com.badlogic.gdx.math.*;

import core.objects.audio.*;
import misc.*;
import tools.*;
import tools.common.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.input.input.*;
import tools.input.processors.drag.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

public class Slider implements Common, DragStarter, DragListener {
    private final PhysicComponent physicComponent;
    private final float minLocalKnobCenterX;
    private final float maxLocalKnobCenterX;
    private final DefaultPhysicComponent knobPhysicComponent = new DefaultPhysicComponent();
    private final TextureComponent backTextureComponent;
    private final TextureComponent symbolTextureComponent;
    private final TextureComponent lineTextureComponent;
    private final TextureComponent knobTextureComponent;
    private final LimitedSound valueChangedSound;
    private final DragProcessor dragProcessor;
    private final SliderListener sliderListener;
    private final Timer touchTimer = new Timer(Constants.CHANGE_TIME);
    private final Mapping scaleMapping = new Concatenation(
      new Polynomial(0.35f, 0.65f), PositiveSinWave.halfPeriod);
    
    public Slider(SliderConfiguration sliderConfiguration, SliderDesign sliderDesign) {
        physicComponent = sliderConfiguration.physicComponent;
        
        float lineWidth = sliderDesign.lineTextureOrigin.textureRegion.getRegionWidth();
        float lineCenterX = sliderDesign.lineOffset.x;
        
        minLocalKnobCenterX = lineCenterX - 0.5f*lineWidth;
        maxLocalKnobCenterX = minLocalKnobCenterX + lineWidth;
        float startLocalKnobCenterX = minLocalKnobCenterX;
        startLocalKnobCenterX += (maxLocalKnobCenterX - minLocalKnobCenterX)
                                   *sliderConfiguration.initialValue;
        knobPhysicComponent.setCenterX(startLocalKnobCenterX);
        
        backTextureComponent = new TextureComponent(physicComponent,
          sliderConfiguration.colorComponent, sliderDesign.backTextureOrigin);
        symbolTextureComponent = new TextureComponent(
          PhysicComponentBagBuilder.build(sliderDesign.symbolOffset,
            physicComponent, new NodePhysicComponentBag()), sliderConfiguration.colorComponent,
          sliderConfiguration.symbolTextureOrigin);
        lineTextureComponent = new TextureComponent(
          PhysicComponentBagBuilder.build(sliderDesign.lineOffset,
            physicComponent, new NodePhysicComponentBag()), sliderConfiguration.colorComponent,
          sliderDesign.lineTextureOrigin);
        NodePhysicComponentBag knobPhysicComponentBag = new NodePhysicComponentBag();
        knobPhysicComponentBag.addPhysicComponent(knobPhysicComponent);
        knobPhysicComponentBag.addPhysicComponent(physicComponent);
        knobTextureComponent = new TextureComponent(knobPhysicComponentBag,
          sliderConfiguration.colorComponent, sliderDesign.knobTextureOrigin);
        
        this.valueChangedSound = sliderDesign.valueChangedSound;
        dragProcessor = new DragProcessor(this).addDragListener(this);
        this.sliderListener = sliderConfiguration.sliderListener;
    }
    
    @Override
    public boolean processInput(Input input) {
        return dragProcessor.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        touchTimer.update(dt);
        float progress = touchTimer.getProgress();
        knobPhysicComponent.setScale(scaleMapping.calculate(progress));
        valueChangedSound.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(backTextureComponent);
        screen.draw(symbolTextureComponent);
        screen.draw(lineTextureComponent);
        screen.draw(knobTextureComponent);
    }
    
    @Override
    public boolean canDragStart(float x, float y, int pointer) {
        float yScale = backTextureComponent.getTextureRegion().getRegionHeight()
                         /lineTextureComponent.getTextureRegion().getRegionHeight();
        return pointer == 0 && Tools.scaleRectangle(lineTextureComponent.getBoundingRectangle(),
          new Vector2(1.2f, yScale)).contains(x, y);
    }
    
    @Override
    public void dragStarted(float x, float y, int pointer) {
        updateKnobCenterX(x);
        touchTimer.increase();
        touchTimer.setTime(touchTimer.getEndTime());
    }
    
    @Override
    public void dragged(float x, float y, int pointer) {
        updateKnobCenterX(x);
    }
    
    @Override
    public void dragEnded(float x, float y, int pointer) {
        updateKnobCenterX(x);
        touchTimer.decrease();
    }
    
    @Override
    public void dragReset(int pointer) {
        touchTimer.decrease();
    }
    
    private void updateKnobCenterX(float x) {
        float oldLocalKnobCenterX = knobPhysicComponent.getCenterX();
        setKnobCenterX(x);
        float newLocalKnobCenterX = knobPhysicComponent.getCenterX();
        if (newLocalKnobCenterX != oldLocalKnobCenterX) {
            sliderListener.valueChanged((newLocalKnobCenterX - minLocalKnobCenterX)/
                                          (maxLocalKnobCenterX - minLocalKnobCenterX));
            valueChangedSound.play();
        }
    }
    
    private void setKnobCenterX(float x) {
        float globalCenterX = physicComponent.getCenterX();
        knobPhysicComponent.setCenterX(MathUtils.clamp(x,
          minLocalKnobCenterX - globalCenterX, maxLocalKnobCenterX - globalCenterX));
    }
}
