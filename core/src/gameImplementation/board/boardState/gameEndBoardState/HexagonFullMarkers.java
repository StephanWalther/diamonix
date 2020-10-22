package gameImplementation.board.boardState.gameEndBoardState;

import com.badlogic.gdx.math.*;

import java.util.*;

import core.*;
import gameImplementation.board.hexagonAndDiamond.*;
import misc.*;
import tools.common.*;
import tools.component.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

class HexagonFullMarkers implements Drawable {
    private final Sprite sprite = new Sprite();
    private final List<Vector2> positions = new ArrayList<Vector2>();
    private final tools.progressLine.Timer timer = new tools.progressLine.Timer(1.f).increase();
    private final SequentialProgressLine sequentialProgressLine
      = new SequentialProgressLine().increase().setTimeBetweenIncreasesAndDecreases(0.2f);
    private final List<ProgressLineInfo> progressLineInfo = new ArrayList<ProgressLineInfo>();
    private final Mapping scaleMapping = Concatenation.linear(0.75f, 0.5f, Polynomial.quadratic);
    
    HexagonFullMarkers() {
        sprite.textureComponent.setTextureOrigin(TextureOrigins.get("mark"));
        for (int i = 0; i < 8; i++) {
            progressLineInfo.add(sequentialProgressLine
                                   .absorb(new tools.progressLine.Timer(Constants.MARKER_TIME)));
        }
    }
    
    void update(float dt) {
        sequentialProgressLine.update(dt);
        if (sequentialProgressLine.getProgress() == 1.f) {
            timer.update(dt);
            if (timer.getTime() == timer.getEndTime()) timer.setTime(0);
        }
    }
    
    @Override
    public void draw(final Screen screen) {
        doForAllSpriteConfigurations(new Action() {
            @Override
            public void perform() {
                screen.draw(sprite);
            }
        });
    }
    
    void newMarkers(List<Hexagon> spawningHexagonsWithDiamond) {
        positions.clear();
        for (Hexagon hexagon : spawningHexagonsWithDiamond) {
            positions.add(hexagon.getPhysicComponent().getCenter());
        }
    }
    
    List<Whole> stop() {
        final List<Whole> wholes = new ArrayList<Whole>(positions.size());
        doForAllSpriteConfigurations(new Action() {
            @Override
            public void perform() {
                wholes.add(new Sprite(sprite));
            }
        });
        timer.setTime(0);
        sequentialProgressLine.setProgress(0);
        return wholes;
    }
    
    private void doForAllSpriteConfigurations(Action action) {
        for (Vector2 position : positions) {
            updateSpriteAndCallAction(position, timer.getProgress(), action);
            for (ProgressLineInfo progressLineInfo : progressLineInfo) {
                updateSpriteAndCallAction(position, progressLineInfo.getProgress(), action);
            }
        }
    }
    
    private void updateSpriteAndCallAction(Vector2 position, float progress, Action action) {
        sprite.physicComponent.setCenter(position);
        sprite.physicComponent.setScale(scaleMapping.calculate(progress));
        sprite.colorComponent.setColorA(PositiveSinWave.fullPeriod.calculate(progress));
        action.perform();
    }
}
