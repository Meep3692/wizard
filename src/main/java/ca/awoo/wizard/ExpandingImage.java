package ca.awoo.wizard;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ExpandingImage extends JComponent implements ComponentListener {
    private BufferedImage image;
    private final GrowDirection dir;

    public enum GrowDirection{
        HORIZONTAL,
        VERTICAL
    }

    public ExpandingImage(GrowDirection dir){
        this(dir, new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    }
    
    public ExpandingImage(GrowDirection dir, BufferedImage image){
        this.image = image;
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        addComponentListener(this);
        this.dir = dir;
    }

    public BufferedImage getImage(){
        return image;
    }

    public void setImage(BufferedImage image){
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        float xscale = (float)super.getWidth() / (float)image.getWidth();
        float yscale = (float)super.getHeight() / (float)image.getHeight();
        float scale = dir == GrowDirection.HORIZONTAL ? xscale : yscale;
        float width = image.getWidth() * scale;
        float height = image.getHeight() * scale;
        int iw = (int)width;
        int ih = (int)height;
        int icx = iw/2;
        int icy = ih/2;
        int cx = getWidth()/2;
        int cy = getHeight()/2;
        g.drawImage(image, cx - icx, cy - icy, iw, ih, null);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        revalidate();
        repaint();
        float xscale = (float)super.getWidth() / (float)image.getWidth();
        float yscale = (float)super.getHeight() / (float)image.getHeight();
        float scale = dir == GrowDirection.HORIZONTAL ? xscale : yscale;
        setPreferredSize(new Dimension((int)(image.getWidth() * scale), (int)(image.getHeight() * scale)));
        revalidate();
        repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
