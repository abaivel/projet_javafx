package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import javafx.scene.layout.GridPane;

public class Book extends Item {
    //region Book's attribute
    private String text;
    //endregion

    //region Constructor
    public Book(GridPane g, String name, boolean dropped, String text, int x, int y) {
        super(g,x, y, name, dropped);
        this.text = text;
    }
    //endregion

    public void read(){
        //faire apparaitre le livre et le texte avec getText
    }

    //region Getters and Setters
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    //endregion
}