package Classes.Player;

import Classes.GameObject;
import Classes.InstantUse.Instant;
import Classes.InstantUse.InstantHealth;
import Classes.InstantUse.InstantMoney;
import Classes.Item.ConsumableItem.Bomb;
import Classes.Item.ConsumableItem.Key;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Book;
import Classes.Monster.Monster;
import Classes.Item.NotConsumableItem.Buoy;
import Classes.Item.NotConsumableItem.Weapon.Weapon;
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


//The main character ! A brave penguin (that deserves a bonus point in itself !!)
//Goal : reach the MIGHTY HEDGEHOG -> to win pick up Hedgehog item
//Can die in fights, drowning, being blown up by a bomb
//Can move in every direction and jump over low decors
public class Player extends GameObject {

    //region Player's attributes
    private final DoubleProperty LP;
    private String name;
    private final DoubleProperty money;
    private double strength;
    private double defense;                 //between 0 and 10
    private boolean dodge;
    private Map<String, Integer> status;
    private final IntegerProperty indexWorld;

    private final IntegerProperty numberStatus;
    private ArrayList<Item> inventory;
    private final IntegerProperty sizeInventory;

    private final ObjectProperty<NPC> nearByNPC;

    private final ObjectProperty<Monster> nearByMonster;
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
        this.numberStatus = new SimpleIntegerProperty(0);
        this.inventory = new ArrayList<>();       //Inventory size is 9 slots max
        this.sizeInventory = new SimpleIntegerProperty(0);
        this.nearByNPC=new SimpleObjectProperty<>(null);
        this.nearByMonster=new SimpleObjectProperty<>(null);
        this.dodge=false;
        this.indexWorld = new SimpleIntegerProperty(0);
        node=new ImageView("image_penguin.png");
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
            if (LP>10){
                this.LP.set(10);
            }else {
                this.LP.set(LP);
            }
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
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
        this.sizeInventory.set(inventory.size());
    }

    public HashMap<String, Integer> getStatus() {
        return (HashMap<String, Integer>) this.status;
    }
    public int getNumberStatus() {
        return numberStatus.get();
    }

    public IntegerProperty numberStatusProperty() {
        return numberStatus;
    }

    public void setNumberStatus(int numberStatus) {
        this.numberStatus.set(numberStatus);
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
    public Monster getNearByMonster() {
        return nearByMonster.get();
    }

    public ObjectProperty<Monster> nearByMonsterProperty() {
        return nearByMonster;
    }

    public void setNearByMonster(Monster nearByMonster) {
        this.nearByMonster.set(nearByMonster);
    }
    public boolean isDodge() {
        return dodge;
    }

    public void setDodge(boolean dodge) {
        this.dodge = dodge;
    }

    public int getIndexWorld() {
        return indexWorld.get();
    }

    public IntegerProperty getIndexWorldProperty() {
        return indexWorld;
    }

    public void setIndexWorld(int indexWorld) {
        this.indexWorld.set(indexWorld);
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
        if (inventory.size()<10) {
            this.inventory.add(item);
            item.setDropped(false);
            this.sizeInventory.set(sizeInventory.getValue() + 1);
            Player p = this;
            item.node.setOnMouseClicked(mouseEvent -> {
                System.out.println(mouseEvent.getButton() == MouseButton.SECONDARY);
                if (!item.isDropped() && mouseEvent.getButton() == MouseButton.SECONDARY) {
                    System.out.println("ici");
                    item.setDropped(true);
                    p.removeFromInventory(item);
                    item.setPosition(p.getPosition().getX(), p.getPosition().getY());
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
                if(item instanceof Bomb && mouseEvent.getButton() == MouseButton.PRIMARY && item.isDropped()){
                    System.out.println(item.getPosition());
                    this.world.destroysRadiusBomb(item.getPosition().getX(), item.getPosition().getY());
                }
            });
        }
    }

    //Need to get the item for the Looter so return an item
    public Item removeFromInventory(Item item){
        int index = this.inventory.indexOf(item);
        if(index != -1){
            Item removed = this.inventory.remove(index);        //get the removed item
            this.sizeInventory.set(sizeInventory.getValue()-1);
            return removed;
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

    public Item randomItemFromInventory(){
        if (!this.getInventory().isEmpty()) {
            int index = new Random().nextInt(this.inventory.size());        //randomize an index
            return this.getInventory().get(index);                          //return the item linked to the random index
        }
        return null;
    }

    public boolean contains(String item){
        for (Item value : inventory) {
            if (value.getName().equals(item)) {
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

    public ArrayList<Key> containsKey() {
        ArrayList<Key> k = new ArrayList<>();     //list of keys to fill
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Key) {
                k.add((Key) this.getInventory().get(i));                 //Adding the weapons to the list
            }
        }
        if (k.isEmpty()) {
            return null;
        } else {
            return k;
        }
    }
    //endregion

    //region Status functions
    public void addStatus(String key, int value) {
        this.getStatus().put(key, value);
        this.setNumberStatus(this.getNumberStatus()+1);
    }

    //Removes status from the map when they reach count 0
    public void statusWornOff(){
        for(String s : this.status.keySet()){       //loop on the keyset
            this.status.put(s,this.status.get(s)-1);
            this.setNumberStatus(this.getNumberStatus()+1);
            this.setNumberStatus(this.getNumberStatus()-1);
            if(this.status.get(s) == 0){            //verification if value is 0
                this.status.remove(s);              //removes the status
                this.setNumberStatus(this.getNumberStatus()-1);
            }
        }
    }

    //Returns the defense status (in percentage) depending on all the potions : buff and debuff
    public int defenseStatus(){
        int defense = 0;                                     //a percentage
        for (String key : this.status.keySet()){
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
        for (String key : this.status.keySet()){
            if (key.contains("ST+")){ //if the player is under a potion that boost his strength
                strength +=  Integer.parseInt(key.substring(3));
            }else if (key.contains("ST-")){ //if the player is under a potion that decreases his strength
                strength += Integer.parseInt(key.substring(3));
            }
        }
        return strength;
    }

    public void isPoisoned(){
        if(this.status.containsKey("poisoned")){                              //if poisoned take one make damage per turn
            this.setLP(this.getLP()- 2);                                      //received poison damage
        }
    }
    //endregion

    //region Fight -> Attack, Defense, Dodge, Use potion
    //Chooses the action done in fight
    public double chooseAction(int action, Monster monster, Item item){        //returns if deal damage or not
        int doDamages=0;
        if (action == 0 && this.canUsePotion()) {                   //use potion
            this.usePotion(monster, (Potion) item);
        }else if(action == 1){                                      //attack
            System.out.println(this.getName() + " attacks !");
            doDamages=1;                                               //1 meaning damage done
        }else if(action == 2){                                      //dodge
            System.out.println(this.getName() + " tries to dodge : ");
            this.dodge();
        }
        //TODO to use items in fight
        /*else if (action==3){
            this.useItem(monster, item);
        }*/
        //this.statusWornOff();                                   //At the end of the round, removes worn off effects from the Map
        return doDamages;
    }

    //Attack function for combat -> return the amount of damage done to the enemy
    public double attack(int i, Monster monster, Item item){               //"i" is for the action the player wants : refer to chooseAction()
        System.out.println(this.getName() + " acts !");
        System.out.println(this.status);
        double weaponDamage = this.containsWeapon();            //Getting the damage from the best weapon on the Player ; 0 if they don't own any
        this.statusWornOff();
        int STStatus = strengthStatus();
        if(STStatus > 0){                                       //If Strength + status -> add it to damage done
            return this.chooseAction(i, monster, item) * (this.getStrength() + weaponDamage)*(1 + STStatus/100);    //buff of strength
        }else if(STStatus < 0){                                 //If Strength - status -> remove it to damage done
            return this.chooseAction(i, monster,item) * (this.getStrength() + weaponDamage)*(1 - STStatus/100);    //debuff of strength
        }else{
            return this.chooseAction(i, monster,item) * (this.getStrength() + weaponDamage);                         //no buff or debuff                                                                    //attack with the flat value of strength
        }
    }

    //TODO destroy potions when used in fights -> is done correctly ?
    //Defense function for combat -> removes LF to the player
    public void defend(double enemyAttack){
        System.out.println(this.getName() + " defends!");
        //this.statusWornOff();                                   //At the beginning of the round, removes worn off effects from the Map
        this.isPoisoned();                                     //Takes damage from poison if is poisoned
        int DEStatus = defenseStatus();

        if(DEStatus > 0){
            this.setLP(this.getLP() - (enemyAttack - ((this.getDefense()) * (1 + DEStatus/100))));    //buff of defense
        } else if(DEStatus < 0){
            this.setLP(this.getLP() - (enemyAttack - ((this.getDefense()) * (1 - DEStatus/100))));    //debuff of defense
        } else{
            if(enemyAttack > this.getDefense()){                                                       //does damage only when the attack is superior to the def
                this.setLP(this.getLP()-(enemyAttack - this.getDefense()));
            }

        }
    }

    public void dodge(){
        double d = (this.getDefense()) * 10;
        for (String key : this.status.keySet()){
            if (key.contains("DE+")){ //if the player is under a potion that boost his defense
                d *= 1 + (Double.parseDouble(key.substring(3))/100); //we had a bonus of defense related to the player's status
            }else if (key.contains("DE-")){ //if the player is under a potion that decreases this defense
                d *= (1 - (Double.parseDouble(key.substring(3))/100)); //we had a bonus of defense related to the player's status
            }
        }
        Random rand = new Random();
        int num = rand.nextInt(101);
        System.out.println("num="+num);
        System.out.println("d="+d);
        if(num < d){
            System.out.println("dodge successful from the player !");
            setDodge(true);
        }
        else{
            System.out.println("failed to dodge from the player !");
            setDodge(false);
        }
    }

    //TODO : how to select the potion
    //Set the new status of the player depending on the potion used
    public void usePotion(Monster monster, Potion potion){
        System.out.println(this.getName() + " uses the potion" + potion);
        potion.setUsed(true);                                           //set used to true because potions are single use
        System.out.println(potion.getEffect().charAt(2));
        if(potion.getEffect().charAt(2) == '+'){           //if the potion is a bonus, looter applies to himself
            this.addStatus(potion.getEffect(), potion.getDuration());
        }else if(potion.getEffect().startsWith("LIFE")){
            int value = Integer.parseInt(potion.getEffect().substring(4));
            this.setLP(this.getLP() + value);


        }else if(potion.getEffect().charAt(2) == '-'){     //if the potion is a malus, looter applies it to the player
            monster.addStatus(potion.getEffect(), potion.getDuration());
        }else if (potion.getEffect().equals("DEATH")){
            monster.setLifePoints(0);
        }
        this.removeFromInventory(potion);                             //removing the potion from the inventory
    }

    //TODO to use items in fight
    /*public void useItem(Monster monster, Item item){
        item.setUsed(true);                                           //set used to true because potions are single use
        //Comportement item
        this.removeFromInventory(item);                             //removing the potion from the inventory
    }*/
    //endregion

    //region Move
    public void move(int x, int y) {
        if (x>=0 && x<=39 && y>=0 && y<=17) {
            if (world.CanGoThere(x,y)) {
                ArrayList<Instant> listInstants = world.IsThereInstant(x, y);
                ArrayList<Item> listItems = world.IsThereItem(x, y);
                for (Item i : listItems) {
                    this.addToInventory(i);
                    world.removeFromWorld(i);
                }
                for (Instant inst : listInstants) {
                    this.useInstant(inst);
                    world.removeFromWorld(inst);
                }
                this.setPosition(x, y);
                setNearByNPC(world.IsThereNPC(x, y));
                setNearByMonster(world.IsThereMonster(x, y));
                if (world.IsThereRiver(x, y)) {
                    swim(); //you die
                }
                if (world.IsThereTrap(x, y)) {
                    this.setLP(this.getLP() - 2);
                }
            }else{
                int indexWorld = world.IsThereDoorOpen(x,y);
                if (indexWorld!=-1){
                    setIndexWorld(indexWorld);
                }

                //TODO : call function change world
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
    public void swim(){
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
        }
    }
    //endregion
    //endregion

    //region Item functions


    public boolean canUsePotion(){
        if(!this.getInventory().isEmpty()){
            for (Item item : inventory) {
                if (item instanceof Potion) {
                    return true;
                }
            }
        }
        return false;
    }

    //endregion

    //region Instants
    public void useInstant(Instant instant){
        if(instant instanceof InstantHealth instH){
            if(this.getLP() + instH.getValue() > 10){
                this.setLP(10);
            }else{
                this.setLP(this.getLP() + instH.getValue());
            }
        }else if(instant instanceof InstantMoney instM){
            this.setMoney(this.getMoney()+instM.getValue());
        }
    }
    //endregion


}