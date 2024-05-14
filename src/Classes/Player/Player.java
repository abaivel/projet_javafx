package Classes.Player;

import Classes.GameObject;
import Classes.Item.Item;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

//TO DO : ItemInterface implementation to use the inventory here
public class Player extends GameObject {
    //region Player's attributes
    private double LP;
    private String name;
    private double money;
    private double strength;
    private double defense;
    private String status;
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
        this.status = "";                   //No status at first
        this.inventory = new Item[9];       //Inventory size is 9 slots max
    }
    //endregion

    //region Default constructor
    public Player(){
        this(null, 10,"Player",0,5,2,0,0);
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

    public String getStatus() {return this.status;}
    public void setStatus(String status) {this.status = status;}

    public Item[] getInventory() {return this.inventory;}
    public void setInventory(Item[] inventory) {this.inventory = inventory;}
    //endregion

    //region ToString function to print
    public String toString(){
        String tmp = "Name : " + this.getName() + "\nLP : " + this.getLP() + "\nMoney : " + this.getMoney() + "\nStrength : " + this.getStrength() + "\nDefense : " + this.getDefense() + "\nStatus : " + this.getStatus() + "\nPosition : " + this.getPosition() + "\n\n";
        for(int i = 0; i < inventory.length; i++){
            tmp += inventory[i].toString() + "\n";
        }
        return tmp;
    }
    //endregion

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

    //Attack function for combat -> return the amount of damage done to the ennemy
    //Do we need to add if(!this.failure)) at the beginning ???????
    public double attack(){
        if(this.getStatus().contains("ST+")){
            return this.getStrength()*(1 + (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else if(this.getStatus().contains("ST-")){
            return this.getStrength()*(1 - (Double.parseDouble(this.getStatus().substring(3))/100));    //we had a bonus of strength related to the player's status
        }else{
            return this.getStrength();                                                                                          //attack with the flat value of strength
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

    //TO DO
    public void useItem(){

    }

    //TO DO
    //Use item named buoy to not drown into rivers
    public void swim(){
        //search for the item
        //instant use of the item
        //displays message ?

    }

    //TO DO
    //to pick up items on the floor ? Need to walk on the cell that has an item in it
    //updates the inventory with adding the new item
    public void pickItem(Item item){
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

    public static void main(String[] args) {
        Player p = new Player();
        p.move("+y",0);
        System.out.println(p);

    }

}