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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MahJong Game");
        setSize(1300, 700);

//        constraints.ipadx = 5;
//        constraints.ipady = 5;
//        constraints.insets = new Insets(5, 5, 5, 5);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        add(new MahJongBoard());


//        pack();
        setVisible(true);
    }

    public class MahJongBoard extends JPanel implements MouseListener {

        public MahJongModel model = new MahJongModel();

        public MahJongBoard() {

            setLayout(null);

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
            for (int i = 0; i < model.tileLayers.size(); i++) {
                layer = model.tileLayers.get(i);
                for (int j = layer.layerRows.size() - 1; j >= 0; j--) {
                    row = layer.layerRows.get(j);
                    for (int k = 0; k < row.rowTiles.size(); k++) {
                        t = row.rowTiles.get(k);

                        t.setLocation((int) (t.getPosX() * Tile.WIDTH + t.getPosZ() * 10), (int) (t.getPosY() * (Tile.HEIGHT - 5) - t.getPosZ() * 10));

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

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            Tile t = (Tile)e.getSource();
            boolean removed = false;

            System.out.println("clicked clickable: " + t.getClickable());
            System.out.println("clicked tileOnTop: " + t.getTileOnTop());

            if (t.getClickable() && !t.getTileOnTop()) {

                // check for visibility just cause
                if (t.getVisibility() ) {
                    t.setInvisible();
                    remove(t);
                    removed = true;
                }
            }

            if (removed) {
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
                    double x = t.getPosX();
                    double y = t.getPosY();
                    int z = (t.getPosZ() - 4) * -1; // layers 0-4 signify top to bottom

                    TileRow row = model.tileLayers.get(z).layerRows.get((int)(y - t.getPosZ()));
                    int index = row.rowTiles.indexOf(t);
                    // far left in row
                    if (index - 1 < 0) {
                        row.rowTiles.get(index + 1).setClickable();

                    // right most in row
                    } else if (index + 1 == row.rowTiles.size()) {
                        row.rowTiles.get(index - 1).setClickable();

                    // left is invisible
                    } else if (!row.rowTiles.get(index - 1).getVisibility()) {
                        if (index + 1 <= row.rowTiles.size() - 1) {
                            row.rowTiles.get(index + 1).setClickable();
                        }
                    } else if (!row.rowTiles.get(index + 1).getVisibility()) {
                        if (index - 1 >= 0) {
                            row.rowTiles.get(index - 1).setClickable();
                        }
                    }

                    try {
                        TileRow rowToReveal = model.tileLayers.get(z + 1).layerRows.get((int) (y - t.getPosZ() + 1));
                        int indexToReveal = 0;
                        while (indexToReveal < 9) {
                            if (rowToReveal.rowTiles.get(indexToReveal).getPosX() == row.rowTiles.get(index).getPosX()) {
                                rowToReveal.rowTiles.get(indexToReveal).removeTileOnTop();
                                break;
                            }
                            indexToReveal++;
                        }
                    } catch (IndexOutOfBoundsException ex) {

                    }

                }
            }

            repaint();

            //TODO: Check for ability to remove;
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Tile t = (Tile)e.getSource();
            if (t.getClickable() && !t.getTileOnTop()) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

//        public void drawBoard() {
//            Tile currentTile = deck.deal();
//            model.setTilePositions(currentTile, 0, 0, 0);
//            currentTile.setLocation((currentTile.getPosX() + currentTile.getPosZ() * 20) - 40, (currentTile.getPosY() * 70 - currentTile.getPosZ() * 20) - 30);
////        currentTile.addMouseListener(this);
//            add(currentTile);
//            revalidate();
//            repaint();
//
//        }

    }

    public static void main(String[] args) {
        new MahJong();


    }
}
