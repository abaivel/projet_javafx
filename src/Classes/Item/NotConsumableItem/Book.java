package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import javafx.scene.layout.GridPane;

public class Book extends Item {
    private String text;


    public Book(GridPane g, String name, boolean dropped, String text, int price, int x, int y) {
        super(g,x, y, name, dropped, price);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}