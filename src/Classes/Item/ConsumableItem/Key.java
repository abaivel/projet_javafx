package Classes.Item.ConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

//is used to open Door with the same color
//when linked key in inventory -> left click on door to open it
public class Key extends Item {

    //region Attributes
    String color;
    //endregion

    //region Constructor
    public Key(World w, int x, int y, String name, int price, boolean dropped, String urlImage, String color) {
        super(w,x,y, name, price, dropped,urlImage);
        this.color=color;
    }
    //endregion

    //region Getters
    public String getColor() {
        return color;
    }
    //endregion

    //region Equals
    @Override
    public boolean equals(Object o) {
        if (o instanceof Key){
            return ((Key)o).getColor().equals(this.getColor());
        }
        return false;
    }
    //endregion
}