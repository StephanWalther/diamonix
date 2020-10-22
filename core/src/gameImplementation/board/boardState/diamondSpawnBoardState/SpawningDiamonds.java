package gameImplementation.board.boardState.diamondSpawnBoardState;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;
import misc.*;
import tools.common.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.progressLine.Timer;
import tools.screen.*;

class SpawningDiamonds implements Updatable, Drawable {
    private final SequentialProgressLine sequentialProgressLine
      = new SequentialProgressLine().increase();
    private final List<ProgressLineInfo> progressLineInfo = new ArrayList<ProgressLineInfo>();
    private final float gridWidthMinusOne;
    private List<List<HexagonDiamondPair>> spawningDiamonds;
    
    SpawningDiamonds(int gridWidth) {
        gridWidthMinusOne = gridWidth;
    }
    
    void handleNewSpawningDiamonds(List<List<HexagonDiamondPair>> spawningDiamonds) {
        this.spawningDiamonds = spawningDiamonds;
        sequentialProgressLine.clear().setTimeBetweenIncreasesAndDecreases(
          Constants.SPAWN_TIME/gridWidthMinusOne);
        progressLineInfo.clear();
        for (int i = 0; i < spawningDiamonds.size(); i++) {
            progressLineInfo.add(sequentialProgressLine.absorb(new Timer(Constants.SPAWN_TIME)));
        }
    }
    
    @Override
    public void update(float dt) {
        sequentialProgressLine.update(dt);
        for (int i = 0; i < progressLineInfo.size(); i++) {
            float progress = progressLineInfo.get(i).getProgress();
            float scale = Mapping.scaleUp.calculate(progress);
            float alpha = PositiveSinWave.halfPeriod.calculate(progress);
            for (HexagonDiamondPair hdp : spawningDiamonds.get(i)) {
                hdp.diamond.physicComponent.setScale(scale);
                hdp.diamond.colorComponent.setColorA(alpha);
            }
        }
        if (everythingSpawned()) {
            attachDiamonds();
            spawningDiamonds = null;
        }
    }
    
    private boolean everythingSpawned() {
        return sequentialProgressLine.getProgress() == 1;
    }
    
    private void attachDiamonds() {
        for (List<HexagonDiamondPair> hdps : spawningDiamonds) {
            for (HexagonDiamondPair hdp : hdps) hdp.hexagon.attachDiamond(hdp.diamond);
        }
    }
    
    @Override
    public void draw(Screen screen) {
        for (List<HexagonDiamondPair> spawningDiamondLine : spawningDiamonds) {
            for (HexagonDiamondPair spawningDiamond : spawningDiamondLine) {
                screen.draw(spawningDiamond.diamond);
            }
        }
    }
    
    boolean hasDiamonds() {
        return spawningDiamonds != null;
    }
    
    List<Whole> clear() {
        sequentialProgressLine.clear();
        progressLineInfo.clear();
        List<Whole> wholes = new ArrayList<Whole>();
        if (spawningDiamonds != null) {
            for (List<HexagonDiamondPair> pairs : spawningDiamonds) {
                wholes.addAll(HexagonDiamondPair.toWholes(pairs));
            }
        }
        spawningDiamonds = null;
        return wholes;
    }
}
