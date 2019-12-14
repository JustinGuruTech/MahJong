# MahJong
<b>Single player MahJong Solitaire game.</b>
<ul><li>Supports undo/redo and seeded games using a game number to allow for setting up the board the same across games. 
<li>Tiles removed are displayed on a tile pane on the right side. </li>
<li>Tournament mode disables undo/redo and discard display.</li></ul>

<h2>Under The Hood</h2>

<b>Tiles</b>
<ul>
<li>Tile class stores the information to paint the tiles and acts as a superclass for the different types of tiles (circle, bamboo, etc).</li>
<li>Tile subclasses have their own constructors/paint method depending on their type.</li></ul>
</ul>

<b>Deck</b>
<ul>
<li>TileDeck class creates a deck as an ArrayList of Tile objects.</li>
<li>Provides method for shuffling with no arguments for shuffling randomly (not actually used in the MahJong file, more on that later)</li>
<li>Provides method for shuffling based on a number (used for repeat games). </li>
<li>Provides method for dealing a single card. If the deck isn't empty it removes a card from the deck and returns it </li>
</ul>
