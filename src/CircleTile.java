import	java.awt.*;

public class CircleTile extends RankTile {

    public CircleTile(int rank) {

        super(rank);
        setToolTipText(toString());
    }

    public String toString() {

        return "Circle " + this.rank;
    }

    @Override
    protected void paintComponent(Graphics g) {

        // preliminary stuff pretty much
        super.paintComponent(g);

        // only draw if visible
        if (getVisibility()) {
            int size;
            int x1;
            int y1;
            // regretting making my tiles so small right about now
            if (rank == 1) {

                // big green circle
                size = 46;
                g.setColor(GREEN);
                g.fillOval(12, 9, size, size);
                g.setColor(BLACK);
                g.drawOval(12, 9, size, size);

                // little red circle
                size = 20;
                g.setColor(RED);
                g.fillOval(25, 22, size, size);
                g.setColor(WHITE);
                g.drawOval(25, 22, size, size);


                // draw x
                drawX(35, 32, 6, g);

                // surrounding circles
                size = 5;
                // this whole pi thing feels hacky but it gets the job done
                double rotation = 2 * Math.PI / 12;
                for (int circle = 0; circle < 12; circle++) {
                    double angularPosition = circle * rotation;
                    g.setColor(WHITE);
                    g.fillOval((int) (33 + Math.cos(angularPosition) * 17),
                            (int) (29 + Math.sin(angularPosition) * 17),
                            size, size);
                }
            } else if (rank == 2) {

                size = 20;

                // top circle
                x1 = 25;
                y1 = 6;
                drawCircle(BLUE, x1, y1, size, 7, g);

                // bottom circle
                y1 = 32;
                drawCircle(GREEN, x1, y1, size, 7, g);

            } else if (rank == 3) {
                size = 16;
                x1 = 14;
                y1 = 6;
                drawCircle(BLUE, x1, y1, size, 5, g);
                x1 = 27;
                y1 = 22;
                drawCircle(RED, x1, y1, size, 5, g);
                x1 = 40;
                y1 = 38;
                drawCircle(GREEN, x1, y1, size, 5, g);
            } else if (rank == 4) {
                size = 20;
                x1 = 13;
                y1 = 8;
                drawCircle(GREEN, x1, y1, size, 7, g);
                x1 = 37;
                drawCircle(BLUE, x1, y1, size, 7, g);
                y1 = 32;
                x1 = 13;
                drawCircle(BLUE, x1, y1, size, 7, g);
                x1 = 37;
                drawCircle(GREEN, x1, y1, size, 7, g);

            } else if (rank == 5) {
                size = 16;
                x1 = 14;
                y1 = 5;
                drawCircle(GREEN, x1, y1, size, 5, g);
                x1 = 40;
                drawCircle(BLUE, x1, y1, size, 5, g);
                y1 = 37;
                drawCircle(GREEN, x1, y1, size, 5, g);
                x1 = 14;
                drawCircle(BLUE, x1, y1, size, 5, g);
                y1 = 21;
                x1 = 27;
                drawCircle(RED, x1, y1, size, 5, g);

            } else if (rank == 6) {
                size = 16;
                x1 = 16;
                y1 = 4;
                drawCircle(GREEN, x1, y1, size, 5, g);
                x1 = 38;
                drawCircle(GREEN, x1, y1, size, 5, g);
                y1 = 22;
                drawCircle(RED, x1, y1, size, 5, g);
                x1 = 16;
                drawCircle(RED, x1, y1, size, 5, g);
                y1 = 39;
                drawCircle(RED, x1, y1, size, 5, g);
                x1 = 38;
                drawCircle(RED, x1, y1, size, 5, g);
            } else if (rank == 7) {
                size = 10;
                x1 = 14;
                y1 = 4;
                drawCircle(GREEN, x1, y1, size, 3, g);
                x1 = 30;
                y1 = 10;
                drawCircle(GREEN, x1, y1, size, 3, g);
                x1 = 46;
                y1 = 16;
                drawCircle(GREEN, x1, y1, size, 3, g);
                x1 = 20;
                y1 = 30;
                drawCircle(RED, x1, y1, size, 3, g);
                x1 = 40;
                drawCircle(RED, x1, y1, size, 3, g);
                y1 = 44;
                drawCircle(RED, x1, y1, size, 3, g);
                x1 = 20;
                drawCircle(RED, x1, y1, size, 3, g);

            } else if (rank == 8) {
                size = 12;
                y1 = 3;
                for (int i = 0; i < 4; i++) {
                    drawCircle(BLUE, 20, y1, size, 3, g);
                    drawCircle(BLUE, 40, y1, size, 3, g);
                    y1 += 14;
                }
            } else if (rank == 9) {
                size = 16;
                x1 = 12;
                y1 = 2;
                Color[] colorsTemp = {BLUE, RED, GREEN};
                // loop within a loop typically no bueno but fine since I know it will only run 9 times
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        drawCircle(colorsTemp[i], x1, y1, size, 5, g);
                        x1 += 15;
                    }
                    x1 = 12;
                    y1 += 20;
                }
            }
        }
    }

    private void drawX(int originX, int originY, int radius, Graphics g) {
        g.drawLine(originX - radius, originY - radius, originX + radius, originY + radius);
        g.drawLine(originX - radius, originY + radius, originX + radius, originY - radius);
    }

    private void drawCircle(Color color, int x1, int y1, int size, int radius, Graphics g) {
        // circle
        g.setColor(color);
        g.fillOval(x1, y1, size, size);

        // x
        g.setColor(WHITE);
        // g.drawOval(x1, y1, size, size);
        drawX(x1 + (size / 2), y1 + (size / 2), radius, g);

    }

}
