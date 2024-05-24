package Classes.World.DecorItem.WalkThroughDecorItem;

import Classes.World.DecorItem.DecorItem;
import Classes.World.World;

//Decors which the player can walk on
public abstract class WalkThroughDecorItem extends DecorItem {

    //region Constructor
    public WalkThroughDecorItem(World w, int x, int y) {
        super(w,x, y);
    }
    //endregion
}