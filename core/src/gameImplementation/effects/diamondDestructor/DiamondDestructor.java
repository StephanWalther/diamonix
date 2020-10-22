package gameImplementation.effects.diamondDestructor;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import java.util.*;

import core.*;
import core.objects.audio.*;
import gameImplementation.board.hexagonAndDiamond.*;
import tools.*;
import tools.common.*;
import tools.component.Sprite;
import tools.component.color.*;
import tools.component.graphic.*;
import tools.component.physic.*;
import tools.mapping.*;
import tools.screen.*;

public class DiamondDestructor implements Drawable {
    private final DefaultPhysicComponent physicComponent = new DefaultPhysicComponent();
    private final DefaultColorComponent colorComponent = new DefaultColorComponent();
    private final Map<DiamondColor, List<TextureComponent>> textureComponents
      = new HashMap<DiamondColor, List<TextureComponent>>(DiamondColor.values().length);
    private final List<Mapping> centerMappingsX = new ArrayList<Mapping>(6);
    private final List<Mapping> centerMappingsY = new ArrayList<Mapping>(6);
    private final List<DiamondPiecesData> diamondPiecesData = new ArrayList<DiamondPiecesData>();
    private final Sound diamondDestructionSound = Sounds.get("diamondDestruction");
    
    public DiamondDestructor() {
        assembleTextureComponents();
        assembleCenterMappings();
    }
    
    private void assembleTextureComponents() {
        for (DiamondColor diamondColor : DiamondColor.values()) {
            List<TextureComponent> tcs = new ArrayList<TextureComponent>(6);
            for (int i = 0; i < 6; i++) {
                TextureOrigin textureOrigin
                  = TextureOrigins.get("diamond" + Tools.capitalize(diamondColor.toString())
                                         + "Triangle" + Integer.toString(i));
                TextureComponent textureComponent
                  = new TextureComponent(physicComponent, colorComponent, textureOrigin);
                tcs.add(textureComponent);
            }
            textureComponents.put(diamondColor, tcs);
        }
    }
    
    private void assembleCenterMappings() {
        float pushFactor = 3.f;
        TextureRegion textureRegion
          = textureComponents.get(DiamondColor.values()[0]).get(0).getTextureRegion();
        float triangleWidth = textureRegion.getRegionWidth();
        float triangleHeight = textureRegion.getRegionHeight();
        List<Vector2> positions = new ArrayList<Vector2>(6);
        positions.add(new Vector2(triangleWidth*0.5f, triangleHeight/3.f));
        positions.add(new Vector2(0, triangleHeight*2.f/3.f));
        positions.add(new Vector2(-triangleWidth*0.5f, triangleHeight/3.f));
        positions.add(new Vector2(-triangleWidth*0.5f, -triangleHeight/3.f));
        positions.add(new Vector2(0, -triangleHeight*2.f/3.f));
        positions.add(new Vector2(triangleWidth*0.5f, -triangleHeight/3.f));
        for (Vector2 position : positions) {
            centerMappingsX.add(Concatenation.linear(
              position.x*(pushFactor - 1.f), position.x, Polynomial.negativeQuadratic));
            centerMappingsY.add(Concatenation.linear(
              position.y*(pushFactor - 1.f), position.y, Polynomial.negativeQuadratic));
        }
    }
    
    public void destroy(Diamond diamond) {
        diamondPiecesData.add(new DiamondPiecesData(diamond));
        diamondDestructionSound.play();
    }
    
    public void update(float dt) {
        UpdateDistributer.updateAll(diamondPiecesData, dt);
    }
    
    @Override
    public void draw(final Screen screen) {
        traverseDiamondPieces(new DiamondPieceHandler() {
            @Override
            public void handle(DefaultPhysicComponent physicComponent,
                               DefaultColorComponent colorComponent,
                               TextureComponent textureComponent) {
                screen.draw(textureComponent);
            }
        });
    }
    
    public List<Whole> stop() {
        final List<Whole> wholes = new ArrayList<Whole>();
        traverseDiamondPieces(new DiamondPieceHandler() {
            @Override
            public void handle(DefaultPhysicComponent physicComponent,
                               DefaultColorComponent colorComponent,
                               TextureComponent textureComponent) {
                wholes.add(new Sprite(physicComponent, colorComponent, textureComponent));
            }
        });
        diamondPiecesData.clear();
        return wholes;
    }
    
    private void traverseDiamondPieces(DiamondPieceHandler diamondPieceHandler) {
        for (DiamondPiecesData dpd : diamondPiecesData) {
            List<TextureComponent> tcs = textureComponents.get(dpd.getDiamondColor());
            for (int i = 0; i < centerMappingsX.size(); i++) {
                TextureComponent textureComponent = tcs.get(i);
                dpd.apply(physicComponent, colorComponent, centerMappingsX.get(i),
                  centerMappingsY.get(i));
                colorComponent.setColorA(colorComponent.getColorA());
                diamondPieceHandler.handle(physicComponent, colorComponent, textureComponent);
            }
        }
    }
}
