package turtle.core;

/**
 * Represents a immutable (mostly), immovable grid component, that falls
 * on the bottom layer of the grid.
 *
 * @author Henry Wang
 */
public abstract class Cell extends Component {
    private static final long serialVersionUID = -1623749690326497639L;
    private int frameTransform;
    private Cell transformed;

    /**
     * Constructs a new cell.
     */
    public Cell() {
        frameTransform = -1;
        transformed = null;
    }

    /**
     * Checks whether if a pass to this cell is ever possible. This
     * does a preliminary check without doing executing any actions
     *
     * @param visitor the visitor to check against.
     * @return true if cell allows visitor to pass cell
     * false if cell prohibits such a move.
     */
    public abstract boolean checkPass(Actor visitor);

    /**
     * Executes a pass when an visitor actor comes to this cell.
     *
     * @param visitor the actor passing this cell
     * @return true if cell allows visitor to pass cell
     * false if cell prohibits such a move.
     */
    public abstract boolean pass(Actor visitor);

    /**
     * Utility method used to transform this cell into something else.
     * Of course, it doesn't literally change this cell, but it just
     * creates a new cell that fill the place of this cell's location.
     *
     * @param other      the other cell to transform into.
     * @param waitFrames the number of frames to wait for transformation.
     * @throws IllegalStateException    if this cell isn't added to anything.
     * @throws IllegalArgumentException if cell to transform to is already
     *                                  added to grid.
     */
    public void transformTo(Cell other, int waitFrames) {
        if (transformed != null) {
            return;
        }

        Grid parent = getParentGrid();
        if (parent == null) {
            throw new IllegalStateException("Parent grid is non-existent!");
        }
        if (other.getParentGrid() != null) {
            throw new IllegalArgumentException("Cell transformed already added!");
        }

        frameTransform = waitFrames;
        transformed = other;
    }

    /**
     * Updates a frame of animation for a cell. This updates the transform animations.
     *
     * @param frame current frame number
     */
    @Override
    public void updateFrame(long frame) {
        super.updateFrame(frame);
        if (transformed != null && frameTransform >= 0) {
            frameTransform--;
            if (frameTransform == -1) {
                Grid parent = getParentGrid();
                if (parent != null) {

                    transformed.getHeadLocation().setLocation(getHeadLocation());
                    parent.removeCell(this);
                    parent.placeCell(transformed);
                }
            }
        }
    }
}
