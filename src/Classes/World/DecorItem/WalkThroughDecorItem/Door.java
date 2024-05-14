package Classes.World.DecorItem.WalkThroughDecorItem;

import Classes.Item.ConsumableItem.Key;
import javafx.scene.layout.GridPane;

public class Door extends WalkThroughDecorItem{
    private Key key;
    public Door(GridPane g, int x, int y) {
        super(g,x, y);
    }
}