package gameImplementation.board.grid;

import java.util.*;

import core.objects.screen.*;
import core.*;
import gameImplementation.board.hexagonAndDiamond.*;

class GridBuilder {
    private static final int gridWidth = 5; // MUST BE ODD!
    private static final int gridHeight = 6;
    private static final float hexagonEffectiveWidth
      = 0.99f*TextureOrigins.get("hexagon").textureRegion.getRegionWidth()*3/4;
    private static final float hexagonHeight
      = 0.99f*TextureOrigins.get("hexagon").textureRegion.getRegionHeight();
    private static List<List<Hexagon>> hexagons;
    
    private GridBuilder() {}
    
    static List<List<Hexagon>> build() {
        hexagons = new ArrayList<List<Hexagon>>(gridWidth);
        float centerY = -0.05f*SizeGetter.getIdealWorldHeight();
        for (int i = 0; i < gridWidth; i++) hexagons.add(assembleLine(i, centerY));
        return hexagons;
    }
    
    private static List<Hexagon> assembleLine(int lineNumber, float centerY) {
        int additionalHexagons = lineNumber > (gridWidth - 1)/2 ?
                                   gridWidth - 1 - lineNumber : lineNumber;
        List<Hexagon> line = new ArrayList<Hexagon>(gridHeight + additionalHexagons);
        float centerX = (lineNumber - (gridWidth - 1)*0.5f)*hexagonEffectiveWidth;
        centerY += (gridHeight + additionalHexagons - 1)*0.5f*hexagonHeight;
        for (int i = 0; i < gridHeight + additionalHexagons; i++) {
            Hexagon hexagon = new Hexagon(centerX, centerY);
            line.add(hexagon);
            hexagon.upperLeft = getUpperLeftHexagon(i);
            if (hexagon.upperLeft != null) hexagon.upperLeft.lowerRight = hexagon;
            if (i > 0) {
                hexagon.upper = line.get(line.size() - 2);
                hexagon.upper.lower = hexagon;
            }
            hexagon.lowerLeft = getLowerLeftHexagon(i);
            if (hexagon.lowerLeft != null) hexagon.lowerLeft.upperRight = hexagon;
            centerY -= hexagonHeight;
        }
        return line;
    }
    
    private static Hexagon getUpperLeftHexagon(int hexagonNumber) {
        if (hexagons.isEmpty()) return null;
        List<Hexagon> previousLine = hexagons.get(hexagons.size() - 1);
        if (hexagons.size() < (gridWidth + 1)*0.5f) {
            if (hexagonNumber > 0) return previousLine.get(hexagonNumber - 1);
        } else {
            return previousLine.get(hexagonNumber);
        }
        return null;
    }
    
    private static Hexagon getLowerLeftHexagon(int hexagonNumber) {
        if (hexagons.isEmpty()) return null;
        List<Hexagon> previousLine = hexagons.get(hexagons.size() - 1);
        if (hexagons.size() < (gridWidth + 1)*0.5f) {
            if (hexagonNumber < previousLine.size()) return previousLine.get(hexagonNumber);
        } else {
            return previousLine.get(hexagonNumber + 1);
        }
        return null;
    }
}
