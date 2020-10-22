package tools;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

import java.util.*;

import tools.component.graphic.*;
import tools.component.physic.*;

public final class Tools {
    public static final float EPS = 0.00001f;
    private static final Random permutateRnd = new Random();
    
    private Tools() {}
    
    public static <E> void permute(List<E> list) {
        List<E> temp = new ArrayList<E>();
        while (!list.isEmpty()) {
            temp.add(list.remove(permutateRnd.nextInt(list.size())));
        }
        list.addAll(temp);
    }
    
    public static Color colorConvexCombination(Color c1, Color c2, float lambda) {
        return new Color(Tools.limitedConvexCombination(c1.r, c2.r, lambda),
          Tools.limitedConvexCombination(c1.g, c2.g, lambda),
          Tools.limitedConvexCombination(c1.b, c2.b, lambda),
          Tools.limitedConvexCombination(c1.a, c2.a, lambda));
    }
    
    public static float limitedConvexCombination(float a, float b, float lambda) {
        if (lambda < 0) return a;
        if (lambda > 1) return b;
        return lambda*b + (1 - lambda)*a;
    }
    
    public static Rectangle buildRectangle(Vector2 size, PhysicComponent physicComponent,
                                           Origin origin) {
        // Without rotation!
        return new Rectangle(physicComponent.getCenterX() - origin.toX(size.x),
          physicComponent.getCenterY() - origin.toY(size.y),
          size.x*physicComponent.getScaleX(), size.y*physicComponent.getScaleY());
    }
    
    public static Rectangle buildRectangle(Vector2 size, Vector2 center, Origin origin) {
        return new Rectangle(center.x - origin.toX(size.x), center.y - origin.toY(size.y),
          size.x, size.y);
    }
    
    public static boolean fullNumberChange(float one, float two) {
        one -= (int) two;
        return one < 0 || 1 <= one;
    }
    
    public static Rectangle scaleRectangle(Rectangle rectangle, Vector2 scale) {
        float addX = rectangle.width*(scale.x - 1);
        float addY = rectangle.height*(scale.y - 1);
        
        Rectangle scaledRectangle = new Rectangle(rectangle);
        scaledRectangle.x -= addX*0.5f;
        scaledRectangle.y -= addY*0.5f;
        scaledRectangle.width += addX;
        scaledRectangle.height += addY;
        
        return scaledRectangle;
    }
    
    public static <E> int getIndexOfElement(List<E> elements, E element) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(element)) return i;
        }
        throw new IllegalStateException("The element is not equal to any other" +
                                          "element of the array");
    }
    
    public static boolean isNearNull(float value) {
        return -EPS < value && value < EPS;
    }
    
    public static float positiveQuasiNull(float value) {
        if (value < EPS) return value + EPS;
        return value;
    }
    
    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
    public static String lowercase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }
    
    public static void nullCheck(Object object, String string) {
        if (object == null) {
            throw new IllegalArgumentException("The Object is null. Information: \n"
                                                 + string);
        }
    }
    
    public static FileHandle getInternalFileHandle(String file) {
        FileHandle fileHandle = Gdx.files.internal(file);
        if (!fileHandle.exists()) {
            throw new IllegalStateException("Could not find '" + file + "'.");
        }
        return fileHandle;
    }
    
    public static <E> boolean equalsOne(E toCheck, E checkAgainst, E... checkAgainstArray) {
        if (toCheck.equals(checkAgainst)) return true;
        for (E checkAgainstElement : checkAgainstArray) {
            if (toCheck.equals(checkAgainstElement)) return true;
        }
        return false;
    }
    
    public static float roundToOneDecimal(float value) {
        return (float) ((int) (value*10.f))/10.f;
    }
    
    public static float roundToTwoDecimals(float value) {
        return (float) ((int) (value*100.f))/100.f;
    }
    
    public static Color getRandomHighColor() {
        Random random = new Random();
        int maxReduction = 800;
        int redReduction = random.nextInt(maxReduction);
        int greenReduction = random.nextInt(maxReduction - redReduction);
        int blueReduction = random.nextInt(maxReduction - redReduction - greenReduction);
        int tol = 50;
        if (greenReduction - tol < redReduction && redReduction < greenReduction + tol &&
              greenReduction - tol < blueReduction && blueReduction < greenReduction + tol) {
            int selection = random.nextInt(3);
            int sign = 1;
            if (redReduction + greenReduction + blueReduction < maxReduction/2) sign = -1;
            if (selection == 0) redReduction += sign*maxReduction/2;
            if (selection == 1) greenReduction += sign*maxReduction/2;
            if (selection == 2) blueReduction += sign*maxReduction/2;
        }
        return new Color(1 - redReduction*0.001f, 1 - greenReduction*0.001f,
          1 - blueReduction*0.001f, 1);
    }
    
    public static void transformPhysicComponent(DefaultPhysicComponent dpc,
                                                Vector2 pos1, Vector2 pos2,
                                                float regionWith) {
        dpc.setCenter((pos1.x + pos2.x)*0.5f, (pos1.y + pos2.y)*0.5f);
        Vector2 diff = new Vector2(pos1).sub(pos2);
        dpc.setScale(diff.len()/regionWith, 1.f);
        dpc.setRotation(diff.angle());
    }
    
    public static <E> List<E> makeList(E... e) {
        return new ArrayList<E>(Arrays.asList(e));
    }
    
    public static <E> List<E> toSingleList(List<List<E>> doubleList) {
        List<E> singleList = new ArrayList<E>();
        for (List<E> list : doubleList) singleList.addAll(list);
        return singleList;
    }
    
    public static <E, F> List<E> transform(List<F> inputList, Transformer<E, F> transformer) {
        List<E> outputList = new ArrayList<E>(inputList.size());
        for (F input : inputList) outputList.add(transformer.transform(input));
        return outputList;
    }
    
    public static <E, F> List<List<E>> doubleTransform(final List<List<F>> inputList,
                                                       final Transformer<E, F> transformer) {
        return transform(inputList,
          new Transformer<List<E>, List<F>>() {
              @Override
              public List<E> transform(List<F> input) {
                  return Tools.transform(input, transformer);
              }
          });
    }
    
    public interface Transformer<E, F> {
        E transform(F input);
    }
}
