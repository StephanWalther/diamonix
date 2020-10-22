package gameImplementation.board.boardState;

import java.util.*;

import core.objects.screen.*;
import core.objects.snapshot.*;
import gameImplementation.board.boardState.destructionBoardState.*;
import gameImplementation.board.grid.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import tools.common.*;
import tools.component.physic.*;
import tools.screen.Screen;

public class FallBoardState extends BoardState {
    private final PhysicAutonomizer physicAutonomizer = new PhysicAutonomizer();
    private final List<List<HexagonDiamondPair>> fallingDiamonds
      = new ArrayList<List<HexagonDiamondPair>>();
    
    public FallBoardState(GameVariant gameVariant, Grid grid) {
        super(gameVariant, grid);
        physicAutonomizer.setAccelerationY(-SizeGetter.getIdealWorldHeight());
    }
    
    @Override
    public void update(float dt) {
        physicAutonomizer.update(dt);
        for (int i = 0; i < fallingDiamonds.size(); i++) {
            List<HexagonDiamondPair> fallingDiamondLine = fallingDiamonds.get(i);
            updateFallingDiamondLine(fallingDiamondLine, dt);
            if (fallingDiamondLine.isEmpty()) {
                fallingDiamonds.remove(i);
                i--;
            }
        }
        checkForStateChange();
    }
    
    private void updateFallingDiamondLine(List<HexagonDiamondPair> fallingDiamondLine, float dt) {
        for (HexagonDiamondPair hexagonDiamondPair : fallingDiamondLine) {
            hexagonDiamondPair.diamond.update(dt);
        }
        while (!fallingDiamondLine.isEmpty()) {
            HexagonDiamondPair hexagonDiamondPair = fallingDiamondLine.get(0);
            if (hexagonDiamondPair.diamond.physicComponent.getCenterY()
                  <= hexagonDiamondPair.hexagon.getPhysicComponent().getCenterY()) {
                hexagonDiamondPair.hexagon.attachDiamond(hexagonDiamondPair.diamond);
                physicAutonomizer.removePhysicComponent(hexagonDiamondPair.diamond.physicComponent);
                fallingDiamondLine.remove(0);
            } else break;
        }
    }
    
    @Override
    public void draw(Screen screen) {
        for (List<HexagonDiamondPair> fallingDiamondLine : fallingDiamonds) {
            for (HexagonDiamondPair hexagonDiamondPair : fallingDiamondLine) {
                screen.draw(hexagonDiamondPair.diamond);
            }
        }
    }
    
    @Override
    public void enter(BoardState lastBoardState, Object data) {
        physicAutonomizer.setVelocityY(0);
        List<Hexagon> lowestHexagons = grid.getLowestHexagons();
        for (Hexagon lowest : lowestHexagons) removeHangingDiamondsFromLine(lowest);
        checkForStateChange();
    }
    
    private void removeHangingDiamondsFromLine(Hexagon lowest) {
        List<HexagonDiamondPair> fallingDiamondLine = new ArrayList<HexagonDiamondPair>();
        Hexagon destination = toLowestWithoutDiamond(lowest);
        if (destination == null) return;
        lowest = destination;
        while (lowest.upper != null) {
            lowest = lowest.upper;
            if (lowest.hasDiamond()) {
                Diamond diamond = lowest.removeDiamond();
                physicAutonomizer.addPhysicComponent(diamond.physicComponent);
                fallingDiamondLine.add(new HexagonDiamondPair(destination, diamond));
                destination = destination.upper;
            }
        }
        if (!fallingDiamondLine.isEmpty()) this.fallingDiamonds.add(fallingDiamondLine);
    }
    
    private Hexagon toLowestWithoutDiamond(Hexagon lowest) {
        while (lowest.hasDiamond()) {
            lowest = lowest.upper;
            if (lowest == null) return null;
        }
        return lowest;
    }
    
    private void checkForStateChange() {
        if (fallingDiamonds.isEmpty()) nextState = DestructionBoardState.class;
    }
    
    @Override
    public List<Whole> stop() {
        List<Whole> wholes = new ArrayList<Whole>();
        for (List<HexagonDiamondPair> pairs : fallingDiamonds) {
            wholes.addAll(HexagonDiamondPair.toWholes(pairs));
        }
        fallingDiamonds.clear();
        return wholes;
    }
}
