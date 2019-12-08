import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MahJong extends JFrame implements ActionListener {

//    private JPanel tilePanel = new MahJongBoard();

//    public static TileDeck deck = new TileDeck();
    private JPanel welcomeLayout = new JPanel();
    private MahJongBoard gameBoard;
    private boolean gameStarted = false;
    private int totalMoves = 0;
    private int redoMoves = 0;
    private ArrayList<Tile> tilesRemoved = new ArrayList<>();
    private ArrayList<Tile> tilesToRedo = new ArrayList<>();

    public void incrementTotalMoves() { totalMoves++; }

    public MahJong() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MahJong Game");
        setSize(760, 540);
        setPreferredSize(new Dimension(1300, 700));

        makeMenu();

        // closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // landing page
        JLabel welcomeText = new JLabel("Welome to MahJong!");
        JButton newGameButton = new JButton("New Game");

        welcomeLayout.setLayout(new BoxLayout(welcomeLayout, BoxLayout.Y_AXIS));
        welcomeLayout.add(welcomeText);
        newGameButton.addActionListener(this);
        welcomeLayout.add(newGameButton);

        add(welcomeLayout);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        // new game pressed
        if (buttonPressed.equals("New Game")) {

            // begin game if on landing screen
            if (!gameStarted) {
                gameStarted = true;
                remove(welcomeLayout);
                newGame();

            // start new game if one is in progress
            } else {
                //TODO: implemement JOPtionPane for confirmation
                remove(gameBoard);
                gameBoard = new MahJongBoard();
                add(gameBoard);
            }

            repaint();
            setVisible(true);

        // restart current game
        } else if (buttonPressed.equals("Restart")) {

            // button pressed before game started, result in new game
            if (!gameStarted) {
                gameStarted = true;
                newGame();
            } else {
                unravel();
            }
        } else if (buttonPressed.equals("Undo")) {
            undo();
        } else if (buttonPressed.equals("Redo")) {
            redo();
        }
    }

    public void newGame() {
        // reset game state variables
        totalMoves = 0;
        redoMoves = 0;
        tilesRemoved.clear();
        gameBoard = new MahJongBoard();
        gameStarted = true;
        add(gameBoard);
    }

    public void unravel() {
        while (totalMoves > 0) {
            undo();
        }
    }

    public void undo() {

        // only if there's a move to undo
        if (totalMoves > 0) {

            for (int i = 0; i < 2; i++) {
                tilesRemoved.get(tilesRemoved.size() - 1).setVisible();
                tilesToRedo.add(tilesRemoved.get(tilesRemoved.size() - 1));
                tilesRemoved.remove(tilesRemoved.size() - 1);
            }
            redoMoves++;
            totalMoves--;

        }

        repaint();
    }

    public void redo() {

        //TODO: implement redo
        if (redoMoves > 0) {
            for (int i = 0; i < 2; i++) {
                tilesToRedo.get(tilesToRedo.size() - 1).setInvisible();
                tilesRemoved.add(tilesToRedo.get(tilesToRedo.size() -  1));
                tilesToRedo.remove(tilesToRedo.size() - 1);
            }
            redoMoves--;
            totalMoves++;
        }

        repaint();
    }

    public void makeMenu() {
        JMenuBar menuBar = new JMenuBar();

        // game menu
        JMenu menu = new JMenu("Game");
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Restart");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("New Numbered Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("New Tournament Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // sound menu
        menu = new JMenu("Sound");
        menuBar.add(menu);

        menuItem = new JMenuItem("On");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Off");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // move menu
        menu = new JMenu("Move");
        menuBar.add(menu);

        menuItem = new JMenuItem("Undo");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Redo");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // help menu

        menu = new JMenu("Help");
        menuBar.add(menu);

        menuItem = new JMenuItem("How To Play");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Game Rules");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        setJMenuBar(menuBar);
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

            // selectable
            if (t.getClickable() && !t.getTileOnTop()) {

                // no tile selected
                if (model.getTileClicked() == null) {
                    model.setTileClicked(t);
                    t.setSelected();

                // tile selected already
                } else {

                    // second selected tile matches first
                    if (t.matches(model.getTileClicked())) {

                        // remove them
                        model.getTileClicked().setInvisible();
                        t.setInvisible();

                        // add to removed tiles for undoing capability
                        tilesRemoved.add(model.getTileClicked());
                        tilesRemoved.add(t);
                        incrementTotalMoves();
                        redoMoves = 0;

                        // update tiles
                        updateClickabilities(model.getTileClicked());
                        updateClickabilities(t);

                        // deselect initially selected tile
                        model.getTileClicked().setDeselected();
                        t.setDeselected();
                        model.unsetTileClicked();

                    // second selected tile does not match first
                    } else {

                        // set new selected tile and remove previous selected visuals
                        model.getTileClicked().setDeselected();
                        model.setTileClicked(t);
                        t.setSelected();
                    }
                }
            }

            repaint();
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
