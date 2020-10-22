package applicationState.implementations;

import com.badlogic.gdx.graphics.*;

import core.*;
import applicationState.keys.*;
import applicationState.keys.data.*;
import applicationState.state.*;
import misc.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.Screen;

public class Curtain extends ComponentApplicationState<Timer> {
    private final Color color = new Color(0, 0, 0, 0);
    
    public Curtain(Core core) {
        super(core, new Timer(Constants.SPAWN_TIME));
    }
    
    @Override
    public void update(float dt) {
        super.update(dt);
        if (stateSpawnProgressLine.getProgress() == 1 && stateSpawnProgressLine.isIncreasing()) {
            requestApplicationExit();
        }
    }
    
    @Override
    public void draw(Screen screen) {
        super.draw(screen);
        float worldWidth = screen.sizes().getWorldWidth();
        float worldHeight = screen.sizes().getWorldHeight();
        color.a = PositiveSinWave.halfPeriod.calculate(stateSpawnProgressLine.getProgress());
        screen.drawer().draw(-0.5f*worldWidth, -0.5f*worldHeight, worldWidth, worldHeight,
          color, color, color, color);
    }
    
    @Override
    public void pushed(ApplicationStateKey applicationStateKey) {
        super.pushed(applicationStateKey);
        CurtainData curtainData = applicationStateKey.getCurtainData();
        if (curtainData.disappear) {
            stateSpawnProgressLine.setTime(stateSpawnProgressLine.getEndTime());
            stateSpawnProgressLine.decrease();
        } else {
            stateSpawnProgressLine.setTime(0);
            stateSpawnProgressLine.increase();
        }
    }
    
    @Override
    public Class<CurtainKey> getApplicationStateKeyClass() {
        return CurtainKey.class;
    }
}
