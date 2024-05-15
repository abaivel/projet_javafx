package Classes.Monster;

import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Looter extends Monster{
    //region Constructor
    public Looter(World w, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y, int cooldown) {
        super(w, name, lifePoints, force, defense, inventory, x, y, cooldown);
    }
    //endregion

    @Override
    public int chooseAttack(Player player) {            //returns damage done
        if(super.specialAttack() == true) {
            this.addToInventory(player.removeFromInventory(player.randomItemFromInvetory()));   //steals a random object from the player's inventory
            return 0;                                   //returns 0 because steals an object instead of attacking
        } else if(this.canUseObject() == true &&){
            Item randomItem = this.randomItemFromInvetory();        //gets a random item from the looter's inventory
            if(randomItem instanceof Potion){
                Potion potion = (Potion) randomItem;
                this.usePotion(player, potion);
                return 0;                                           //if it's not a potion does nothing in damage
            }
        } else{
            return 1;                                   //returns 1 to do the calculus in attack function
        }
    }

    //To know if looter can use an object
    public boolean canUseObject(){
        if(!this.getInventory().isEmpty()){
            return true;
        }
        return false;
    }

    //returns a random item from the looter's inventory
    public Item randomItemFromInvetory(){
        int index = new Random().nextInt(this.getInventory().size());        //randomize an index
        return this.getInventory().get(index);                          //return the item linked to the random index
    }

    //Use a potion from the Looter's inventory
    public void usePotion(Player player, Potion potion){
        if(potion.getEffect().substring(3) == ("+")){           //if the potion is a bonus, looter applies to himself
            this.addStatus(potion.getEffect(), 3);
        }else if(potion.getEffect().substring(3) == ("-")){     //if the potion is a malus, looter applies it to the player
            player.addStatus(potion.getEffect(), 3);
        }
    }
}