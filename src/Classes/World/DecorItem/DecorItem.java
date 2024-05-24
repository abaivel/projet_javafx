package Classes.World.DecorItem;

import Classes.GameObject;
import Classes.World.World;

//Decor items in the world
public abstract class DecorItem extends GameObject {

    //region Constructor
    public DecorItem(World w, int x, int y) {
        super(w,x, y);
    }
    //endregion
}