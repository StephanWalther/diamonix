package gameImplementation.effects.pointNumbers;

import java.util.*;

import tools.common.*;
import tools.screen.*;

public class PointNumbers implements Drawable {
    private final List<PointNumber> pointNumbers = new ArrayList<PointNumber>();
    
    public void addPointNumber(PointNumber pointNumber) {
        pointNumbers.add(pointNumber);
    }
    
    public void update(float dt) {
        UpdateDistributer.updateAll(pointNumbers, dt);
        Remover.removeAllDoneElements(pointNumbers);
    }
    
    @Override
    public void draw(Screen screen) {
        DrawDistributer.drawAll(pointNumbers, screen);
    }

    public List<Whole> stop() {
        List<Whole> wholes = new ArrayList<Whole>(pointNumbers.size());
        for (PointNumber pointNumber : pointNumbers) wholes.add(pointNumber.text);
        pointNumbers.clear();
        return wholes;
    }
}
