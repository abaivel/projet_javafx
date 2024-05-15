package Classes.World.DecorItem;

import Classes.GameObject;
import Classes.World.World;

public abstract class DecorItem extends GameObject {

    public DecorItem(World w, int x, int y) {
        super(w,x, y);
    }
}