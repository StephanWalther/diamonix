package gameImplementation.effects.pointNumbers;

import com.badlogic.gdx.math.*;

import core.*;
import misc.*;
import tools.common.*;
import tools.component.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.*;

public class PointNumber implements Updatable, Drawable, Removable {
    private final Timer timer = new Timer(0.75f).increase();
    final Text text = new Text();
    
    public PointNumber(Vector2 center, int pointCount) {
        text.physicComponent.setCenter(center);
        text.colorComponent.setColorA(0);
        text.textComponent.setString(Integer.toString(pointCount));
        text.textComponent.setFont(Fonts.get("medium"));
    }
    
    @Override
    public void update(float dt) {
        timer.update(dt);
        float timeProgress = timer.getProgress();
        float scale = new Concatenation(new Polynomial(0.5f, 0.5f),
          Polynomial.negativeQuadraticNull).calculate(timeProgress);
        float alpha = PositiveSinWave.fullPeriod.calculate(timeProgress);
        text.physicComponent.setScale(scale);
        text.colorComponent.setColorA(alpha);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(text);
    }
    
    @Override
    public boolean isDone() {
        return timer.getTime() == timer.getEndTime();
    }
}
