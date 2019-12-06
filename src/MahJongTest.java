import java.awt.*;
import javax.swing.*;

public class MahJongTest extends JFrame {

    public MahJongTest() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        add(new TestPanel());

        setSize(500, 500);
        setVisible(true);
    }

    public class TestPanel extends JPanel {
        public TestPanel() {
//            setBackground(Color.RED);
            setLayout(null);
            Tile t;

            t = new SeasonTile("Summer");
            t.setLocation(210, 90);
            add(t);


            t = new SeasonTile("Spring");
            t.setLocation(200, 100);
            add(t);


        }
    }

    public static void main(String[] args) {
        new MahJongTest();
    }

}
