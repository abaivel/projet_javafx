package Classes.Item.NotConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import Classes.World.World;

//An item that can be sold, no other purpose
public class Trinket extends Item{

    //region Constructor
    public Trinket(World w, int price, int x, int y, String name, boolean dropped, String urlImage) {
        super(w,x,y,name,dropped, price,urlImage);
    }
    //endregion
}