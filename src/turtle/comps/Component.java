/**
 * Represents the abstract base of all grid components
 * that will be displayed on the Grid.
 * 
 * @author Henry Wang
 * Date: 4/26/17
 * Period: 2
 */
package turtle.comps;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Component extends Pane 
{
	public static final double MOVE_SPEED = 10.0;
	
	public static final TileSet DEFAULT_SET = new TileSet();
	
	private final ImageView img;
	private final Location headLoc;
	private final Location trailLoc;
	private Grid parent;
	
	/**
	 * Constructs a new component with the image background.
	 */
	protected Component()
	{
		img = new ImageView();
		this.getChildren().add(img);
		
		headLoc = new Location();
		trailLoc = new Location();
	}

	/**
	 * @return the head location of the actor
	 */
	public Location getHeadLocation()
	{
		return headLoc;
	}

	/**
	 * @return the trailing location of the actor 
	 */
	public Location getTrailingLocation()
	{
		return trailLoc;
	}

	/**
	 * @param parent the new parent grid to set to
	 */
	public void setParentGrid(Grid parent)
	{
		this.parent = parent;
	}

	/**
	 * @return the parent grid that contains this component
	 * 	    or null if there is no parent.
	 */
	public Grid getParentGrid()
	{
		return parent;
	}

	/**
	 * Updates a frame of animation for a Component.
	 * @return current frame number
	 */
	public void updateFrame(long frame)
	{
		if (parent != null)
		{
			int cellSize = parent.getCellSize();
			int xPos = cellSize * headLoc.getColumn();
			int yPos = cellSize * headLoc.getRow();
			boolean validLocs = headLoc.isValidLocation() && trailLoc.isValidLocation();
			if (xPos != getTranslateX() || yPos != getTranslateY())
			{
				//TODO: increment step.
				
			}
		}
	}
	
	/**
	 * Obtains the move speed of this component.
	 * @return move speed in pixels per frame
	 */
	public double getMoveSpeed()
	{
		return MOVE_SPEED;
	}
	
	/**
	 * Layouts the children (i.e. ImageView)
	 * of this Component object
	 */
	@Override
	protected void layoutChildren()
	{
		layoutInArea(img, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, 
				VPos.CENTER);
	}
	
	/**
	 * Sets the image of this component to the given index.
	 * @param index the index from the TileSet of frames.
	 */
	public void setImageFrame(int index)
	{
		img.setImage(DEFAULT_SET.frameAt(index));
	}
	
	/**
	 * Obtains next step of incrementing a value
	 * @param from intial value
	 * @param to the goal value to achieve
	 * @param step the increment value per step
	 * @return the next value to increment to.
	 */
	private double increment(double from, double to, double step)
	{
		if (from == to)
			return to;
		
		double after = from + step;
		if (after > to ^ from > to) 
			return to; // Incremented pass the target.
		else
			return from; 
	}
}
