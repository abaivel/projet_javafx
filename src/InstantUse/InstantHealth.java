package InstantUse;

import Classes.Player.Player;
import Classes.World.World;

//TODO : when picked up on the ground -> player gets back health
public class InstantHealth extends Instant{

    //region Constructor
    public InstantHealth(World w, int x, int y, int value) {
        super(w, x, y, value);
    }
    //endregion

    public void playerGetsHealth(Player player){
        player.setLP(player.getLP() + getValue());            //The player gains the amount of health of the InstantUse "item"
    }
}
