package Classes.Monster;

import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.World;

import java.util.ArrayList;

//Enemy that poisons the player as a special attack
public class Slime extends Monster{

    //region Constructor
    public Slime(World w, int x, int y, String name, ArrayList<Item> inventory, int lifePoints, int strength, int defense, int cooldown, String urlImage) {
        super(w, x, y, name, inventory, lifePoints, strength, defense,cooldown, urlImage);
    }
    //endregion

    //region Fight functions
    @Override
    public int chooseAttack(Player player) {            //returns damage done
        if(super.specialAttack()){
            System.out.println(this.getName() + " poisons " + player.getName());
            player.addStatus("poisoned",2);             //adding poisoned status to player
            this.setCooldown(3);                        //reset cooldown
            setMessageAttack("The slime poisoned you !");
            return 0;                                   //returns 0 because only affects status
        }else{
            this.setCooldown(this.getCooldown()-1);
            System.out.println(this.getName() + " attacks " + player.getName() + "\n");
            setMessageAttack("The slime attacked you !");
            return 1;                                   //returns 1 to do the calculus in attack function : normal attack
        }
    }
    //endregion
}