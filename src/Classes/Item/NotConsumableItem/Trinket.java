package Classes.Item.NotConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import javafx.scene.layout.GridPane;

public class Trinket extends Item{

    public Trinket(GridPane g, int price, int x, int y, String name, boolean dropped) {
        super(g,x,y,name,dropped, price);
    }
}