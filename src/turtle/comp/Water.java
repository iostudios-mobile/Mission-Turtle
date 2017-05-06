

package turtle.comp;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import turtle.core.Actor;
import turtle.core.Cell;

/**
 * Water.java
 * 
 * This embodies a cell of water, it is the safety zone of the player.
 * 
 * @author Henry Wang
 * Date: 5/6/17
 * Period: 2
 */
public class Water extends Cell
{
	public static final int DEFAULT_IMAGE = 12;
	
	private static final int[] ANIMATION_FRAME = {12,13,14,15,16};
	private static final int[] TRANSFORM_ANIMATION_FRAME = {30, 29, 28, 27, 26}; 
	private static final int MAX_TRANSFORM = TRANSFORM_ANIMATION_FRAME.length * 
			DEF_ANIMATION_FRAME_CHANGE;
	private int frameCount;
	
	private final ImageView top; 
	
	/**
	 * Constructs a Water tile and initializes UI.
	 */
	public Water()
	{
		animateFrames(ANIMATION_FRAME, true);
		frameCount = -1;
		
		top = new ImageView();
		top.setImage(getTileSet().getImageset());
		top.setViewport(new Rectangle2D(0, 0, SMALL, SMALL));
		this.getChildren().add(top);
	}
	
	/**
	 * Kills everything that passes it.
	 * @param visitor the actor passing this cell.
	 * @return always returns true to allow visitor to pass cell
	 */
	@Override
	public boolean pass(Actor visitor)
	{
		visitor.die(this);
		return true;
	}
	
	/**
	 * Transforms this water cell into sand (and animate it).
	 */
	public void transformToSand()
	{
		frameCount = 0;
		transformTo(new Sand(), MAX_TRANSFORM);
	}
	
	/**
	 * Overrides the update frame method in order to animate the
	 * transforming to sand frames (layered on top of water
	 * animation).
	 * 
	 * @param frame the current frame number
	 */
	@Override
	public void updateFrame(long frame)
	{
		super.updateFrame(frame);
		if (frameCount != -1)
		{
			if (frameCount >= DEF_ANIMATION_FRAME_CHANGE)
			{
				top.setViewport(getTileSet().frameAt(TRANSFORM_ANIMATION_FRAME
						[frameCount / DEF_ANIMATION_FRAME_CHANGE - 1]));
			}
			if (frameCount < MAX_TRANSFORM)
				frameCount++;
		}
	}
}
