package Classes.Monster;

import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.World;

import java.util.ArrayList;

public class Slime extends Monster{
    //region Constructor
    public Slime(World w, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y, int cooldown) {
        super(w, name, lifePoints, force, defense, inventory, x, y,cooldown);
    }
    //endregion

    @Override
    public int chooseAttack(Player player) {            //returns damage done
        if(super.specialAttack() == true){
            player.addStatus("poisoned",3);             //adding poisoned status to player
            return 0;                                   //returns 0 because only affects status
        }else{
            return 1;                                   //returns 1 to do the calculus in attack function : normal attack
        }
    }


}