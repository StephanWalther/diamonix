package gameImplementation.points;

import com.badlogic.gdx.math.*;

import core.*;
import core.objects.screen.*;
import tools.common.*;
import tools.component.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.Screen;

public class Points implements Drawable {
    private final Sprite symbol = new Sprite();
    private final BouncingPoints bouncingPoints;
    private final ProgressLineInfo spawnProgressLineInfo;
    
    public Points(ProgressLineInfo spawnProgressLineInfo,
                  ProgressLineInfo gameEndVisualsProgressLineInfo) {
        symbol.physicComponent.setCenter(SizeGetter.getIdealWorldWidth()*0.375f,
          SizeGetter.getIdealWorldHeight()*0.425f);
        symbol.textureComponent.setTextureOrigin(TextureOrigins.get("star"));
        symbol.colorComponent.setColorA(0);
        this.spawnProgressLineInfo = spawnProgressLineInfo;
        bouncingPoints = new BouncingPoints(spawnProgressLineInfo, gameEndVisualsProgressLineInfo);
    }
    
    public void update(float dt) {
        float timeProgress = spawnProgressLineInfo.getProgress();
        symbol.physicComponent.setScale(Mapping.scaleUp.calculate(timeProgress));
        symbol.colorComponent.setColorA(PositiveSinWave.halfPeriod.calculate(timeProgress));
        bouncingPoints.update(dt);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(symbol);
        screen.draw(bouncingPoints);
    }
    
    public Vector2 getPointDestination() {
        return symbol.physicComponent.getCenter();
    }
    
    public void setPoints(int points) {
        bouncingPoints.setPoints(points);
    }
    
    public void addPoints(int points) {
        bouncingPoints.addPoints(points);
    }
    
    public int getPoints() {
        return bouncingPoints.getPoints();
    }
    
    public void stop() {
        bouncingPoints.stop();
    }
}
