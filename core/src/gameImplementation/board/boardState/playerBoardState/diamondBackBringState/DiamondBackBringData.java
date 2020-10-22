package gameImplementation.board.boardState.playerBoardState.diamondBackBringState;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;

public class DiamondBackBringData {
    final boolean lastDiamondMovementSucceeded;
    public final List<HexagonDiamondPair> backBringPairs;

    public DiamondBackBringData(boolean lastDiamondMovementSucceeded,
                                List<HexagonDiamondPair> backBringPairs) {
        this.lastDiamondMovementSucceeded = lastDiamondMovementSucceeded;
        this.backBringPairs = backBringPairs;
    }
}
