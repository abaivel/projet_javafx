package Classes.Monster;

import Classes.GameObject;
import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Weapon.Weapon;
import Classes.Player.Player;
import Classes.World.Position;
import Classes.World.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Monster extends GameObject {
    //region Monster's Attributes
    private String name;
    private int lifePoints;
    private int strength;
    private int defense;
    private int cooldown;       //number of rounds until special attack ; 3 by default

    private ArrayList<Item> inventory;
    private boolean alive;
    private Map<String, Integer> status;
    //endregion

    //region Getters
    public String getName() {
        return name;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public boolean isAlive() {
        return alive;
    }

    public HashMap<String, Integer> getStatus() {
        return (HashMap<String, Integer>) this.status;
    }

    //endregion

    //region Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void setStrength(int force) {
        this.strength = force;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setCooldown(int cooldown) {this.cooldown = cooldown;}

    public int getCooldown() {return cooldown;}
    //endregion

    //region Constructeur
    public Monster(World w, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y, int cooldown) {
        super(w,x,y);
        this.name = name;
        this.lifePoints = lifePoints;
        this.strength = force;
        this.defense = defense;
        this.inventory = inventory;
        this.alive=true;
        this.cooldown = cooldown;
        this.status = new HashMap<String, Integer>();
    }
    //endregion


    public void addStatus(String key, int value) {
        this.getStatus().put(key, value);
    }

    public void addToInventory(Item item){
        this.inventory.add(item);
    }

    public Item removeFromInventory(Item item){
        int index = this.inventory.indexOf(item);
        if(index != -1){
            Item removed = this.inventory.remove(index);        //get the removed item
            return removed;
        }else{
            return null;                                        //if item not in inventory
        }
    }

    public abstract int chooseAttack(Player player);

    //Monster attack function
    public double attack(Player player){
        double weaponDamage = this.containsWeapon();            //Getting the damage from the best weapon on the Player ; 0 if they don't own any
        this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        if(this.status.containsKey("ST+")){                     //If Strengh+ status -> add it to damage done
            this.status.put("ST+",this.status.get("ST+")-1);
            return (this.chooseAttack(player) * this.getStrength() + weaponDamage)*(1 + (Double.parseDouble("ST+".substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.status.containsKey("ST-")){             //If Strengh- status -> remove it to damage done
            this.status.put("ST+",this.status.get("ST-")-1);
            return (this.chooseAttack(player) * this.getStrength() + weaponDamage)*(1 - (Double.parseDouble("ST-".substring(3))/100));    //we had a bonus of strength related to the player's status
        }else{
            return this.chooseAttack(player) * (this.getStrength() + weaponDamage);                                                                                          //attack with the flat value of strength
        }
    }

    //True if the monster can do a special attack
    public boolean specialAttack(){
        if(this.getCooldown() == 0){
            return true;
        }
        return false;
    }

    //Removes status from the map when they reach count 0
    public void statusWornOff(){
        for(String s : this.status.keySet()){       //loop on the keyset
            if(this.status.get(s) == 0){            //verification if value is 0
                this.status.remove(s);              //removes the status
            }
        }
    }

    //Look for all the Weapons in the monster's inventory and chooses the best one
    public double containsWeapon(){
        ArrayList<Weapon> w = new ArrayList<Weapon>();     //list of weapons to fill
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) instanceof Weapon){
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

    public void defend(){

    }

    public void die(){
        this.setAlive(false);
        //TODO Faire disparaitre le monstre en le faisant clignoter par exemple
    }

    public void dropItem(){
        for (Item item: getInventory()) {
            //TODO: Appeler la fonction faisant apparaitre un item dans la classe de l'item
        }
    }

}