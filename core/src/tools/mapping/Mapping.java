package tools.mapping;

public interface Mapping {
    Mapping scaleUp = Concatenation.linear(0.25f, 0.75f, Polynomial.negativeQuadratic);
    Mapping scaleDown = Concatenation.linear(-0.5f, 1.5f, Polynomial.negativeQuadratic);
    
    float calculate(float x);
}
