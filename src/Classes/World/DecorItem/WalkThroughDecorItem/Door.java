package Classes.World.DecorItem.WalkThroughDecorItem;

import Classes.Item.ConsumableItem.Key;
import Classes.World.World;

public class Door extends WalkThroughDecorItem{

    private Key key;
    private String color;
    private boolean open;
    public Door(World w, int x, int y, String color) {
        super(w,x, y);
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