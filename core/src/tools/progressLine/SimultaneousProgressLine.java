package tools.progressLine;

public class SimultaneousProgressLine extends PrototypeManifoldProgressLine {
    @Override
    public void update(float dt) {
        for (ProgressLine timeLine : progressLines) timeLine.update(dt);
    }
    
    @Override
    public SimultaneousProgressLine setProgress(float progress) {
        for (ProgressLine progressLine : progressLines) progressLine.setProgress(progress);
        return this;
    }
    
    @Override
    public SimultaneousProgressLine increase() {
        for (ProgressLine timeLine : progressLines) timeLine.increase();
        return this;
    }
    
    @Override
    public SimultaneousProgressLine decrease() {
        for (ProgressLine timeLine : progressLines) timeLine.decrease();
        return this;
    }
    
    @Override
    public boolean isIncreasing() {
        return progressLines.isEmpty() || progressLines.get(0).isIncreasing();
    }
    
    @Override
    public void informPendingProgressLineAdd(ProgressLine progressLine) {
        if (!progressLines.isEmpty()) {
            if (progressLines.get(0).isIncreasing()) progressLine.increase();
            else progressLine.decrease();
        } else progressLine.decrease();
    }
    
    @Override
    public SimultaneousProgressLine extract() {
        SimultaneousProgressLine simultaneousTimeLine = new SimultaneousProgressLine();
        simultaneousTimeLine.progressLines.addAll(progressLines);
        progressLines.clear();
        return simultaneousTimeLine;
    }
}
