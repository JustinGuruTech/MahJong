import java.util.*;
import	java.awt.*;
import	javax.swing.*;

public class CharacterTile extends Tile {

    protected char symbol;
    public static HashMap<Character, Character> map = new HashMap<>();
    public static HashMap<Character, String> map2 = new HashMap<>();
    static {
        Character normal[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'N', 'E', 'W', 'S', 'C', 'F', 'w'};
        Character chinese[] = {'\u4E00', '\u4E8C', '\u4E09', '\u56DB', '\u4E94', '\u516D', '\u4E03', '\u516B', '\u4E5D', '\u5317', '\u6771', '\u897F', '\u5357', '\u4E2D', '\u767C', '\u842C'};
        for (int i = 0; i < normal.length; i++) {
            map.put(normal[i], chinese[i]);
        }
    }

    public CharacterTile(char symbol) {
        this.symbol = symbol;
    }

    public boolean matches(Tile other) {

        // check for same class
        if (super.matches(other)) {

            // return true only if symbols are equal
            return this.symbol == ((CharacterTile)other).symbol;
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {

        // only paint if visible
            // parent constructor
            super.paintComponent(g);

            // get font for testing purposes
            // EDIT: keep font to alter to better fit tiles
            Font font = g.getFont();
            Font newFont = font.deriveFont(9f);

            // prepare to write
            g.setColor(RED);
            g.setFont(newFont);

            // retrieve symbol to write to top corner
            g.drawString(String.valueOf(symbol), WIDTH, 10);

            // prepare to write to center
            g.setColor(BLACK);
            float size = 38f; // size of font unless it's the red one

            // after trial and error this looks centered enough I suppose
            int x = WIDTH / 2 - 8;
            int y = HEIGHT / 2 + 12;

            // since 1-9 need room for multiple symbols size them down
            if (String.valueOf(symbol).matches("[1-9]")) {
                size = 20f;
                x = WIDTH / 2;
                y = HEIGHT / 2 - 5;
            }

            // set font to either small for 1-9 tiles or large for others
            newFont = font.deriveFont(size);
            g.setFont(newFont);

            // draw main symbol
            g.setColor(BLUE);
            // is C (red)
            if (symbol == 'C') {
                g.setColor(RED);
            }

            // is F (green)
            if (symbol == 'F') {
                g.setColor(GREEN);
            }
            g.drawString(Character.toString(map.get(symbol)), x, y);

            // only draw wan symbol if 1-9 was drawn above
            if (size == 20f) {
                g.setColor(RED);
                g.drawString(Character.toString(map.get('w')), x, y + 22);
            }

    }

    public String toString() {

        switch (this.symbol) {
            case 'N':
                return "North Wind";
            case 'E':
                return "East Wind";
            case 'W':
                return "West Wind";
            case 'S':
                return "South Wind";
            case 'C':
                return "Red Dragon";
            case 'F':
                return "Green Dragon";
            default:
                return "Character " + this.symbol;
        }

    }

    public String toChinese() {

        return Character.toString(map.get(this.symbol));
    }
}
