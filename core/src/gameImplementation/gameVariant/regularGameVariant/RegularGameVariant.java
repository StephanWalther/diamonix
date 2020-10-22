package gameImplementation.gameVariant.regularGameVariant;

import java.util.*;

import core.objects.snapshot.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.gameVariant.*;
import tools.screen.*;

public class RegularGameVariant implements GameVariantImplementation {
    private final SpawningDiamondsBuilder spawningDiamondsBuilder = new SpawningDiamondsBuilder();
    private final Snapshotter snapshotter;
    
    public RegularGameVariant(Snapshotter snapshotter) {
        this.snapshotter = snapshotter;
    }
    
    @Override
    public void update(float dt) {}
    
    @Override
    public void draw(Screen screen) {}
    
    @Override
    public List<List<DiamondColor>> buildSpawningDiamonds(List<List<Hexagon>> spawningHexagons) {
        return spawningDiamondsBuilder.build(spawningHexagons);
    }
    
    @Override
    public Hexagon getNextGrabbedDiamondDestination() {
        return null;
    }
    
    @Override
    public Hexagon getNextGrabbedDiamondOrigin() {
        return null;
    }
    
    @Override
    public void notifyPlayerMoveStarted() {}
    
    @Override
    public void notifyPlayerMoveSucceeded() {}
    
    @Override
    public void requestSnapshot() {
        snapshotter.createSnapshot();
    }
    
    @Override
    public void stop() {}
    
    @Override
    public boolean switchStateToMenu() {
        return false;
    }
}
