import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class MahJong extends JFrame implements ActionListener {

    private JPanel welcomeLayout = new JPanel();
    public MahJongBoard gameBoard;
    private boolean gameStarted = false;
    private int totalMoves = 0;
    private int redoMoves = 0;
    private boolean tournamentMode = false;
    private ArrayList<Tile> tilesRemoved = new ArrayList<>();
    private ArrayList<Tile> tilesToRedo = new ArrayList<>();
    private JScrollPane discardPane = new JScrollPane();
    private JPanel cardColumn = new JPanel();
    private ArrayList<JPanel> cardPanels = new ArrayList<>();
    private Help howToPlay = new Help("html/how-to-play.html", "Help");
    private Help gameRules = new Help("html/game-rules.html", "Game Rules");

    public void incrementTotalMoves() { totalMoves++; }

    public MahJong() {

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("MahJong Game");
        setSize(1000, 540);
        setPreferredSize(new Dimension(1000, 540));

        makeMenu();

        // closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(welcomeLayout, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // landing page
        JLabel welcomeText = new JLabel("Welome to MahJong!");
        JButton newGameButton = new JButton("New Game");

        // scroll pane setup
        discardPane.setPreferredSize(new Dimension(180, 0));
        discardPane.setBorder(BorderFactory.createRaisedBevelBorder());
        discardPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        discardPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // box layout setup
        cardColumn.setLayout(new BoxLayout(cardColumn, BoxLayout.Y_AXIS));

        // panel containing cards added to scroll view
        JPanel cardPanel = new JPanel(new BorderLayout());
        discardPane.setViewportView(cardPanel);

        // add box layout and scroll view
        cardPanel.add(cardColumn);
        add(discardPane, BorderLayout.EAST);

        // welcome screen
        welcomeLayout.setLayout(new BoxLayout(welcomeLayout, BoxLayout.Y_AXIS));
        welcomeLayout.add(welcomeText);
        newGameButton.addActionListener(this);
        welcomeLayout.add(newGameButton);
        add(welcomeLayout, BorderLayout.CENTER);

        // let's goooo
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
                // ask for confirmation
                if (JOptionPane.showConfirmDialog(this, "Are you sure you want to start a new game?", "Confirm New Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    tournamentMode = false;
                    remove(gameBoard);
                    newGame();
                }
            }

            setVisible(true);

        // restart current game
        } else if (buttonPressed.equals("Restart")) {
            if (!tournamentMode) {
                if (JOptionPane.showConfirmDialog(this, "Are you sure you want to restart your current game?", "Confirm Restart", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    restartGame();
                }
            }
        } else if (buttonPressed.equals("New Numbered Game")) {
            try {
                int gameSeed = Integer.parseInt(JOptionPane.showInputDialog("Enter Game Seed:"));

                if (JOptionPane.showConfirmDialog(this, "Are you sure you want to start a new game from this seed?", "Confirm New Seeded Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (!gameStarted) {
                        gameStarted = true;
                        remove(welcomeLayout);
                    } else if (gameStarted) {
                        remove(gameBoard);
                    }
                    newGame(gameSeed);
                }
            } catch (NumberFormatException ex) {
                System.out.println("invalid seed");
                JOptionPane.showMessageDialog(this, "Invalid Seed Number", "Direction", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (buttonPressed.equals("Undo")) {
            if (gameStarted && !tournamentMode) {
                undo();
            }
        } else if (buttonPressed.equals("Redo")) {
            if (gameStarted && !tournamentMode) {
                redo();
            }
        } else if (buttonPressed.equals("How To Play")) {
            showHowToPlay();
        } else if (buttonPressed.equals("Game Rules")) {
            showGameRules();
        } else if (buttonPressed.equals("New Tournament Game")) {

            tournamentMode = true; // set tournament mode

            // begin game if on landing screen
            if (!gameStarted) {
                if (JOptionPane.showConfirmDialog(this, "Are you sure you want to start a tournament game? \nUndo/Redo and displaying discards will be disabled.", "Confirm Tournament Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    gameStarted = true;
                    remove(welcomeLayout);
                    newGame();
                }

            // start new game if one is in progress
            } else {
                if (JOptionPane.showConfirmDialog(this, "Are you sure you want to start a tournament game? \nUndo/Redo and displaying discards will be disabled.", "Confirm Tournament Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    remove(gameBoard);
                    newGame();
                    gameBoard.drawDiscards();
                }

            }
            setVisible(true);
        }
    }

    public void newGame() {

        // choose game number and call constructor with it
        long gameNumber = System.currentTimeMillis() % 100000;
        newGame(gameNumber);
    }

    public void newGame(long gameNumber) {

        setTitle("Mahjong Game - Game Number " + gameNumber);

        // reset game variables
        totalMoves = 0;
        redoMoves = 0;
        tilesRemoved.clear();
        cardPanels.clear();
        gameStarted = true;

        // new game board
        gameBoard = new MahJongBoard(gameNumber);
        add(gameBoard);
        repaint();
        setVisible(true);
        if (!tournamentMode) {
            gameBoard.drawDiscards();
        }
    }

    public void restartGame() {

        // button pressed before game start results in new game
        if (!gameStarted) {
            gameStarted = true;
            newGame();
        } else {
            // undo stack entirely
            while (totalMoves > 0) {
                undo();
            }
        }
    }

    public void undo() {

        // only if there's a move to undo
        if (totalMoves > 0) {

            // store tiles that need to be updated
            Tile t1 = tilesRemoved.get(tilesRemoved.size() - 1);
            Tile t2 = tilesRemoved.get(tilesRemoved.size() - 2);

            // twice for each move (two tiles per move)
            for (int i = 0; i < 2; i++) {
                tilesRemoved.get(tilesRemoved.size() - 1).setVisible(); // make tile visible
                tilesToRedo.add(tilesRemoved.get(tilesRemoved.size() - 1)); // add tile to redo stack
                tilesRemoved.remove(tilesRemoved.size() - 1); // remove tile from removed stack
            }
            redoMoves++; // increment redo moves allowed
            totalMoves--; // decrement total moves

            // update discards
            removeDiscard();
            if (!tournamentMode) {
                gameBoard.drawDiscards();
            }

            // put tiles back
            t1.setLocation(t1.getBoardLocation());
            t2.setLocation(t2.getBoardLocation());
            // update clickabilities of affected tiles
            gameBoard.updateClickabilities(t1);
            gameBoard.updateClickabilities(t2);
            repaint();

            // if a tile is selected when they undo reset it
            if (gameBoard.model.getTileClicked() != null) {
                resetClickedTile();
            }

        }
    }

    public void redo() {

        // moves to redo
        if (redoMoves > 0) {

            // store tiles to redo and discard them
            Tile t1 = tilesToRedo.get(tilesToRedo.size() - 1);
            Tile t2 = tilesToRedo.get(tilesToRedo.size() - 2);
            discard(t1, t2);

            // twice for each move (two tiles per move)
            for (int i = 0; i < 2; i++) {
                tilesToRedo.get(tilesToRedo.size() - 1).setInvisible(); // make tile invisible
                tilesRemoved.add(tilesToRedo.get(tilesToRedo.size() -  1)); // add tile to removed stack
                tilesToRedo.remove(tilesToRedo.size() - 1); // remove tile from redo stack
            }
            redoMoves--; // decrement redo moves allowed
            totalMoves++; // increment total moves

            // update discards and clickabilities of affected tiles
            if (!tournamentMode) {
                gameBoard.drawDiscards();
            }
            gameBoard.updateClickabilities(t1);
            gameBoard.updateClickabilities(t2);
            repaint();

            // if a tile is selected when they redo reset it
            if (gameBoard.model.getTileClicked() != null) {
                resetClickedTile();
            }
        }
    }

    public void resetClickedTile() {
        gameBoard.model.getTileClicked().setDeselected();
        gameBoard.model.unsetTileClicked();
    }

    public void discard(Tile t1, Tile t2) {

        // make panel from discarded
        JPanel cardPanel = new JPanel(new FlowLayout());
        cardPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        cardPanel.add(t1, FlowLayout.LEFT);
        cardPanel.add(t2, FlowLayout.LEFT);
        cardPanels.add(cardPanel);

    }

    public void removeDiscard() {
        cardPanels.remove(cardPanels.size() - 1);
    }

    public void showHowToPlay() {
        howToPlay.display();
    }

    public void showGameRules() {
        gameRules.display();
    }

    public void tournamentConfirmation() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to start a tournament game? \nUndo/Redo and displaying discards will be disabled.", "Confirm Tournament Game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            remove(gameBoard);
            newGame();
        }
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

        JMenuItem menuItemSave = new JMenuItem();

        menuItem = new JMenuItem("Undo");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // keyboard shortcut for undo
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));

        menuItem = new JMenuItem("Redo");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // keyboard shortcut for redo
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));

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

        public MahJongModel model;
        public ImageIcon background;
        private Image backgroundImage;

        public MahJongBoard(long gameNumber) {
            model = new MahJongModel(gameNumber);
            drawBackground();
        }

        public void drawBackground() {
            try {
                background = new ImageIcon(getClass().getResource("images/dragon_bg.png"));
                backgroundImage = background.getImage();
            } catch (NullPointerException ex) {
                System.out.println("Background image not found.");
            }

            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                g.drawImage(backgroundImage, 0, 0, this);
            } catch (NullPointerException ex) {
                System.out.println("Background image not painted.");
            }
            drawBoard();

        }


        @Override
        public void mouseClicked(MouseEvent e) {
            Tile t = (Tile)e.getSource();
            tileClicked(t);
        }

        public void tileClicked(Tile t) {
            // selectable
            if ((t.getTileOpenLeft() || t.getTileOpenRight()) && !t.getTileOnTop()) {

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

                        // discard tiles
                        discard(model.getTileClicked(), t);

                        // update tiles
                        updateClickabilities(model.getTileClicked());
                        updateClickabilities(t);

                        // deselect initially selected tile
                        model.getTileClicked().setDeselected();
                        t.setDeselected();
                        model.unsetTileClicked();

                        if (!tournamentMode) {
                            drawDiscards();
                        }

                        // second selected tile does not match first
                    } else {

                        // set new selected tile and remove previous selected visuals
                        model.getTileClicked().setDeselected();
                        model.setTileClicked(t);
                        t.setSelected();
                    }
                }
            }

            revalidate();
            repaint();
        }

        public void updateClickabilities(Tile t) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            // top tile removed
            if (t.getPosZ() == 4) {
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        model.secondLayer.layerRows.get(i).rowTiles.get(j).toggleTileOnTop();
                    }
                }
            }

            // far left tile removed
            else if (t == model.leftExtra.layerRows.get(0).rowTiles.get(0)) {
                for (int i = 3; i < 5; i++) {
                    model.bottomLayer.layerRows.get(i).rowTiles.get(0).toggleTileOpenLeft();
                }

            }

            // far right tile removed
            else if (t == model.rightExtras.layerRows.get(0).rowTiles.get(1)) {
                model.rightExtras.layerRows.get(0).rowTiles.get(0).toggleTileOpenRight();
            }

            // second far right tile removed
            else if (t == model.rightExtras.layerRows.get(0).rowTiles.get(0)) {
                for (int i = 3; i < 5; i++) {
                    model.bottomLayer.layerRows.get(i).rowTiles.get(11).toggleTileOpenRight();
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
                    row.rowTiles.get(index + 1).toggleTileOpenLeft();

                // right most in row
                } else if (index + 1 == row.rowTiles.size()) {
                    row.rowTiles.get(index - 1).toggleTileOpenRight();

                // left is hidden
                } else if (!row.rowTiles.get(index - 1).getVisibility()) {

                    // check if at edge
                    if (index + 1 <= row.rowTiles.size() - 1) {
                        row.rowTiles.get(index + 1).toggleTileOpenLeft();
                    }

                // right is hidden
                } else if (!row.rowTiles.get(index + 1).getVisibility()) {

                    // check if at edge
                    if (index - 1 >= 0) {
                        row.rowTiles.get(index - 1).toggleTileOpenRight();
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
                            rowToReveal.rowTiles.get(i).toggleTileOnTop();
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

            if ((t.getTileOpenLeft() || t.getTileOpenRight()) && !t.getTileOnTop()) {
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

            drawTile(t);

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
                        drawTile(t);
                    }
                }

            }

            // display right extras
            layer = model.rightExtras;
            row = layer.layerRows.get(0);
            for (int i = 0; i < 2; i++) {
                    t = row.rowTiles.get(i);
                    drawTile(t);
            }

        }

        void drawTile(Tile t) {
            if (t.getVisibility()) {

                // location only needs to be calculated once
                if (t.getBoardLocation() == null) {
                    t.setLocation((int) (t.getPosX() * Tile.WIDTH + t.getPosZ() * 10), (int) (t.getPosY() * (Tile.HEIGHT - 5) - t.getPosZ() * 10));
                    t.setBoardLocation(t.getLocation());
                }

                // this is... not great... but it gets the job done.
                // if I had more time I'd make it more efficient but at least
                // this keeps it from making a new mouseListener every time it redraws
                t.removeMouseListener(this);
                t.addMouseListener(this);
                add(t);
                revalidate();

            } else {
                remove(t);
                revalidate();
            }
        }

        void drawDiscards() {

            // again, bit weird to remove all then add but all I can get to work reliably right now
            cardColumn.removeAll();
            revalidate();

            // update discards from array
            for (int i = cardPanels.size() - 1; i >= 0; i--) {
                cardColumn.add(cardPanels.get(i));
            }
        }
    }

    public static void main(String[] args) {
        new MahJong();
    }
}
