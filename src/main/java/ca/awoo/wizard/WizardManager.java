package ca.awoo.wizard;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Manages the flow of a wizard.
 * This class erases the types of the steps and blindly passes Object between steps.
 * For this reason, we do not use this class directly, but instead use the WizardBuilder to create a Wizard.
 */
class WizardManager {
    private final List<WizardStep<?, ?>> steps;
    private int currentStepIndex = 0;

    private final Consumer<Boolean> validListener = valid -> setValid(valid);

    public WizardManager(List<WizardStep<?, ?>> steps) {
        this.steps = steps;
    }

    public void addStep(WizardStep<?, ?> step) {
        steps.add(step);
    }

    public void start() {
        WizardStep<?, ?> currentStep = steps.get(currentStepIndex);
        currentStep.addValidChangeListener(validListener);
        currentStep.onStepEnter(null);
    }

    public boolean next() {
        WizardStep<?, ?> currentStep = steps.get(currentStepIndex);
        Object data = currentStep.onStepLeave();
        currentStep.removeValidChangeListener(validListener);
        currentStepIndex++;
        if (currentStepIndex < steps.size()) {
            @SuppressWarnings("unchecked")
            WizardStep<Object, Object> nextStep = (WizardStep<Object, Object>) steps.get(currentStepIndex);
            nextStep.addValidChangeListener(validListener);
            nextStep.onStepEnter(data);
            return true;
        }
        return false;
    }

    public WizardStep<?, ?> currentStep() {
        WizardStep<?, ?> step = steps.get(currentStepIndex);
        return step;
    }

    private final Set<Consumer<Boolean>> validChangeListeners = new HashSet<>();

    public void setValid(boolean valid){
        validChangeListeners.forEach(listener -> listener.accept(valid));
    }

    public void addValidChangeListener(Consumer<Boolean> listener){
        validChangeListeners.add(listener);
    }

    public void removeValidChangeListener(Consumer<Boolean> listener){
        validChangeListeners.remove(listener);
    }
}
