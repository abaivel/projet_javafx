package Classes.Monster;

import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Player.Player;
import Classes.World.World;

import java.util.ArrayList;
import java.util.Random;

//Enemy that can steal player's item as a special attack
public class Looter extends Monster{

    //region Constructor
    public Looter(World w, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y,int cooldown, String urlImage) {
        super(w, name, lifePoints, force, defense, inventory, x, y,cooldown,urlImage);
    }
    //endregion

    //region Fight functions
    @Override
    public int chooseAttack(Player player) {            //returns damage done
        if(super.specialAttack()) {
            this.setCooldown(3);                        //reset cooldown
            Item itemToSteal = player.removeFromInventory(player.randomItemFromInventory());
            if (itemToSteal != null) {
                this.addToInventory(itemToSteal);   //steals a random object from the player's inventory
                setMessageAttack("The looter stole "+itemToSteal.getName()+" from your inventory");
            }else{
                setMessageAttack("The looter tried to steal something from your inventory, but it's empty ! ");
            }
            return 0;                                   //returns 0 because steals an object instead of attacking
        } else if(this.canUseObject()){
            this.setCooldown(this.getCooldown()-1);
            Item randomItem = this.randomItemFromInventory();        //gets a random item from the looter's inventory
            if(randomItem instanceof Potion){
                Potion potion = (Potion) randomItem;
                this.usePotion(player, potion);
                setMessageAttack("The looter used a potion");
                return 0;                                           //if it's not a potion does nothing in damage
            }else{
                setMessageAttack("The looter attacked the player");
                this.setCooldown(this.getCooldown()-1);
                return 1;
            }
        } else{
            setMessageAttack("The looter attacked the player");
            this.setCooldown(this.getCooldown()-1);
            return 1;                                               //returns 1 to do the calculus in attack function
        }
    }
    //endregion

    //region Item functions
    //To know if looter can use an object : meaning inventory not empty
    public boolean canUseObject(){
        return !this.getInventory().isEmpty();
    }

    //returns a random item from the looter's inventory
    public Item randomItemFromInventory(){
        int index = new Random().nextInt(this.getInventory().size());        //randomize an index
        return this.getInventory().get(index);                          //return the item linked to the random index
    }

    //Use a potion from the Looter's inventory
    public void usePotion(Player player, Potion potion){
        potion.setUsed(true);                                           //Set the status used to true because potions are single use
        this.getInventory().remove(potion);

        if(potion.getEffect().charAt(2) == '+'){           //if the potion is a bonus, looter applies to himself
            this.addStatus(potion.getEffect(), potion.getDuration());
        }else if(potion.getEffect().startsWith("LIFE")) {
            int value = Integer.parseInt(potion.getEffect().substring(4));
            this.setLifePoints(this.getLifePoints() + value);
        }else if(potion.getEffect().charAt(2) == '-'){     //if the potion is a malus, looter applies it to the player
            player.addStatus(potion.getEffect(), potion.getDuration());
        }else if(potion.getEffect().equals("DEATH")){
            player.setLP(0);
        }
    }
    //endregion
}