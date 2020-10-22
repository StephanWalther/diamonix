package gameImplementation.board.boardState.playerBoardState;

public class MoveCounter {
    private final int maxMoves = 2;
    private int moveCount = maxMoves;
    
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
    
    public int getMoveCount() {
        return moveCount;
    }
    
    public void reduce() {
        moveCount--;
    }
    
    public void reset() {
        moveCount = maxMoves;
    }
}
