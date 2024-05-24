package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

//Saves a player from drowning when in their inventory
public class Buoy extends Item {

    //region Constructor
    public Buoy(World w, int x, int y, String name, boolean dropped, int price, String urlImage) {
        super(w, x, y, name, dropped, price,urlImage);
    }
    //endregion
}
