package gui;

import tools.*;
import tools.component.graphic.*;
import tools.progressLine.*;
import tools.spawn.*;

public class SpawnTextureBuilder extends SpawnBuilder {
    public TextureOrigin textureOrigin;
    
    public Pair<TextureComponent, ProgressLine> buildSpawnTexture() {
        SpawnPack spawnPack = build();
        return new Pair<TextureComponent, ProgressLine>(
          new TextureComponent(spawnPack.physicComponent, spawnPack.colorComponent, textureOrigin),
          spawnPack.progressLine);
    }
}
