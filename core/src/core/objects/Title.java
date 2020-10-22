package core.objects;

import core.objects.screen.*;
import core.*;
import misc.*;
import tools.common.*;
import tools.component.*;
import tools.mapping.*;
import tools.progressLine.*;
import tools.screen.Screen;

public class Title implements Updatable, Drawable {
    private final Text text = new Text();
    private final Timer spawnTimer = new Timer(Constants.SPAWN_TIME).increase();
    
    public Title() {
        text.physicComponent.setCenter(0, SizeGetter.getIdealWorldHeight()*0.4f);
        text.colorComponent.setColorA(0);
        text.textComponent.setString("DIAMONIX");
        text.textComponent.setFont(Fonts.get("large"));
    }
    
    @Override
    public void update(float dt) {
        spawnTimer.update(dt);
        float spawnProgress = spawnTimer.getProgress();
        text.physicComponent.setScale(Mapping.scaleUp.calculate(spawnProgress));
        text.colorComponent.setColorA(PositiveSinWave.halfPeriod.calculate(spawnProgress));
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(text);
    }
    
    public void spawn() {
        spawnTimer.increase();
    }
    
    public void despawn() {
        spawnTimer.decrease();
    }
}
