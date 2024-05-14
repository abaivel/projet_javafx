package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import javafx.scene.layout.GridPane;

public class Book extends Item {
    private String title;
    private String text;

    public Book(GridPane g, String title, String text, int x, int y) {
        super(g,x, y);
        this.title = title;
        this.text = text;
    }

    public void read(){
        //faire apparaitre le livre et le texte avec getText
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}