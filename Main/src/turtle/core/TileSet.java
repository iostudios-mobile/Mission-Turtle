package turtle.core;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import turtle.comp.*;

/**
 * Manages the set of image frames and mapped indexes to component types.
 *
 * @author Henry Wang
 */
public class TileSet {
    @SuppressWarnings("unchecked")
    private static final Class<Component>[] DEF_COMPS = new Class[]{Door.class,
            Grass.class, PlasticWrap.class, Water.class, Exit.class, Fire.class,
            Sand.class, Bucket.class, Cannon.class, Projectile.class,
            Player.class, Key.class, Wall.class, Bird.class, Food.class,
            Hint.class, Trap.class, LawnMower.class, Child.class,
            Button.class, Factory.class};

    private static final int FRAME_ROWS = 16;
    private static final int FRAME_COLS = 16;

    private static final double SMALL = .0001;

    private final Image tileset;
    private final Class<Component>[] compIndex;

    /**
     * Constructs a new default tile-set
     */
    //TODO: implement custom tilesets
    public TileSet() {
        tileset = new Image(ClassLoader.getSystemResourceAsStream(
                "tileset.png"));
        compIndex = DEF_COMPS;
    }

    /**
     * Gets all the frames of this tile-set.
     *
     * @return the image tile-set.
     */
    public Image getImageSet() {
        return tileset;
    }

    /**
     * Obtains the image frame at the given index.
     *
     * @param index the index of image
     * @return an image at that particular frame.
     * @throws IndexOutOfBoundsException if index is not within
     *                                   -1 <= index < 256
     */
    public Rectangle2D frameAt(int index) {
        if (index == -1) {
            return new Rectangle2D(0, 0, SMALL, SMALL);
        }

        int col = index % FRAME_COLS;
        int row = index / FRAME_COLS;

        if (index < 0 || row >= FRAME_ROWS) {
            throw new IndexOutOfBoundsException("" + index);
        }

        double s = Grid.CELL_SIZE;
        return new Rectangle2D(col * s, row * s, s, s);
    }

    /**
     * Obtains the component type at the slot index.
     *
     * @param index the index of component to get
     * @return the class associated with component type.
     * @throws IllegalArgumentException if index is out of bounds.
     */
    public Class<Component> componentAt(short index) {
        if (index < 0 || index >= compIndex.length) {
            throw new IllegalArgumentException("Illegal component index");
        }
        return compIndex[index];
    }

    /**
     * @return number of possible components
     */
    public int getComponentCount() {
        return compIndex.length;
    }
}
