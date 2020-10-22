package gui.checkBox;

import tools.*;
import tools.component.graphic.*;
import tools.progressLine.*;
import tools.spawn.*;

public class SpawnCheckBoxBuilder extends SpawnBuilder {
    public TextureOrigin symbolTextureOrigin;
    public boolean initiallyChecked;
    public CheckBoxListener checkBoxListener;
    public CheckBoxDesign checkBoxDesign;
    
    public Pair<CheckBox, ProgressLine> buildSpawnCheckBox() {
        SpawnPack spawnPack = build();
        CheckBoxConfiguration checkBoxConfiguration = new CheckBoxConfiguration(
          spawnPack.physicComponent, spawnPack.colorComponent,
          symbolTextureOrigin, initiallyChecked, checkBoxListener);
        return new Pair<CheckBox, ProgressLine>(new CheckBox(checkBoxConfiguration, checkBoxDesign),
          spawnPack.progressLine);
    }
}
