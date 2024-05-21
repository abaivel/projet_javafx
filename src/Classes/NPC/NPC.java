package Classes.NPC;
import Classes.GameObject;
import Classes.Item.Item;
import Classes.World.Position;
import Classes.World.World;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class NPC extends GameObject {
    //region NPC's attributes
    private String name;
    private double money;
    private ArrayList<Item> inventory;
    //endregion

    //region Constructor with all parameters
    public NPC(World w, String name, double money, int x, int y, String urlImage) {
        super(w,x,y);
        this.name = name;
        this.money = money;
        this.inventory = new ArrayList<>();
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
    //endregion

    //region Default constructor
    public NPC(String name) {
        this(null, "NPC",100,0,0,"");
    }
    //endregion

    //region Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getMoney() {return money;}
    public void setMoney(double money) {this.money = money;}

    public ArrayList<Item> getInventory() {return this.inventory;}
    public void setInventory(ArrayList<Item> inventory) {this.inventory = inventory;}
    //endregion

    //region Inventory functions
    public boolean addToInventory(Item item) {
        if (this.getInventory().size() < 10) {
            this.getInventory().add(item);
            return true;
        } else {
            System.out.println("Cannot add element. The inventory is full.");
            return false;
        }
    }

    public boolean inventoryIsFull() {
        return this.getInventory().size() == 10;
    }

    public void removeFromInventory(Item item){
        this.inventory.remove(item);
    }
    //endregion

    //region ToString function to print
    public String toString() {
        String tmp = "Name :" + this.getName() + "\nMoney :" + this.getMoney() + "\nPosition :" + this.getPosition();
        for(int i = 0; i < inventory.size(); i++){
            tmp += inventory.get(i).toString() + "\n";
        }
        return tmp;
    }
    //endregion





}