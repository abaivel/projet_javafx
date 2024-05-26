package Classes.Monster;

import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.World;

import java.util.ArrayList;

//Enemy that does greater damage as a special attack
public class Wolf extends Monster{

    //region Constructor
    public Wolf(World w, int x, int y, String name, ArrayList<Item> inventory, int lifePoints, int strength, int defense, int cooldown, String urlImage) {
        super(w, x, y, name, inventory, lifePoints, strength, defense, cooldown, urlImage);
    }
    //endregion

    //region Fight functions
    @Override
    public int chooseAttack(Player player) {            //returns damage done
        if(super.specialAttack()){
            this.setCooldown(3);                        //reset cooldown
            setMessageAttack("The wolf attacked you violently !");
            return 2;                                   //does double damage to player
        }else{
            this.setCooldown(this.getCooldown()-1);
            setMessageAttack("The wolf attacked you !");
            return 1;                                   //returns 1 to do the calculus in attack function : normal attack
        }
    }
    //endregion
}