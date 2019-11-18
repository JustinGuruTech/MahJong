import java.util.*;
import javax.swing.*;

/**
 * Drivier to test Lab 4.
 * The program displays two JOPtionPanes:
 *
 * 1. The first JOPtionPane displays 15 Chinese characters that will, in Lab 5,
 *    be rendered on the CharacterTiles.
 *
 * 2. The second JOPtionPane displays three tables
 *    * The first table must display the names of 144 tiles in a random order
 *    * The second and third tables also display the names of 144 tile, but
 *      the two tables MUST be IDENTICAL
 *
 * You may find it convenient to comment out one part of the test while working
 * on the other part - see main at the bottom.
 */

public class Lab4
{
    public static void characterTest()
    {
        JTextArea area = new JTextArea();

        area.append(new CharacterTile('1').toChinese() + "  ");
        area.append(new CharacterTile('2').toChinese() + "  ");
        area.append(new CharacterTile('3').toChinese() + "  ");
        area.append(new CharacterTile('4').toChinese() + "  ");
        area.append(new CharacterTile('5').toChinese() + "  ");
        area.append(new CharacterTile('6').toChinese() + "  ");
        area.append(new CharacterTile('7').toChinese() + "  ");
        area.append(new CharacterTile('8').toChinese() + "  ");
        area.append(new CharacterTile('9').toChinese() + "  ");
        area.append(new CharacterTile('N').toChinese() + "  ");
        area.append(new CharacterTile('E').toChinese() + "  ");
        area.append(new CharacterTile('W').toChinese() + "  ");
        area.append(new CharacterTile('S').toChinese() + "  ");
        area.append(new CharacterTile('C').toChinese() + "  ");
        area.append(new CharacterTile('F').toChinese() + "  ");

        JOptionPane.showMessageDialog(null, area, "Character Tiles",
                JOptionPane.INFORMATION_MESSAGE);
    }


    public static JTable makeGame(long game)
    {
        TileDeck	deck = new TileDeck();
        Tile[][]	tiles = new Tile[12][12];
        String[]	cols = new String[12];

        Arrays.fill(cols, "");

        if (game < 0)
            deck.shuffle();
        else
            deck.shuffle(game);

        for (int i = 0; i < 144; i++)
        {
            Tile	tile = deck.deal();
            if (tile == null)
            {
                JOptionPane.showMessageDialog(null, "Empty Deck",
                        "Deal Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            tiles[i/12][i%12] = tile;
        }

        return new JTable(tiles, cols);

    }


    public static void main(String[] args)
    {
        characterTest();			// Assignment Part 1

        Object[] tables = new Object[6];	// Assignment Part 2

        tables[0] = "Random Game";
        tables[1] = makeGame(-1);
        tables[2] = "Fixed Game";
        tables[3] = makeGame(100);
        tables[4] = "Fixed Game";
        tables[5] = makeGame(100);

        JOptionPane.showMessageDialog(null, tables,
                "Tiles", JOptionPane.PLAIN_MESSAGE);
    }
}

