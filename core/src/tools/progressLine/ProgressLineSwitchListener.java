package tools.progressLine;

public class ProgressLineSwitchListener implements ProgressLineListener {
    private final ProgressLineSwitchAction switchedToDecreaseAction;
    private final ProgressLineSwitchAction switchedToIncreaseAction;
    
    public ProgressLineSwitchListener(ProgressLineSwitchAction switchedToDecreaseAction,
                                      ProgressLineSwitchAction switchedToIncreaseAction) {
        this.switchedToDecreaseAction = switchedToDecreaseAction;
        this.switchedToIncreaseAction = switchedToIncreaseAction;
    }
    
    @Override
    public void updateCalled(float dt, ProgressLineInfo progressLineInfo) {}
    
    @Override
    public void setProgressCalled(float progress, ProgressLineInfo progressLineInfo) {}
    
    @Override
    public void switchedToDecrease(ProgressLineInfo progressLineInfo) {
        switchedToDecreaseAction.perform(progressLineInfo);
    }
    
    @Override
    public void switchedToIncrease(ProgressLineInfo progressLineInfo) {
        switchedToIncreaseAction.perform(progressLineInfo);
    }
}
