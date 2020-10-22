package tools.progressLine;

import java.util.*;

public abstract class PrototypeManifoldProgressLine implements ManifoldProgressLine {
    final List<ProgressLine> progressLines = new ArrayList<ProgressLine>();
    
    @Override
    public float getProgress() {
        if (progressLines.isEmpty()) return 1;
        float progress = 0;
        for (ProgressLine progressLine : progressLines) progress += progressLine.getProgress();
        return progress/progressLines.size();
    }
    
    @Override
    public ProgressLineInfo absorb(ProgressLine progressLine) {
        ProgressLine movedProgressLine = progressLine.extract();
        informPendingProgressLineAdd(movedProgressLine);
        progressLines.add(movedProgressLine);
        return movedProgressLine;
    }
    
    abstract void informPendingProgressLineAdd(ProgressLine timeLine);
    
    @Override
    public PrototypeManifoldProgressLine clear() {
        progressLines.clear();
        return this;
    }
    
    @Override
    public int size() {
        return progressLines.size();
    }
}
