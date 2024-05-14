package Classes.Item;

import Classes.GameObject;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

public abstract class Item extends GameObject {
    private String name;
    private int price;

    private boolean dropped;    //true : on the ground
                                //false : in an inventory
    private boolean used;       //true if used

    //region Constructor
    public Item(GridPane g, int x, int y, String name, boolean dropped, int price) {
        super(g, x, y);
        this.name = name;
        this.used = false;
        this.dropped = dropped;
        this.price = price;
    }
    //endregion

    //region Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public boolean isDropped() {return dropped;}
    public void setDropped(boolean dropped) {this.dropped = dropped;}
    public boolean isUsed() {
        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    //endregion

    //When an item is picked up by the player
    public void pickedUp(){
        setDropped(false);
    }


    public void appear(){
        setDropped(true);
    }
}
