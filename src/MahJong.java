import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MahJong extends JFrame {

//    private JPanel tilePanel = new MahJongBoard();

    public static TileDeck deck = new TileDeck();

    static {
        deck.shuffle();
    }

    public MahJong() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MahJong Game");
        setSize(1300, 700);
        setPreferredSize(new Dimension(1300, 700));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        add(new MahJongBoard(), BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public class MahJongBoard extends JPanel implements MouseListener {

        public MahJongModel model = new MahJongModel();
        public ImageIcon background;
        private Image backgroundImage;

        public MahJongBoard() {

            try {
                background = new ImageIcon(getClass().getResource("images/dragon_bg.png"));
                backgroundImage = background.getImage();
            } catch (NullPointerException ex) {
                System.out.println("Background image not found.");
            }

            setLayout(null);
            drawBoard();

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                g.drawImage(backgroundImage, 0, 0, this);
            } catch (NullPointerException ex) {
                System.out.println("Background image not painted.");
            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            Tile t = (Tile)e.getSource();

            // TODO: Not really a todo just nice to have my debug marked
//            System.out.println("tile on top: " + t.getTileOnTop());
//            System.out.println("clickable: " + t.getClickable());

            if (t.getClickable() && !t.getTileOnTop()) {

                // no tile selected
                if (model.getTileClicked() == null) {
                    model.setTileClicked(t);
                    tileSelected(t);

                // tile selected already
                } else {

                    // second selected tile matches first
                    if (t.matches(model.getTileClicked())) {

                        // remove them
                        model.getTileClicked().setInvisible();
                        t.setInvisible();
                        remove(model.getTileClicked());
                        remove(t);

                        // update tiles
                        updateClickabilities(model.getTileClicked());
                        updateClickabilities(t);

                        // deselect initially selected tile
                        tileDeselected(model.getTileClicked());
                        model.unsetTileClicked();

                    // second selected tile does not match first
                    } else {

                        // set new selected tile and remove previous selected visuals
                        model.setTileClicked(t);
                        tileSelected(t);
                    }
                }
            }

            repaint();
        }

        public void tileSelected(Tile t) {
            // TODO: Visual change for selected tile
        }

        public void tileDeselected(Tile t) {
            // TODO: Visual change for deselected tile
        }

        public void updateClickabilities(Tile t) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            // top tile removed
            if (t.getPosZ() == 4) {
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        model.secondLayer.layerRows.get(i).rowTiles.get(j).setClickable();
                    }
                }
            }

            // far left tile removed
            else if (t == model.leftExtra.layerRows.get(0).rowTiles.get(0)) {
                for (int i = 3; i < 5; i++) {
                    model.bottomLayer.layerRows.get(i).rowTiles.get(0).setClickable();
                }

            }

            // far right tile removed
            else if (t == model.rightExtras.layerRows.get(0).rowTiles.get(1)) {
                model.rightExtras.layerRows.get(0).rowTiles.get(0).setClickable();
            }

            // second far right tile removed
            else if (t == model.rightExtras.layerRows.get(0).rowTiles.get(0)) {
                for (int i = 3; i < 5; i++) {
                    model.bottomLayer.layerRows.get(i).rowTiles.get(11).setClickable();
                }

            // general scenario
            } else {
                double y = t.getPosY();
                int z = (t.getPosZ() - 4) * -1; // remember how I added them backwards to the array? Yup.

                // vars
                TileRow row = model.tileLayers.get(z).layerRows.get((int)(y - t.getPosZ()));
                int index = row.rowTiles.indexOf(t);

                // left most in row
                if (index - 1 < 0) {
                    row.rowTiles.get(index + 1).setClickable();

                // right most in row
                } else if (index + 1 == row.rowTiles.size()) {
                    row.rowTiles.get(index - 1).setClickable();

                // left is hidden
                } else if (!row.rowTiles.get(index - 1).getVisibility()) {

                    // check if at edge
                    if (index + 1 <= row.rowTiles.size() - 1) {
                        row.rowTiles.get(index + 1).setClickable();
                    }

                // right is hidden
                } else if (!row.rowTiles.get(index + 1).getVisibility()) {

                    // check if at edge
                    if (index - 1 >= 0) {
                        row.rowTiles.get(index - 1).setClickable();
                    }
                }

                // updating tile below when one is removed
                try {

                    // vars
                    TileRow rowToReveal = model.tileLayers.get(z + 1).layerRows.get((int) (y - t.getPosZ() + 1));

                    // index 8 is highest of any covered tiles in arraylist
                    for (int i = 0; i < 9; i++) {
                        // found the tile below
                        if (rowToReveal.rowTiles.get(i).getPosX() == row.rowTiles.get(index).getPosX()) {
                            rowToReveal.rowTiles.get(i).removeTileOnTop();
                            break;
                        }
                    }
                } catch (IndexOutOfBoundsException ex){
                }

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

            // nice lil indicator for the user that they can click the one they hover over
            Tile t = (Tile)e.getSource();

            if (t.getClickable() && !t.getTileOnTop()) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        public void drawBoard() {
            // used for displaying iteratively
            TileLayer layer;
            TileRow row;
            Tile t;

            // display left extra
            layer = model.leftExtra;
            row = layer.layerRows.get(0);
            t = row.rowTiles.get(0);
            t.setLocation((int) (t.getPosX() * Tile.WIDTH + t.getPosZ() * 10), (int) (t.getPosY() * (Tile.HEIGHT - 5) - t.getPosZ() * 10));
            t.addMouseListener(this);
            add(t);

            // 10 less x and 10 more y for each lower layer
            // display main board
            // layers
            for (int i = 0; i < model.tileLayers.size(); i++) {
                layer = model.tileLayers.get(i);
                // rows
                for (int j = layer.layerRows.size() - 1; j >= 0; j--) {
                    row = layer.layerRows.get(j);
                    // tiles
                    for (int k = 0; k < row.rowTiles.size(); k++) {
                        t = row.rowTiles.get(k);

                        // reeeeeeee
                        t.setLocation((int) (t.getPosX() * Tile.WIDTH + t.getPosZ() * 10), (int) (t.getPosY() * (Tile.HEIGHT - 5) - t.getPosZ() * 10));
                        t.setBoardLocation(t.getLocation());

                        t.addMouseListener(this);
                        add(t);
                    }
                }

            }

            // display right extras
            layer = model.rightExtras;
            row = layer.layerRows.get(0);
            for (int i = 0; i < 2; i++) {
                t = row.rowTiles.get(i);
                t.setLocation((int) (t.getPosX() * Tile.WIDTH + t.getPosZ() * 10), (int) (t.getPosY() * (Tile.HEIGHT - 5) - t.getPosZ() * 10));
                t.addMouseListener(this);
                add(t);
            }

        }

    }

    public static void main(String[] args) {
        new MahJong();


    }
}
