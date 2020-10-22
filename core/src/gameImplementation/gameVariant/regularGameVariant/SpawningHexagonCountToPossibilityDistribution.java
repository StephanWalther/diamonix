package gameImplementation.gameVariant.regularGameVariant;

import java.util.*;

class SpawningHexagonCountToPossibilityDistribution {
    private final List<Integer> spawningHexagonCountLimits = new ArrayList<Integer>();
    private final List<List<Integer>> possibilityDistributions = new ArrayList<List<Integer>>();
    
    SpawningHexagonCountToPossibilityDistribution() {
        assemblePossibilityDistributions();
        assembleSpawningHexagonCountLimits();
    }
    
    private void assemblePossibilityDistributions() {
        possibilityDistributions.add(buildPossibilityDistribution(1));
        possibilityDistributions.add(buildPossibilityDistribution(1, 2));
        possibilityDistributions.add(buildPossibilityDistribution(1, 2, 3));
    }
    
    private void assembleSpawningHexagonCountLimits() {
        spawningHexagonCountLimits.add(9);
        spawningHexagonCountLimits.add(14);
    }
    
    private List<Integer> buildPossibilityDistribution(int... ints) {
        List<Integer> possibilityDistribution = new ArrayList<Integer>(ints.length);
        for (int i : ints) possibilityDistribution.add(i);
        return possibilityDistribution;
    }
    
    List<Integer> get(int spawningHexagonCount) {
        for (int i = 0; i < spawningHexagonCountLimits.size(); i++) {
            if (spawningHexagonCount < spawningHexagonCountLimits.get(i)) {
                return possibilityDistributions.get(i);
            }
        }
        return possibilityDistributions.get(possibilityDistributions.size() - 1);
    }
}
