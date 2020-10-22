package gameImplementation.board.boardState.playerBoardState.movingPack;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.boardState.playerBoardState.furtherHexagon.*;
import gameImplementation.board.hexagonAndDiamond.*;
import tools.common.*;
import tools.screen.*;

public class MovingDiamonds implements Drawable {
    private final List<HexagonDiamondPair> pairs = new ArrayList<HexagonDiamondPair>();
    
    public void update(float dt) {
        for (HexagonDiamondPair hexagonDiamondPair : pairs) hexagonDiamondPair.diamond.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        for (HexagonDiamondPair hexagonDiamondPair : pairs) {
            screen.draw(hexagonDiamondPair.diamond);
        }
    }
    
    public void add(HexagonDiamondPair pair) {
        pairs.add(pair);
    }
    
    public void setCenter(Hexagon hexagon, Vector2 center) {
        HexagonDiamondPair hexagonDiamondPair = getPairIterator(hexagon).previous();
        hexagonDiamondPair.diamond.physicComponent.setCenter(center);
    }
    
    public Diamond remove(Hexagon hexagon) {
        ListIterator<HexagonDiamondPair> iterator = getPairIterator(hexagon);
        HexagonDiamondPair hexagonDiamondPair = iterator.previous();
        iterator.remove();
        return hexagonDiamondPair.diamond;
    }
    
    public void clear(FurtherHexagon furtherHexagon, List<HexagonDiamondPair> p) {
        Hexagon hexagon = p.get(0).hexagon;
        for (HexagonDiamondPair pair : pairs) {
            hexagon = furtherHexagon.further(hexagon);
            p.add(new HexagonDiamondPair(hexagon, pair.diamond));
        }
        pairs.clear();
    }
    
    private ListIterator<HexagonDiamondPair> getPairIterator(Hexagon hexagon) {
        ListIterator<HexagonDiamondPair> iterator = pairs.listIterator();
        while (iterator.hasNext()) {
            HexagonDiamondPair hexagonDiamondPair = iterator.next();
            if (hexagonDiamondPair.hexagon == hexagon) return iterator;
        }
        throw new java.lang.IllegalStateException("Hexagon not found");
    }
    
    List<Whole> clear() {
        return HexagonDiamondPair.toWholes(pairs);
    }
}
