package ca.awoo.wizard;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        Wizard wizard = WizardBuilder.start(TermsStep.from(App.class.getResourceAsStream("/gnu/gpl3.txt"))).build();
        wizard.setVisible(true);
    }
}
