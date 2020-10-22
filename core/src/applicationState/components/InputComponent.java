package applicationState.components;

import java.util.*;

import applicationState.state.*;
import tools.common.*;
import tools.input.input.*;

public class InputComponent extends ApplicationStateComponent {
    private final List<InputProcessable> inputProcessables = new ArrayList<InputProcessable>();
    
    public void add(InputProcessable common) {
        inputProcessables.add(common);
    }
    
    @Override
    public boolean processInput(Input input) {
        return InputDistributor.distribute(inputProcessables, input);
    }
}
