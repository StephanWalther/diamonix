package tools.progressLine;

public class ProgressLineSelector extends PrototypeManifoldProgressLine {
    private ProgressLine selectedTimeLine = null;
    
    public void select(int i) {
        selectedTimeLine = progressLines.get(i);
    }
    
    @Override
    public void update(float dt) {
        selectCheck();
        selectedTimeLine.update(dt);
    }
    
    @Override
    public ProgressLineSelector setProgress(float progress) {
        selectCheck();
        selectedTimeLine.setProgress(progress);
        return this;
    }
    
    @Override
    public float getProgress() {
        selectCheck();
        return selectedTimeLine.getProgress();
    }
    
    @Override
    public ProgressLineSelector increase() {
        selectCheck();
        selectedTimeLine.increase();
        return this;
    }
    
    @Override
    public ProgressLineSelector decrease() {
        selectCheck();
        selectedTimeLine.decrease();
        return this;
    }
    
    @Override
    public boolean isIncreasing() {
        selectCheck();
        return selectedTimeLine.isIncreasing();
    }
    
    @Override
    public void informPendingProgressLineAdd(ProgressLine timeLine) {}
    
    @Override
    public ProgressLine extract() {
        selectCheck();
        ProgressLine timeLine = selectedTimeLine.extract();
        progressLines.remove(selectedTimeLine);
        selectedTimeLine = null;
        return timeLine;
    }
    
    private void selectCheck() {
        if (progressLines.isEmpty()) {
            throw new java.lang.IllegalStateException("No ProgressLine selected.");
        }
    }
}
