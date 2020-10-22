package gameImplementation.board.boardState.destructionBoardState;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.boardMediator.*;
import tools.progressLine.Timer;

class DiamondDestructor {
    private final BoardMediator boardMediator;
    private final Marker marker;
    private final Timer timer = new Timer(0.15f).increase();
    private List<Hexagon> diamondsForDestruction;
    
    DiamondDestructor(BoardMediator boardMediator, Marker marker) {
        this.boardMediator = boardMediator;
        this.marker = marker;
    }
    
    void destroy(List<Hexagon> diamondsForDestruction) {
        this.diamondsForDestruction = diamondsForDestruction;
        timer.setTime(timer.getEndTime());
    }
    
    void update(float dt) {
        if (diamondsForDestruction != null && !diamondsForDestruction.isEmpty()) {
            timer.update(dt);
            if (timer.getProgress() == 1) {
                Diamond diamond = diamondsForDestruction.remove(0).removeDiamond();
                boardMediator.notifyDiamondDestruction(diamond);
                timer.setTime(0);
            } if (diamondsForDestruction.isEmpty()) marker.decrease();
        }
    }
    
    void reset() {
        if (diamondsForDestruction != null) diamondsForDestruction.clear();
    }
}
