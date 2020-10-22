package core.objects.files;

import java.io.*;

class AchievementsSaveData implements Serializable {
    int maxDestroyedDiamonds;
    int maxDestroyedDiamondsWithSameColour;
    int maxDestroyedDiamondsWithDifferentColour;
    int everDestroyedDiamonds;
    int everDestroyedDiamondsLeftFor5000Achievement;
    int everDestroyedDiamondsLeftFor50000Achievement;
}
