package ca.awoo.wizard;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.BevelBorder;

/**
 * A bevel border with settings for which sides to render on
 */
public class PartialBevelBorder extends BevelBorder {
    private final boolean top, left, bottom, right;

    public PartialBevelBorder(int bevelType, boolean top, boolean left, boolean bottom, boolean right) {
        super(bevelType);
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        int cx = x;
        int cy = y;
        int cwidth = width;
        int cheight = height;
        if (!top) {
            cy += 2;
            cheight -= 2;
        }
        if (!left) {
            cx += 2;
            cwidth -= 2;
        }
        if (!bottom) {
            cheight -= 2;
        }
        if (!right) {
            cwidth -= 2;
        }
        g.setClip(cx, cy, cwidth, cheight);
        super.paintBorder(c, g, x, y, width, height);
    }

    
    
}
