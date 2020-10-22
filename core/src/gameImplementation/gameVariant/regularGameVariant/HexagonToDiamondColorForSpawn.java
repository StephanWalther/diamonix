package gameImplementation.gameVariant.regularGameVariant;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;

class HexagonToDiamondColorForSpawn extends HexagonToDiamondColor {
    Hexagon hexagon;
    List<List<HexagonDiamondColorPair>> diamondColorsToSpawn;
    DiamondColor diamondColor;
    
    @Override
    protected DiamondColor getDiamondColor(Hexagon hexagon) {
        if (this.hexagon == hexagon) return diamondColor;
        for (List<HexagonDiamondColorPair> spawningDiamondLine : diamondColorsToSpawn) {
            for (HexagonDiamondColorPair spawningDiamond : spawningDiamondLine) {
                if (spawningDiamond.hexagon == hexagon) return spawningDiamond.diamondColor;
            }
        }
        return null;
    }
}
