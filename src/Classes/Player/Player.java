package Classes.Player;

import Classes.GameObject;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Book;
import Classes.Monster.Looter;
import Classes.Monster.Monster;
import Classes.Monster.Slime;
import Classes.Item.NotConsumableItem.Buoy;
import Classes.Item.NotConsumableItem.Weapon.Weapon;
import Classes.Monster.Wolf;
import Classes.NPC.NPC;
import Classes.World.Position;
import com.game.projet_javafx.BookApplication;
import javafx.beans.property.*;
import javafx.scene.image.ImageView;
import Classes.World.World;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.random;

public class Player extends GameObject {
    //region Constants
    private static final int HEIGHT = 675;
    private static final int WIDTH = 1500;
    private static final int ROWS = 18;
    private static final int COLUMNS=40;
    //endregion

    //region Player's attributes
    private final DoubleProperty LP;
    private String name;
    private final DoubleProperty money;
    private double strength;
    private double defense;                 //between 0 and 10
    private Map<String, Integer> status;
    private ArrayList<Item> inventory;
    private final IntegerProperty sizeInventory;

    private final ObjectProperty<NPC> nearByNPC;
    //endregion

    //region Constructor with all parameters
    public Player(World w, double LP, String name, double money, double strength, double defense, int x, int y) {
        super(w,x,y);
        this.LP = new SimpleDoubleProperty(LP);
        this.name = name;
        this.money = new SimpleDoubleProperty(money);
        this.strength = strength;
        this.defense = defense;
        this.status = new HashMap<String, Integer>();                   //No status at first
        this.inventory = new ArrayList<>();       //Inventory size is 9 slots max
        this.sizeInventory = new SimpleIntegerProperty(0);
        this.nearByNPC=new SimpleObjectProperty<>(null);
        node=new ImageView("image_pinguin.png");
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);

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
    public DoubleProperty getLPProperty() {return this.LP;}
    public double getLP() {return this.LP.getValue();}
    public void setLP(double LP) {
        if (LP>=0) {
            this.LP.set(LP);
        }else{
            this.LP.set(0);
        }
    }

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public DoubleProperty getMoneyProperty() {return this.money;}
    public double getMoney() {return this.money.getValue();}

    public void setMoney(double money) {this.money.set(money);}

    public double getStrength() {return this.strength;}
    public void setStrength(double strength) {this.strength = strength;}

    public double getDefense() {return this.defense;}
    public void setDefense(double defense) {this.defense = defense;}

    public ArrayList<Item> getInventory() {return this.inventory;}
    public void setInventory(ArrayList<Item> inventory) {this.inventory = inventory;}

    public HashMap<String, Integer> getStatus() {
        return (HashMap<String, Integer>) this.status;
    }
    public NPC getNearByNPC() {
        return nearByNPC.get();
    }

    public ObjectProperty<NPC> nearByNPCProperty() {
        return nearByNPC;
    }

    public void setNearByNPC(NPC nearByNPC) {
        this.nearByNPC.set(nearByNPC);
    }


    //endregion

    //region ToString function to print
    public String toString(){
        String tmp = "Name : " + this.getName() + "\nLP : " + this.getLP() + "\nMoney : " + this.getMoneyProperty() + "\nStrength : " + this.getStrength() + "\nDefense : " + this.getDefense() + "\nPosition : " + this.getPosition() + "\n";
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
        if (inventory.size()<10) {
            this.inventory.add(item);
            item.setDropped(false);
            this.sizeInventory.set(sizeInventory.getValue() + 1);
            Player p = this;
            item.node.setOnMouseClicked(mouseEvent -> {
                System.out.println(mouseEvent.getButton() == MouseButton.SECONDARY);
                if (!item.isDropped() && mouseEvent.getButton() == MouseButton.SECONDARY) {
                    System.out.println("ici");
                    p.removeFromInventory(item);
                    item.setPosition(p.getPosition().getX(), p.getPosition().getY());
                    world.addToWorld(item);
                }
                if(!item.isDropped() && mouseEvent.getButton() == MouseButton.PRIMARY){
                    if(item instanceof Book){
                        BookApplication bookApp = new BookApplication((Book) item);
                        try {
                            bookApp.start(new Stage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            });
        }
    }

    //Need to get the item for the Looter so return an item
    public Item removeFromInventory(Item item){
        int index = this.inventory.indexOf(item);
        if(index != -1){
            this.sizeInventory.set(sizeInventory.getValue()-1);
            return this.inventory.remove(index);
        }else{
            return null;                                        //if item not in inventory
        }
    }

    public boolean inventoryIsFull() {
        return this.getInventory().size() == 10;
    }

    public IntegerProperty sizeInventoryProperty() {
        return sizeInventory;
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
        if (action == 0 && this.canUsePotion()) {                   //use potion
            Potion potion = this.choosePotion();
            this.usePotion(monster, potion);
            return 0;                                               //doesn't do any damage
        }else if(action == 1){                                      //attack
            System.out.println(this.getName() + " attacks !");
            return 1;                                               //1 meaning damage done
        }else if(action == 2){                                      //dodge
            System.out.println(this.getName() + " tries to dodge : ");
            this.dodge();
            return 0;                                               //doesn't do any damage
        }
        return 0;
    }

    //Attack function for combat -> return the amount of damage done to the ennemy
    public double attack(int i, Monster monster){               //i is for the action the player wants : refer to chooseAction()
        System.out.println(this.getName() + " acts !");
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
        System.out.println(this.getName() + " defends!");
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
            System.out.println("dodge successful from the player !");
            return true;
        }
        else{
            System.out.println("failed to dodge from the player !");
            return false;
        }
    }

    //TODO : how to select the potion
    //Set the new status of the player depending on the potion used
    public void usePotion(Monster monster, Potion potion){
        System.out.println(this.getName() + " uses the potion" + potion);
        potion.setUsed(true);                                           //set used to true because potions are single use
        System.out.println(potion.getEffect().substring(2,3));
        if(potion.getEffect().substring(2, 3).equals("+")){           //if the potion is a bonus, looter applies to himself
            this.addStatus(potion.getEffect(), potion.getDuration());
            System.out.println("TEST+");
        }else if(potion.getEffect().substring(4).equals("LIFE")){
            this.setLP(this.getLP() + Integer.getInteger(potion.getEffect().substring(3)));

        }else if(potion.getEffect().substring(2, 3).equals("-")){     //if the potion is a malus, looter applies it to the player
            monster.addStatus(potion.getEffect(), potion.getDuration());
            System.out.println("TEST-");
        }
        this.getInventory().remove(potion);                             //removing the potion from the inventory
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
    public void moveold(String direction, int jump){
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
    public void move(int x, int y) {
        int x_start = getPosition().getX();
        int y_start = getPosition().getY();
        if (x>=0 && x<=39 && y>=0 && y<=17 && world.CanGoThere(x,y)) {
            ArrayList<Item> listItems = world.IsThereItem(x,y);
            for (Item i : listItems){
                this.addToInventory(i);
                world.removeFromWorld(i);
            }
            this.setPosition(x, y);
            NPC npc= world.IsThereNPC(x,y);
            if (npc!=null){
                System.out.println("There are a NPC here !");
                setNearByNPC(npc);
            }
            if (world.IsThereMonster(x,y)){
                System.out.println("There are a Monster here !");
            }
            if (world.IsThereRiver(x,y)){
                swim(); //you die
            }
            if (world.IsThereTrap(x,y)){
                this.setLP(this.getLP()-2);
            }
        }
    }

    public void jump(int x, int y) {
        System.out.println("function jump");
        if (x>=0 && x<=39 && y>=0 && y<=17 && world.CanGoThere(x,y)) {
            System.out.println("function jump");
            if (world.CanJumpThere(this.getPosition().getX(),this.getPosition().getY(),x,y)){
                System.out.println("function jump");
                move(x,y);
            }
        }
    }

    //region Automatic use of item
    //Use item named buoy to not drown into rivers
    //returns false if the player dies
    public boolean swim(){
        boolean swim = false;
        for (Item item : inventory) {
            if (item instanceof Buoy) {
                swim = true;                            //Search for buoy
                break;
            }
        }
        System.out.println(swim);
        if(!swim){
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
        //verifier si inventaire pas full   --> pour front si inventaire full -> alert de type warning
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

    public Potion choosePotion(){
        int i=0;
        ArrayList<Potion> potionlist = new ArrayList<>();
        for(Item item : this.getInventory().toArray(new Item[0])){
            if(item instanceof Potion){                                 //Gets all the potion from the player's inventory
                potionlist.add((Potion) item);
            }
        }
        //Printing potions to choose the one the player wants
        for(Potion potion : potionlist){
            System.out.println(i +" : " + potion);
            i++;
        }

        Scanner sc = new Scanner(System.in);                            //Use of scanner to get the player's answer for now
        System.out.println("Select your potion between 0 and " + i);
        int action = sc.nextInt();
        Potion potion = potionlist.get(action);
        System.out.println("The selected potion is " + potion);
        return potion;
    }
    //endregion

    //TODO : savoir ce qu'on en fait
    //region Victory and failure condition
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
    //endregion


}