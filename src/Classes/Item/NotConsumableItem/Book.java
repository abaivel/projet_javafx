package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

public class Book extends Item {
    private String text;


    public Book(World w, String name, boolean dropped, String text, int price, int x, int y) {
        super(w,x, y, name, dropped, price);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}