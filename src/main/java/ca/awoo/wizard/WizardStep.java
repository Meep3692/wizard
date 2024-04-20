package ca.awoo.wizard;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JPanel;

public abstract class WizardStep<Input, Output> extends JPanel{
    public abstract void onStepEnter(Input input);
    public abstract Output onStepLeave();

    private final Set<Consumer<Boolean>> validChangeListeners = new HashSet<>();

    private boolean valid = false;

    public boolean isValid(){
        return valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
        validChangeListeners.forEach(listener -> listener.accept(valid));
    }

    public void addValidChangeListener(Consumer<Boolean> listener){
        validChangeListeners.add(listener);
    }

    public void removeValidChangeListener(Consumer<Boolean> listener){
        validChangeListeners.remove(listener);
    }
}
