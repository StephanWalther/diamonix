package gameImplementation;

import java.util.*;

import core.objects.progress.*;
import gameImplementation.board.hexagonAndDiamond.*;
import gameImplementation.boardMediator.*;
import gameImplementation.gameVariant.*;

class AchievementsUnlocker implements BoardListener {
    private final GameVariant gameVariant;
    private final Achievements achievements;
    private final List<List<DiamondColor>> destroyedDiamonds = new ArrayList<List<DiamondColor>>(4);
    
    AchievementsUnlocker(GameVariant gameVariant, Achievements achievements) {
        this.gameVariant = gameVariant;
        this.achievements = achievements;
    }
    
    @Override
    public void newDestruction(int destructionCountThisRound) {
        if (gameVariant.isTutorialGame()) return;
        destroyedDiamonds.add(new ArrayList<DiamondColor>(34));
    }
    
    @Override
    public void diamondDestroyed(Diamond diamond) {
        if (gameVariant.isTutorialGame()) return;
        destroyedDiamonds.get(destroyedDiamonds.size() - 1).add(diamond.diamondColor);
    }
    
    @Override
    public void newRound() {
        if (gameVariant.isTutorialGame()) return;
        updateAchievements();
        stop();
    }
    
    private void updateAchievements() {
        AchievementsData achievementsData = achievements.getAchievementsData();
        while (!destroyedDiamonds.isEmpty()) {
            updateOneDestruction(destroyedDiamonds.remove(0), achievementsData);
        }
        achievements.setAchievementsData(achievementsData);
    }
    
    private void updateOneDestruction(List<DiamondColor> destruction,
                                      AchievementsData achievementsData) {
        achievementsData.maxDestroyedDiamonds
          = Math.max(achievementsData.maxDestroyedDiamonds, destruction.size());
        achievementsData.maxDestroyedDiamondsWithSameColour
          = Math.max(achievementsData.maxDestroyedDiamondsWithSameColour,
          getSameColourCount(destruction));
        achievementsData.maxDestroyedDiamondsWithDifferentColour
          = Math.max(achievementsData.maxDestroyedDiamondsWithDifferentColour,
          getDifferentColourCount(destruction));
        
        int destroyedDiamonds = destruction.size();
        achievementsData.everDestroyedDiamonds += destroyedDiamonds;
        achievementsData.everDestroyedDiamondsLeftFor5000Achievement += destroyedDiamonds;
        achievementsData.everDestroyedDiamondsLeftFor50000Achievement += destroyedDiamonds;
    }
    
    private int getSameColourCount(List<DiamondColor> destruction) {
        DiamondColor[] diamondColors = DiamondColor.values();
        List<Integer> counts = new ArrayList<Integer>(diamondColors.length);
        for (DiamondColor diamondColor : diamondColors) counts.add(0);
        for (DiamondColor diamondColorToDestroy : destruction) {
            for (int i = 0; i < diamondColors.length; i++) {
                if (diamondColors[i].equals(diamondColorToDestroy)) {
                    counts.set(i, counts.get(i) + 1);
                }
            }
        }
        int max = 0;
        for (Integer count : counts) max = Math.max(max, count);
        return max;
    }
    
    private int getDifferentColourCount(List<DiamondColor> destruction) {
        List<DiamondColor> colours = new ArrayList<DiamondColor>(DiamondColor.values().length);
        for (DiamondColor diamondColor : destruction) {
            if (!colours.contains(diamondColor)) colours.add(diamondColor);
        }
        return colours.size();
    }
    
    void stop() {
        destroyedDiamonds.clear();
    }
}
