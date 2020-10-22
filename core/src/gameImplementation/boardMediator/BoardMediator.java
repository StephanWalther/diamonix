package gameImplementation.boardMediator;

import java.util.*;

import core.objects.audio.*;
import gameImplementation.*;
import gameImplementation.board.boardState.*;
import gameImplementation.board.boardState.destructionBoardState.*;
import gameImplementation.board.boardState.diamondSpawnBoardState.*;
import gameImplementation.board.boardState.gameEndBoardState.*;
import gameImplementation.board.hexagonAndDiamond.*;

public class BoardMediator {
    private final GameEndHandler gameEndHandler;
    private final MusicPlayer musicPlayer;
    private final List<BoardListener> boardListeners = new ArrayList<BoardListener>();
    private int destructionCountThisRound = 0;
    
    public BoardMediator(GameEndHandler gameEndHandler, MusicPlayer musicPlayer) {
        this.gameEndHandler = gameEndHandler;
        this.musicPlayer = musicPlayer;
    }
    
    public void addBoardListener(BoardListener boardListener) {
        boardListeners.add(boardListener);
    }
    
    public void notifyBoardStateEntered(Class<? extends BoardState> nextBoardState) {
        if (nextBoardState == DiamondSpawnBoardState.class) {
            stop();
            for (BoardListener boardListener : boardListeners) boardListener.newRound();
        } else if (nextBoardState == DestructionBoardState.class) {
            destructionCountThisRound++;
            for (BoardListener boardListener : boardListeners) {
                boardListener.newDestruction(destructionCountThisRound);
            }
        } else if (nextBoardState == GameEndBoardState.class) gameEndHandler.handleGameEnd();
    }
    
    public void notifyDiamondDestruction(Diamond diamond) {
        for (BoardListener boardListener : boardListeners) boardListener.diamondDestroyed(diamond);
    }
    
    public void notifySpawningHexagonCountChanged(int spawningHexagonCount) {
        musicPlayer.setFadeOutTime(4.f);
        musicPlayer.setFadeInTime(12.f);
        if (spawningHexagonCount < 9) musicPlayer.playMusic("phaseOne");
        else if (spawningHexagonCount < 14) musicPlayer.playMusic("phaseTwo");
        else if (spawningHexagonCount < 19) musicPlayer.playMusic("phaseThree");
        else musicPlayer.playMusic("endPhase");
    }
    
    public void stop() {
        destructionCountThisRound = 0;
    }
}
