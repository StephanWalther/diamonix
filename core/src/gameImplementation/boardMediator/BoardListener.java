package gameImplementation.boardMediator;

import gameImplementation.board.hexagonAndDiamond.*;

public interface BoardListener {
    void newDestruction(int destructionCountThisRound);
    
    void diamondDestroyed(Diamond diamond);
    
    void newRound();
}
