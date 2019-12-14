# MahJong
<b>Single player MahJong Solitaire game.</b>
<ul>
  <li>Supports undo/redo and seeded games using a game number to allow for setting up the board the same across games. 
  <li>Tiles removed are displayed on a tile pane on the right side. </li>
  <li>Tournament mode disables undo/redo and discard display.</li>
</ul>

<h2>Under The Hood</h2>

<b>Tile</b>
<ul>
  <li>Tile class stores the information to paint the tiles and acts as a superclass for the different types of tiles (circle, bamboo, etc).</li>
  <li>Tile subclasses have their own constructors/paint method depending on their type.</li>  
  <li>Attributes posX, posY, posZ are used for determining where a tile is to be drawn on the board. More on that in MahJongBoard.</li>
  <li>Booleans for tileOnTop, tileOpenRight, tileOpenLeft used to determine if a Tile can be clicked.</li>
  <li>Boolean visible determines if a Tile is to be drawn and selected determines if to draw the selected version of the tile</li>
  <li>Point boardLocation is set after drawing to place the tile back on the board after undoing.</li>
  <li>Getters and setters provided for all private variables</li>
  <li>Matches method provided to determine if a tile matches another, different tile. Matches method in Tile fully functional for all types of Tile thanks to polymorphism.</li>
</ul>

<b>TileDeck</b>
<ul>
  <li>TileDeck class creates a deck as an ArrayList of Tile objects.</li>
  <li>Provides method for shuffling with no arguments for shuffling randomly (not actually used in the MahJong file, more on that later)</li>
  <li>Provides method for shuffling based on a number (used for repeat games). </li>
  <li>Provides method for dealing a single card. If the deck isn't empty it removes a card from the deck and returns it </li>
</ul>

<b>TileRow & TileLayer</b>
<ul>
  <li>Data structure to set up the layout of the board<li>
  <b>TileRow</b>
  <ul>
    <li>Contains an ArrayList of Tiles that make up a single row</li>
    <li>Constructor takes arguments (TileDeck deck, int tilesInRow, double xStart, double yVal, int zVal)</li>
    <ul>
      <li>TileDeck deck - Where to pull the tiles from</li>
      <li>int tilesInRow - How many Tiles to put in the row, used for the loop in constructor</li>
      <li>double xStart - Where on the board in a tile grid the first tile belongs</li>
      <li>double yVal - Where on the y-axis on the board the row belongs</li>
      <li>int zVal - What layer the row belongs on</li>
      <li>The loop in the constructor calls setXYZ in tile and sets based on where x is in the loop and the given yVal and zVal</li>
    </ul>
  </ul>
  <b>TileLayer</b>
  <ul>
    <li>Contains an ArrayList of layerRows</li>
    <li>Constructor takes no arguments, layerRows is public and added to in MahJongModel (will be update to private later)</li>
  </ul>
</ul>

<b>MahJongModel</b>
<ul>
  <li>This is where the magic of the board data structure happens</li>
  <li>Note: Currently the z-indexing across the project is inconsistent and will eventually be refactored.</li>
  <li>Contains a TileLayer attribute for each layer which is then added to an ArrayList of TileLayers</li>
  <li>Also has a TileLayer for leftExtra and rightExtras since it's easier to deal with those tiles separately</li>
  <li>Contains a Tile attribute with getters/setters for storing a tile when it's clicked. This is used for program flow control in MahJong to determine how the program should act when a tile is clicked. Set to null if no tile is selected.</li>
  <li>Contains TileDeck attribute that will be the deck for the game.</li>
  <li>Constructor creates a deck, shuffles it using provided gameNumber, and calls formLayers().</li>
  <b>formLayers Method</b>
  <ul>
    <li>Creates ArrayList of tileLayers and stores in tileLayers var.</li>
    <li>The forming of layers is heavily commented, refer to those comments for exact formatting. General format is create tileRows and add to tileLayer then set the right/left side open or set tile on top where applicable</li>
    <li>This function gets the model completely ready to be displayed by MahJongBoard and used by MahJong</li>
  </ul>
</ul>

<b>MahJong and MahJongBoard</b>
<ul>
  <li>MahJong is the driver and has nested class MahJongBoard. Nesting it makes it easier to get everything displaying smoothly.</li>
<b>MahJongBoard</b>
  <ul>
    <li>MahJongBoard extends JPanel and implements MouseListener, adding a listener to each of the tiles as they are added to the board.</li>
    <li>Has a MahJongModel attribute which is used for getting the deck and layout</li>
    <li>ImageIcon and Image are used to display the background image, nothing special.<li>
    <li>Constructor requires a gameNumber (passed to it in MahJong, more on that later) and creates a new MahJongModel based on the number before calling drawBackground.</li>
    <li>drawBackground method gets image from resources in a try catch and displays it, nothing special</li>
    <b>DrawBoard() Method</b>
    <ul>
      <li>DrawBoard() is called by paintComponent every time there is a repaint.</li>
      <li>First checks to see if 144 tiles have been discarded (winning condition)</li>
      <li>Temporary variables for TileLayer, TileRow, and Tile for drawing</li>
      <li>Draws edge tiles then tileLayers iteratively. Again, refer to the heavy commentation for a deeper understanding.</li>
    </ul>
    <b>DrawTile(Tile t)</b>
    <ul>
      <li>DrawTile() is called from drawBoard()</li>
      <li>First it checks if the visible variable in Tile is true, if so it removes then adds a mouselistener back (needs to be fixed) then adds it to the board.
    </ul>
    <b>DrawDiscards()</b>
    <ul>
      <li>This uses a variable in MahJong, cardPanels, to get the discards that need to be displayed and adds them to cardColumn, another varibale in MahJong.</li>
      <li>First it gets rid of all the tiles in the discard then redraws them based on cardPanels</li>
    </ul>
  </ul>
