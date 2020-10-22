package gameImplementation.board.grid;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.grid.hexagonSelector.*;
import gameImplementation.board.hexagonAndDiamond.*;
import misc.*;
import tools.common.*;
import tools.progressLine.*;
import tools.progressLine.Timer;
import tools.screen.*;

public class Grid implements Drawable {
    private List<List<Hexagon>> hexagons;
    
    public Grid() {
        hexagons = GridBuilder.build();
    }
    
    public ProgressLine buildProgressLine() {
        SequentialProgressLine sequentialProgressLine = new SequentialProgressLine();
        sequentialProgressLine.setTimeBetweenIncreasesAndDecreases(
          Constants.SPAWN_TIME/(float) (hexagons.size() - 1));
        for (List<Hexagon> line : hexagons) {
            SimultaneousProgressLine simultaneousProgressLine = new SimultaneousProgressLine();
            for (Hexagon hexagon : line) {
                hexagon.setProgressLineInfo(simultaneousProgressLine.absorb(
                  new Timer(Constants.SPAWN_TIME)));
            }
            sequentialProgressLine.absorb(simultaneousProgressLine);
        }
        return sequentialProgressLine;
    }
    
    public void update(float dt) {
        for (List<Hexagon> line : hexagons) UpdateDistributer.updateAll(line, dt);
    }
    
    @Override
    public void draw(Screen screen) {
        for (List<Hexagon> line : hexagons) DrawDistributer.drawAll(line, screen);
    }
    
    public List<List<Hexagon>> selectHexagons(HexagonSelector selector) {
        List<List<Hexagon>> selectedHexagons = new ArrayList<List<Hexagon>>(hexagons.size());
        for (List<Hexagon> line : hexagons) {
            List<Hexagon> selectedLine = new ArrayList<Hexagon>(line.size());
            for (Hexagon hexagon : line) {
                if (selector.select(hexagon)) selectedLine.add(hexagon);
            }
            if (!selectedLine.isEmpty()) selectedHexagons.add(selectedLine);
        }
        return selectedHexagons;
    }
    
    public Hexagon getHighestMiddleHexagon() {
        List<Hexagon> middleLine = hexagons.get((hexagons.size() - 1)/2);
        return middleLine.get(0);
    }
    
    public List<Hexagon> getLowestHexagons() {
        List<Hexagon> lowestHexagons = new ArrayList<Hexagon>(hexagons.size());
        for (List<Hexagon> line : hexagons) lowestHexagons.add(line.get(line.size() - 1));
        return lowestHexagons;
    }
    
    public boolean contains(float x, float y) {
        return getContainingHexagon(new Vector2(x, y)) != null;
    }
    
    public Hexagon getContainingHexagon(Vector2 point) {
        for (List<Hexagon> line : hexagons) {
            for (Hexagon hexagon : line) if (hexagon.contains(point.x, point.y)) return hexagon;
        }
        return null;
    }
    
    public List<List<DiamondColor>> extractColors() {
        List<List<DiamondColor>> diamondColors = new ArrayList<List<DiamondColor>>(hexagons.size());
        for (List<Hexagon> hs : hexagons) {
            List<DiamondColor> dc = new ArrayList<DiamondColor>(hs.size());
            for (Hexagon hexagon : hs) {
                if (hexagon.hasDiamond()) {
                    dc.add(hexagon.getDiamond().diamondColor);
                } else dc.add(null);
            }
            diamondColors.add(dc);
        }
        return diamondColors;
    }
    
    public int getSpawningHexagonCount() {
        int spawningHexagonCount = 0;
        for (List<Hexagon> line : hexagons) {
            for (Hexagon hexagon : line) {
                if (hexagon.isSpawningHexagon()) spawningHexagonCount++;
            }
        }
        return spawningHexagonCount;
    }
    
    public List<HexagonDiamondPair> toHexagonDiamondPairs(List<List<DiamondColor>> diamondColors) {
        List<HexagonDiamondPair> hexagonDiamondPairs = new ArrayList<HexagonDiamondPair>();
        Iterator<List<Hexagon>> hexagonListIterator = hexagons.iterator();
        for (List<DiamondColor> dcs : diamondColors) {
            Iterator<Hexagon> hexagonIterator = hexagonListIterator.next().iterator();
            for (DiamondColor diamondColor : dcs) {
                Hexagon hexagon = hexagonIterator.next();
                if (diamondColor != null) {
                    hexagonDiamondPairs.add(
                      new HexagonDiamondPair(hexagon, new Diamond(diamondColor)));
                }
            }
        }
        return hexagonDiamondPairs;
    }
    
    public void setAllHexagonsToNotSpawning() {
        for (List<Hexagon> line : hexagons) for (Hexagon hexagon : line) hexagon.setToNotSpawning();
    }
    
    public int getWidth() {
        return hexagons.size();
    }
    
    public int getHeight() {
        return hexagons.get(0).size();
    }
    
    public boolean isFull() {
        for (List<Hexagon> line : hexagons) {
            for (Hexagon hexagon : line) if (!hexagon.hasDiamond()) return false;
        }
        return true;
    }
}
