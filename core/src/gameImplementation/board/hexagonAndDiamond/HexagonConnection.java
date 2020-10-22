package gameImplementation.board.hexagonAndDiamond;

import java.util.*;

public class HexagonConnection {
    private final HexagonToDiamondColor hexagonToDiamondColor;
    
    public HexagonConnection(HexagonToDiamondColor hexagonToDiamondColor) {
        this.hexagonToDiamondColor = hexagonToDiamondColor;
    }
    
    public List<Hexagon> getConnectedHexagons(Hexagon hexagon) {
        List<Hexagon> connectedHexagons = new ArrayList<Hexagon>();
        checkHexagon(hexagon, connectedHexagons);
        return connectedHexagons;
    }
    
    private void checkHexagon(Hexagon hexagon, List<Hexagon> connectedHexagons) {
        connectedHexagons.add(hexagon);
        DiamondColor diamondColor = hexagonToDiamondColor.getDiamondColor(hexagon);
        if (diamondColor == null) return;
        checkConnection(diamondColor, hexagon.upperLeft, connectedHexagons);
        checkConnection(diamondColor, hexagon.upper, connectedHexagons);
        checkConnection(diamondColor, hexagon.upperRight, connectedHexagons);
        checkConnection(diamondColor, hexagon.lowerLeft, connectedHexagons);
        checkConnection(diamondColor, hexagon.lower, connectedHexagons);
        checkConnection(diamondColor, hexagon.lowerRight, connectedHexagons);
    }
    
    private void checkConnection(DiamondColor diamondColor, Hexagon hexagon,
                                 List<Hexagon> connectedHexagons) {
        if (notConnectedAndColorMatch(diamondColor, hexagon, connectedHexagons)) {
            checkHexagon(hexagon, connectedHexagons);
        }
    }
    
    private boolean notConnectedAndColorMatch(DiamondColor diamondColor, Hexagon hexagon,
                                              List<Hexagon> connectedHexagons) {
        if (hexagon == null) return false;
        if (connectedHexagons.contains(hexagon)) return false;
        return hexagonToDiamondColor.getDiamondColor(hexagon) == diamondColor;
    }
}
