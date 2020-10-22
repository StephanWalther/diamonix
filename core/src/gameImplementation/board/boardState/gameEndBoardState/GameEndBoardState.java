package gameImplementation.board.boardState.gameEndBoardState;

import java.util.*;

import core.*;
import core.objects.audio.*;
import gameImplementation.board.boardState.*;
import gameImplementation.board.grid.*;
import gameImplementation.board.grid.hexagonSelector.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import tools.*;
import tools.common.*;
import tools.screen.*;

public class GameEndBoardState extends BoardState {
    private final HexagonFullMarkers hexagonFullMarkers = new HexagonFullMarkers();
    private final Sound endSound = Sounds.get("gameEnd");
    
    public GameEndBoardState(GameVariant gameVariant, Grid grid) {
        super(gameVariant, grid);
    }
    
    @Override
    public void update(float dt) {
        hexagonFullMarkers.update(dt);
    }
    
    @Override
    public void draw(final Screen screen) {
        screen.draw(hexagonFullMarkers);
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        HexagonSelector selector = new HexagonSelector() {
            @Override
            public boolean select(Hexagon hexagon) {
                return hexagon.isSpawningHexagon() && hexagon.hasDiamond();
            }
        };
        List<Hexagon> spawningHexagonsWithDiamond
          = Tools.toSingleList(grid.selectHexagons(selector));
        hexagonFullMarkers.newMarkers(spawningHexagonsWithDiamond);
        endSound.play();
    }
    
    @Override
    public List<Whole> stop() {
        return hexagonFullMarkers.stop();
    }
}
