package Classes.Item.ConsumableItem;

import Classes.Item.Item;
import Classes.World.World;
import javafx.scene.input.MouseButton;

//Bomb explodes when primary clicked on after being left on the ground --> destroy decor nearby (except door and river), kill Player
public class Bomb extends Item {

    //region Constructor
    public Bomb(World w, int x, int y, String name, int price, boolean dropped, String urlImage) {
        super(w, x, y, name, price, dropped, urlImage);
    }
    //endregion


}
