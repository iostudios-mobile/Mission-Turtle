package turtle.core;

import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;
import turtle.comp.Player;

import static turtle.core.Grid.CELL_SIZE;

/**
 * Displays only a portion of the grid --- the portion that the player can
 * see at one time.
 *
 * @author Henry Wang
 */
public class GridView extends Pane {
    private static final double INTERNAL_PADDING = 20;
    private static final double CORNER_RADIUS = 5.0;
    private static final double SCREEN_PADDING = 200.0;
    private static final Duration FADE_DURATION = Duration.seconds(.25);

    private final int rows;
    private final int cols;
    private Grid viewed;

    /**
     * Constructs a GridView.
     *
     * @param init the initial grid to view
     */
    public GridView(Grid init) {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double width = bounds.getWidth() - SCREEN_PADDING;
        double height = bounds.getHeight() - SCREEN_PADDING;

        rows = (int) (height / CELL_SIZE);
        cols = (int) (width / CELL_SIZE);
        initGrid(init);

        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Rectangle clip = new Rectangle(0, 0, CELL_SIZE * cols, CELL_SIZE * rows);
        clip.setArcHeight(CORNER_RADIUS);
        clip.setArcWidth(CORNER_RADIUS);
        setClip(clip);
    }

    /**
     * Initializes this GridView with another grid,
     * cleaning up the previous grid's stuff.
     *
     * @param grid the grid to initialize with.
     */
    public void initGrid(Grid grid) {
        viewed = grid;
        getChildren().clear();
        initGrid0(grid);
    }

    /**
     * Similar to {@link #initGrid(Grid)}, except this will show a fade-in
     * transition from the previous grid to this grid.
     *
     * @param grid the grid to initialize with.
     */
    public void fadeInitGrid(Grid grid) {
        if (viewed == null) {
            initGrid(grid);
            return;
        }

        Grid old = viewed;
        FadeTransition fadeOut = new FadeTransition(FADE_DURATION, old);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(evt -> getChildren().remove(old));
        fadeOut.play();

        viewed = grid;
        initGrid0(grid);
    }

    /**
     * @return the grid viewed by this grid-view.
     */
    public Grid getGrid() {
        return viewed;
    }

    /**
     * Updates a frame, propagating it to grid.
     *
     * @param frame the frame number
     */
    public void updateFrame(long frame) {
        if (viewed == null) {
            return;
        }

        viewed.updateFrame(frame);
        updatePos();
    }

    /**
     * Computes maximum width.
     *
     * @param height height to compare with (can be -1).
     * @return the value in pixels
     */
    @Override
    protected double computeMaxWidth(double height) {
        return cols * CELL_SIZE;
    }

    /**
     * Computes maximum height.
     *
     * @param width width to compare with (can be -1).
     * @return the value in pixels
     */
    @Override
    protected double computeMaxHeight(double width) {
        return rows * CELL_SIZE;
    }

    /**
     * Computes preferred width.
     *
     * @param height height to compare with (can be -1).
     * @return the value in pixels
     */
    @Override
    protected double computePrefWidth(double height) {
        return cols * CELL_SIZE;
    }

    /**
     * Computes preferred height.
     *
     * @param width width to compare with (can be -1).
     * @return the value in pixels
     */
    @Override
    protected double computePrefHeight(double width) {
        return rows * CELL_SIZE;
    }

    /**
     * Computes minimum width.
     *
     * @param height height to compare with (can be -1).
     * @return the value in pixels
     */
    @Override
    protected double computeMinWidth(double height) {
        return cols * CELL_SIZE;
    }

    /**
     * Computes minimum height.
     *
     * @param width width to compare with (can be -1).
     * @return the value in pixels
     */
    @Override
    protected double computeMinHeight(double width) {
        return rows * CELL_SIZE;
    }

    /**
     * Layouts all the children in this GridView.
     */
    @Override
    protected void layoutChildren() {
        if (viewed == null) {
            return;
        }

        double width = cols * CELL_SIZE;
        double height = rows * CELL_SIZE;
        layoutInArea(viewed, 0, 0, width, height, 0, HPos.CENTER, VPos.CENTER);
    }

    /**
     * Calculates the viewport offset so that the viewport contains specified
     * point, and viewport does not go out of bounds.
     *
     * @param viewSize the view port size.
     * @param maxSize  the max bound size.
     * @param point    the point offset
     * @return an offset of the viewport to fit condition
     */
    private double calcOffset(double viewSize, double maxSize, double point) {
        if (viewSize > maxSize) {
            return -(viewSize - maxSize) / 2;
        }

        double off = point - viewSize / 2;
        return Math.min(Math.max(-INTERNAL_PADDING, off),
                (maxSize + INTERNAL_PADDING) - viewSize);
    }

    /**
     * Initializes the grid itself.
     *
     * @param grid the grid to initialize.
     */
    private void initGrid0(Grid grid) {
        if (grid != null) {
            getChildren().add(0, grid);
        }
        layoutChildren();
        updatePos();
    }

    /**
     * Updates the grid translate offset to follow player.
     */
    private void updatePos() {
        if (viewed == null) {
            return;
        }

        Player p = viewed.getPlayer();
        if (p == null) {
            return;
        }

        double cell = CELL_SIZE;
        viewed.setTranslateX(-calcOffset(viewed.getWidth(),
                viewed.getColumns() * cell, p.getTranslateX()));
        viewed.setTranslateY(-calcOffset(viewed.getHeight(),
                viewed.getRows() * cell, p.getTranslateY()));
    }

    /**
     * Delegate method that queries the interactive player.
     *
     * @return the player of the grid contained inside this grid-view.
     */
    public Player getPlayer() {
        if (viewed == null) {
            return null;
        }
        return viewed.getPlayer();
    }
}
