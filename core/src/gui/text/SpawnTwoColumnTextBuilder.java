package gui.text;

import core.objects.screen.*;
import misc.*;
import tools.*;
import tools.component.*;
import tools.progressLine.*;
import tools.spawn.*;

public class SpawnTwoColumnTextBuilder extends SpawnBuilder {
    public SpawnTwoColumnTextBuilder() {
        spawnMoveDistance.set(-SizeGetter.getIdealWorldWidth()*Constants.TEXT_SPAWN_REL_DIST_X, 0);
    }
    
    public Pair<TwoColumnText, ProgressLine> buildTwoColumnSpawnText() {
        SpawnPack spawnPack = build();
        return new Pair<TwoColumnText, ProgressLine>(
          new TwoColumnText(spawnPack.physicComponent, spawnPack.colorComponent),
          spawnPack.progressLine);
    }
}
