package core.objects.snapshot;

import java.io.*;
import java.util.*;

import gameImplementation.board.boardState.*;
import gameImplementation.board.hexagonAndDiamond.*;

public class SnapshotData implements Serializable {
    public int points;
    public int playerMoveCount;
    public int spawningHexagonCount;
    public int diamondsSpawnCount;
    public List<List<DiamondColor>> diamondColorsToSpawn;
    public List<List<DiamondColor>> gridData;
    public Class<? extends BoardState> boardState;
}
