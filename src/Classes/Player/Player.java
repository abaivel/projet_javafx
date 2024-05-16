package Classes.Player;

import Classes.GameObject;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Monster.Looter;
import Classes.Monster.Monster;
import Classes.Monster.Slime;
import Classes.Item.NotConsumableItem.Buoy;
import Classes.Item.NotConsumableItem.Weapon.Weapon;
import Classes.Monster.Wolf;
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

public class Player extends GameObject {
    //region Constants
    private static final int HEIGHT = 675;
    private static final int WIDTH = 1500;
    private static final int ROWS = 18;
    private static final int COLUMNS=40;
    //endregion

    //region Player's attributes
    private double LP;
    private String name;
    private double money;
    private double strength;
    private double defense;                 //between 0 and 10
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
        this.status = new HashMap<String, Integer>();                   //No status at first
        this.inventory = new ArrayList<>();       //Inventory size is 9 slots max
        //image=new ImageView("H:\\Desktop\\CY TECH\\S2\\Java\\PROJET-FX\\projet_javafx\\src\\main\\resources\\image_pinguin.png");
        //image.setFitHeight((double) HEIGHT /ROWS);
        //image.setFitWidth((double) WIDTH/COLUMNS);
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

    public HashMap<String, Integer> getStatus() {
        return (HashMap<String, Integer>) this.status;
    }


    //endregion

    //region ToString function to print
    public String toString(){
        String tmp = "Name : " + this.getName() + "\nLP : " + this.getLP() + "\nMoney : " + this.getMoney() + "\nStrength : " + this.getStrength() + "\nDefense : " + this.getDefense() + "\nPosition : " + this.getPosition() + "\n";
        if(!inventory.isEmpty()){
            tmp += "Inventory : ";
        }
        for(int i = 0; i < inventory.size(); i++){
            tmp += "Item n°" + i + " : " + inventory.get(i).toString() + "\n";
        }
        tmp += "Status :\n";
        for(String s : this.status.keySet()){
            tmp += s + ":" + this.status.get(s) + "rounds left";
        }
        return tmp;
    }
    //endregion

    //region Inventory functions
    public void addToInventory(Item item){
        this.inventory.add(item);
    }

    //Need to get the item for the Looter so return an item
    public Item removeFromInventory(Item item){
        int index = this.inventory.indexOf(item);
        if(index != -1){
            Item removed = this.inventory.remove(index);        //get the removed item
            return removed;
        }else{
            return null;                                        //if item not in inventory
        }
    }

    public Item randomItemFromInvetory(){
        int index = new Random().nextInt(this.inventory.size());        //randomize an index
        return this.getInventory().get(index);                          //return the item linked to the random index
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
        ArrayList<Weapon> w = new ArrayList<>();     //list of weapons to fill
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
    //endregion

    //region Status functions
    public void addStatus(String key, int value) {
        this.getStatus().put(key, value);
    }

    //Removes status from the map when they reach count 0
    public void statusWornOff(){
        for(String s : this.status.keySet()){       //loop on the keyset
            if(this.status.get(s) == 0){            //verification if value is 0
                this.status.remove(s);              //removes the status
            }
        }
    }

    //Returns the defense status (in percentage) depending on all the potions : buff and debuff
    public int defenseStatus(){
        int defense = 0;                                     //a percentage
        if(this.status.containsKey("DE+")){                     //if the player is under a potion that boost his defense
            this.status.put("DE+",this.status.get("DE+")-1);
            defense += Integer.parseInt("DE+".substring(3));    //we had a bonus of strength related to the player's status
        } else if(this.status.containsKey("DE-")){               //if the player is under a potion that decreases this defense
            this.status.put("DE-",this.status.get("DE-")-1);
            defense += Integer.parseInt("DE-".substring(3));    //we had a bonus of strength related to the player's status
        }
        return defense;
    }

    //Returns the strength status (in percentage) depending on all the potions : buff and debuff
    public int strengthStatus(){
        int strength = 0;                                                       //a percentage
        if(this.status.containsKey("ST+")){                                     //if the player is under a potion that boost his defense
            this.status.put("ST+",this.status.get("ST+")-1);
            strength += Integer.parseInt("ST+".substring(3));          //we had a bonus of strength related to the player's status
        } else if(this.status.containsKey("ST-")){                              //if the player is under a potion that decreases this defense
            this.status.put("ST-",this.status.get("ST-")-1);
            strength -= Integer.parseInt("ST-".substring(3));          //we had a bonus of strength related to the player's status
        }
        return strength;
    }

    public void isPoisonned(){
        if(this.status.containsKey("poisoned")){                              //if poisoned take one make damage per turn
            this.setLP(this.getLP()- 2);                                      //received poison damage
            this.status.put("poisoned",this.status.get("poisoned")-1);
        }
    }
    //endregion

    //region Fight -> Attack, Defense, Dodge, Use potion
    //Chooses the action done in fight
    public double chooseAction(int action, Monster monster){        //returns if deal damage or not
        if (action == 0 && this.canUsePotion()) {   //use potion
            Potion potion = this.choosePotion();
            this.usePotion(monster, potion);
            return 0;                                               //doesn't do any damage
        }else if(action == 1){                                      //attack
            System.out.println(this.getName() + " attacks !\n");
            return 1;                                               //1 meaning damage done
        }else if(action == 2){                                      //dodge
            System.out.println(this.getName() + " tries to dodge : ");
            this.dodge();
            return 0;                                               //doesn't do any damage
        }
        return 0;
    }

    //Attack function for combat -> return the amount of damage done to the ennemy
    public double attack(int i, Monster monster){                                //i is for the action the player wants : refer to chooseAction()
        System.out.println(this.getName() + " acts !\n");
        double weaponDamage = this.containsWeapon();            //Getting the damage from the best weapon on the Player ; 0 if they don't own any
        this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        int STStatus = strengthStatus();
        if(STStatus > 0){                                       //If Strengh+ status -> add it to damage done
            return (this.chooseAction(i, monster) * this.getStrength() + weaponDamage)*(1 + STStatus/100);    //buff of strength
        }else if(STStatus < 0){                                 //If Strengh- status -> remove it to damage done
            return (this.chooseAction(i, monster) * this.getStrength() + weaponDamage)*(1 - STStatus/100);    //debuff of strength
        }else{
            return this.chooseAction(i, monster) * this.getStrength() + weaponDamage;                         //no buff or debuff                                                                    //attack with the flat value of strength
        }
    }

    //TODO destroy potions when used in fights -> is done correctly ?
    //Defense function for combat -> removes LF to the player
    public void defend(double ennemyAttack){
        System.out.println(this.getName() + " defends!\n");
        this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        this.isPoisonned();                                     //Takes damage from poison if is poisoned
        int DEStatus = defenseStatus();

        if(DEStatus > 0){
            this.setLP(this.getLP() - (ennemyAttack - ((this.getDefense()) * (1 + DEStatus/100))));    //buff of defense
        } else if(DEStatus < 0){
            this.setLP(this.getLP() - (ennemyAttack - ((this.getDefense()) * (1 - DEStatus/100))));    //debuff of defense
        } else{
            if(ennemyAttack > this.getDefense()){                                                       //does damage only when the attack is superior to the def
                this.setLP(this.getLP()-(ennemyAttack - this.getDefense()));
            }

        }
    }

    public boolean dodge(){
        double d;
        if(this.status.containsKey("DE+")){       //if the player is under a potion that boost his defense
            d = (this.getDefense()) * 10 + (1 + (Double.parseDouble("DE+".substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.status.containsKey("DE-")){ //if the player is under a potion that decreases this defense
            d = (this.getDefense()) * 10 + (1 - (Double.parseDouble("DE-".substring(3))/100));    //we had a bonus of strength related to the player's status
        }else{
            d= this.getDefense() * 10;                                                                                          //attack with the flat value of strength
        }
        Random rand = new Random();
        int num = rand.nextInt(100 - 0 + 1) + 0;              //(max - min + 1) + min
        if(num < d){
            System.out.println("dodge successful from the player !\n");
            return true;
        }
        else{
            System.out.println("failed to dodge from the player !\n");
            return false;
        }
    }

    //Set the new status of the player depending on the potion used
    public void usePotion(Monster monster, Potion potion){
        System.out.println(this.getName() + " uses a potion");
        this.getInventory().remove(potion);
        if(potion.getEffect().substring(3) == ("+")){           //if the potion is a bonus, looter applies to himself
            this.addStatus(potion.getEffect(), potion.getDuration());
        }else if(potion.getEffect() == "LIFE"){
            this.setLP(this.getLP() + Integer.getInteger(potion.getEffect().substring(3)));

        }else if(potion.getEffect().substring(3) == ("-")){     //if the potion is a malus, looter applies it to the player
            monster.addStatus(potion.getEffect(), potion.getDuration());
        }
    }
    //endregion

    //region Move
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
    //endregion

    //region Automatic use of item
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
    //endregion

    //TODO
    //region Item functions
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


    public boolean canUsePotion(){
        if(!this.getInventory().isEmpty()){
            for(int i = 0; i < inventory.size(); i++){
                if(inventory.get(i) instanceof Potion){
                    return true;
                }
            }
        }
        return false;
    }

    //TODO how to get the potion the player wants ?????
    public Potion choosePotion(){
        Potion potion = null;
        return potion;
    }
    //endregion

    //TODO : savoir ce qu'on en fait
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



}