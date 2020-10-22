package gameImplementation;

import java.util.*;

import tools.common.*;
import tools.misc.*;
import tools.progressLine.*;
import tools.screen.*;

public class Cleaner implements Updatable, Drawable {
    private final ProgressLineInfo progressLineInfo;
    private final List<CleanPack> cleanPacks = new ArrayList<CleanPack>();
    
    Cleaner(ProgressLineInfo progressLineInfo) {
        this.progressLineInfo = progressLineInfo;
    }
    
    @Override
    public void update(float dt) {
        UpdateDistributer.updateAll(cleanPacks, dt);
        Remover.removeAllDoneElements(cleanPacks);
    }
    
    @Override
    public void draw(Screen screen) {
        DrawDistributer.drawAll(cleanPacks, screen);
    }
    
    public void clean(List<Whole> wholes) {
        if (wholes == null) return;
        for (Whole whole : wholes) clean(whole);
    }
    
    public void clean(Whole whole) {
        if (whole == null) return;
        cleanPacks.add(new CleanPack(whole, progressLineInfo));
    }
}
