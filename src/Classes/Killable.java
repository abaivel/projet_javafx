package Classes;

import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Weapon.Weapon;
import Classes.Monster.Monster;
import Classes.Player.Player;
import Classes.World.World;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Killable extends Character {
    private int strength;
    private int defense;
    private final IntegerProperty lifePoints;
    private Map<String, Integer> status;

    private final IntegerProperty numberStatus;
    public Killable(World w, int x, int y, ArrayList<Item> inventory, int lifePoints, int strength, int defense) {
        super(w, x, y, inventory);
        this.lifePoints = new SimpleIntegerProperty(lifePoints);
        this.strength = strength;
        this.defense = defense;
        this.status = new HashMap<String, Integer>();
        this.numberStatus = new SimpleIntegerProperty(0);
    }
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getLifePoints() {
        return lifePoints.get();
    }

    public IntegerProperty getLifePointsProperty() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints.set(lifePoints);
    }

    public Map<String, Integer> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Integer> status) {
        this.status = status;
    }

    public int getNumberStatus() {
        return numberStatus.get();
    }

    public IntegerProperty getNumberStatusProperty() {
        return numberStatus;
    }

    public void setNumberStatus(int numberStatus) {
        this.numberStatus.set(numberStatus);
    }



    public void addStatus(String key, int value) {
        this.getStatus().put(key, value);
        this.setNumberStatus(this.getNumberStatus()+1);
    }
    public void statusWornOff(){
        for(String s : this.getStatus().keySet()){       //loop on the keyset
            this.getStatus().put(s,this.getStatus().get(s)-1);
            this.setNumberStatus(this.getNumberStatus()+1);
            this.setNumberStatus(this.getNumberStatus()-1);
            if(this.getStatus().get(s) == 0){            //verification if value is 0
                this.getStatus().remove(s);              //removes the status
                this.setNumberStatus(this.getNumberStatus()-1);
            }
        }
    }
    //region Status functions
    //Returns the defense status (in percentage) depending on all the potions : buff and debuff
    public int defenseStatus(){
        int defense = 0;                                     //a percentage
        for (String key : this.getStatus().keySet()){
            if (key.contains("DE+")){ //if the player is under a potion that boost his defense
                defense += Integer.parseInt(key.substring(3));
            }else if (key.contains("DE-")){ //if the player is under a potion that decreases this defense
                defense += Integer.parseInt(key.substring(3));
            }
        }
        return defense;
    }
    //Returns the strength status (in percentage) depending on all the potions : buff and debuff
    public int strengthStatus(){
        int strength = 0;                                                       //a percentage
        for (String key : this.getStatus().keySet()){
            if (key.contains("ST+")){ //if the player is under a potion that boost his strength
                strength +=  Integer.parseInt(key.substring(3));
            }else if (key.contains("ST-")){ //if the player is under a potion that decreases his strength
                strength += Integer.parseInt(key.substring(3));
            }
        }
        return strength;
    }
    public void isPoisoned(){
        if(this.getStatus().containsKey("poisoned")){                              //if poisoned take one make damage per turn
            this.setLifePoints(this.getLifePoints()- 2);                                      //received poison damage
        }
    }
    //endregion

    //Look for all the Weapons in the player's inventory and chooses the best one
    public double containsWeapon(){
        ArrayList<Weapon> w = new ArrayList<>();     //list of weapons to fill
        for(int i = 0; i < this.getInventory().size(); i++){
            if(this.getInventory().get(i) instanceof Weapon){
                w.add((Weapon) this.getInventory().get(i));                 //Adding the weapons to the list
            }
        }
        if(w.isEmpty()){
            return 0;
        }
        int max=0;
        double value=0;
        for(int i = 0; i < w.size(); i++){
            if(w.get(i).getDamage() > value){
                value = w.get(i).getDamage();
                max = i;
            }
        }
        return w.get(max).getDamage();
    }
}
