package gameImplementation.board.boardState.destructionBoardState;

import java.util.*;

import core.*;
import gameImplementation.board.hexagonAndDiamond.*;
import misc.*;
import tools.common.*;
import tools.component.*;
import tools.mapping.*;
import tools.progressLine.Timer;
import tools.screen.*;

class Marker implements Drawable {
    private final List<Sprite> marks = new ArrayList<Sprite>();
    private final Timer timer = new Timer(Constants.MARKER_TIME);
    
    void mark(List<Hexagon> diamondsForDestruction) {
        for (Hexagon hexagon : diamondsForDestruction) {
            Sprite sprite = new Sprite();
            sprite.physicComponent.setCenter(hexagon.getPhysicComponent().getCenter());
            sprite.colorComponent.setColorA(0);
            sprite.textureComponent.setTextureOrigin(TextureOrigins.get("mark"));
            marks.add(sprite);
        }
    }
    
    void increase() {
        timer.increase();
    }
    
    boolean isMarked() {
        return timer.getProgress() == 1;
    }
    
    void decrease() {
        timer.decrease();
    }
    
    boolean isNotMarked() {
        return timer.getTime() == 0;
    }
    
    void update(float dt) {
        timer.update(dt);
        if (timer.getTime() == 0) marks.clear();
        else {
            float scale = Mapping.scaleDown.calculate(timer.getProgress());
            float alpha = PositiveSinWave.halfPeriod.calculate(timer.getProgress());
            for (Sprite mark : marks) {
                mark.physicComponent.setScale(scale);
                mark.colorComponent.setColorA(alpha);
            }
        }
    }
    
    @Override
    public void draw(Screen screen) {
        DrawDistributer.drawAll(marks, screen);
    }
    
    List<Whole> reset() {
        List<Whole> wholes = new ArrayList<Whole>(marks);
        marks.clear();
        timer.decrease().setTime(0);
        return wholes;
    }
}
