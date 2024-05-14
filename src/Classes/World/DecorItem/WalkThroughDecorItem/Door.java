package Classes.World.DecorItem.WalkThroughDecorItem;

import Classes.Item.ConsumableItem.Key;
import javafx.scene.layout.GridPane;

public class Door extends WalkThroughDecorItem{

    private Key key;
    private String color;
    private boolean open;
    public Door(GridPane g, int x, int y, String color) {
        super(g,x, y);
        this.color=color;
        open=false;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public Key getKey() {
        return key;
    }
}