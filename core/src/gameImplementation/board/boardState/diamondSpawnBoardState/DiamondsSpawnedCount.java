package gameImplementation.board.boardState.diamondSpawnBoardState;

import gameImplementation.board.SpawnHexagonController;

class DiamondsSpawnedCount {
    private gameImplementation.board.SpawnHexagonController spawnHexagonController;
    int diamondsSpawnCount = 0;
    
    DiamondsSpawnedCount(SpawnHexagonController spawnHexagonController) {
        this.spawnHexagonController = spawnHexagonController;
    }
    
    void diamondsSpawned(int spawningHexagonCount) {
        diamondsSpawnCount++;
        int spawnCountTillDiamondSpawnIncrease;
        if (spawningHexagonCount < 9) spawnCountTillDiamondSpawnIncrease = 3;
        else if (spawningHexagonCount < 14) spawnCountTillDiamondSpawnIncrease = 2;
        else spawnCountTillDiamondSpawnIncrease = 1;
        if (diamondsSpawnCount >= spawnCountTillDiamondSpawnIncrease) {
            diamondsSpawnCount = 0;
            spawnHexagonController.setNextHexagonToSpawning();
        }
    }
    
    void reset() {
        diamondsSpawnCount = 0;
    }
}
