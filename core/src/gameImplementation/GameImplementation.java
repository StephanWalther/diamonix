package gameImplementation;

import core.objects.audio.*;
import core.objects.progress.*;
import core.objects.progress.highScores.*;
import core.objects.snapshot.*;
import gameImplementation.board.*;
import gameImplementation.board.grid.*;
import gameImplementation.board.grid.hexagonSelector.*;
import gameImplementation.boardMediator.*;
import gameImplementation.effects.*;
import gameImplementation.gameEndVisuals.*;
import gameImplementation.gameVariant.*;
import gameImplementation.points.*;
import misc.*;
import tools.common.*;
import tools.input.input.*;
import tools.progressLine.*;
import tools.screen.*;

public class GameImplementation implements Common, Snapshotter, GameEndHandler {
    private final Snapshot snapshot;
    private final HighScores highScores;
    
    private final GameVariant gameVariant;
    private final BoardMediator boardMediator;
    private final AchievementsUnlocker achievementsUnlocker;
    private final Points points;
    private final Effects effects;
    private final Board board;
    private final Cleaner cleaner;
    private final GameEndVisuals gameEndVisuals;
    
    public GameImplementation(Snapshot snapshot, Progress progress, MusicPlayer musicPlayer,
                              ManifoldProgressLine realStateSpawnProgressLine,
                              ProgressLineInfo stateProgressLine) {
        this.snapshot = snapshot;
        this.highScores = progress.highScores;
        
        Grid grid = new Grid();
        realStateSpawnProgressLine.absorb(grid.buildProgressLine());
        gameEndVisuals = new GameEndVisuals(highScores);
        cleaner = new Cleaner(stateProgressLine);
        gameVariant = new GameVariant(this, grid.selectHexagons(All.instance), cleaner);
        boardMediator = new BoardMediator(this, musicPlayer);
        achievementsUnlocker = new AchievementsUnlocker(gameVariant, progress.achievements);
        boardMediator.addBoardListener(achievementsUnlocker);
        points = new Points(realStateSpawnProgressLine.absorb(new Timer(Constants.SPAWN_TIME)),
          gameEndVisuals.getProgressLineInfo());
        effects = new Effects(points);
        boardMediator.addBoardListener(effects);
        board = new Board(grid, stateProgressLine, gameEndVisuals.getProgressLineInfo(),
          boardMediator, gameVariant);
    }
    
    public ProgressLineInfo getGameEndViasualProgressLineInfo() {
        return gameEndVisuals.getProgressLineInfo();
    }
    
    @Override
    public boolean processInput(Input input) {
        return board.processInput(input);
    }
    
    @Override
    public void update(float dt) {
        effects.update(dt);
        board.update(dt);
        gameVariant.update(dt);
        points.update(dt);
        gameEndVisuals.update(dt);
        cleaner.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(board);
        screen.draw(gameVariant);
        screen.draw(points);
        screen.draw(effects);
        screen.draw(cleaner);
        screen.draw(gameEndVisuals);
    }
    
    @Override
    public void createSnapshot() {
        SnapshotData snapshotData = new SnapshotData();
        snapshotData.points = points.getPoints() + effects.getPoints();
        board.fillSnapshotData(snapshotData);
        snapshot.setSnapshotData(snapshotData);
    }
    
    @Override
    public void handleGameEnd() {
        if (gameVariant.isTutorialGame()) return;
        try {
            snapshot.getSnapshotData();
            snapshot.setSnapshotData(null);
            highScores.gameEnded(points.getPoints() + effects.getPoints());
        } catch (HasNoSnapshotDataException e) {}
        gameEndVisuals.refreshAndSpawn();
    }
    
    public void load() {
        try {
            if (gameVariant.isTutorialGame()) throw new HasNoSnapshotDataException();
            SnapshotData snapshotData = snapshot.getSnapshotData();
            points.setPoints(snapshotData.points);
            board.load(snapshotData);
        } catch (HasNoSnapshotDataException e) {
            points.setPoints(0);
            board.loadNewGame();
        }
    }
    
    public void stop() {
        gameVariant.stop();
        boardMediator.stop();
        achievementsUnlocker.stop();
        cleaner.clean(board.stop());
        cleaner.clean(effects.stop());
        points.stop();
        gameEndVisuals.despawn();
    }
    
    public GameVariant getGameVariant() {
        return gameVariant;
    }
}
