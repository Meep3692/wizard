package ca.awoo.wizard;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class WizardBuilder<T> {
    private final WizardBuilder<?> previousBuilder;
    private final WizardStep<?, T> step;
    private String title = null;
    private BufferedImage sideImage = null;
    
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

    public WizardBuilder<T> title(String title) {
        this.title = title;
        return this;
    }

    public WizardBuilder<T> sideImage(BufferedImage sideImage) {
        this.sideImage = sideImage;
        return this;
    }

    public Wizard build() throws IOException {
        return new Wizard(getManager(), getTitle(), getSideImage());
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

    private String getTitle(){
        if(title == null){
            if(previousBuilder == null){
                return "Wizard";
            }else{
                return previousBuilder.getTitle();
            }
        }else{
            return title;
        }
    }

    private BufferedImage getSideImage() throws IOException{
        if(sideImage == null){
            if(previousBuilder == null){
                return ImageIO.read(getClass().getResourceAsStream("wizard.png"));
            }else{
                return previousBuilder.getSideImage();
            }
        }else{
            return sideImage;
        }
    }
}
