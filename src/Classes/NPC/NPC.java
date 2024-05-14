package Classes.NPC;
import Classes.GameObject;
import Classes.Item.Item;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

public abstract class NPC extends GameObject {
    //region NPC's attributes
    private String name;
    private double money;
    private Item[] inventory;
    //endregion

    //region Constructor with all parameters
    public NPC(GridPane g, String name, double money, int x, int y) {
        super(g,x,y);
        this.name = name;
        this.money = money;
        this.inventory = new Item[9];
    }
    //endregion

    //region Default constructor
    public NPC(String name) {
        this(null, "NPC",100,0,0);
    }
    //endregion

    //region Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getMoney() {return money;}
    public void setMoney(double money) {this.money = money;}

    public Item[] getInventory() {return this.inventory;}
    public void setInventory(Item[] inventory) {this.inventory = inventory;}
    //endregion

    //region ToString function to print
    public String toString() {
        String tmp = "Name :" + this.getName() + "\nMoney :" + this.getMoney() + "\nPosition :" + this.getPosition();
        for(int i = 0; i < inventory.length; i++){
            tmp += inventory[i].toString() + "\n";
        }
        return tmp;
    }
    //endregion





}