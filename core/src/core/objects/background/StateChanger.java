package core.objects.background;

import com.badlogic.gdx.math.*;

import applicationState.keys.*;
import applicationState.keys.data.*;
import applicationState.state.*;
import core.*;
import core.objects.audio.*;
import core.objects.progress.*;
import core.objects.screen.*;
import tools.common.*;
import tools.input.input.*;
import tools.input.processors.grab.*;

class StateChanger implements InputProcessable, GrabListener {
    private final GrabProcessor grabProcessor = new GrabProcessor(this);
    private final Vector2 grabStart = new Vector2();
    private ExternalStateChangeRequestHandler externalStateChangeRequestHandler;
    private final Progress progress;
    private final Sound sound = Sounds.get("beep");
    
    StateChanger(Progress progress) {
        this.progress = progress;
    }
    
    void setExternalStateChangeRequestHandler(ExternalStateChangeRequestHandler e) {
        externalStateChangeRequestHandler = e;
    }
    
    @Override
    public boolean processInput(Input input) {
        return grabProcessor.processInput(input);
    }
    
    @Override
    public boolean grabTry(float x, float y, int pointer) {
        grabStart.set(x, y);
        return true;
    }
    
    @Override
    public void grabMoved(float x, float y, int pointer) {
        Vector2 diff = new Vector2(x - grabStart.x, y - grabStart.y);
        if (diff.len() >= SizeGetter.getIdealWorldWidth()*0.1f) {
            float angle = diff.angle();
            if (315.f < angle || angle < 45.f) requestStateChange(new RecordsKey());
            else if (angle < 135.f) requestStateChange(new InformationKey());
            else if (angle < 225.f) requestStateChange(new OptionsKey());
            else if (progress.tutorialPlayed.wasPlayed()) {
                requestStateChange(new GameKey(new GameData(true)));
            }
            grabProcessor.processInput(new Reset());
        }
    }
    
    private void requestStateChange(ApplicationStateKey stateKey) {
        if (externalStateChangeRequestHandler.handleExternalStateChangeRequest(stateKey)) {
            sound.play();
        }
    }
    
    @Override
    public void grabReleased(float x, float y, int pointer) {}
    
    @Override
    public void grabReset(int pointer) {}
}
