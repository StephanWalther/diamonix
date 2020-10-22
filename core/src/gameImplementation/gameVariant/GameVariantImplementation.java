package gameImplementation.gameVariant;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;
import tools.common.*;

public interface GameVariantImplementation extends Updatable, Drawable {
    List<List<DiamondColor>> buildSpawningDiamonds(List<List<Hexagon>> spawningHexagons);
    
    Hexagon getNextGrabbedDiamondOrigin();
    
    Hexagon getNextGrabbedDiamondDestination();
    
    void notifyPlayerMoveStarted();
    
    void notifyPlayerMoveSucceeded();
    
    void requestSnapshot();
    
    void stop();
    
    boolean switchStateToMenu();
}
