import java.awt.*;

public class WhiteDragonTile extends Tile {

    public WhiteDragonTile() {
        setToolTipText("White Dragon");
    }

    public String toString() {

        return "White Dragon";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x1 = 17;
        int y1 = 4;
        g.setColor(BLUE);
        g.drawRect(x1, y1, 39, 52);
        g.fillRect(x1, y1, 8, 4);
        x1 += 14;
        g.fillRect(x1, y1, 8, 4);
        x1 += 14;
        g.fillRect(x1, y1, 8, 4);

        x1 = 17;
        y1 = 52;
        g.fillRect(x1, y1, 8, 4);
        x1 += 14;
        g.fillRect(x1, y1, 8, 4);
        x1 += 14;
        g.fillRect(x1, y1, 8, 4);

        x1 = 17;
        y1 = 12;
        g.fillRect(x1, y1, 4, 8);
        y1 += 14;
        g.fillRect(x1, y1, 4, 8);
        y1 += 14;
        g.fillRect(x1, y1, 4, 8);

        x1 = 52;
        y1 = 12;
        g.fillRect(x1, y1, 4, 8);
        y1 += 14;
        g.fillRect(x1, y1, 4, 8);
        y1 += 14;
        g.fillRect(x1, y1, 4, 8);

        x1 = 21;
        y1 = 8;
        g.drawRect(x1, y1, 31, 44);

    }
}
