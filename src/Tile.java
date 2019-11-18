import java.awt.*;
import javax.swing.*;
import java.util.*;



public class Tile extends JPanel {

    // constants
    protected static final int WIDTH = 50;
    protected static final int HEIGHT = 65;
    protected static Dimension dimension = new Dimension(WIDTH, HEIGHT);

    protected static final Color RED = Color.decode("#CC2C1B");
    protected static final Color BLUE = Color.decode("#3B3DF4");
    protected static final Color GREEN = Color.decode("#43B317");
    protected static final Color TAN = Color.decode("#FFF1D3");
    protected static final Color BLACK = Color.BLACK;
    protected static final Color WHITE = Color.WHITE;
    protected static final Color LGRAY = Color.lightGray;

    static ArrayList<GradientPaint> gradients = new ArrayList<>();
    static ArrayList<int[]> coordinates1 = new ArrayList<>();
    static ArrayList<int[]> coordinates2 = new ArrayList<>();

    static {
        gradients.add(new GradientPaint(0, HEIGHT, GREEN, 0, HEIGHT - 85, BLACK));
        gradients.add(new GradientPaint(0, 0, GREEN, WIDTH + 35, 0, LGRAY));
        gradients.add(new GradientPaint(0, HEIGHT, TAN, HEIGHT - 85, 0, BLACK));
        gradients.add(new GradientPaint(WIDTH, 0, LGRAY, 10, HEIGHT, TAN));
        coordinates1.add(new int[] {0, 5, 5, 0});
        coordinates2.add(new int[] {10, 5, HEIGHT, HEIGHT + 5});
        coordinates1.add(new int[] {0, 5, WIDTH + 5, WIDTH});
        coordinates2.add(new int[] {HEIGHT + 5, HEIGHT, HEIGHT, HEIGHT + 5});
        coordinates1.add(new int[] {0, 5, 5, 0});
        coordinates2.add(new int[] {10, 5, HEIGHT, HEIGHT + 5});
        coordinates1.add(new int[] {0, 5, WIDTH + 5, WIDTH});
        coordinates2.add(new int[] {HEIGHT + 5, HEIGHT, HEIGHT, HEIGHT + 5});
        coordinates1.add(new int[] {5, 10, 10, 5});
        coordinates2.add(new int[] {5, 0, HEIGHT - 5, HEIGHT});
        coordinates1.add(new int[] {5, 10, WIDTH + 10, WIDTH + 5});
        coordinates2.add(new int[] {HEIGHT, HEIGHT - 5, HEIGHT - 5, HEIGHT});
        coordinates1.add(new int[] {5, 10, 10, 5});
        coordinates2.add(new int[] {5, 0, HEIGHT - 5, HEIGHT});
        coordinates1.add(new int[] {5, 10, WIDTH + 10, WIDTH + 5});
        coordinates2.add(new int[] {HEIGHT, HEIGHT - 5, HEIGHT - 5, HEIGHT});
    }



    public Tile() {
        setToolTipText(toString());

        // set size based off constants
        System.out.println(WIDTH);
        System.out.println(HEIGHT);

        setPreferredSize(dimension);


    }


    public boolean matches(Tile other) {

        // check for same tile and null
        if (this == other || other == null) {
            return false;
        }

        // return true only if the same class (tile)
        return other.getClass() == this.getClass();
    }

    @Override
    protected void paintComponent(Graphics g) {

        // call constructor for paint component
        super.paintComponent(g);

        //TODO: figure out how to put this in constructor without messing up display
        setSize(WIDTH + 10, HEIGHT + 10);

        // 2d graphics, gradient and shading will control depth
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(gradients.get(0));
        g2d.fillPolygon(coordinates1.get(0), coordinates2.get(0), 4);

        g2d.setPaint(gradients.get(1));
        g2d.fillPolygon(coordinates1.get(1), coordinates2.get(1), 4);

        g2d.setColor(BLACK);
        g2d.drawPolygon(coordinates1.get(2), coordinates2.get(2), 4);
        g2d.drawPolygon(coordinates1.get(3), coordinates2.get(3), 4);

        g2d.setPaint(gradients.get(2));
        g2d.fillPolygon(coordinates1.get(4), coordinates2.get(4), 4);

        g2d.setColor(TAN);
        g2d.fillPolygon(coordinates1.get(5), coordinates2.get(5), 4);

        g2d.setColor(BLACK);
        g2d.drawPolygon(coordinates1.get(6), coordinates2.get(6), 4);
        g2d.drawPolygon(coordinates1.get(7), coordinates2.get(7), 4);

        g2d.setPaint(gradients.get(3));
        g2d.fillRect(10, 0, WIDTH, HEIGHT - 5);
        g2d.setColor(BLACK);
        g2d.drawRect(10, 0, WIDTH, HEIGHT - 5);
    }
}
