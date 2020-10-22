package core.objects.progress;

import java.io.*;

public class AchievementsData implements Serializable {
    public int maxDestroyedDiamonds = 0;
    public int maxDestroyedDiamondsWithSameColour = 0;
    public int maxDestroyedDiamondsWithDifferentColour = 0;
    public int everDestroyedDiamonds = 0;
    public int everDestroyedDiamondsLeftFor5000Achievement = 0;
    public int everDestroyedDiamondsLeftFor50000Achievement = 0;
    
    public AchievementsData() {}
    
    public AchievementsData(AchievementsData rhs) {
        set(rhs);
    }
    
    public AchievementsData set(AchievementsData rhs) {
        maxDestroyedDiamonds = rhs.maxDestroyedDiamonds;
        maxDestroyedDiamondsWithSameColour = rhs.maxDestroyedDiamondsWithSameColour;
        maxDestroyedDiamondsWithDifferentColour = rhs.maxDestroyedDiamondsWithDifferentColour;
        everDestroyedDiamonds = rhs.everDestroyedDiamonds;
        everDestroyedDiamondsLeftFor5000Achievement
          = rhs.everDestroyedDiamondsLeftFor5000Achievement;
        everDestroyedDiamondsLeftFor50000Achievement
          = rhs.everDestroyedDiamondsLeftFor50000Achievement;
        return this;
    }
}
