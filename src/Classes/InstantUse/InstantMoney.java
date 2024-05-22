package Classes.InstantUse;

import Classes.Player.Player;
import Classes.World.World;

public class InstantMoney extends Instant{

    //region Constructor
    public InstantMoney(World w, int x, int y, int value) {
        super(w, x, y, value);
    }
    //endregion

    public void playerGetsMoney(Player player){
        player.setMoney(player.getMoney() + getValue());            //The player gains the amount of money of the InstantUse "item"
    }
}
