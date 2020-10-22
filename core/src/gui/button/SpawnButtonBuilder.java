package gui.button;

import tools.*;
import tools.common.*;
import tools.component.*;
import tools.component.graphic.*;
import tools.progressLine.*;
import tools.spawn.*;

public class SpawnButtonBuilder extends SpawnBuilder {
    public TextureOrigin symbolTextureOrigin;
    public Action action;
    public ButtonDesign buttonDesign;
    public TextureOrigin lockTextureOrigin;
    
    public Pair<Button, ProgressLine> buildSpawnButton() {
        SpawnPack spawnPack = build();
        ButtonConfiguration buttonConfiguration = new ButtonConfiguration(spawnPack.physicComponent,
          spawnPack.colorComponent, symbolTextureOrigin, action);
        return new Pair<Button, ProgressLine>(new Button(buttonConfiguration, buttonDesign),
          spawnPack.progressLine);
    }
    
    public Pair<Lock, ProgressLine> buildSpawnLock() {
        Pair<Button, ProgressLine> buttonProgressLinePair = buildSpawnButton();
        return new Pair<Lock, ProgressLine>(
          new Lock(buttonProgressLinePair.one, lockTextureOrigin), buttonProgressLinePair.two);
    }
}
