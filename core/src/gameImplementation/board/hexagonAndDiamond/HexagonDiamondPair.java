package gameImplementation.board.hexagonAndDiamond;

import java.util.*;

import tools.common.*;

public class HexagonDiamondPair {
    public static List<Whole> toWholes(List<HexagonDiamondPair> pairs) {
        List<Whole> wholes = new ArrayList<Whole>(pairs.size());
        for (HexagonDiamondPair pair : pairs) wholes.add(pair.diamond);
        return wholes;
    }
    
    public final Hexagon hexagon;
    public final Diamond diamond;
    
    public HexagonDiamondPair(Hexagon hexagon, Diamond diamond) {
        this.hexagon = hexagon;
        this.diamond = diamond;
    }
}
