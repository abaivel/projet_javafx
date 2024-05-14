package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import javafx.scene.layout.GridPane;

public class Book extends Item {
    private String text;


    public Book(GridPane g, String title, String name, boolean dropped, String text, int x, int y) {
        super(g,x, y, name, dropped);
        this.text = text;
    }

    public void read(){
        //faire apparaitre le livre et le texte avec getText
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}