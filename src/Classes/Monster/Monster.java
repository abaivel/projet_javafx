package Classes.Monster;

import Classes.Item.Item;
import Classes.Killable;
import Classes.Player.Player;
import Classes.World.Position;
import Classes.World.World;
import javafx.beans.property.*;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

//Enemies in game
//Fight is launched when walking on the 9 cases around the monster
public abstract class Monster extends Killable {

    //region Monster's Attributes
    private int cooldown;       //number of rounds until special attack ; 3 by default
    private boolean alive;
    private final StringProperty messageAttack;
    //endregion

    //region Constructor
    public Monster(World w, int x, int y, String name, ArrayList<Item> inventory, int lifePoints, int strength, int defense, int cooldown,String urlImage) {
        super(w,x,y,name,inventory,lifePoints,strength,defense);
        this.alive=true;
        this.cooldown = cooldown;
        this.messageAttack = new SimpleStringProperty("");
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }

    //endregion

    //region Getters

    public boolean isAlive() {
        return alive;
    }

    //endregion

    //region Setters
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setCooldown(int cooldown) {this.cooldown = cooldown;}

    public int getCooldown() {return cooldown;}

    public String getMessageAttack() {
        return messageAttack.get();
    }

    public StringProperty messageAttackProperty() {
        return messageAttack;
    }

    public void setMessageAttack(String messageAttack) {
        this.messageAttack.set(messageAttack);
    }
    //endregion

    //region toString Function
    public String toString(){
        String tmp = "\nName : " + this.getName() + "\nLP : " + this.getLifePoints() + "\nMoney : " + this.getCooldown() + "\nStrength : " + this.getStrength() + "\nDefense : " + this.getDefense() + "\n";
        for (Item item : this.getInventory()) {
            tmp += item.toString() + "\n";
        }
        return tmp;
    }
    //endregion

    //region Fight functions
    //True if the monster can do a special attack
    public boolean specialAttack(){
        if(this.getCooldown() == 0){
            return true;
        }
        return false;
    }

    public abstract int chooseAttack(Player player);

    //Monster attack function
    public double attack(Player player){
        System.out.println(this.getName() + " attacks !");
        double weaponDamage = this.containsWeapon();            //Getting the damage from the best weapon on the Player ; 0 if they don't own any
        this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        int STStatus = strengthStatus();
        if(STStatus > 0){                     //If Strength + status -> add it to damage done
            return (this.chooseAttack(player) * this.getStrength() + weaponDamage)*(1 + STStatus/100);    //we had a bonus of strength related to the player's status
        }else if(STStatus < 0){             //If Strength - status -> remove it to damage done
            return (this.chooseAttack(player) * this.getStrength() + weaponDamage)*(1 - (STStatus/100));    //we had a bonus of strength related to the player's status
        }else{
            return this.chooseAttack(player) * (this.getStrength() + weaponDamage);                                                                                          //attack with the flat value of strength
        }
    }

    public void defend(double enemyAttack){
        System.out.println(this.getName() + " defends !");
        System.out.println(enemyAttack);
        this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        int DEStatus = defenseStatus();
        if(DEStatus > 0){                     //if the monster is under a potion that boost his defense
            this.setLifePoints((int) (this.getLifePoints() - (enemyAttack - ((this.getDefense()) * (1 + DEStatus/100)))));    //we had a bonus of strength related to the player's status
        }else if(DEStatus < 0){               //if the monster is under a potion that decreases this defense
            this.setLifePoints((int) (this.getLifePoints() - (enemyAttack - ((this.getDefense()) * (1 - DEStatus/100)))));    //we had a bonus of strength related to the player's status
        }else{
            if(enemyAttack > this.getDefense()){
                this.setLifePoints((int) (this.getLifePoints()-(enemyAttack - this.getDefense())));    //attack with the flat value of strength ; only deal damage if attack superior to def
            }

        }
    }

    //endregion

    //region death functions
    public void dropItem(){
        ArrayList<Item> copyInventory = (ArrayList<Item>) getInventory().clone();
        for (Item item : copyInventory) {
            item.setPosition(this.getPosition().getX(), this.getPosition().getY());
            item.setDropped(true);
            removeFromInventory(item);
        }
    }
    //endregion

}