package gameImplementation.gameVariant.tutorialGameVariant;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;

class PredefinedSpawningDiamonds {
    private final List<DiamondColor> predefinedDiamondColors = new ArrayList<DiamondColor>();
    private int current = 0;
    
    PredefinedSpawningDiamonds() {
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.yellow);
        predefinedDiamondColors.add(DiamondColor.blue);
        
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.green);
        predefinedDiamondColors.add(DiamondColor.yellow);
        
        predefinedDiamondColors.add(DiamondColor.green);
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.green);
        
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.yellow);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.green);
        
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.green);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.blue);
        
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.green);
        predefinedDiamondColors.add(DiamondColor.yellow);
        predefinedDiamondColors.add(DiamondColor.yellow);
        
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.green);
        predefinedDiamondColors.add(DiamondColor.green);
        predefinedDiamondColors.add(DiamondColor.red);
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.yellow);
    
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.blue);
        predefinedDiamondColors.add(DiamondColor.blue);
    }
    
    List<List<DiamondColor>> build(List<List<Hexagon>> spawningHexagons) {
        List<List<DiamondColor>> diamondColors
          = new ArrayList<List<DiamondColor>>(spawningHexagons.size());
        for (List<Hexagon> shs : spawningHexagons) {
            List<DiamondColor> dcs = new ArrayList<DiamondColor>(spawningHexagons.size());
            for (int i = 0; i < shs.size(); i++) {
                dcs.add(predefinedDiamondColors.get(current + i));
            }
            current += shs.size();
            diamondColors.add(dcs);
        }
        return diamondColors;
    }
    
    boolean isBuildNotPossible() {
        return current == predefinedDiamondColors.size();
    }
    
    void reset() {
        current = 0;
    }
}
