import java.util.*;
import	java.awt.*;
import java.awt.event.*;
import	javax.swing.*;

public class MahJongBoardOld extends JPanel implements MouseListener {

    public MahJongModel model = new MahJongModel();
    public static TileDeck deck = new TileDeck();
    static {
        deck.shuffle();
    }

    public MahJongBoardOld(Graphics g) {
        super.paintComponent(g);
        setLayout(null);
        drawBoard();
    }

    // layers from top to bottom
    // layer 4 - tile  0                    total: 1 tile
    //      x: 6.5 tiles
    //      y: 3.5 tiles
    // layer 3 - tiles 1-4                  total: 4
    //      row 0 x: tiles 6-7 y: tile 3    tiles: 2
    //      row 1 x: tiles 6-7 y: tile 4    tiles: 2
    // layer 2 - tiles 5-20                 total: 16 tiles
    //      row 0 x: tiles 5-8 y: tile 2    tiles: 4
    //      row 1 x: tiles 5-8 y: tile 3    tiles: 4
    //      row 2 x: tiles 5-8 y: tile 4    tiles: 4
    //      row 3 x: tiles 5-8 y: tile 5    tiles: 4
    // layer 1 - tiles 21-56                total: 36 tiles
    //      row 1 x: tiles 4-9 y: tile 1    tiles: 6
    //      row 2 x: tiles 4-9 y: tile 2    tiles: 6
    //      row 3 x: tiles 4-9 y: tile 3    tiles: 6
    //      row 4 x: tiles 4-9 y: tile 4    tiles: 6
    //      row 5 x: tiles 4-9 y: tile 5    tiles: 6
    //      row 6 x: tiles 4-9 y: tile 6    tiles: 6
    // layer 0 - tiles 57-143               total: 87 tiles
    //      row 0 x: tiles 1-12 y: tile 0   tiles: 12
    //      row 1 x: tiles 3-10 y: tile 1   tiles: 8
    //      row 2 x: tiles 2-11 y: tile 2   tiles: 10
    //      left side special               tiles: 1
    //          x: tile 0 y: tile 3.5
    //      row 3 x: tiles 1-12 y: tile 3   tiles: 12
    //      row 4 x: tiles 1-12 y: tile 4   tiles: 12
    //      right side special              tiles: 2
    //          x: tiles 13-14 y: tile 3.5
    //      row 5 x: tiles 2-11 y: tile 5   tiles: 10
    //      row 6 x: tiles 3-10 y: tile 6   tiles: 8
    //      row 7 x: tiles 1-12 y: tile 7   tiles: 12

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    public void drawBoard() {
        Tile currentTile = deck.deal();
//        currentTile.addMouseListener(this);
        add(currentTile);
        revalidate();
        repaint();

    }

}
