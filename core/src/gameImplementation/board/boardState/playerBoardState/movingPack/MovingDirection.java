package gameImplementation.board.boardState.playerBoardState.movingPack;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;

public enum MovingDirection {
    lowerDiagonal {
        @Override
        public Hexagon next(Hexagon hexagon) {
            return hexagon.upperLeft;
        }
    
        @Override
        public Hexagon previous(Hexagon hexagon) {
            return hexagon.lowerRight;
        }
    },
    
    upperDiagonal {
        @Override
        public Hexagon next(Hexagon hexagon) {
            return hexagon.upperRight;
        }
    
        @Override
        public Hexagon previous(Hexagon hexagon) {
            return hexagon.lowerLeft;
        }
    },
    
    vertical {
        @Override
        public Hexagon next(Hexagon hexagon) {
            return hexagon.upper;
        }
    
        @Override
        public Hexagon previous(Hexagon hexagon) {
            return hexagon.lower;
        }
    };
    
    public List<Hexagon> getShrinkedHexagonLine(Hexagon startHexagon) {
        List<Hexagon> hexagons = new ArrayList<Hexagon>();
        while (previous(startHexagon) != null) startHexagon = previous(startHexagon);
        do {
            hexagons.add(startHexagon);
            startHexagon = next(startHexagon);
        } while (startHexagon != null);
        shrinkList(hexagons);
        return hexagons;
    }
    
    public abstract Hexagon next(Hexagon hexagon);
    
    public abstract Hexagon previous(Hexagon hexagon);
    
    void shrinkList(List<Hexagon> hexagons) {
        while (hexagons.get(0).hasDiamond()) hexagons.remove(0);
        while (hexagons.get(hexagons.size() - 1).hasDiamond()) {
            hexagons.remove(hexagons.size() - 1);
        }
    }
}
