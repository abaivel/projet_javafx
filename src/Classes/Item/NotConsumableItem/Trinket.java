package Classes.Item.NotConsumableItem;

import Classes.World.Position;
import Classes.Item.Item;
import javafx.scene.layout.GridPane;

public class Trinket extends Item{
    private int price;

    public Trinket(GridPane g, int price, int x, int y) {
        super(g,x,y);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}