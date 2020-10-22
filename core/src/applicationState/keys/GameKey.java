package applicationState.keys;

import applicationState.keys.data.*;
import core.*;
import applicationState.implementations.*;
import applicationState.state.*;

public class GameKey extends ApplicationStateKey {
    private final GameData gameData;
    
    public GameKey(GameData gameData) {
        this.gameData = gameData;
    }
    
    @Override
    protected ApplicationState create(Core core) {
        return new Game(core);
    }
    
    @Override
    public GameData getGameData() {
        return gameData;
    }
}
