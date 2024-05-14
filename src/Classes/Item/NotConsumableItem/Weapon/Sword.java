package Classes.Item.NotConsumableItem.Weapon;
import javafx.scene.layout.GridPane;

public class Sword extends Weapon{
    public Sword(GridPane g, String name, boolean dropped, int x, int y, double damage, int price) {
        super(g, name, dropped,x, y,damage, price);
    }
}
