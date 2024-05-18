package Classes.Item.ConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import Classes.World.World;

import java.util.Objects;

public class Key extends Item {
    String color;
    public Key(World w, int x, int y, String name, boolean dropped, String color, int price, String urlImage) {
        super(w,x,y, name, dropped, price,urlImage);
        this.color=color;
    }
    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Key){
            return ((Key)o).getColor().equals(this.getColor());
        }
        return false;
    }
}