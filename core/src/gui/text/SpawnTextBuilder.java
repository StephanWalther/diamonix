package gui.text;

import com.badlogic.gdx.graphics.g2d.*;

import core.objects.screen.*;
import misc.*;
import tools.*;
import tools.component.graphic.*;
import tools.progressLine.*;
import tools.spawn.*;

public class SpawnTextBuilder extends SpawnBuilder {
    public BitmapFont font;
    
    public SpawnTextBuilder() {
        spawnMoveDistance.set(-SizeGetter.getIdealWorldWidth()*Constants.TEXT_SPAWN_REL_DIST_X, 0);
    }
    
    public Pair<TextComponent, ProgressLine> buildSpawnText() {
        SpawnPack spawnPack = build();
        return new Pair<TextComponent, ProgressLine>(
          new TextComponent(font).setPhysicComponent(spawnPack.physicComponent)
            .setColorComponent(spawnPack.colorComponent), spawnPack.progressLine);
    }
}
