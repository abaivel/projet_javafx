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
import Classes.Killable;
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
public class Player extends Killable {

    //region Player's attributes
    private final DoubleProperty money;
    private boolean dodge;
    private final IntegerProperty indexWorld;

    private final ObjectProperty<NPC> nearByNPC;

    private final ObjectProperty<Monster> nearByMonster;
    //endregion

    //region Constructor with all parameters
    public Player(World w, int x, int y, String name, int LP, int strength, int defense, double money, String urlImage) {
        super(w,x,y,name,new ArrayList<>(),LP,strength,defense);
        this.money = new SimpleDoubleProperty(money);
        this.nearByNPC=new SimpleObjectProperty<>(null);
        this.nearByMonster=new SimpleObjectProperty<>(null);
        this.dodge=false;
        this.indexWorld = new SimpleIntegerProperty(0);
        node=new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);

    }
    //endregion

    //region Getters and Setters

    public DoubleProperty getMoneyProperty() {return this.money;}
    public double getMoney() {return this.money.getValue();}

    public void setMoney(double money) {this.money.set(money);}
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
        String tmp = "Name : " + this.getName() + "\nLP : " + this.getLifePoints() + "\nMoney : " + this.getMoney() + "\nStrength : " + this.getStrength() + "\nDefense : " + this.getDefense() + "\nPosition : " + this.getPosition() + "\n";
        if(!this.getInventory().isEmpty()){
            tmp += "Inventory : ";
        }
        for(int i = 0; i < this.getInventory().size(); i++){
            tmp += "Item n°" + i + " : " + this.getInventory().get(i).toString() + "\n";
        }
        tmp += "Status :\n";
        for(String s : this.getStatus().keySet()){
            tmp += s + ":" + this.getStatus().get(s) + "rounds left";
        }
        return tmp;
    }
    //endregion

    //region Inventory functions
    public void addToInventory(Item item){
        if (this.getInventory().size()<10) {
            this.getInventory().add(item);
            item.setDropped(false);
            this.setSizeInventory(this.getSizeInventory()+1);
        }
    }

    public boolean contains(String item){
        for (Item value : this.getInventory()) {
            if (value.getName().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Key> containsKey() {
        ArrayList<Key> k = new ArrayList<>();     //list of keys to fill
        for (int i = 0; i < this.getInventory().size(); i++) {
            if (this.getInventory().get(i) instanceof Key) {
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
        System.out.println(this.getStatus());
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
            this.setLifePoints((int) (this.getLifePoints() - (enemyAttack - ((this.getDefense()) * (1 + DEStatus/100)))));    //buff of defense
        } else if(DEStatus < 0){
            this.setLifePoints((int) (this.getLifePoints() - (enemyAttack - ((this.getDefense()) * (1 - DEStatus/100)))));    //debuff of defense
        } else{
            if(enemyAttack > this.getDefense()){                                                       //does damage only when the attack is superior to the def
                this.setLifePoints((int) (this.getLifePoints()-(enemyAttack - this.getDefense())));
            }

        }
    }

    public void dodge(){
        double d = (this.getDefense()) * 10;
        for (String key : this.getStatus().keySet()){
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
        }else if(potion.getEffect().startsWith("LIFE+")){
            int value = Integer.parseInt(potion.getEffect().substring(5));
            this.setLifePoints(this.getLifePoints() + value);


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
                    this.setLifePoints(this.getLifePoints() - 2);
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
        for (Item item : this.getInventory()) {
            if (item instanceof Buoy) {
                swim = true;                            //Search for buoy
                break;
            }
        }
        System.out.println(swim);
        if(!swim){
            this.setLifePoints(0);
        }
    }
    //endregion
    //endregion

    //region Item functions


    public boolean canUsePotion(){
        if(!this.getInventory().isEmpty()){
            for (Item item : this.getInventory()) {
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
            if(this.getLifePoints() + instH.getValue() > 10){
                this.setLifePoints(10);
            }else{
                this.setLifePoints(this.getLifePoints() + instH.getValue());
            }
        }else if(instant instanceof InstantMoney instM){
            this.setMoney(this.getMoney()+instM.getValue());
        }
    }
    //endregion


}