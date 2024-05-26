package Classes.Item.NotConsumableItem;

import Classes.Item.Item;
import Classes.World.World;

//An item that can be read and sold
public class Book extends Item {

    //region Book's attribute
    private String text;
    //endregion

    //region Constructor
    public Book(World w, int x, int y, String name, int price, boolean dropped, String urlImage, String text) {
        super(w,x, y, name, price, dropped,urlImage);
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