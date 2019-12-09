import	java.util.*;
import	java.awt.*;
import	javax.swing.*;


public class ScrollDemo extends JScrollPane
{
    private	static	Dimension	size = new Dimension(Tile.WIDTH + 10,
            Tile.HEIGHT + 10);

    private	JPanel[]	discard = new JPanel[2];
    private	Stack<Tile>	undoStack = new Stack<Tile>();
    //private	Stack<Tile>	redoStack = new Stack<Tile>();		// optional
    private		int		width = Tile.WIDTH + 10;
    private		int		height = Tile.HEIGHT + 10;
    private		int		count = 0;


    public ScrollDemo()
    {
        setPreferredSize(new Dimension(0, 2 * height + 33));
        setBorder(BorderFactory.createRaisedBevelBorder());

        discard[0] = new JPanel(new FlowLayout(FlowLayout.LEFT));
        discard[1] = new JPanel(new FlowLayout(FlowLayout.LEFT));
        discard[0].setPreferredSize(new Dimension(0, height));
        discard[1].setPreferredSize(new Dimension(0, height));

        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel	panel = new JPanel(new BorderLayout());
        setViewportView(panel);

        panel.add(discard[0], BorderLayout.NORTH);
        panel.add(discard[1], BorderLayout.SOUTH);

        discard[0].setBackground(Color.RED);
        discard[1].setBackground(Color.BLACK);
        panel.setBackground(new Color(254, 205, 33));
    }


    public void addToUndo(Tile t1, Tile t2)
    {
        undoStack.push(t1);
        undoStack.push(t2);

        Dimension	size = new Dimension(++count * width, height + 6);
        discard[0].setPreferredSize(size);
        discard[1].setPreferredSize(size);

        // This version puts the most recently added tiles on the right and scrolls
        // a scroll pane so that the most recently added tiles are visible.

        discard[0].add(t1);
        discard[1].add(t2);

        Rectangle	r = new Rectangle(count * width, 0, width, height + 6);
        getViewport().scrollRectToVisible(r);


        // This version puts the most recently added tiles on the left - it doesn't
        // need to scroll - the most recently added tiles are always visible.

		/*discard[0].add(t1, 0);
		discard[1].add(t2, 0);*/

        revalidate();
        repaint();
    }


    public static void main(String[] args)
    {
        ScrollDemo	demo = new ScrollDemo();
        JFrame		frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(demo);
        frame.setSize(400, 250);
        frame.setVisible(true);

//        try
//        {
//            int	pause = 2000;
//
//            demo.addToUndo(new PictureTile("Chrysanthemum"), new PictureTile("Orchid"));
//            Thread.sleep(pause);
//            demo.addToUndo(new PictureTile("Plum"), new PictureTile("Bamboo"));
//            Thread.sleep(pause);
//            demo.addToUndo(new PictureTile("Spring"), new PictureTile("Summer"));
//            Thread.sleep(pause);
//            demo.addToUndo(new PictureTile("Fall"), new PictureTile("Winter"));
//            Thread.sleep(pause);
//
//            demo.addToUndo(new CharacterTile('1'), new CharacterTile('2'));
//            Thread.sleep(pause);
//            demo.addToUndo(new CharacterTile('3'), new CharacterTile('4'));
//            Thread.sleep(pause);
//            demo.addToUndo(new CharacterTile('5'), new CharacterTile('6'));
//            Thread.sleep(pause);
//            demo.addToUndo(new CharacterTile('7'), new CharacterTile('8'));
//        }
//        catch (InterruptedException ie)
//        {
//            System.out.println(ie);
//        }
    }
}
