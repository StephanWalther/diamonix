package applicationState.implementations;

import java.util.*;

import applicationState.components.*;
import applicationState.keys.*;
import applicationState.keys.data.trueFalseQuestionData.*;
import applicationState.state.*;
import core.*;
import core.objects.screen.*;
import core.objects.snapshot.*;
import gameImplementation.*;
import gui.button.*;
import misc.*;
import tools.*;
import tools.common.*;
import tools.progressLine.*;
import tools.progressLine.Timer;
import tools.spawn.*;
import tools.state.stackState.*;

// TODO: Put the GameEndVisuals here (or intermediate class).
public class Game extends ComponentApplicationState<ProgressLineDecreaseBuffer>
  implements TrueFalseQuestionListener {
    private final GameImplementation gameImplementation;
    
    public Game(Core core) {
        super(core, new ProgressLineDecreaseBuffer().setBufferPercentage(
          Constants.GAME_END_VISUALS_PROGRESS_TILL_OTHER_CHANGES));
        SimultaneousProgressLine realStateSpawnProgressLine = new SimultaneousProgressLine();
        buildBackKeyComponent();
        gameImplementation = new GameImplementation(core.snapshot, core.progress,
          core.audio.musicPlayer, realStateSpawnProgressLine, stateSpawnProgressLine);
        addComponent(new CommonComponent().add(gameImplementation));
        buildReplayButton(realStateSpawnProgressLine);
        stateSpawnProgressLine.setBuffer(gameImplementation.getGameEndViasualProgressLineInfo());
        stateSpawnProgressLine.absorbRealProgressLine(realStateSpawnProgressLine);
    }
    
    @Override
    protected void buildBackKeyComponent() {
        Action keyAction = new Action() {
            @Override
            public void perform() {
                handleStateChange(new MenuKey());
            }
        };
        buildBackKeyComponent(keyAction);
    }
    
    private void buildReplayButton(ManifoldProgressLine realStateSpawnProgressLine) {
        CommonComponent commonComponent = new CommonComponent();
        addComponent(commonComponent);
        SpawnButtonBuilder spawnButtonBuilder = new SpawnButtonBuilder();
        CenterPhysicBuilder centerPhysicBuilder = new CenterPhysicBuilder();
        spawnButtonBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnButtonBuilder.buttonDesign = core.designs.buttonDesigns.main;
        spawnButtonBuilder.symbolTextureOrigin = TextureOrigins.get("symbolReplay");
        spawnButtonBuilder.action = new Action() {
            @Override
            public void perform() {
                try {
                    core.snapshot.getSnapshotData();
                    TrueFalseQuestionData tfqd = new TrueFalseQuestionData(
                      TrueFalseQuestion.Question.newGame, Game.this);
                    requestStatePopPush(new PopPushRequest<ApplicationStateKey>(0,
                      Collections.singletonList(
                        (ApplicationStateKey) new TrueFalseQuestionKey(tfqd))));
                } catch (HasNoSnapshotDataException e) {
                    prepareNewGame();
                }
            }
        };
        spawnButtonBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        centerPhysicBuilder.centerX
          = SizeGetter.getIdealWorldWidth()*Constants.REPLAY_BUTTON_REL_POS_X;
        centerPhysicBuilder.centerY
          = SizeGetter.getIdealWorldHeight()*Constants.REPLAY_BUTTON_REL_POS_Y;
        Pair<Button, ProgressLine> pair = spawnButtonBuilder.buildSpawnButton();
        commonComponent.add(pair.one);
        realStateSpawnProgressLine.absorb(pair.two);
    }
    
    @Override
    public void update(float dt) {
        super.update(dt);
        if (gameImplementation.getGameVariant().switchStateToMenu()) {
            handleStateChange(new MenuKey());
        }
    }
    
    private void handleStateChange(ApplicationStateKey applicationStateKey) {
        gameImplementation.stop();
        scheduleStatePush(applicationStateKey);
    }
    
    @Override
    public void pushed(ApplicationStateKey applicationStateKey) {
        super.pushed(applicationStateKey);
        if (applicationStateKey.getGameData().isRegularGame) {
            gameImplementation.getGameVariant().playRegularGame();
        } else gameImplementation.getGameVariant().playTutorialGame();
        gameImplementation.load();
    }
    
    @Override
    public void receiveTrueFalseQuestionAnswer(boolean yes) {
        if (yes) prepareNewGame();
    }
    
    private void prepareNewGame() {
        gameImplementation.handleGameEnd();
        gameImplementation.stop();
        gameImplementation.load();
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey applicationStateKey) {
        handleStateChange(applicationStateKey);
        return true;
    }
    
    @Override
    public Class<GameKey> getApplicationStateKeyClass() {
        return GameKey.class;
    }
}
