package Classes.NPC;
import Classes.GameObject;
import Classes.Item.Item;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

public abstract class NPC extends GameObject {
    //NPC's attributes
    private String name;
    private double money;
    //private ItemInterface[] inventory;

    //Constructor with all parameters
    public NPC(GridPane g, String name, double money, int x, int y) {
        super(g,x,y);
        this.name = name;
        this.money = money;
        //this.inventory = inventory;
    }

    //Default constructor
    public NPC(String name) {
        this(null, "NPC",100,0,0);
    }

    //Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getMoney() {return money;}
    public void setMoney(double money) {this.money = money;}

    //public ItemInterface[] getInventory() {return this.inventory;}
    //public void setInventory(ItemInterface[] inventory) {this.inventory = inventory;}

    //Equals function
    //TO DO : add inventory part
    public boolean equals(Object o) {
        if (o instanceof NPC) {
            NPC n = (NPC) o;
            return this.getName().equals(n.getName()) && this.getMoney() == n.getMoney() && this.getPosition().equals(n.getPosition());
        }
        return false;
    }

    //ToString function to print
    //TO DO : add inventory part
    public String toString() {
        return "Name :" + this.getName() + "\nMoney :" + this.getMoney() + "\nPosition :" + this.getPosition();
    }





}