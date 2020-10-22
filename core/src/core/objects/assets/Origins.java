package core.objects.assets;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;
import tools.*;
import tools.component.graphic.*;

class Origins {
    private final Map<String, Origin> origins = new HashMap<String, Origin>();
    
    Origins() {
        origins.put("symbolPlay", Origin.leftTriangleCenter);
        origins.put("popUpBack", new DefaultOrigin(0.47569f, 0.53131f));
        origins.put("arrowLine", Origin.leftCenter);
        origins.put("arrowHead", Origin.rightCenter);
        for (DiamondColor diamondColor : DiamondColor.values()) {
            String color = Tools.capitalize(diamondColor.name());
            origins.put("diamond" + color + "Triangle0", Origin.lowerTriangleCenter);
            origins.put("diamond" + color + "Triangle1", Origin.upperTriangleCenter);
            origins.put("diamond" + color + "Triangle2", Origin.lowerTriangleCenter);
            origins.put("diamond" + color + "Triangle3", Origin.upperTriangleCenter);
            origins.put("diamond" + color + "Triangle4", Origin.lowerTriangleCenter);
            origins.put("diamond" + color + "Triangle5", Origin.upperTriangleCenter);
        }
    }
    
    Origin getOrigin(String string) {
        Origin origin = origins.get(string);
        return origin == null ? Origin.center : origin;
    }
}
