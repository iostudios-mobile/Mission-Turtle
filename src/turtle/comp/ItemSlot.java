/**
 * ItemUI.java
 * 
 * This represents a single item slot representing a multiplicity of a type of
 * item. This is used by player to keep track of items.
 * 
 * @author Henry Wang
 * Date: 5/5/17
 * Period: 2
 */

package turtle.comp;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import turtle.core.TileSet;

public class ItemSlot extends Pane
{
	private static final double HIGHLIGHT_SPREAD = .6;
	private static final int HIGHLIGHT_RADIUS = 30;

	private static final int ITEM_SIZE = 30;
	
	private ArrayList<Item> items;
	private ImageView itemRep;
	private Label number;
	
	/**
	 * Creates a new ItemUI and initializes UI.
	 */
	public ItemSlot()
	{
		items = new ArrayList<>();
		
		itemRep = new ImageView();
		
		itemRep.setFitWidth(ITEM_SIZE);
		itemRep.setFitHeight(ITEM_SIZE);
		
		number = new Label();
		
		this.getChildren().add(itemRep);
		
		DropShadow highlight = new DropShadow(HIGHLIGHT_RADIUS, Color.WHITE);
		highlight.setSpread(HIGHLIGHT_SPREAD);
		this.setEffect(highlight);
	}
	
	/**
	 * Adds a new item to this ui panel. This will only add
	 * items of the same type.
	 * 
	 * @param itm the item to add.
	 * @return true if item was added, false if not.
	 */
	public boolean addItem(Item itm)
	{
		if (items.size() == 0)
		{
			TileSet ts = itm.getTileSet();
			itemRep.setImage(ts.getImageset());
			itemRep.setViewport(ts.frameAt(itm.getItemImage()));
		}
		else if (!items.get(0).identical(itm))
			return false;
		
		items.add(itm);
		number.setText("" + items.size());
		
		return true;
	}

	/**
	 * Removes an item from this item UI.
	 * @param itm the item to remove.
	 * @return true if an item has been removed.
	 */
	public boolean removeItem(Item itm)
	{
		boolean removed = items.remove(itm);
		number.setText("" + items.size());
		return removed;
	}
	
	/**
	 * Determines whether if this item UI slot is empty.
	 * @return true if empty, false if filled.
	 */
	public boolean isEmpty()
	{
		return items.isEmpty();
	}
	
	/**
	 * Layouts the image view within this component to fit the entire
	 * screen and center alignment.
	 */
	@Override
	protected void layoutChildren()
	{
		double width = getWidth();
		double height = getHeight();
		layoutInArea(itemRep, 0, 0, width, height, 0, HPos.CENTER, VPos.CENTER);
	}
}
