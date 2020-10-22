package gameImplementation.points;

import core.*;
import core.objects.audio.*;
import core.objects.screen.*;
import misc.*;
import tools.common.*;
import tools.component.*;
import tools.component.graphic.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.Screen;

class BouncingPoints implements Drawable {
    private final Text text = new Text();
    private final Mapping scaleMapping = Concatenation.linear(
      0.2f, 1.f, PositiveSinWave.halfPeriod);
    private final Timer popUpTimer = new Timer(0.1f);
    private final Timer pointSetTimer = new Timer(Constants.SPAWN_TIME).increase();
    private int points = 0;
    private final ProgressLineInfo spawnProgressLineInfo;
    private final ProgressLineInfo gameEndVisualsProgressLineInfo;
    private final LimitedSound sound = Sounds.get("pointBounce", 0.03f);
    
    BouncingPoints(ProgressLineInfo spawnProgressLineInfo,
                   ProgressLineInfo gameEndVisualsProgressLineInfo) {
        text.physicComponent.setCenter(SizeGetter.getIdealWorldWidth()*0.285f,
          SizeGetter.getIdealWorldHeight()*0.425f);
        text.colorComponent.setColorA(0);
        text.textComponent.setOrigin(Origin.rightCenter);
        text.textComponent.setString(Integer.toString(points));
        text.textComponent.setFont(Fonts.get("medium"));
        pointSetTimer.setTime(pointSetTimer.getEndTime());
        this.spawnProgressLineInfo = spawnProgressLineInfo;
        this.gameEndVisualsProgressLineInfo = gameEndVisualsProgressLineInfo;
    }
    
    public void update(float dt) {
        updatePopUpTimer(dt);
        updatePointSetTimer(dt);
        float popUpScale = scaleMapping.calculate(popUpTimer.getProgress());
        float spawnScale = Mapping.scaleUp.calculate(spawnProgressLineInfo.getProgress());
        float pointSetScale = Mapping.scaleUp.calculate(pointSetTimer.getProgress());
        text.physicComponent.setScale(popUpScale*spawnScale*pointSetScale);
        text.colorComponent.setColorA(PositiveSinWave.halfPeriod.calculate(
          spawnProgressLineInfo.getProgress()*pointSetTimer.getProgress()));
        sound.update(dt);
    }
    
    private void updatePopUpTimer(float dt) {
        popUpTimer.update(dt);
        if (popUpTimer.getTime() == popUpTimer.getEndTime()) {
            popUpTimer.decrease();
            updateString();
        }
    }
    
    private void updatePointSetTimer(float dt) {
        if (gameEndVisualsProgressLineInfo.getProgress()
              > Constants.GAME_END_VISUALS_PROGRESS_TILL_OTHER_CHANGES) return;
        pointSetTimer.update(dt);
        if (pointSetTimer.getTime() == 0) {
            pointSetTimer.increase();
            updateString();
        }
    }
    
    private void updateString() {
        text.textComponent.setString(Integer.toString(points));
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(text);
    }
    
    void setPoints(int points) {
        if (this.points == points) return;
        this.points = points;
        if (spawnProgressLineInfo.getProgress() == 0) updateString();
        else pointSetTimer.decrease();
    }
    
    void addPoints(int points) {
        this.points += points;
        popUpTimer.increase();
        sound.play();
    }
    
    int getPoints() {
        return points;
    }
    
    void stop() {
        popUpTimer.decrease();
    }
}
