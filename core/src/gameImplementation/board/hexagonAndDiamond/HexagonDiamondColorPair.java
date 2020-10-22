package gameImplementation.board.hexagonAndDiamond;

import java.util.*;

public class HexagonDiamondColorPair {
    public static List<List<DiamondColor>> toDiamondColors(
      List<List<HexagonDiamondColorPair>> hexagonDiamondColorPairs) {
        List<List<DiamondColor>> diamondColors
          = new ArrayList<List<DiamondColor>>(hexagonDiamondColorPairs.size());
        for (List<HexagonDiamondColorPair> hdcps : hexagonDiamondColorPairs) {
            List<DiamondColor> dcs = new ArrayList<DiamondColor>(hdcps.size());
            for (HexagonDiamondColorPair hdcp : hdcps) dcs.add(hdcp.diamondColor);
            diamondColors.add(dcs);
        }
        return diamondColors;
    }
    
    public final Hexagon hexagon;
    public final DiamondColor diamondColor;
    
    public HexagonDiamondColorPair(Hexagon hexagon, DiamondColor diamondColor) {
        this.hexagon = hexagon;
        this.diamondColor = diamondColor;
    }
}
