package Classes.Item.NotConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import Classes.World.World;

public class Trinket extends Item{

    public Trinket(World w, int price, int x, int y, String name, boolean dropped) {
        super(w,x,y,name,dropped, price);
    }
}