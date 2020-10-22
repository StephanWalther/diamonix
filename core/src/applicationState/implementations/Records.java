package applicationState.implementations;

import java.util.*;

import applicationState.components.*;
import applicationState.keys.*;
import applicationState.keys.data.trueFalseQuestionData.*;
import applicationState.state.*;
import core.*;
import core.objects.progress.highScores.*;
import core.objects.screen.*;
import core.objects.worldButton.*;
import gui.button.*;
import misc.*;
import tools.*;
import tools.common.*;
import tools.progressLine.*;
import tools.progressLine.Timer;
import tools.screen.Screen;
import tools.spawn.*;
import tools.state.stackState.*;

public class Records extends ComponentApplicationState<SequentialProgressLine>
  implements TrueFalseQuestionListener {
    private final HighScoresVisualization highScoresVisualization;
    
    public Records(Core core) {
        super(core, new SequentialProgressLine());
        highScoresVisualization
          = core.progress.highScores.buildHighScoresVisualization(stateSpawnProgressLine);
        highScoresVisualization.setFontForAll(Fonts.get("medium"));
        highScoresVisualization.setColumnDistForAll(SizeGetter.getIdealWorldWidth()*0.475f);
        highScoresVisualization.setCenterXForAll(SizeGetter.getIdealWorldWidth()*0.0875f);
        highScoresVisualization.verticalAlign(SizeGetter.getIdealWorldHeight()*0.225f,
          SizeGetter.getIdealWorldHeight()*0.0625f, null);
        buildBackKeyComponent();
        buildWorldButtons();
        stateSpawnProgressLine.setTimeBetweenIncreasesAndDecreases(
          Constants.SPAWN_TIME/(float) (highScoresVisualization.size()));
    }
    
    private void buildWorldButtons() {
        CommonComponent commonComponent = new CommonComponent();
        addComponent(commonComponent);
        SpawnButtonBuilder spawnButtonBuilder = new SpawnButtonBuilder();
        CenterPhysicBuilder centerPhysicBuilder = new CenterPhysicBuilder();
        spawnButtonBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnButtonBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        spawnButtonBuilder.buttonDesign = core.designs.buttonDesigns.main;
        centerPhysicBuilder.centerY = -SizeGetter.getIdealWorldHeight()*0.35f;
        
        spawnButtonBuilder.action = new Action() {
            @Override
            public void perform() {
                worldButtonAction(true);
            }
        };
        spawnButtonBuilder.symbolTextureOrigin = TextureOrigins.get("symbolOnlineRecords");
        centerPhysicBuilder.centerX = -SizeGetter.getIdealWorldWidth()*0.2f;
        buildButton(spawnButtonBuilder, commonComponent);
        
        spawnButtonBuilder.action = new Action() {
            @Override
            public void perform() {
                worldButtonAction(false);
            }
        };
        spawnButtonBuilder.symbolTextureOrigin = TextureOrigins.get("symbolOnlineAchievements");
        centerPhysicBuilder.centerX = SizeGetter.getIdealWorldWidth()*0.2f;
        buildButton(spawnButtonBuilder, commonComponent);
        
        spawnButtonBuilder.action = new Action() {
            @Override
            public void perform() {
                TrueFalseQuestionData tfqd = new TrueFalseQuestionData(
                  TrueFalseQuestion.Question.revokeAccess, Records.this);
                requestStatePopPush(new PopPushRequest<ApplicationStateKey>(0,
                  Collections.singletonList((ApplicationStateKey) new TrueFalseQuestionKey(tfqd))));
            }
        };
        spawnButtonBuilder.buttonDesign = core.designs.buttonDesigns.mainSmall;
        spawnButtonBuilder.symbolTextureOrigin = TextureOrigins.get("symbolCrossSmall");
        centerPhysicBuilder.centerX = -SizeGetter.getIdealWorldWidth()*0.5f
                                        + spawnButtonBuilder.buttonDesign.backTextureOrigin
                                            .textureRegion.getRegionWidth()*0.85f;
        centerPhysicBuilder.centerY = -SizeGetter.getIdealWorldHeight()*0.5f
                                        + spawnButtonBuilder.buttonDesign.backTextureOrigin
                                            .textureRegion.getRegionHeight()*0.85f;
        buildButton(spawnButtonBuilder, commonComponent);
    }
    
    private void buildButton(SpawnButtonBuilder spawnButtonBuilder,
                             CommonComponent commonComponent) {
        Pair<Button, ProgressLine> pair = spawnButtonBuilder.buildSpawnButton();
        commonComponent.add(pair.one);
        stateSpawnProgressLine.absorb(pair.two);
    }
    
    @Override
    public void receiveTrueFalseQuestionAnswer(boolean yes) {
        if (yes) core.onlineButtonHandler.handleRevokeAccess();
    }
    
    private void worldButtonAction(boolean leaderboardsButtonPressed) {
        OnlineButtonData onlineButtonData = new OnlineButtonData();
        onlineButtonData.leaderboardsButtonPressed = leaderboardsButtonPressed;
        onlineButtonData.progress = core.progress;
        core.onlineButtonHandler.handleOnlineButtonPressed(onlineButtonData);
    }
    
    @Override
    public void draw(Screen screen) {
        super.draw(screen);
        screen.draw(highScoresVisualization);
    }
    
    @Override
    public void pushed(ApplicationStateKey applicationStateKey) {
        super.pushed(applicationStateKey);
        highScoresVisualization.refreshStrings();
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey applicationStateKey) {
        scheduleStatePush(applicationStateKey);
        return true;
    }
    
    @Override
    public Class<RecordsKey> getApplicationStateKeyClass() {
        return RecordsKey.class;
    }
}
