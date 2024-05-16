package Classes.Monster;

import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.World;

import java.util.ArrayList;

public class Slime extends Monster{
    //region Constructor
    public Slime(World w, String name, int lifePoints, int strength, int defense, ArrayList<Item> inventory, int x, int y, int cooldown) {
        super(w, name, lifePoints, strength, defense, inventory, x, y,cooldown);
    }

    public Slime(ArrayList<Item> inventory){
        super(inventory);
    }
    //endregion

    @Override
    public int chooseAttack(Player player) {            //returns damage done
        if(super.specialAttack() == true){
            System.out.println(this.getName() + " poisons " + player.getName());
            player.addStatus("poisoned",3);             //adding poisoned status to player
            this.setCooldown(3);                        //reset cooldown
            return 0;                                   //returns 0 because only affects status
        }else{
            System.out.println(this.getName() + " attacks " + player.getName() + "\n");
            return 1;                                   //returns 1 to do the calculus in attack function : normal attack
        }
    }


}