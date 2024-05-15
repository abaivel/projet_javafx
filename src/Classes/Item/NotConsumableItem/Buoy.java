package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import javafx.scene.layout.GridPane;

public class Buoy extends Item {

    //region Constructor
    public Buoy(GridPane g, int x, int y, String name, boolean dropped) {
        super(g, x, y, name, dropped);
    }
    //endregion
}
