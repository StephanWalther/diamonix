package gameImplementation.gameEndVisuals;

import java.util.*;

import core.*;
import core.objects.progress.highScores.*;
import core.objects.screen.*;
import tools.*;
import tools.common.*;
import tools.component.graphic.*;
import tools.misc.*;
import tools.progressLine.*;
import tools.screen.Screen;

public class GameEndVisuals implements Drawable {
    private final ProgressLine progressLine;
    private final VisualsPack visualsPack;
    private final HighScores highScores;
    private final Indicator indicator = new Indicator();
    private int lastInsertionIndex;
    
    public GameEndVisuals(HighScores highScores) {
        Pair<ProgressLine, VisualsPack> visuals = new VisualsBuilder().build(highScores);
        progressLine = visuals.one;
        visualsPack = visuals.two;
        
        this.highScores = highScores;
        
        indicator.setPeriodTime(1.5f);
        indicator.setScaleBounds(0.9f, 1.1f);
        indicator.setAlphaBounds(0.2f, 1.f);
    }
    
    public ProgressLineInfo getProgressLineInfo() {
        return progressLine;
    }
    
    public void update(float dt) {
        progressLine.update(dt);
        indicator.update(dt);
        float alpha = indicator.getAlpha();
        visualsPack.buttonIndicator.two.setColorA(alpha);
        for (Arrow arrow : visualsPack.arrows) arrow.setAlpha(alpha);
        if (lastInsertionIndex != -1) {
            visualsPack.highScoresVisualization.setScale(indicator.getScale(), lastInsertionIndex);
        }
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(visualsPack.shadow);
        screen.draw(visualsPack.buttonIndicator.one);
        for (Arrow arrow : visualsPack.arrows) screen.draw(arrow);
        screen.draw(visualsPack.gameOverText);
        if (lastInsertionIndex != -1) screen.draw(visualsPack.newRecordText);
        else screen.draw(visualsPack.noRecordText);
        screen.draw(visualsPack.highScoresVisualization);
    }
    
    public void refreshAndSpawn() {
        if (lastInsertionIndex != -1) {
            visualsPack.highScoresVisualization.setScale(1, lastInsertionIndex);
            visualsPack.highScoresVisualization.setOrigin(Origin.rightCenter, lastInsertionIndex);
        }
        progressLine.increase();
        visualsPack.highScoresVisualization.refreshStrings();
        visualsPack.highScoresVisualization.setFontForAll(Fonts.get("small"));
        visualsPack.highScoresVisualization
          .setColumnDistForAll(0.2f*SizeGetter.getIdealWorldWidth());
        visualsPack.highScoresVisualization.setCenterXForAll(SizeGetter.getIdealWorldWidth()*0.04f);
        HashMap<Integer, Float> specialDistCenterY = null;
        try {
            lastInsertionIndex = highScores.getLastInsertionIndex();
            visualsPack.highScoresVisualization
              .setFont(Fonts.get("small_medium"), lastInsertionIndex);
            int lastHighScore = highScores.getHighScores().get(lastInsertionIndex);
            visualsPack.highScoresVisualization.setColumnDist(
              SizeGetter.getIdealWorldWidth()*0.25f, lastInsertionIndex);
            if (lastHighScore < 100) {
                visualsPack.highScoresVisualization.setCenterX(0, lastInsertionIndex);
            } else if (lastHighScore < 1000) {
                visualsPack.highScoresVisualization.setCenterX(
                  -SizeGetter.getIdealWorldWidth()*0.01f, lastInsertionIndex);
            } else {
                visualsPack.highScoresVisualization.setCenterX(
                  -SizeGetter.getIdealWorldWidth()*0.02f, lastInsertionIndex);
            }
            float bigDistCenterY = 0.05f*SizeGetter.getIdealWorldHeight();
            specialDistCenterY = new HashMap<Integer, Float>(2);
            specialDistCenterY.put(lastInsertionIndex - 1, bigDistCenterY);
            specialDistCenterY.put(lastInsertionIndex, bigDistCenterY);
            visualsPack.highScoresVisualization.setOrigin(Origin.center, lastInsertionIndex);
        } catch (LastPointsNotInsertedException e) {
            lastInsertionIndex = -1;
        }
        visualsPack.highScoresVisualization.verticalAlign(-0.05f*SizeGetter.getIdealWorldHeight(),
          0.0375f*SizeGetter.getIdealWorldHeight(), specialDistCenterY);
    }
    
    public void despawn() {
        progressLine.decrease();
    }
}
