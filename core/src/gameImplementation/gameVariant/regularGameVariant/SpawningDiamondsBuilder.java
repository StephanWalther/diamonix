package gameImplementation.gameVariant.regularGameVariant;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;

class SpawningDiamondsBuilder {
    private final HexagonToDiamondColorForSpawn hexagonToDiamondColorForSpawn
      = new HexagonToDiamondColorForSpawn();
    private final HexagonConnection hexagonConnection
      = new HexagonConnection(hexagonToDiamondColorForSpawn);
    private final SpawningHexagonCountToPossibilityDistribution
      spawningHexagonCountToPossibilityDistribution
      = new SpawningHexagonCountToPossibilityDistribution();
    private final Random random = new Random();
    private int spawningHexagonCount;
    private List<List<HexagonDiamondColorPair>> diamondColorsToSpawn;
    
    List<List<DiamondColor>> build(List<List<Hexagon>> spawningHexagons) {
        updateSpawningHexagonCount(spawningHexagons);
        diamondColorsToSpawn = new ArrayList<List<HexagonDiamondColorPair>>();
        hexagonToDiamondColorForSpawn.diamondColorsToSpawn = diamondColorsToSpawn;
        for (List<Hexagon> spawningHexagonLine : spawningHexagons) {
            diamondColorsToSpawn.add(new ArrayList<HexagonDiamondColorPair>(
              spawningHexagonLine.size()));
            for (Hexagon spawningHexagon : spawningHexagonLine) {
                if (spawningHexagon.hasDiamond()) return null;
                else createSpawningDiamond(spawningHexagon);
            }
        }
        return HexagonDiamondColorPair.toDiamondColors(diamondColorsToSpawn);
    }
    
    private void updateSpawningHexagonCount(List<List<Hexagon>> spawningHexagons) {
        spawningHexagonCount = 0;
        for (List<Hexagon> spawningHexagonLine : spawningHexagons) {
            spawningHexagonCount += spawningHexagonLine.size();
        }
    }
    
    private void createSpawningDiamond(Hexagon hexagon) {
        hexagonToDiamondColorForSpawn.hexagon = hexagon;
        List<Integer> colorPossibilities = getColorPossibilities(hexagon);
        DiamondColor diamondColor = getColorByPossibility(colorPossibilities);
        HexagonDiamondColorPair hexagonDiamondColorPair
          = new HexagonDiamondColorPair(hexagon, diamondColor);
        diamondColorsToSpawn.get(diamondColorsToSpawn.size() - 1).add(hexagonDiamondColorPair);
    }
    
    private List<Integer> getColorPossibilities(Hexagon hexagon) {
        List<Integer> connectionCount = getConnectionCount(hexagon);
        return connectionCountToColorPossibilities(connectionCount);
    }
    
    private List<Integer> getConnectionCount(Hexagon hexagon) {
        List<Integer> connectionCount = new ArrayList<Integer>(DiamondColor.values().length);
        for (int i = 0; i < DiamondColor.values().length; i++) {
            hexagonToDiamondColorForSpawn.diamondColor = DiamondColor.values()[i];
            connectionCount.add(hexagonConnection.getConnectedHexagons(hexagon).size());
        }
        return connectionCount;
    }
    
    private List<Integer> connectionCountToColorPossibilities(List<Integer> connectionCount) {
        List<Integer> possibilityDistribution
          = spawningHexagonCountToPossibilityDistribution.get(spawningHexagonCount);
        List<Integer> colorPossibilities = new ArrayList<Integer>(connectionCount.size());
        for (Integer integer : connectionCount) {
            if (integer < possibilityDistribution.size() + 1) {
                colorPossibilities.add(possibilityDistribution.get(integer - 1));
            } else colorPossibilities.add(0);
        }
        return colorPossibilities;
    }
    
    private DiamondColor getColorByPossibility(List<Integer> colorPossibilities) {
        int sum = 0;
        for (Integer integer : colorPossibilities) sum += integer;
        int randomInt = random.nextInt(sum);
        sum = 0;
        for (int i = 0; i < colorPossibilities.size(); i++) {
            sum += colorPossibilities.get(i);
            if (randomInt < sum) return DiamondColor.values()[i];
        }
        return DiamondColor.values()[random.nextInt(DiamondColor.values().length)];
    }
}
