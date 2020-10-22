package gameImplementation.board;

import gameImplementation.board.grid.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.boardMediator.*;

public class SpawnHexagonController {
    private final BoardMediator boardMediator;
    private Hexagon middleSpawnHexagon;
    private int spawningHexagonCount = 0;
    
    SpawnHexagonController(BoardMediator boardMediator) {
        this.boardMediator = boardMediator;
    }
    
    public void reset(Grid grid, int spawningCount) {
        grid.setAllHexagonsToNotSpawning();
        middleSpawnHexagon = grid.getHighestMiddleHexagon();
        spawningHexagonCount = 0;
        for (int i = 0; i < spawningCount; i++) setNextHexagonToSpawning();
    }
    
    public void setNextHexagonToSpawning() {
        if (middleSpawnHexagon.isSpawningHexagon()) {
            setUpperHexagonsToSpawning();
        } else middleSpawnHexagon.setToSpawning();
        spawningHexagonCount++;
        boardMediator.notifySpawningHexagonCountChanged(spawningHexagonCount);
    }
    
    private void setUpperHexagonsToSpawning() {
        Hexagon nextUpperLeft = middleSpawnHexagon.upperLeft;
        Hexagon nextUpperRight = middleSpawnHexagon.upperRight;
        while (nextUpperLeft != null) {
            if (nextUpperLeft.isSpawningHexagon()) {
                nextUpperLeft = nextUpperLeft.upperLeft;
                nextUpperRight = nextUpperRight.upperRight;
            } else {
                nextUpperLeft.setToSpawning();
                nextUpperRight.setToSpawning();
                middleSpawnHexagon.setToNotSpawning();
                break;
            }
        }
        if (nextUpperLeft == null && middleSpawnHexagon.lower != null) {
            middleSpawnHexagon = middleSpawnHexagon.lower;
            middleSpawnHexagon.setToSpawning();
        }
    }
}
