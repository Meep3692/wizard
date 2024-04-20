package ca.awoo.wizard;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import ca.awoo.wizard.ExpandingImage.GrowDirection;

import java.awt.FlowLayout;
import java.io.IOException;
import java.awt.BorderLayout;

public class Wizard extends JFrame{

    private final JButton nextButton = new JButton("Next");
    private final JButton cancelButton = new JButton("Cancel");

    public Wizard(WizardManager manager) throws IOException {
        super("Wizard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLayout(new BorderLayout());

        ExpandingImage sideImage = new ExpandingImage(GrowDirection.VERTICAL, ImageIO.read(getClass().getResourceAsStream("wizard.png")));
        add(sideImage, BorderLayout.LINE_START);

        JPanel controlPanel = new JPanel();
        FlowLayout controlLayout = new FlowLayout();
        controlLayout.setAlignment(FlowLayout.TRAILING);
        controlPanel.setLayout(controlLayout);
        controlPanel.setBorder(new PartialBevelBorder(BevelBorder.RAISED, true, false, false, false));
        nextButton.setEnabled(false);
        controlPanel.add(nextButton);
        controlPanel.add(cancelButton);
        add(controlPanel, BorderLayout.PAGE_END);

        manager.addValidChangeListener(valid -> nextButton.setEnabled(valid));
        nextButton.addActionListener(e -> {
            remove(manager.currentStep());
            if(manager.next()){
                add(manager.currentStep(), BorderLayout.CENTER);
                revalidate();
                repaint();
            } else {
                dispose();
            }
        });
        cancelButton.addActionListener(e -> {
            dispose();
        });

        manager.start();
        add(manager.currentStep(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}