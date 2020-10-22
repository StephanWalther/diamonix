package gameImplementation.board.boardState.diamondSpawnBoardState;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.board.*;
import gameImplementation.board.boardState.*;
import gameImplementation.board.boardState.gameEndBoardState.*;
import gameImplementation.board.boardState.playerBoardState.*;
import gameImplementation.board.grid.*;
import gameImplementation.board.grid.hexagonSelector.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.screen.Screen;

public class DiamondSpawnBoardState extends BoardState {
    private final SpawningDiamonds spawningDiamonds;
    private final DiamondsSpawnedCount diamondsSpawnedCount;
    private List<List<DiamondColor>> diamondColorsToSpawn;
    
    public DiamondSpawnBoardState(GameVariant gameVariant, Grid grid,
                                  SpawnHexagonController spawnHexagonController) {
        super(gameVariant, grid);
        spawningDiamonds = new SpawningDiamonds(grid.getWidth());
        diamondsSpawnedCount = new DiamondsSpawnedCount(spawnHexagonController);
    }
    
    @Override
    public void update(float dt) {
        spawningDiamonds.update(dt);
        if (!spawningDiamonds.hasDiamonds()) {
            diamondsSpawnedCount.diamondsSpawned(grid.getSpawningHexagonCount());
            nextState = PlayerBoardState.class;
        }
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(spawningDiamonds);
    }
    
    @Override
    public void apply(SnapshotData snapshotData) {
        diamondsSpawnedCount.diamondsSpawnCount = snapshotData.diamondsSpawnCount;
        diamondColorsToSpawn = snapshotData.diamondColorsToSpawn;
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        List<List<Hexagon>> spawningHexagons = grid.selectHexagons(Spawn.instance);
        if (diamondColorsToSpawn == null) {
            diamondColorsToSpawn = gameVariant.buildSpawningDiamonds(spawningHexagons);
        }
        if (diamondColorsToSpawn == null) nextState = GameEndBoardState.class;
        else {
            gameVariant.requestSnapshot();
            spawningDiamonds.handleNewSpawningDiamonds(
              buildSpawningDiamondsFromColors(spawningHexagons));
            diamondColorsToSpawn = null;
        }
    }
    
    private List<List<HexagonDiamondPair>> buildSpawningDiamondsFromColors(
      List<List<Hexagon>> spawningHexagons) {
        List<List<HexagonDiamondPair>> hexagonDiamondPairs
          = new ArrayList<List<HexagonDiamondPair>>(spawningHexagons.size());
        for (int i = 0; i < spawningHexagons.size(); i++) {
            List<HexagonDiamondPair> hdps
              = new ArrayList<HexagonDiamondPair>(spawningHexagons.get(i).size());
            for (int j = 0; j < spawningHexagons.get(i).size(); j++) {
                Diamond diamond = new Diamond(diamondColorsToSpawn.get(i).get(j));
                Hexagon hexagon = spawningHexagons.get(i).get(j);
                diamond.physicComponent.setCenter(hexagon.getPhysicComponent().getCenter());
                hdps.add(new HexagonDiamondPair(hexagon, diamond));
            }
            hexagonDiamondPairs.add(hdps);
        }
        return hexagonDiamondPairs;
    }
    
    @Override
    public void fillSnapshotData(SnapshotData snapshotData) {
        snapshotData.diamondsSpawnCount = diamondsSpawnedCount.diamondsSpawnCount;
        snapshotData.diamondColorsToSpawn = diamondColorsToSpawn;
    }
    
    @Override
    public List<Whole> stop() {
        diamondsSpawnedCount.reset();
        return spawningDiamonds.clear();
    }
}
