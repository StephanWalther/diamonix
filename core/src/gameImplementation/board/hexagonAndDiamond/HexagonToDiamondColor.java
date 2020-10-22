package gameImplementation.board.hexagonAndDiamond;

public class HexagonToDiamondColor {
    protected DiamondColor getDiamondColor(Hexagon hexagon) {
        if (!hexagon.hasDiamond()) return null;
        return hexagon.getDiamond().diamondColor;
    }
}
