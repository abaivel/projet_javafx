package Classes.InstantUse;

import Classes.World.World;

//TODO : when picked up on the ground -> player gets back health
public class InstantHealth extends Instant{

    //region Constructor
    public InstantHealth(World w, int x, int y, int value, String urlImage) {
        super(w, x, y, value,urlImage);
    }
    //endregion
}
