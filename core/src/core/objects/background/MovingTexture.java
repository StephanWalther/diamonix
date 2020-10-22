package core.objects.background;

import com.badlogic.gdx.math.*;

import java.util.*;

import core.*;
import core.objects.screen.*;
import tools.common.*;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.progressLine.Timer;
import tools.screen.Screen;

class MovingTexture implements Drawable {
    private final Rectangle movingRectangle = new Rectangle();
    private final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    private final TextureComponent textureComponent = new TextureComponent(
      physicComponent, EasyColorComponent.WHITE, TextureOrigins.get("background"));
    private final Timer movingTimer = new Timer().setEndTime(0).increase();
    private Mapping mappingX;
    private Mapping mappingY;
    
    void resize(int width, int height) {
        float movingWidth = textureComponent.getTextureRegion().getRegionWidth() - width;
        float movingHeight = textureComponent.getTextureRegion().getRegionHeight() - height;
        movingRectangle.set(-0.5f*movingWidth, -0.5f*movingHeight,
          movingWidth, movingHeight);
    }
    
    void update(float dt) {
        movingTimer.update(dt);
        if (movingTimer.getTime() == movingTimer.getEndTime()) setNextMovingPoint();
        physicComponent.setCenter(mappingX.calculate(movingTimer.getProgress()),
          mappingY.calculate(movingTimer.getProgress()));
    }
    
    private void setNextMovingPoint() {
        Vector2 nextMovingPoint = calculateNextMovingPoint();
        Vector2 currentMovingPoint = physicComponent.getCenter();
        float dist = Vector2.dst(nextMovingPoint.x, nextMovingPoint.y, currentMovingPoint.x,
          currentMovingPoint.y);
        float desiredVelocity = SizeGetter.getIdealWorldWidth()*0.015f;
        movingTimer.setTime(0);
        movingTimer.setEndTime(dist/desiredVelocity);
        mappingX = new Polynomial(nextMovingPoint.x - currentMovingPoint.x, currentMovingPoint.x);
        mappingY = new Polynomial(nextMovingPoint.y - currentMovingPoint.y, currentMovingPoint.y);
    }
    
    private Vector2 calculateNextMovingPoint() {
        Random random = new Random();
        float randomWidth = movingRectangle.width*random.nextInt(100)*0.01f;
        float randomHeight = movingRectangle.height*random.nextInt(100)*0.01f;
        return new Vector2(movingRectangle.x + randomWidth, movingRectangle.y + randomHeight);
    }
    
    @Override
    public void draw(Screen screen) {
        screen.draw(textureComponent);
    }
}
