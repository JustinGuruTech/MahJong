import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class BambooTile extends RankTile {

    public BambooTile(int rank) {
        super(rank);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x1;
        int y1;
        if (rank == 2) {
            x1 = (WIDTH - 10) / 2 + 10;
            y1 = 12;
            drawBamboo(x1, y1, GREEN, g);
            y1 = 32;
            drawBamboo(x1, y1, GREEN, g);
        } else if (rank == 3) {
            x1 = (WIDTH - 10) / 2 + 10;
            y1 = 12;
            drawBamboo(x1, y1, GREEN, g);
            y1 = 31;
            x1 -= 12;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 24;
            drawBamboo(x1, y1, GREEN, g);
        } else if (rank == 4) {
            x1 = (WIDTH - 10) / 2 - 2; // 12 left from center
            y1 = 12;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 24;
            drawBamboo(x1, y1, GREEN, g);
            y1 = 32;
            drawBamboo(x1, y1, GREEN, g);
            x1 -= 24;
            drawBamboo(x1, y1, GREEN, g);
        } else if (rank == 5) {
            x1 = (WIDTH - 10) / 2 - 4; // 14 left from center
            y1 = 12;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 28;
            drawBamboo(x1, y1, GREEN, g);
            x1 = (WIDTH - 10) / 2 + 10; // center
            y1 = 22;
            drawBamboo(x1, y1, RED, g);
            x1 -= 14;
            y1 = 32;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 28;
            drawBamboo(x1, y1, GREEN, g);
        } else if (rank == 6) {
            x1 = (WIDTH - 10) / 2 - 4; // 14 left from center
            y1 = 12;
            for (int i = 0; i < 3; i++) {
                drawBamboo(x1, y1, GREEN, g);
                x1 += 14;
            }
            x1 = (WIDTH - 10) / 2 - 4;
            y1 = 32;
            for (int i = 0; i < 3; i++) {
                drawBamboo(x1, y1, GREEN, g);
                x1 += 14;
            }
        } else if (rank == 7) {
            x1 = (WIDTH - 10) / 2 + 10; // center
            y1 = 2;
            drawBamboo(x1, y1, RED, g);
            y1 += 18;
            drawBamboo(x1, y1, GREEN, g);
            x1 -= 14;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 28;
            drawBamboo(x1, y1, GREEN, g);
            y1 += 18;
            drawBamboo(x1, y1, GREEN, g);
            x1 -= 14;
            drawBamboo(x1, y1, GREEN, g);
            x1 -= 14;
            drawBamboo(x1, y1, GREEN, g);
        } else if (rank == 8) {
            x1 = (WIDTH - 10) / 2 + 10; // center
            y1 = 2;
            drawBamboo(x1, y1, GREEN, g);
            x1 -= 14;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 28;
            drawBamboo(x1, y1, GREEN, g);
            y1 += 36;
            drawBamboo(x1, y1, GREEN, g);
            x1 -= 14;
            drawBamboo(x1, y1, GREEN, g);
            x1 -= 14;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 7;
            y1 -= 18;
            drawBamboo(x1, y1, GREEN, g);
            x1 += 14;
            drawBamboo(x1, y1, GREEN, g);


        } else if (rank == 9) {

            y1 = 2;
            x1 = (WIDTH - 10) / 2 - 4;
            Color[] colorsTemp = {GREEN, RED, GREEN};
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    drawBamboo(x1, y1, colorsTemp[j], g);
                    x1 += 14;
                }
                x1 = (WIDTH - 10) / 2 - 4;
                y1 += 18;
            }
        }
    }

    private void drawBamboo(int x1, int y1, Color color, Graphics g) {
        g.setColor(color);
        // bamboo base
        g.fillRect(x1 + 2, y1 + 1, 4, 14);
        // 3 bamboo ovals
        g.fillOval(x1, y1, 8, 4);
        g.fillOval(x1, y1 + 7, 8, 4);
        g.fillOval(x1, y1 + 14, 8, 4);
        g.setColor(WHITE);
        g.drawLine(x1 + 4, y1 + 3, x1 + 4, y1 + 6);
        g.drawLine(x1 + 4, y1 + 11, x1 + 4, y1 + 14);
    }

    public String toString() {

        return "Bamboo " + this.rank;
    }
}
