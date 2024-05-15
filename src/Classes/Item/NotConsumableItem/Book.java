package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

public class Book extends Item {
    //region Book's attribute
    private String text;
    //endregion

    //region Constructor
    public Book(World w, String name, boolean dropped, String text, int x, int y) {
        super(w,x, y, name, dropped);
        this.text = text;
    }
    //endregion

    //region Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    //endregion
}