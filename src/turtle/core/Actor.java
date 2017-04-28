/**
 * Actor.java
 * 
 * Represents a movable/ interactable grid component that is readily mutable
 * 
 * @author Henry Wang
 * Date: 4/26/17
 * Period: 2 
 */

package turtle.core;

public abstract class Actor extends Component
{
	public static final int DYING_FRAMES = 10;
	
	//Directions
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	//Some common dominance levels.
	public static final DominanceLevel PLAYER = new DominanceLevel("Player", 0);
	public static final DominanceLevel ENEMY = new DominanceLevel("Enemy", 100);
	public static final DominanceLevel ITEM = new DominanceLevel("Item", 200);
	public static final DominanceLevel FIXTURE = new DominanceLevel("Fixture", 300);
	
	private boolean dying;
	private int dieFrame;
	
	/**
	 * Constructs a new actor.
	 */
	public Actor()
	{
		dying = false;
	}
	
	/**
	 * Kills this actor (this sets a flag for this actor to be removed).
	 * Any class can override this method to determine which items this
	 * actor will die by (whether if it is immune to something).
	 * 
	 * @param attacker the thing that is killing this actor.
	 * @return true if this actor died as a result of this call, false if
	 * 		nothing changed.
	 */
	public boolean die(Component attacker)
	{
		if (isDying())
			return false;
		if (attacker instanceof Actor && ((Actor)attacker).isDying())
			return false;
		
		dying = true;
		dieFrame = 0;
		return true;
	}
	
	/**
	 * Checks whether if this actor has been killed and is dying.
	 * @return true if died, false if alive.
	 */
	public boolean isDying()
	{
		return dying;
	}
	
	/**
	 * Checks whether if this actor is dead and marked for removal.
	 * @return true if dead, false if dying or alive.
	 */
	public boolean isDead()
	{
		return dieFrame >= DYING_FRAMES;
	}
	
	/**
	 * Executes an interaction with another actor.
	 * 
	 * @param other the other actor to interact with.
	 * @return true if the other actor can pass into location
	 *         false if other actor is prohibited to pass.
	 */
	public abstract boolean interact(Actor other);
	
	/**
	 * Obtains the dominance level of the actor in relation to another actor.
	 * @param other other actor to compare with (or null for generally).
	 * @return a dominance level of the actor.
	 */
	public abstract DominanceLevel dominanceLevelFor(Actor other);
	
	/**
	 * Moves this actor one space in specified direction. 
	 * @param direction direction to move in.
	 * @return true if successful, false otherwise.
	 */
	public boolean traverseDirection(int direction)
	{
		Grid parent = getParentGrid();
		if (parent == null)
			return false;
		
		Location loc = getHeadLocation();
		int row = loc.getRow();
		int col = loc.getColumn();
		
		switch (direction)
		{
		case NORTH: row--; break;
		case EAST: col++; break;
		case SOUTH: row++; break;
		case WEST: col--; break;
		default: return false;
		}
		
		return parent.moveActor(this, row, col);
	}
	
	/**
	 * Updates frames of actor. This particularly updates dying frames.
	 * @param frame the frame number.
	 */
	@Override
	public void updateFrame(long frame)
	{
		super.updateFrame(frame);
		if (dying)
		{
			if (dieFrame < DYING_FRAMES)
				dieFrame++;
			setOpacity(1 - ((double)dieFrame / DYING_FRAMES)); 
		}
		
	}
}
