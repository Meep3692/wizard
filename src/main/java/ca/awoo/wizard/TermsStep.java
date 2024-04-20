package ca.awoo.wizard;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TermsStep extends WizardStep<Void, Void>{

    public TermsStep(String terms){
        super();
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets.set(1, 5, 1, 5);

        TitleLabel titleLabel = new TitleLabel("Terms and Conditions");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(titleLabel, constraints);

        WrapLabel descLabel = new WrapLabel("Please read the following terms and conditions carefully. By clicking 'I Agree', you are agreeing to the following terms and conditions:");
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(descLabel, constraints);

        JCheckBox agreeBox = new JCheckBox("I Agree");

        JTextArea textArea = new JTextArea(terms);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        System.out.println(textArea.getFont());
        JScrollPane scrollPane = new JScrollPane(textArea);
        Dimension scrollPaneSize = scrollPane.getSize();
        scrollPaneSize.height = Integer.MAX_VALUE;
        scrollPane.setPreferredSize(scrollPaneSize);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            if(e.getValue() == scrollPane.getVerticalScrollBar().getMaximum() - scrollPane.getVerticalScrollBar().getVisibleAmount()){
                agreeBox.setEnabled(true);
            }
        });
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(scrollPane, constraints);

        agreeBox.setEnabled(false);
        agreeBox.addActionListener(e -> setValid(agreeBox.isSelected()));
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.weightx = 0;
        constraints.weighty = 0;
        add(agreeBox, constraints);
    }

    public static TermsStep from(InputStream terms){
        BufferedReader reader = new BufferedReader(new InputStreamReader(terms));
        StringBuilder builder = new StringBuilder();
        reader.lines().forEach(line -> builder.append(line).append("\n"));
        return new TermsStep(builder.toString());
    }

    @Override
    public void onStepEnter(Void input) {

    }

    @Override
    public Void onStepLeave() {
        return null;
    }
    
}
