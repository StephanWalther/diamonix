package tools.progressLine;

import tools.common.*;

public interface ProgressLine extends Updatable, ProgressLineInfo {
    ProgressLine setProgress(float progress);
    
    ProgressLine increase();
    
    ProgressLine decrease();
    
    ProgressLine extract();
}
