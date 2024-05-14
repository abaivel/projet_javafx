package Classes.Item;

import Classes.GameObject;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

public abstract class Item extends GameObject {
    private String name;
    private boolean dropped;    //true : on the ground
                                //false : in an inventory
    private boolean used;       //true if used

    //region Constructor
    public Item(GridPane g, int x, int y, String name, boolean dropped) {
        super(g, x, y);
        this.name = name;
        this.used = false;
        this.dropped = dropped;
    }
    //endregion

    //region Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    //endregion

    //When an item is picked up by the player
    public void pickedUp(){
        this.dropped = false;
    }


    public void appear(){
        this.dropped = true;
    }
}
