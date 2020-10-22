package tools.mapping;

import java.util.*;

public class Concatenation implements Mapping {
    public static Mapping linear(float a, float b, Mapping mapping) {
        return new Concatenation(new Polynomial(a, b), mapping);
    }
    
    public static List<Mapping> pairwise(List<Mapping> outerMappings, List<Mapping> innerMappings) {
        List<Mapping> mappings = new ArrayList<Mapping>(outerMappings.size());
        for (int i = 0; i < outerMappings.size(); i++) {
            mappings.add(new Concatenation(outerMappings.get(i), innerMappings.get(i)));
        }
        return mappings;
    }
    
    private final Mapping outerMapping, innerMapping;
    
    public Concatenation(Mapping outerMapping, Mapping innerMapping) {
        this.outerMapping = outerMapping;
        this.innerMapping = innerMapping;
    }
    
    @Override
    public float calculate(float x) {
        return outerMapping.calculate(innerMapping.calculate(x));
    }
}
