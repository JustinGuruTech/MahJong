import java.awt.*;
import javax.swing.*;

public class PictureTile extends Tile {

    private String name;
    private ImageIcon img;

    public PictureTile(String name) {

        this.name = name;
        setToolTipText(toString());
        try {
            this.img = new ImageIcon(getClass().getResource("images/" + name + ".png"));
            img = new ImageIcon(img.getImage().getScaledInstance(40, -1, java.awt.Image.SCALE_SMOOTH));
        } catch (NullPointerException e) {
            System.out.println("Resource not found.");
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            g.drawImage(img.getImage(), 14, 8, this);
            return;
        } catch (Exception e) {
            System.out.println("Image not found.");
        }

        // janky but shouldn't happen anyway
        Font currentFont = g.getFont();
        currentFont.deriveFont(.5f);
        g.setFont(currentFont);
        g.drawString(name, 10, 40);
    }


    public String toString() {

        return this.name;
    }
}
