package Classes.Player;

import Classes.GameObject;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Buoy;
import Classes.Item.NotConsumableItem.Weapon.Weapon;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.random;

//TODO faire un dico pour le status key : status et sa valeur un int qui décrémente à chaque tour

//TO DO : ItemInterface implementation to use the inventory here
public class Player extends GameObject {
    //region Player's attributes
    private double LP;
    private String name;
    private double money;
    private double strength;
    private double defense;
    private Map<String, Integer> status;
    private Item[] inventory;
    //endregion

    //region Constructor with all parameters
    public Player(GridPane g, double LP, String name, double money, double strength, double defense, int x, int y) {
        super(g,x,y);
        this.LP = LP;
        this.name = name;
        this.money = money;
        this.strength = strength;
        this.defense = defense;
        this.status = new HashMap<String, Integer>();                  //No status at first
        this.inventory = new Item[9];                                   //Inventory size is 9 slots max
    }
    //endregion

    //region Default constructor
    public Player(){
        this(null, 10,"Player",0,5,2,0,0);
        this.status = new HashMap<String, Integer>();                  //No status at first
        this.inventory = new Item[9];      //Inventory size is 9 slots max
    }
    //endregion

    //region Getters and Setters
    public double getLP() {return this.LP;}
    public void setLP(double LP) {this.LP = LP;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public double getMoney() {return this.money;}
    public void setMoney(double money) {this.money = money;}

    public double getStrength() {return this.strength;}
    public void setStrength(double strength) {this.strength = strength;}

    public double getDefense() {return this.defense;}
    public void setDefense(double defense) {this.defense = defense;}

    public Item[] getInventory() {return this.inventory;}
    public void setInventory(Item[] inventory) {this.inventory = inventory;}
    //endregion

    //region ToString function to print
    public String toString(){
        String tmp = "Name : " + this.getName() + "\nLP : " + this.getLP() + "\nMoney : " + this.getMoney() + "\nStrength : " + this.getStrength() + "\nDefense : " + this.getDefense() + "\nStatus : " + "\nPosition : " + this.getPosition() + "\nInventory :";
        for(int i = 0; i < inventory.length; i++){
            tmp += inventory[i].toString() + " ; ";
        }
        for(String s : this.status.keySet()){
            tmp+= s + " : " + this.status.get(s) + "\n";
        }
        return tmp;
    }
    //endregion

    //TO DO : savoir ce qu'on en fait
    //Victory condition
    public boolean victory(){
        return this.contains("Hegdehog");           //if the last picked-up item is Hedgehog -> victory
    }

    //Failure condition
    public boolean failure(){
        if(this.getLP() <= 0){                      //if LP fall to 0 -> death of the player
            return true;
        }else{
            return false;                           //else we continue the game
        }
    }

    //Attack function for combat -> return the amount of damage done to the ennemy with his weapon and status (buff, debuff)
    public double attack(){
        double weaponDamage = this.containsWeapon();
        if(this.getStatus().contains("ST+")){
            return (this.getStrength() + weaponDamage)*(1 + (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.getStatus().contains("ST-")){
            return (this.getStrength() + weaponDamage)*(1 - (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else{
            return this.getStrength() + weaponDamage;                                                                                          //attack with the flat value of strength
        }
    }

    //Defense function for combat -> removes LF to the player
    public void takeDamage(double ennemyAttack){
        if(this.getStatus().contains("DE+")){       //if the player is under a potion that boost his defense
            this.setLP(this.getLP() - (ennemyAttack - ((this.getDefense()) * (1 + (Double.parseDouble(this.getStatus().substring(3))/100)))));    //we had a bonus of strength related to the player's status
        }else if(this.getStatus().contains("DE-")){ //if the player is under a potion that decreases this defense
            this.setLP(this.getLP() - (ennemyAttack - ((this.getDefense()) * (1 - (Double.parseDouble(this.getStatus().substring(3))/100)))));    //we had a bonus of strength related to the player's status
        }else{
            this.setLP(this.getLP()-(ennemyAttack - this.getDefense()));                                                                                          //attack with the flat value of strength
        }
    }

    public boolean dodge(){
        double d;
        if(this.getStatus().contains("DE+")){       //if the player is under a potion that boost his defense
            d = (this.getDefense()) * 100 + (1 + (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.getStatus().contains("DE-")){ //if the player is under a potion that decreases this defense
            d = (this.getDefense()) * 100 + (1 - (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else{
            d= this.getDefense();                                                                                          //attack with the flat value of strength
        }
        Random rand = new Random();
        int num = rand.nextInt(100 - 0 + 1) + 0;              //(max - min + 1) + min
        if(num < d){
            return true;
        }
        else{
            return false;
        }
    }


    //If jump is to 1 it means the player jumps --> moves 2 cases
    //If jump is to 0 the player only moves of 1 case
    //Direction :   -x means we go to the left on the x axis
    //              +x means we go to the right on the x axis
    //              -y means we go to the top of the y axis
    //              +y means we go to the bottom of the y axis
    // (0,0) top left corner
    //TO DO : if obstacle, player cannot move there
    public void move(String direction, int jump){
        switch(direction){
            case "-x":
                this.setPosition(this.position.getX()-1-jump,this.position.getY());
                break;
            case "+x":
                this.setPosition(this.position.getX()+1+jump,this.position.getY());
                System.out.println("test");
                break;
            case "-y":
                this.setPosition(this.position.getX(),this.position.getY()-1-jump);
                break;
            case "+y":
                this.setPosition(this.position.getX(),this.position.getY()+1+jump);
                break;
        }
    }

    //Set the new status of the player depending of the potion used
    public void usePotion(Potion potion){
        this.setStatus(potion.getEffect());
    }

    //TO DO
    //Use item named buoy to not drown into rivers
    //returns false if the player dies
    public boolean swim(){
        boolean swim = false;
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i] instanceof Buoy){
                swim = true;                            //Search for buoy
            }
        }
        if(swim == false){
            this.setLP(0);
            return false;
        }
        return true;
    }

    //TO DO
    //to pick up items on the floor ? Need to walk on the cell that has an item in it
    //updates the inventory with adding the new item
    public void pickItem(Item item){
        //faire ajouter à arraylise TO DO
        //verifier si inventaire pas full
        if(item.getName().equals("Hedgehog")){
            //calls front
        }
    }

    //TO DO
    public void throwItem(Item item){

    }

    public boolean contains(String item){
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i].getName().equals(item)){
                return true;
            }
        }
        return false;
    }

    //Look for all the Weapons in the player's inventory and chooses the best one
    public double containsWeapon(){
        ArrayList<Weapon> w = new ArrayList<Weapon>();     //list of weapons to fill
        for(int i = 0; i < inventory.length; i++){
            if(inventory[i] instanceof Weapon){
                w.add((Weapon) this.getInventory()[i]);                 //Adding the weapons to the list
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


    public static void main(String[] args) {
        Player p = new Player();
        p.move("+y",0);
        System.out.println(p);

    }

}