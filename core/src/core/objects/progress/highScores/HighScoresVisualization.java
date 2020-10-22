package core.objects.progress.highScores;

import com.badlogic.gdx.graphics.g2d.*;

import java.util.*;

import tools.common.*;
import tools.component.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.screen.*;

public class HighScoresVisualization implements Drawable {
    private final HighScores highScores;
    private final List<TwoColumnText> twoColumnTexts;
    private final List<DefaultPhysicComponent> defaultPhysicComponents;
    
    HighScoresVisualization(HighScores highScores, List<TwoColumnText> twoColumnTexts,
                            List<DefaultPhysicComponent> defaultPhysicComponents) {
        this.highScores = highScores;
        this.twoColumnTexts = twoColumnTexts;
        this.defaultPhysicComponents = defaultPhysicComponents;
    }
    
    @Override
    public void draw(Screen screen) {
        for (TwoColumnText twoColumnText : twoColumnTexts) screen.draw(twoColumnText);
    }
    
    public void refreshStrings() {
        List<Integer> recordList = highScores.getHighScores();
        for (int i = 0; i < twoColumnTexts.size(); i++) {
            twoColumnTexts.get(i).setRightString(Integer.toString(recordList.get(i)));
        }
    }
    
    public void setFontForAll(BitmapFont font) {
        for (TwoColumnText twoColumnText : twoColumnTexts) twoColumnText.setFont(font);
    }
    
    public void setFont(BitmapFont font, int i) {
        twoColumnTexts.get(i).setFont(font);
    }
    
    public void setColumnDistForAll(float columnDist) {
        for (TwoColumnText twoColumnText : twoColumnTexts) twoColumnText.setColumnDist(columnDist);
    }
    
    public void setColumnDist(float columnDist, int i) {
        twoColumnTexts.get(i).setColumnDist(columnDist);
    }
    
    public void setCenterXForAll(float centerX) {
        for (DefaultPhysicComponent defaultPhysicComponent : defaultPhysicComponents) {
            defaultPhysicComponent.setCenterX(centerX);
        }
    }
    
    public void setCenterX(float centerX, int i) {
        defaultPhysicComponents.get(i).setCenterX(centerX);
    }
    
    public void verticalAlign(float startCenterY, float defaultDistCenterY,
                              Map<Integer, Float> specialDistCenterY) {
        float currentCenterY = startCenterY;
        for (int i = 0; i < twoColumnTexts.size(); i++) {
            defaultPhysicComponents.get(i).setCenterY(currentCenterY);
            if (specialDistCenterY != null &&
                  specialDistCenterY.containsKey(i)) currentCenterY -= specialDistCenterY.get(i);
            else currentCenterY -= defaultDistCenterY;
        }
    }
    
    public void setScale(float scale, int i) {
        defaultPhysicComponents.get(i).setScale(scale);
    }
    
    public void setOrigin(Origin origin, int i) {
        twoColumnTexts.get(i).setOrigin(origin);
    }
    
    public int size() {
        return twoColumnTexts.size();
    }
}
