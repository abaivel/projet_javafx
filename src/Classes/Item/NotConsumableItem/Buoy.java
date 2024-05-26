package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

//Saves a player from drowning when in their inventory
public class Buoy extends Item {

    //region Constructor
    public Buoy(World w, int x, int y, String name, int price, boolean dropped, String urlImage) {
        super(w, x, y, name, price, dropped,urlImage);
    }
    //endregion
}
