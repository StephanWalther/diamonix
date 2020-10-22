package core.objects.progress;

import core.objects.files.*;

public class Achievements {
    private final Files files;
    private final AchievementsData achievementsData;
    
    Achievements(Files files) {
        this.files = files;
        achievementsData = files.loadAchievementsData();
    }
    
    public void setAchievementsData(AchievementsData achievementsData) {
        this.achievementsData.set(achievementsData);
        files.saveAchievementsData(achievementsData);
    }
    
    public AchievementsData getAchievementsData() {
        return new AchievementsData(achievementsData);
    }
}
