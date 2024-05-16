package Classes.Monster;

import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.World;

import java.util.ArrayList;
import java.util.List;

public class Wolf extends Monster{
    //region Constructor
    public Wolf(World w, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y, int cooldown) {
        super(w, name, lifePoints, force, defense, inventory, x, y, cooldown);
    }

    public Wolf(ArrayList<Item> inventory){
        super(inventory);
    }
    //endregion


    @Override
    public int chooseAttack(Player player) {            //returns damage done
        if(super.specialAttack() == true){
            return 2;                                   //does double damage to player
        }else{
            return 1;                                   //returns 1 to do the calculus in attack function : normal attack
        }
    }
}