package applicationState.implementations;

import applicationState.components.*;
import applicationState.keys.*;
import applicationState.state.*;
import core.*;
import gui.checkBox.*;
import gui.slider.*;
import misc.*;
import tools.*;
import tools.progressLine.*;
import tools.spawn.*;

public class Options extends ComponentApplicationState<SequentialProgressLine> {
    private final CommonComponent commonComponent = new CommonComponent();
    private CenterPhysicBuilder centerPhysicBuilder = new CenterPhysicBuilder();
    private SpawnSliderBuilder spawnSliderBuilder = new SpawnSliderBuilder();
    
    public Options(Core core) {
        super(core, new SequentialProgressLine().setTimeBetweenIncreasesAndDecreases(
          Constants.SPAWN_TIME/3.f));
        buildBackKeyComponent();
        buildSliders();
        buildFPSCheckBox();
    }
    
    private void buildSliders() {
        addComponent(commonComponent);
        
        spawnSliderBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnSliderBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        spawnSliderBuilder.sliderDesign = core.designs.sliderDesigns.main;
        spawnSliderBuilder.spawnMoveDistance.set(-core.screen.sizes().getIdealWorldWidth()*0.1f, 0);
        
        buildGlobalVolumeSlider();
        buildMusicVolumeSlider();
        buildSoundVolumeSlider();
    }
    
    private void buildGlobalVolumeSlider() {
        spawnSliderBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        centerPhysicBuilder.centerY = core.screen.sizes().getIdealWorldHeight()*0.15f;
        spawnSliderBuilder.symbolTextureOrigin = TextureOrigins.get("symbolVolume");
        spawnSliderBuilder.initialValue = core.audio.volume.getGlobalVolume();
        spawnSliderBuilder.sliderListener = new SliderListener() {
            @Override
            public void valueChanged(float newValue) {
                core.audio.volume.setGlobalVolume(newValue);
            }
        };
        buildSlider();
    }
    
    private void buildMusicVolumeSlider() {
        centerPhysicBuilder.centerY = -core.screen.sizes().getIdealWorldHeight()*0.01f;
        spawnSliderBuilder.symbolTextureOrigin = TextureOrigins.get("symbolMusic");
        spawnSliderBuilder.initialValue = core.audio.volume.getMusicVolume();
        spawnSliderBuilder.sliderListener = new SliderListener() {
            @Override
            public void valueChanged(float newValue) {
                core.audio.volume.setMusicVolume(newValue);
            }
        };
        buildSlider();
    }
    
    private void buildSoundVolumeSlider() {
        centerPhysicBuilder.centerY = -core.screen.sizes().getIdealWorldHeight()*0.17f;
        spawnSliderBuilder.symbolTextureOrigin = TextureOrigins.get("symbolSound");
        spawnSliderBuilder.initialValue = core.audio.volume.getSoundVolume();
        spawnSliderBuilder.sliderListener = new SliderListener() {
            @Override
            public void valueChanged(float newValue) {
                core.audio.volume.setSoundVolume(newValue);
            }
        };
        buildSlider();
    }
    
    private void buildSlider() {
        Pair<Slider, ProgressLine> pair = spawnSliderBuilder.buildSpawnSlider();
        commonComponent.add(pair.one);
        stateSpawnProgressLine.absorb(pair.two);
    }
    
    private void buildFPSCheckBox() {
        SpawnCheckBoxBuilder spawnCheckBoxBuilder = new SpawnCheckBoxBuilder();
        spawnCheckBoxBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnCheckBoxBuilder.checkBoxDesign = core.designs.checkBoxDesigns.main;
        spawnCheckBoxBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        spawnCheckBoxBuilder.spawnMoveDistance.set(spawnSliderBuilder.spawnMoveDistance);
    
        centerPhysicBuilder.centerY = -core.screen.sizes().getIdealWorldHeight()*0.33f;
        spawnCheckBoxBuilder.symbolTextureOrigin = TextureOrigins.get("symbolText");
        spawnCheckBoxBuilder.initiallyChecked = core.fps.isDisplayActive();
        spawnCheckBoxBuilder.checkBoxListener = new CheckBoxListener() {
            @Override
            public void checked() {
                core.fps.setDisplayActive();
            }
            
            @Override
            public void unchecked() {
                core.fps.setDisplayInactive();
            }
        };
        Pair<CheckBox, ProgressLine> pair = spawnCheckBoxBuilder.buildSpawnCheckBox();
        commonComponent.add(pair.one);
        stateSpawnProgressLine.absorb(pair.two);
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey applicationStateKey) {
        scheduleStatePush(applicationStateKey);
        return true;
    }
    
    @Override
    public Class<OptionsKey> getApplicationStateKeyClass() {
        return OptionsKey.class;
    }
}
