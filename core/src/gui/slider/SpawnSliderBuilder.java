package gui.slider;

import tools.*;
import tools.component.graphic.*;
import tools.progressLine.*;
import tools.spawn.*;

public class SpawnSliderBuilder extends SpawnBuilder {
    public TextureOrigin symbolTextureOrigin;
    public float initialValue;
    public SliderListener sliderListener;
    public SliderDesign sliderDesign;
    
    public Pair<Slider, ProgressLine> buildSpawnSlider() {
        SpawnPack spawnPack = build();
        SliderConfiguration sliderConfiguration = new SliderConfiguration(spawnPack.physicComponent,
          spawnPack.colorComponent, symbolTextureOrigin, initialValue, sliderListener);
        return new Pair<Slider, ProgressLine>(new Slider(sliderConfiguration, sliderDesign),
          spawnPack.progressLine);
    }
}
