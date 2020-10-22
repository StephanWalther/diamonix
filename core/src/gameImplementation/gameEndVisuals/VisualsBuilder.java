package gameImplementation.gameEndVisuals;

import java.util.*;

import core.*;
import core.objects.progress.highScores.*;
import core.objects.screen.*;
import gui.*;
import gui.text.*;
import misc.*;
import tools.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.progressLine.*;
import tools.progressLine.Timer;
import tools.spawn.*;

class VisualsBuilder {
    private VisualsPack visualsPack;
    private HighScores highScores;
    
    Pair<ProgressLine, VisualsPack> build(HighScores highScores) {
        visualsPack = new VisualsPack();
        this.highScores = highScores;
        
        SequentialProgressLine progressLine = new SequentialProgressLine();
        progressLine.setTimeBetweenIncreases(2.f);
        progressLine.setTimeBetweenDecreases(Constants.SPAWN_TIME);
        progressLine.absorb(buildShadow());
        progressLine.absorb(buildIndicationAndTexts());
        
        return new Pair<ProgressLine, VisualsPack>(progressLine, visualsPack);
    }
    
    private ProgressLine buildShadow() {
        visualsPack.shadow = new Shadow();
        float shadowSpawnTime = 2.5f;
        return new ProgressLineObserver(new Timer(shadowSpawnTime).setDecreaseFactor(
          shadowSpawnTime/Constants.SPAWN_TIME), visualsPack.shadow);
    }
    
    private ProgressLine buildIndicationAndTexts() {
        SequentialProgressLine indicationAndTextProgressLine = new SequentialProgressLine();
        indicationAndTextProgressLine.setTimeBetweenIncreases(Constants.SPAWN_TIME);
        indicationAndTextProgressLine.setTimeBetweenDecreases(0);
        indicationAndTextProgressLine.absorb(buildIndication());
        indicationAndTextProgressLine.absorb(buildTexts());
        return indicationAndTextProgressLine;
    }
    
    private ProgressLine buildIndication() {
        SequentialProgressLine indicationProgressLine = new SequentialProgressLine();
        indicationProgressLine.setTimeBetweenIncreasesAndDecreases(Constants.SPAWN_TIME/3.f);
        
        SpawnTextureBuilder spawnTextureBuilder = new SpawnTextureBuilder();
        CenterPhysicBuilder centerPhysicBuilder = new CenterPhysicBuilder();
        ControlColorBuilder controlColorBuilder = new ControlColorBuilder();
        spawnTextureBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnTextureBuilder.controlColorBuilder = controlColorBuilder;
        centerPhysicBuilder.centerX
          = SizeGetter.getIdealWorldWidth()*Constants.REPLAY_BUTTON_REL_POS_X;
        centerPhysicBuilder.centerY
          = SizeGetter.getIdealWorldHeight()*Constants.REPLAY_BUTTON_REL_POS_Y;
        spawnTextureBuilder.textureOrigin = TextureOrigins.get("buttonIndicator");
        spawnTextureBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        
        Pair<TextureComponent, ProgressLine> pair = spawnTextureBuilder.buildSpawnTexture();
        visualsPack.buttonIndicator
          = new Pair<TextureComponent, DefaultColorComponent>(pair.one,
          controlColorBuilder.producedColorComponents.remove(0));
        indicationProgressLine.absorb(pair.two);
        
        int arrowCount = 3;
        visualsPack.arrows = new ArrayList<Arrow>(arrowCount);
        for (int i = 0; i < arrowCount; i++) {
            indicationProgressLine.absorb(buildArrow(-30.f*(i + 1)));
        }
        
        return indicationProgressLine;
    }
    
    private ProgressLine buildArrow(float angle) {
        Arrow arrow = new Arrow(
          SizeGetter.getIdealWorldWidth()*Constants.REPLAY_BUTTON_REL_POS_X,
          SizeGetter.getIdealWorldHeight()*Constants.REPLAY_BUTTON_REL_POS_Y,
          SizeGetter.getIdealWorldWidth()*0.225f, angle);
        visualsPack.arrows.add(arrow);
        return new ProgressLineObserver(new Timer(Constants.SPAWN_TIME), arrow);
    }
    
    private ProgressLine buildTexts() {
        SpawnTextBuilder spawnTextureBuilder = new SpawnTextBuilder();
        CenterPhysicBuilder centerPhysicBuilder = new CenterPhysicBuilder();
        spawnTextureBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnTextureBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        
        String OSLanguage = java.util.Locale.getDefault().getDisplayLanguage();
        
        SequentialProgressLine textProgressLine = new SequentialProgressLine();
        textProgressLine.absorb(buildGameOverText(spawnTextureBuilder, centerPhysicBuilder));
        SimultaneousProgressLine recordProgressLine = new SimultaneousProgressLine();
        recordProgressLine.absorb(buildNewRecordText(spawnTextureBuilder, centerPhysicBuilder,
          OSLanguage));
        recordProgressLine.absorb(buildNoRecordText(spawnTextureBuilder, OSLanguage));
        textProgressLine.absorb(recordProgressLine);
        visualsPack.highScoresVisualization
          = highScores.buildHighScoresVisualization(textProgressLine);
        textProgressLine.setTimeBetweenIncreases(2.5f/(textProgressLine.size() + 1));
        textProgressLine.setTimeBetweenDecreases(
          Constants.SPAWN_TIME/(textProgressLine.size() + 1));
        return textProgressLine;
    }
    
    private ProgressLine buildGameOverText(SpawnTextBuilder spawnTwoColumnTextBuilder,
                                           CenterPhysicBuilder centerPhysicBuilder) {
        centerPhysicBuilder.centerY = 0.15f*SizeGetter.getIdealWorldHeight();
        Pair<TextComponent, ProgressLine> gameOverPair = spawnTwoColumnTextBuilder.buildSpawnText();
        visualsPack.gameOverText = gameOverPair.one;
        visualsPack.gameOverText.setFont(Fonts.get("small_medium"));
        visualsPack.gameOverText.setString("Game Over");
        return gameOverPair.two;
    }
    
    private ProgressLine buildNewRecordText(SpawnTextBuilder spawnTwoColumnTextBuilder,
                                            CenterPhysicBuilder centerPhysicBuilder,
                                            String OSLanguage) {
        centerPhysicBuilder.centerY = 0.075f*SizeGetter.getIdealWorldHeight();
        Pair<TextComponent, ProgressLine> newRecordPair
          = spawnTwoColumnTextBuilder.buildSpawnText();
        visualsPack.newRecordText = newRecordPair.one;
        visualsPack.newRecordText.setFont(Fonts.get("small_medium"));
        if (OSLanguage.equals("Deutsch")) visualsPack.newRecordText.setString("Neuer Highscore!");
        else visualsPack.newRecordText.setString("New Highscore!");
        return newRecordPair.two;
    }
    
    private ProgressLine buildNoRecordText(SpawnTextBuilder spawnTwoColumnTextBuilder,
                                           String OSLanguage) {
        Pair<TextComponent, ProgressLine> noRecordPair = spawnTwoColumnTextBuilder.buildSpawnText();
        visualsPack.noRecordText = noRecordPair.one;
        visualsPack.noRecordText.setFont(Fonts.get("small_medium"));
        if (OSLanguage.equals("Deutsch")) visualsPack.noRecordText.setString("Kein Highscore");
        else visualsPack.noRecordText.setString("No Highscore");
        return noRecordPair.two;
    }
}
