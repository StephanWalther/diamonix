package tools.progressLine;

public interface ManifoldProgressLine extends ProgressLine {
    ProgressLineInfo absorb(ProgressLine timeLine);
    
    ManifoldProgressLine clear();

    int size();
}
