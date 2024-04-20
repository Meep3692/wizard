package ca.awoo.wizard;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class WrapLabel extends JTextArea{
    public WrapLabel(String contents){
        super(contents);
        setLineWrap(true);
        setWrapStyleWord(true);
        setEditable(false);
        setFocusable(false);
        setOpaque(false);
        setBackground(UIManager.getColor("Label.background"));
        setBorder(UIManager.getBorder("Label.border"));
        setFont(UIManager.getFont("Label.font"));
    }
}
