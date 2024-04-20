package ca.awoo.wizard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WizardBuilder<T> {
    private final WizardBuilder<?> previousBuilder;
    private final WizardStep<?, T> step;
    private WizardBuilder(WizardBuilder<?> previousBuilder, WizardStep<?, T> step) {
        this.previousBuilder = previousBuilder;
        this.step = step;
    }

    public static <T> WizardBuilder<T> start(WizardStep<Void, T> firstStep) {
        return new WizardBuilder<>(null, firstStep);
    }

    public <U> WizardBuilder<U> then(WizardStep<T, U> nextStep) {
        return new WizardBuilder<>(this, nextStep);
    }

    public Wizard build() throws IOException {
        return new Wizard(getManager());
    }



    private WizardManager getManager() {
        if(previousBuilder == null) {
            List<WizardStep<?, ?>> steps = new ArrayList<>();
            steps.add(step);
            return new WizardManager(steps);
        } else {
            WizardManager previousManager = previousBuilder.getManager();
            previousManager.addStep(step);
            return previousManager;
        }
    }
}
