import java.util.*;

public class MahJongModel {

    public ArrayList<TileLayer> tileLayers = new ArrayList<>();
    public TileLayer topLayer;
    public TileLayer secondLayer;
    public TileLayer thirdLayer;
    public TileLayer fourthLayer;
    public TileLayer bottomLayer;
    public TileLayer leftExtra;
    public TileLayer rightExtras;
    public TileDeck deck;

    // draw left to right to preserve overlay
    // all tiles zero indexed
    //
    // entire grid
    //      x-axis: 14
    //      y-axis: 7

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

    public MahJongModel() {

        deck = new TileDeck();
        tileLayers = new ArrayList<TileLayer>(4);

        // left extra tile
        leftExtra = new TileLayer();
        leftExtra.layerRows.add(new TileRow(1, 0, 3.5, 0));
        leftExtra.layerRows.get(0).rowTiles.get(0).setClickable();

        // top tile
        topLayer = new TileLayer();
        topLayer.layerRows.add(new TileRow(1, 6.5, 3.5, 4));
        topLayer.layerRows.get(0).rowTiles.get(0).setClickable();

        // second layer from top
        secondLayer = new TileLayer();
        // make edges clickable
        for (int i = 0; i < 2; i++) {
            secondLayer.layerRows.add(new TileRow(2, 6, 3 + i, 3));
        }

        // third layer from top
        thirdLayer = new TileLayer();
        // make edges clickable
        for (int i = 0; i < 4; i++) {
            thirdLayer.layerRows.add(new TileRow(4, 5, 2 + i, 2));
            thirdLayer.layerRows.get(i).rowTiles.get(0).setClickable();
            thirdLayer.layerRows.get(i).rowTiles.get(3).setClickable();
        }
        // set tileOnTop var for those with tile on top
        for (int i = 1; i < 3; i++) {
            thirdLayer.layerRows.get(i).rowTiles.get(1).setTileOnTop();
            thirdLayer.layerRows.get(i).rowTiles.get(2).setTileOnTop();
        }

        // fourth layer from top
        fourthLayer = new TileLayer();
        // make edges clickable
        for (int i = 0; i < 6; i++) {
            fourthLayer.layerRows.add(new TileRow(6, 4, 1 + i, 1));
            fourthLayer.layerRows.get(i).rowTiles.get(0).setClickable();
            fourthLayer.layerRows.get(i).rowTiles.get(5).setClickable();
        }
        // set tileOnTop var for those with tile on top
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                fourthLayer.layerRows.get(i).rowTiles.get(j).setTileOnTop();
            }
        }

        // bottom layer (is a bastard)
        bottomLayer = new TileLayer();
        bottomLayer.layerRows.add(new TileRow(12, 1, 0, 0));
        bottomLayer.layerRows.add(new TileRow(8, 3, 1, 0));
        bottomLayer.layerRows.add(new TileRow(10, 2, 2, 0));
        bottomLayer.layerRows.add(new TileRow(12, 1, 3, 0));
        bottomLayer.layerRows.add(new TileRow(12, 1, 4, 0));
        bottomLayer.layerRows.add(new TileRow(10, 2, 5, 0));
        bottomLayer.layerRows.add(new TileRow(8, 3, 6, 0));
        bottomLayer.layerRows.add(new TileRow(12, 1, 7, 0));

        // make edges clickable where extre left and right aren't
        for (int i = 0; i < bottomLayer.layerRows.size(); i++) {
            if (i != 3 && i != 4) {
                bottomLayer.layerRows.get(i).rowTiles.get(0).setClickable();
                bottomLayer.layerRows.get(i).rowTiles.get(bottomLayer.layerRows.get(i).rowTiles.size() - 1).setClickable();
            }
        }

        // rows 1-6
        // row 1 - tiles 1-6
        // row 2 - tiles 2-7
        // row 3 - tiles 3-8
        // row 4 - tiles 3-8
        // row 5 - tiles 2-7
        // row 6 - tiles 1-6
        // coordinates for covering tiles
        int[] x1Cover = new int[] {1, 2, 3, 3, 2, 1};
        int[] x2Cover = new int[] {6, 7, 8, 8, 7, 6};
        // set tileOnTop var for those with tile on top
        for (int i = 1; i < 7; i++) {
            for (int j = x1Cover[i - 1]; j <= x2Cover[i - 1]; j++) {
                bottomLayer.layerRows.get(i).rowTiles.get(j).setTileOnTop();
            }
        }

        // left extra
        rightExtras = new TileLayer();
        rightExtras.layerRows.add(new TileRow(2, 13, 3.5, 0));
        rightExtras.layerRows.get(0).rowTiles.get(1).setClickable();

        // store in arrayList
        tileLayers.add(topLayer);
        tileLayers.add(secondLayer);
        tileLayers.add(thirdLayer);
        tileLayers.add(fourthLayer);
        tileLayers.add(bottomLayer);

    }




}
