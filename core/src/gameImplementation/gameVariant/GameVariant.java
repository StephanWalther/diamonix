package gameImplementation.gameVariant;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.regularGameVariant.*;
import gameImplementation.gameVariant.tutorialGameVariant.*;
import tools.common.*;
import tools.screen.*;

public class GameVariant implements Updatable, Drawable {
    private final RegularGameVariant regularGameVariant;
    private final TutorialGameVariant tutorialGameVariant;
    private GameVariantImplementation currentGameVariant;
    
    public GameVariant(Snapshotter snapshotter, List<List<Hexagon>> hexagons, Cleaner cleaner) {
        regularGameVariant = new RegularGameVariant(snapshotter);
        tutorialGameVariant = new TutorialGameVariant(hexagons, cleaner);
        currentGameVariant = regularGameVariant;
    }
    
    @Override
    public void update(float dt) {
        currentGameVariant.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(currentGameVariant);
    }
    
    public List<List<DiamondColor>> buildSpawningDiamonds(List<List<Hexagon>> spawningHexagons) {
        return currentGameVariant.buildSpawningDiamonds(spawningHexagons);
    }
    
    public Hexagon getNextGrabbedDiamondOrigin() {
        return currentGameVariant.getNextGrabbedDiamondOrigin();
    }
    
    public Hexagon getNextGrabbedDiamondDestination() {
        return currentGameVariant.getNextGrabbedDiamondDestination();
    }
    
    public void notifyPlayerMoveStarted() {
        currentGameVariant.notifyPlayerMoveStarted();
    }
    
    public void notifyPlayerMoveSucceeded() {
        currentGameVariant.notifyPlayerMoveSucceeded();
    }
    
    public void requestSnapshot() {
        currentGameVariant.requestSnapshot();
    }
    
    public void playRegularGame() {
        currentGameVariant = regularGameVariant;
    }
    
    public void playTutorialGame() {
        currentGameVariant = tutorialGameVariant;
    }
    
    public boolean isTutorialGame() {
        return currentGameVariant == tutorialGameVariant;
    }
    
    public void stop() {
        currentGameVariant.stop();
    }
    
    public boolean switchStateToMenu() {
        return currentGameVariant.switchStateToMenu();
    }
}
