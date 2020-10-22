package gameImplementation.gameVariant.tutorialGameVariant;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import tools.screen.*;

public class TutorialGameVariant implements GameVariantImplementation {
    private final PredefinedSpawningDiamonds predefinedSpawningDiamonds
      = new PredefinedSpawningDiamonds();
    private final GrabbedDiamondController grabbedDiamondController;
    private final Arrows arrows;
    
    public TutorialGameVariant(List<List<Hexagon>> hexagons, Cleaner cleaner) {
        grabbedDiamondController = new GrabbedDiamondController(hexagons);
        arrows = new Arrows(cleaner);
    }
    
    @Override
    public void update(float dt) {
        arrows.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(arrows);
    }
    
    @Override
    public void requestSnapshot() {}
    
    @Override
    public List<List<DiamondColor>> buildSpawningDiamonds(List<List<Hexagon>> spawningHexagons) {
        return predefinedSpawningDiamonds.build(spawningHexagons);
    }
    
    @Override
    public Hexagon getNextGrabbedDiamondOrigin() {
        return grabbedDiamondController.getNextGrabbedDiamondOrigin();
    }
    
    @Override
    public Hexagon getNextGrabbedDiamondDestination() {
        return grabbedDiamondController.getNextGrabbedDiamondDestination();
    }
    
    @Override
    public void notifyPlayerMoveStarted() {
        setNextArrow();
    }
    
    @Override
    public void notifyPlayerMoveSucceeded() {
        grabbedDiamondController.notifyPlayerMoveSucceeded();
        arrows.removeArrow();
    }
    
    private void setNextArrow() {
        Vector2 arrowStart = grabbedDiamondController.getNextGrabbedDiamondOrigin()
                               .getPhysicComponent().getCenter();
        Vector2 arrowEnd = grabbedDiamondController.getNextGrabbedDiamondDestination()
                             .getPhysicComponent().getCenter();
        arrows.newArrow(arrowStart, arrowEnd);
    }
    
    @Override
    public void stop() {
        predefinedSpawningDiamonds.reset();
        grabbedDiamondController.reset();
        arrows.removeArrow();
    }
    
    @Override
    public boolean switchStateToMenu() {
        return predefinedSpawningDiamonds.isBuildNotPossible();
    }
}
