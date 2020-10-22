package core.objects.progress.highScores;

import java.util.*;

import gui.text.*;
import misc.*;
import tools.*;
import tools.component.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.progressLine.*;
import tools.progressLine.Timer;
import tools.spawn.*;

class HighScoresVisualizationBuilder {
    private List<TwoColumnText> twoColumnTextSpawnPacks;
    private ManifoldProgressLine manifoldProgressLine;
    private final SpawnTwoColumnTextBuilder spawnTextBuilder = new SpawnTwoColumnTextBuilder();
    private final ControlPhysicBuilder controlPhysicBuilder = new ControlPhysicBuilder();
    
    HighScoresVisualization build(ManifoldProgressLine manifoldProgressLine,
                                  HighScores highScores) {
        twoColumnTextSpawnPacks = new ArrayList<TwoColumnText>(HighScores.MAX_HIGH_SCORES);
        this.manifoldProgressLine = manifoldProgressLine;
        spawnTextBuilder.spawnPhysicBuilder = controlPhysicBuilder;
        for (int i = 0; i < HighScores.MAX_HIGH_SCORES; i++) buildText(i + 1);
        List<DefaultPhysicComponent> defaultPhysicComponents
          = new ArrayList<DefaultPhysicComponent>(controlPhysicBuilder.producedControlComponents);
        controlPhysicBuilder.producedControlComponents.clear();
        return new HighScoresVisualization(highScores, twoColumnTextSpawnPacks,
          defaultPhysicComponents);
    }
    
    private void buildText(int number) {
        spawnTextBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        Pair<TwoColumnText, ProgressLine> pair = spawnTextBuilder.buildTwoColumnSpawnText();
        pair.one.setOrigin(Origin.rightCenter);
        pair.one.setLeftString(Integer.toString(number) + ".");
        twoColumnTextSpawnPacks.add(pair.one);
        manifoldProgressLine.absorb(pair.two);
    }
}
