import java.util.ArrayList;

public class TileRow {

    public ArrayList<Tile> rowTiles = new ArrayList<>();

    public TileRow(TileDeck deck, int tilesInRow, double xStart, double yVal, int zVal) {
        for (int i = 0; i < tilesInRow; i++) {
            rowTiles.add(deck.deal());
            Tile t = rowTiles.get(i);
            t.setXYZ(xStart + i, yVal, zVal);
        }
    }

}