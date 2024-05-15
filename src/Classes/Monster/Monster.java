package Classes.Monster;

import Classes.GameObject;
import Classes.Item.Item;
import Classes.World.Position;
import Classes.World.World;

import java.util.ArrayList;
import java.util.List;

public abstract class Monster extends GameObject {
    //region Attributes
    private String name;
    private int lifePoints;
    private int strength;
    private int defense;

    private Position position;
    private ArrayList<Item> inventory;
    private boolean alive;
    private String status;
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

    public Position getPosition() {
        return position;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getStatus() {
        return status;
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

    public void setStatus(String status) {
        this.status = status;
    }
    //endregion

    public Monster(World w, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y) {
        super(w,x,y);
        this.name = name;
        this.lifePoints = lifePoints;
        this.strength = force;
        this.defense = defense;
        this.inventory = inventory;
        this.alive=true;
    }

    public abstract void chooseAttack(int numRound);

    public double attack(){
        if(this.getStatus().contains("ST+")){
            return this.getStrength()*(1 + (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.getStatus().contains("ST-")){
            return this.getStrength()*(1 - (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else{
            return this.getStrength();                                                                                          //attack with the flat value of strength
        }
    }
    public void defend(){

    }

    public void die(){
        alive=false;
        //TODO Faire disparaitre le monstre en le faisant clignoter par exemple
    }

    public void dropItem(){
        for (Item item: getInventory()) {
            //TODO: Appeler la fonction faisant apparaitre un item dans la classe de l'item
        }
    }
}