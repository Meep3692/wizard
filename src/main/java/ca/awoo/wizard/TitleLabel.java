package ca.awoo.wizard;

import java.awt.Font;

public class TitleLabel extends WrapLabel{

    public TitleLabel(String contents) {
        super(contents);
        setFont(getFont().deriveFont(24f).deriveFont(getFont().getStyle() | Font.BOLD));
    }
    
}
