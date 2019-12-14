# MahJong
<b>Single player MahJong Solitaire game.</b>
<ul>
  <li>Supports undo/redo and seeded games using a game number to allow for setting up the board the same across games. 
  <li>Tiles removed are displayed on a tile pane on the right side. </li>
  <li>Tournament mode disables undo/redo and discard display.</li>
</ul>

<h2>Under The Hood</h2>

<b>Tiles</b>
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

<b>Deck</b>
<ul>
  <li>TileDeck class creates a deck as an ArrayList of Tile objects.</li>
  <li>Provides method for shuffling with no arguments for shuffling randomly (not actually used in the MahJong file, more on that later)</li>
  <li>Provides method for shuffling based on a number (used for repeat games). </li>
  <li>Provides method for dealing a single card. If the deck isn't empty it removes a card from the deck and returns it </li>
</ul>
