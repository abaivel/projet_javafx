package Classes.Item.NotConsumableItem.Weapon;
import Classes.World.World;

//A type of weapon
public class Sword extends Weapon{

    //region Constructor
    public Sword(World w, String name, boolean dropped, int x, int y, double damage, int price, String urlImage) {
        super(w, name, dropped,x, y,damage, price,urlImage);
    }
    //endregion
}
