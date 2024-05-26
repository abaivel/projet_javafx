package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

//An item that can be sold, no other purpose
public class Trinket extends Item{

    //region Constructor
    public Trinket(World w, int x, int y, String name, int price, boolean dropped, String urlImage) {
        super(w,x,y,name, price,dropped,urlImage);
    }
    //endregion
}