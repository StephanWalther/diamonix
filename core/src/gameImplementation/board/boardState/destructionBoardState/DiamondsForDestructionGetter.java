package gameImplementation.board.boardState.destructionBoardState;

import java.util.*;

import gameImplementation.board.grid.*;
import gameImplementation.board.grid.hexagonSelector.*;
import gameImplementation.board.hexagonAndDiamond.*;
import tools.*;

class DiamondsForDestructionGetter {
    private final HexagonConnection hexagonConnection
      = new HexagonConnection(new HexagonToDiamondColor());
    
    List<Hexagon> getDiamondsForDestruction(Grid grid) {
        List<Hexagon> hexagons = Tools.toSingleList(grid.selectHexagons(WithDiamond.instance));
        final float connectionNumber = 4;
        List<Hexagon> hexagonsWithDiamondsForDestruction = new ArrayList<Hexagon>(hexagons.size());
        while (!hexagons.isEmpty()) {
            List<Hexagon> connectedHexagons
              = hexagonConnection.getConnectedHexagons(hexagons.remove(0));
            if (connectedHexagons.size() >= connectionNumber) {
                hexagonsWithDiamondsForDestruction.addAll(connectedHexagons);
            }
            hexagons.removeAll(connectedHexagons);
        }
        return mix(hexagonsWithDiamondsForDestruction);
    }
    
    private List<Hexagon> mix(final List<Hexagon> hexagons) {
        List<Hexagon> mixedHexagons = new ArrayList<Hexagon>(hexagons.size());
        Random random = new Random();
        while (!hexagons.isEmpty()) {
            mixedHexagons.add(hexagons.remove(random.nextInt(hexagons.size())));
        }
        return mixedHexagons;
    }
}
