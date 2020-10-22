package gameImplementation.board.boardState.playerBoardState.movingPack;

import com.badlogic.gdx.math.*;

import java.util.*;

import gameImplementation.board.hexagonAndDiamond.*;
import tools.common.*;

public class MovingPack {
    public final Hexagon startHexagon;
    public final Diamond grabbedDiamond;
    public final Vector2 grabOffset;
    public final MovingDirection movingDirection;
    public final MovingLine movingLine;
    public final MovingDiamonds movingDiamonds = new MovingDiamonds();
    
    public MovingPack(Hexagon startHexagon, Diamond grabbedDiamond, Vector2 grabOffset,
               MovingDirection movingDirection, MovingLine movingLine) {
        this.startHexagon = startHexagon;
        this.grabbedDiamond = grabbedDiamond;
        this.grabOffset = grabOffset;
        this.movingDirection = movingDirection;
        this.movingLine = movingLine;
    }
    
    public List<Whole> toWholes() {
        List<Whole> wholes = movingDiamonds.clear();
        wholes.add(grabbedDiamond);
        return wholes;
    }
}
