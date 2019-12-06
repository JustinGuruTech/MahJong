import java.util.ArrayList;

public class TileRow {

    public ArrayList<Tile> rowTiles = new ArrayList<>();

    public TileRow(int tilesInRow, double xStart, double yVal, int zVal) {
        for (int i = 0; i < tilesInRow; i++) {
            rowTiles.add(MahJong.deck.deal());
            Tile t = rowTiles.get(i);
            t.setXYZ(xStart + i, yVal, zVal);
        }
    }

}