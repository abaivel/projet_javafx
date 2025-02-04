package Classes.Item.ConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

public class Trophee extends Item {
    public Trophee(World w, int x, int y, String name, int price, boolean dropped, String urlImage) {
        super(w, x, y, name, price, dropped, urlImage);
    }
}
