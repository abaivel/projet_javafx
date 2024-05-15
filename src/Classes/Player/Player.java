package Classes.Player;

import Classes.GameObject;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Buoy;
import Classes.Item.NotConsumableItem.Weapon.Weapon;
import Classes.World.Position;
import javafx.scene.image.ImageView;
import Classes.World.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.random;

//TODO faire un dico pour le status key : status et sa valeur un int qui décrémente à chaque tour

//TO DO : ItemInterface implementation to use the inventory here
public class Player extends GameObject {
    private static final int HEIGHT = 675;
    private static final int WIDTH = 1500;
    private static final int ROWS = 18;
    private static final int COLUMNS=40;
    //region Player's attributes
    private double LP;
    private String name;
    private double money;
    private double strength;
    private double defense;
    private Map<String, Integer> status;
    private ArrayList<Item> inventory;
    public ImageView image;
    //endregion

    //region Constructor with all parameters
    public Player(World w, double LP, String name, double money, double strength, double defense, int x, int y) {
        super(w,x,y);
        this.LP = LP;
        this.name = name;
        this.money = money;
        this.strength = strength;
        this.defense = defense;
        this.status = new HashMap<String, Integer>();;                   //No status at first
        this.inventory = new ArrayList<>();       //Inventory size is 9 slots max
        image=new ImageView("H:\\Documents\\école\\ING1\\POO Java\\projet_javafx\\projet_javafx\\src\\main\\resources\\image_pinguin.png");
        image.setFitHeight((double) HEIGHT /ROWS);
        image.setFitWidth((double) WIDTH/COLUMNS);
        /*g.add(image,x,y);*/
    }
    //endregion

    //region Default constructor
    public Player(){
        this(null, 10,"Player",0,5,2,0,0);
        this.status = new HashMap<String, Integer>();                  //No status at first//Inventory size is 9 slots max
        this.inventory = new ArrayList<>();      //Inventory size is 9 slots max
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

    public ArrayList<Item> getInventory() {return this.inventory;}
    public void setInventory(ArrayList<Item> inventory) {this.inventory = inventory;}
    //endregion
    public void addToInventory(Item item){
        this.inventory.add(item);
    }
    public void removeFromInventory(Item item){
        this.inventory.remove(item);
    }

    //region ToString function to print
    public String toString(){
        String tmp = "Name : " + this.getName() + "\nLP : " + this.getLP() + "\nMoney : " + this.getMoney() + "\nStrength : " + this.getStrength() + "\nDefense : " + this.getDefense() + "\nPosition : " + this.getPosition() + "\n\n";
        for(int i = 0; i < inventory.size(); i++){
            tmp += inventory.get(i).toString() + "\n";
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
    //TODO potion de vie

    //Attack function for combat -> return the amount of damage done to the ennemy
    public double attack(){
        double weaponDamage = this.containsWeapon();            //Getting the damage from the best weapon on the Player ; 0 if they don't own any
        this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        if(this.status.containsKey("ST+")){                     //If Strengh+ status -> add it to damage done
            this.status.put("ST+",this.status.get("ST+")-1);
            return (this.getStrength() + weaponDamage)*(1 + (Double.parseDouble("ST+".substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.status.containsKey("ST-")){             //If Strengh- status -> remove it to damage done
            this.status.put("ST+",this.status.get("ST-")-1);
            return (this.getStrength() + weaponDamage)*(1 - (Double.parseDouble("ST-".substring(3))/100));    //we had a bonus of strength related to the player's status
        }else{
            return this.getStrength() + weaponDamage;                                                                                          //attack with the flat value of strength
        }
    }

    //Removes status from the map when they reach count 0
    public void statusWornOff(){
        for(String s : this.status.keySet()){       //loop on the keyset
            if(this.status.get(s) == 0){            //verification if value is 0
                this.status.remove(s);              //removes the status
            }
        }
    }

    //Defense function for combat -> removes LF to the player
    public void takeDamage(double ennemyAttack){
        this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        if(this.status.containsKey("DE+")){       //if the player is under a potion that boost his defense
            this.status.put("DE+",this.status.get("DE+")-1);
            this.setLP(this.getLP() - (ennemyAttack - ((this.getDefense()) * (1 + (Double.parseDouble("DE+".substring(3))/100)))));    //we had a bonus of strength related to the player's status
        }else if(this.status.containsKey("DE-")){ //if the player is under a potion that decreases this defense
            this.status.put("DE-",this.status.get("DE-")-1);
            this.setLP(this.getLP() - (ennemyAttack - ((this.getDefense()) * (1 - (Double.parseDouble("DE-".substring(3))/100)))));    //we had a bonus of strength related to the player's status
        }else if(this.status.containsKey("poisoned")){                              //if poisoned take one make damage per turn
            this.setLP(this.getLP()-(ennemyAttack + 1 - this.getDefense()));                                                                    //attack with the flat value of strength
        }else{
            this.setLP(this.getLP()-(ennemyAttack - this.getDefense()));
        }
    }

    public boolean dodge(){
        double d;
        if(this.status.containsKey("DE+")){       //if the player is under a potion that boost his defense
            d = (this.getDefense()) * 100 + (1 + (Double.parseDouble("DE+".substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.status.containsKey("DE-")){ //if the player is under a potion that decreases this defense
            d = (this.getDefense()) * 100 + (1 - (Double.parseDouble("DE-".substring(3))/100));    //we had a bonus of strength related to the player's status
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
        this.status.put(potion.getEffect(),potion.getDuration());
    }

    //Use item named buoy to not drown into rivers
    //returns false if the player dies
    public boolean swim(){
        boolean swim = false;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) instanceof Buoy){
                swim = true;                            //Search for buoy
            }
        }
        if(swim == false){
            this.setLP(0);
            return false;
        }
        return true;
    }

    //TODO
    //to pick up items on the floor ? Need to walk on the cell that has an item in it
    //updates the inventory with adding the new item
    public void pickItem(Item item){
        //faire ajouter à arraylise TO DO
        //verifier si inventaire pas full
        if(item.getName().equals("Hedgehog")){
            //calls front
        }
    }

    //TODO
    public void throwItem(Item item){

    }

    public boolean contains(String item){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).getName().equals(item)){
                return true;
            }
        }
        return false;
    }

    //Look for all the Weapons in the player's inventory and chooses the best one
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


    public static void main(String[] args) {
        Player p = new Player();
        p.move("+y",0);
        System.out.println(p);

    }

}