package applicationState.implementations;

import applicationState.components.*;
import applicationState.keys.*;
import applicationState.keys.data.*;
import applicationState.state.*;
import core.*;
import core.objects.screen.*;
import gui.button.*;
import misc.*;
import tools.*;
import tools.common.*;
import tools.component.*;
import tools.component.graphic.*;
import tools.keyCheck.*;
import tools.progressLine.*;
import tools.spawn.*;

public class Menu extends ComponentApplicationState<SimultaneousProgressLine> {
    private final CommonComponent commonComponent = new CommonComponent();
    private final CenterPhysicBuilder centerPhysicBuilder = new CenterPhysicBuilder();
    private final SpawnButtonBuilder spawnButtonBuilder = new SpawnButtonBuilder();
    private Lock playButtonLock;
    
    public Menu(Core core) {
        super(core, new SimultaneousProgressLine());
        buildBackKeyComponent();
        buildButtons();
    }
    
    @Override
    protected void buildBackKeyComponent() {
        Action keyAction = new Action() {
            @Override
            public void perform() {
                if (stateSpawnProgressLine.getProgress() >= 0.8f) {
                    core.title.despawn();
                    scheduleStatePush(new CurtainKey(new CurtainData(false)), 1.f);
                }
            }
        };
        KeyComponent keyComponent = new KeyComponent(IsBackKey.instance, keyAction);
        addComponent(keyComponent);
    }
    
    private void buildButtons() {
        addComponent(commonComponent);
        
        spawnButtonBuilder.spawnPhysicBuilder = centerPhysicBuilder;
        spawnButtonBuilder.buttonDesign = core.designs.buttonDesigns.main;
        spawnButtonBuilder.progressLine = new Timer(Constants.SPAWN_TIME);
        
        Action action = new Action() {
            @Override
            public void perform() {
                scheduleStatePush(new GameKey(new GameData(true)));
            }
        };
        spawnButtonBuilder.lockTextureOrigin = TextureOrigins.get("buttonLock");
        buildLock(0, -0.1f, 0, 0.1f, action,
          TextureOrigins.get("symbolPlay"));
        spawnButtonBuilder.lockTextureOrigin = null;
        
        action = new Action() {
            @Override
            public void perform() {
                scheduleStatePush(new RecordsKey());
            }
        };
        buildButton(-0.27f, -0.37f, -0.1f, 0, action,
          TextureOrigins.get("symbolRecords"));
        
        action = new Action() {
            @Override
            public void perform() {
                scheduleStatePush(new GameKey(new GameData(false)));
                core.progress.tutorialPlayed.notifyWasPlayed();
            }
        };
        buildButton(0, -0.37f, 0, 0, action,
          TextureOrigins.get("symbolPlayTutorial"));
        
        action = new Action() {
            @Override
            public void perform() {
                scheduleStatePush(new OptionsKey());
            }
        };
        buildButton(0.27f, -0.37f, 0.1f, 0, action,
          TextureOrigins.get("symbolSettings"));
        
        action = new Action() {
            @Override
            public void perform() {
                scheduleStatePush(new InformationKey());
            }
        };
        buildButton(0, -0.64f, 0, -0.1f, action,
          TextureOrigins.get("symbolInfo"));
    }
    
    private void buildButton(float percentageX, float percentageY,
                             float movePercentageX, float movePercentageY, Action action,
                             TextureOrigin symbolTextureRegion) {
        prepareBuild(percentageX, percentageY, movePercentageX, movePercentageY,
          action, symbolTextureRegion);
        addPair(spawnButtonBuilder.buildSpawnButton());
    }
    
    private void buildLock(float percentageX, float percentageY,
                           float movePercentageX, float movePercentageY, Action action,
                           TextureOrigin symbolTextureRegion) {
        prepareBuild(percentageX, percentageY, movePercentageX, movePercentageY,
          action, symbolTextureRegion);
        Pair<Lock, ProgressLine> pair = spawnButtonBuilder.buildSpawnLock();
        playButtonLock = pair.one;
        addPair(pair);
    }
    
    private void prepareBuild(float percentageX, float percentageY,
                              float movePercentageX, float movePercentageY, Action action,
                              TextureOrigin symbolTextureRegion) {
        float idealWorldWidth = SizeGetter.getIdealWorldWidth();
        centerPhysicBuilder.centerX = percentageX*idealWorldWidth;
        centerPhysicBuilder.centerY = percentageY*idealWorldWidth;
        spawnButtonBuilder.action = action;
        spawnButtonBuilder.spawnMoveDistance.set(movePercentageX*idealWorldWidth,
          movePercentageY*idealWorldWidth);
        spawnButtonBuilder.symbolTextureOrigin = symbolTextureRegion;
    }
    
    private <E extends Common> void addPair(Pair<E, ProgressLine> pair) {
        commonComponent.add(pair.one);
        stateSpawnProgressLine.absorb(pair.two);
    }
    
    @Override
    public void pushed(ApplicationStateKey applicationStateKey) {
        super.pushed(applicationStateKey);
        if (core.progress.tutorialPlayed.wasPlayed()) playButtonLock.unlock();
        else playButtonLock.lock();
    }
    
    @Override
    public boolean handleExternalStateChangeRequest(ApplicationStateKey applicationStateKey) {
        scheduleStatePush(applicationStateKey);
        return true;
    }
    
    @Override
    public Class<MenuKey> getApplicationStateKeyClass() {
        return MenuKey.class;
    }
}
