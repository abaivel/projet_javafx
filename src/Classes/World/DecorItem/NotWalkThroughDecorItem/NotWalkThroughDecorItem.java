package Classes.World.DecorItem.NotWalkThroughDecorItem;

import Classes.World.DecorItem.DecorItem;
import Classes.World.World;

//Decors which the player can't walk through
public abstract class NotWalkThroughDecorItem extends DecorItem {

    //region Constructor
    public NotWalkThroughDecorItem(World w, int x, int y) {
        super(w,x, y);
    }
    //endregion
}