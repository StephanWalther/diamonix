package gameImplementation.board.boardState;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.board.grid.*;
import gameImplementation.board.grid.hexagonSelector.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import misc.*;
import tools.*;
import tools.common.*;
import tools.mapping.*;
import tools.misc.*;
import tools.progressLine.*;
import tools.progressLine.Timer;
import tools.screen.*;

public class PreparationBoardState extends BoardState {
    private final ProgressLineInfo stateProgressLineInfo;
    private final List<CleanPack> diamondsToRemove = new ArrayList<CleanPack>();
    private final List<HexagonDiamondPair> nextHexagonDiamondPairs
      = new ArrayList<HexagonDiamondPair>();
    private final Timer nextDiamondsTimer = new Timer(Constants.SPAWN_TIME).increase();
    
    public PreparationBoardState(GameVariant gameVariant, Grid grid,
                                 ProgressLineInfo stateProgressLineInfo) {
        super(gameVariant, grid);
        this.stateProgressLineInfo = stateProgressLineInfo;
    }
    
    @Override
    public void update(float dt) {
        UpdateDistributer.updateAll(diamondsToRemove, dt);
        Remover.removeAllDoneElements(diamondsToRemove);
        if (diamondsToRemove.isEmpty() && stateProgressLineInfo.getProgress() == 1) {
            updateNextHexagonDiamondPairs(dt);
        }
        checkForStateChange();
    }
    
    private void updateNextHexagonDiamondPairs(float dt) {
        nextDiamondsTimer.update(dt);
        float timeProgress = nextDiamondsTimer.getProgress();
        if (timeProgress < 1) {
            float scale = Mapping.scaleUp.calculate(timeProgress);
            float alpha = PositiveSinWave.halfPeriod.calculate(timeProgress);
            for (HexagonDiamondPair hdp : nextHexagonDiamondPairs) {
                hdp.diamond.physicComponent.setScale(scale);
                hdp.diamond.colorComponent.setColorA(alpha);
            }
        } else {
            for (HexagonDiamondPair hdp : nextHexagonDiamondPairs) {
                hdp.hexagon.attachDiamond(hdp.diamond);
            }
        }
    }
    
    @Override
    public void draw(Screen screen) {
        DrawDistributer.drawAll(diamondsToRemove, screen);
        for (HexagonDiamondPair hdp : nextHexagonDiamondPairs) screen.draw(hdp.diamond);
    }
    
    @Override
    public void apply(SnapshotData snapshotData) {
        nextHexagonDiamondPairs.addAll(grid.toHexagonDiamondPairs(snapshotData.gridData));
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        nextDiamondsTimer.setTime(0);
        List<Hexagon> alreadyPreparedHexagons = checkForImmediateChange();
        extractDiamondsFromGrid(alreadyPreparedHexagons);
        checkForStateChange();
    }
    
    private List<Hexagon> checkForImmediateChange() {
        List<Hexagon> alreadyChangedHexagons
          = new ArrayList<Hexagon>(nextHexagonDiamondPairs.size());
        ListIterator<HexagonDiamondPair> iterator = nextHexagonDiamondPairs.listIterator();
        while (iterator.hasNext()) {
            HexagonDiamondPair hdp = iterator.next();
            if (hdp.hexagon.hasDiamond() &&
                  hdp.hexagon.getDiamond().diamondColor == hdp.diamond.diamondColor) {
                alreadyChangedHexagons.add(hdp.hexagon);
                iterator.remove();
            } else if (!hdp.hexagon.isVisible()) {
                if (hdp.hexagon.hasDiamond()) hdp.hexagon.removeDiamond();
                hdp.hexagon.attachDiamond(hdp.diamond);
                alreadyChangedHexagons.add(hdp.hexagon);
                iterator.remove();
            }
        }
        return alreadyChangedHexagons;
    }
    
    private void extractDiamondsFromGrid(List<Hexagon> alreadyPreparedHexagons) {
        List<Hexagon> hexagonsWithDiamond
          = Tools.toSingleList(grid.selectHexagons(WithDiamond.instance));
        for (Hexagon hexagonWithDiamond : hexagonsWithDiamond) {
            if (!alreadyPreparedHexagons.contains(hexagonWithDiamond)) {
                Diamond diamond = hexagonWithDiamond.takeDiamond();
                if (hexagonWithDiamond.isVisible()) diamondsToRemove.add(new CleanPack(diamond));
            }
        }
    }
    
    private void checkForStateChange() {
        if (diamondsToRemove.isEmpty() && nextHexagonDiamondPairs.isEmpty()) {
            nextState = WaitForStartBoardState.class;
        }
    }
    
    @Override
    public List<Whole> stop() {
        nextHexagonDiamondPairs.clear();
        List<Whole> wholes = new ArrayList<Whole>(diamondsToRemove.size());
        for (CleanPack cleanPack : diamondsToRemove) wholes.add(cleanPack.getWhole());
        diamondsToRemove.clear();
        return wholes;
    }
}
