package Classes.Item.ConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class Key extends Item {
    String color;
    public Key(GridPane g, int x, int y, String name, boolean dropped, String color, int price) {
        super(g,x,y, name, dropped, price);
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